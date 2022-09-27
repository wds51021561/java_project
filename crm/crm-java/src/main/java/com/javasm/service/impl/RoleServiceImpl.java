package com.javasm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javasm.common.page.PageResult;
import com.javasm.domin.criteria.RoleCriteria;
import com.javasm.domin.entity.Role;
import com.javasm.domin.entity.Menu;
import com.javasm.domin.entity.RoleMenu;
import com.javasm.domin.vo.RoleVo;
import com.javasm.mapper.AdminRoleMapper;
import com.javasm.mapper.MenuMapper;
import com.javasm.mapper.RoleMapper;
import com.javasm.mapper.RoleMenuMapper;
import com.javasm.service.RoleService;
import com.javasm.service.base.impl.BaseServiceImpl;
import com.javasm.transfer.RoleTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {



    private final RoleMapper roleMapper;
    private final RoleTransfer roleTransfer;//转换entity 和vo对象
    private final MenuMapper menuMapper;

    private final AdminRoleMapper adminRoleMapper;

    private final RoleMenuMapper roleMenuMapper;


    
    
    /**
     *角色分页查询
     */
    @Override
    public PageResult<RoleVo> searchPage(RoleCriteria roleCriteria) {
        //开启分页
        PageHelper.startPage(roleCriteria.getCurrentPage(), roleCriteria.getPageSize());
        //条件创建
        LambdaQueryWrapper<Role> lambda = new QueryWrapper<Role>().lambda();
        //判断角色名称
        if (!StringUtils.isEmpty(roleCriteria.getRoleName())) {
            lambda.like(Role::getRoleName, roleCriteria.getRoleName());
        }
        //判断开始时间以及结束时间是否为空
        LocalDateTime startTime = roleCriteria.getStartTime();
        LocalDateTime endTime = roleCriteria.getEndTime();
        if (startTime != null && endTime != null) {
            //根据between进行判断
            lambda.between(Role::getCreateTime, startTime, endTime);
        }
        List<Role> Roles = roleMapper.selectList(lambda);
        /*获取总条数*/
        PageInfo<Role> pageInfo = new PageInfo<>(Roles);
        /*返回总条数以及 转换成Vo对象*/
        return new PageResult<RoleVo>(pageInfo.getTotal(), roleTransfer.toVO(Roles));
    }


    @Override
    public RoleVo findById(Long id) {
        return roleTransfer.toVO(getById(id));
    }




    /**
     * 给角色赋予权限
     * @param roleId
     * @param menuIds
     * @return
     */
    @Override
    public int setRoleMenu(Long roleId, List<Long> menuIds) {
        //首先删除其中的权限
        roleMenuMapper.delete(new UpdateWrapper<RoleMenu>().lambda().eq(RoleMenu::getRoleId, roleId));
        //设置权限的关系表
        RoleMenu roleMenu = new RoleMenu();
        //设置角色中的权限id
        roleMenu.setRoleId(roleId);
        //遍历拿到的权限数组  根据权限的ids 对角色进行添加
        menuIds.forEach(menuId -> {
            //对角色添加权限
            roleMenu.setMenuId(menuId);
            //添加
            roleMenuMapper.insert(roleMenu);
        });
        //返回
        return 1;
    }



    /**
     * 获取角色的权限（不全的） 只用于前端展示而已
     */
    @Override
    public List<Long> getRoleTreeMenuForShow(Long roleId) {
        List<Long> list = new ArrayList<>();
        //通过角色 拿到当前说对应的所有权限
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(new QueryWrapper<RoleMenu>().lambda().eq(RoleMenu::getRoleId, roleId));
        //获得所有的menuIds
        List<Long> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        if (menuIds.size() > 0) {
            List<Menu> menus = menuMapper.selectBatchIds(menuIds);
            //实在不会写就这样写
            //获得所有的按钮级别
            List<Menu> menuAndBtnList = menus.stream().filter(menu -> menu.getMenuType() != 0).collect(Collectors.toList());

            menuAndBtnList.forEach(menu -> {
                if (!menuAndBtnList.stream().anyMatch(menu1 -> menu.getId().equals(menu1.getParentId()))) {
                    list.add(menu.getId());
                }
            });


        }
        return list;
    }


}
