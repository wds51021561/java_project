package com.javasm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javasm.common.page.PageResult;
import com.javasm.common.util.TokenService;
import com.javasm.common.util.TreeUtils;
import com.javasm.domin.criteria.AdminCriteria;
import com.javasm.domin.entity.Admin;
import com.javasm.domin.entity.AdminRole;
import com.javasm.domin.entity.LoginAdmin;
import com.javasm.domin.entity.Menu;
import com.javasm.domin.vo.AdminVo;
import com.javasm.domin.vo.MenuVo;
import com.javasm.mapper.AdminMapper;
import com.javasm.mapper.AdminRoleMapper;
import com.javasm.mapper.MenuMapper;
import com.javasm.service.AdminService;
import com.javasm.service.MenuService;
import com.javasm.service.base.impl.BaseServiceImpl;
import com.javasm.transfer.AdminTransfer;
import com.javasm.transfer.MenuTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

    private final AdminMapper adminMapper;
    private final AdminTransfer adminTransfer;
    private final AdminRoleMapper adminRoleMapper;
    private final TokenService tokenService;
    private  final MenuService menuService;
    private final MenuMapper menuMapper;
    private final MenuTransfer menuTransfer;
    /**
     * 分页条件查询
     */
    @Override
    public PageResult<AdminVo> searchPage(AdminCriteria adminCriteria) {
        PageHelper.startPage(adminCriteria.getCurrentPage(), adminCriteria.getPageSize());
        LambdaQueryWrapper<Admin> lambda = new QueryWrapper<Admin>().lambda();

        if (!StringUtils.isEmpty(adminCriteria.getAdminName())) {
            lambda.like(Admin::getAdminName, adminCriteria.getAdminName());
        }
        if (!StringUtils.isEmpty(adminCriteria.getAdminPhone())) {
            lambda.eq(Admin::getAdminPhone, adminCriteria.getAdminPhone());
        }

        if (adminCriteria.getDeptId() != null && adminCriteria.getDeptId() != 0) {
            lambda.eq(Admin::getDeptId, adminCriteria.getDeptId());
        }
        if (!StringUtils.isEmpty(adminCriteria.getStartTime())) {
            lambda.between(Admin::getCreateTime, adminCriteria.getStartTime(), adminCriteria.getEndTime());
        }
        List<Admin> admins = adminMapper.selectList(lambda);
        PageInfo<Admin> pageInfo = new PageInfo<>(admins);
        List<AdminVo> adminVos = adminTransfer.setSex(admins);
        return new PageResult<AdminVo>(pageInfo.getTotal(), adminVos);
    }


    /**
     * 添加员工 并添加角色
     */
    @Override
    public int saveAdminAndRoles(Admin admin) {
        //保存员工信息
        int save = this.save(admin);
        //拿到角色id
        Set<Long> roleIds = admin.getRoleIds();
        //添加角色 拿到实体类
        roleIds.forEach(roleId->{
            //添加
            adminRoleMapper.insert(new AdminRole(admin.getId(),roleId));
        });
        return save;

    }

    /**
     * 修改 获取角色 已经员工信息  根据id查询
     */
    @Override
    public Admin getAdminAndRoleIdsById(Long id) {
        //根据id拿到当前员工信息
        Admin byId = this.getById(id);
        //根据员工信息 拿到当前对应的角色信息
        List<AdminRole> adminRoles = adminRoleMapper.selectList(new QueryWrapper<AdminRole>().lambda().eq(AdminRole::getAdminId, id));
        //拿到角色信息并赋值 过去 返回
        byId.setRoleIds(adminRoles.stream().map(AdminRole::getRoleId).collect(Collectors.toSet()));
        return byId;
    }

    /**
     * 更新用户和用户的角色
     */
    @Override
    public int updateAdminAndRoles(Admin admin) {
        //先删除当前员工说对应的所有角色
        adminRoleMapper.delete(new UpdateWrapper<AdminRole>().lambda().eq(AdminRole::getAdminId, admin.getId()));
        //添加最新的角色信息
        admin.getRoleIds().forEach(roleId -> adminRoleMapper.insert(new AdminRole(admin.getId(), roleId)));
        //修改
        return this.update(admin);
    }

    /**
     * 登录功能  通过用户名查询用户
     * @param username
     * @return
     */
    @Override
    public Admin doLogin(String username) {
//        return adminMapper.selectOne(new QueryWrapper<Admin>().lambda().eq(Admin::getAdminName, username));
        List<Admin> admins = adminMapper.selectList(new QueryWrapper<Admin>().lambda().eq(Admin::getAdminName, username));
        if (admins.isEmpty()) {
            return null;
        }
        return admins.get(0);
    }

    /**
     * 获得用户信息
     * 菜单 本身信息 按钮级别权限
     */
    @Override
    public Map<String, Object> getAdminInfo() {
        long startTime = System.currentTimeMillis();

        Admin admin = tokenService.getLoginAdmin().getAdmin();
        Map<String, Object> map = new HashMap<>();
        map.put("admin", admin);
        List<Menu> list = null;
        //拿菜单
        if (tokenService.isAdmin()) {
            list = menuMapper.selectList(null);
            System.out.println("所有的权限："+list);
        } else {
            list = menuService.getMenusByAdminId(tokenService.getAdminId());
        }
        LoginAdmin loginAdmin = tokenService.getLoginAdmin();
        loginAdmin.setPerms(list);
        //缓存当前信息到redis中
        tokenService.setLoginAdmin(loginAdmin);
        //获取所有的菜单权限后 需要去除按钮级别权限
        List<Menu> collect = list.stream().filter(menu -> menu.getMenuType() != 2).collect(Collectors.toList());
        System.out.println("菜单列表"+collect);
        List<MenuVo> menuVos = menuTransfer.toVO(collect);
        System.out.println("vo列表"+menuVos);
        List<MenuVo> menuVos1 = TreeUtils.bulidTree(menuVos);
        System.out.println("tree列表"+menuVos1);
        map.put("hjmenuList", menuVos1);
        //按钮级别
        List<Menu> btnPerm = list.stream().filter(menu -> menu.getMenuType() != 0).collect(Collectors.toList());
        map.put("hjbtnPerm", btnPerm);
        long endTime = System.currentTimeMillis();
        System.out.println("查询用户详情的用时时间:"+ (endTime-startTime));
        return map;
    }


}
