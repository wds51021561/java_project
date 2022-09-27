package com.javasm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javasm.common.page.PageResult;
import com.javasm.domin.criteria.MenuCriteria;
import com.javasm.domin.entity.AdminRole;
import com.javasm.domin.entity.Menu;
import com.javasm.domin.entity.RoleMenu;
import com.javasm.domin.vo.MenuVo;
import com.javasm.mapper.AdminRoleMapper;
import com.javasm.mapper.MenuMapper;
import com.javasm.mapper.RoleMenuMapper;
import com.javasm.service.MenuService;
import com.javasm.service.base.impl.BaseServiceImpl;
import com.javasm.transfer.MenuTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements MenuService {

    private final MenuTransfer menuTransfer;
    private final AdminRoleMapper adminRoleMapper;


    private final RoleMenuMapper roleMenuMapper;


    private final MenuMapper menuMapper;

    /**
     *获得所有的菜单  分页情况
     */
    @Override
    public PageResult<MenuVo> getMenuTree(MenuCriteria menuCriteria) {
        //分页开启
        PageHelper.startPage(menuCriteria.getCurrentPage(), menuCriteria.getPageSize());
        //条件查询拿到数据
        List<Menu> root = this.search(new QueryWrapper<Menu>().lambda().eq(Menu::getParentId, 0));
        //放到PageInfo
        PageInfo<Menu> pageInfo = new PageInfo<>(root);
        //转换vo
        List<MenuVo> rootVo = menuTransfer.toVO(root);
        //进行排序
        Collections.sort(rootVo, (t, t1) -> t.getMenuSort() - t1.getMenuSort());
        // 条件查询 并找孩子  找二级数据
        // ne 条件getParentId 不等于 0的数据
        List<Menu> other = this.search(new QueryWrapper<Menu>().lambda().ne(Menu::getParentId, 0));
        List<MenuVo> otherVo = menuTransfer.toVO(other);
        //在不等于 0 的一级数据中找孩子
        rootVo.forEach(item -> getChildren(item, otherVo));
        return new PageResult<MenuVo>(pageInfo.getTotal(), rootVo);





    }

    /**
     *获得所有的菜单  无分页
     */
    @Override
    public List<MenuVo> getAllMenuTree() {
        //获得当前的所有信息
        List<Menu> list = this.list();
        //根据sorted进行排序
        List<Menu> root = list.stream().filter(item -> item.getParentId().longValue() == 0).sorted((t, t1) -> t.getMenuSort() - t1.getMenuSort()).collect(Collectors.toList());
        //删除一个集合中 存在另一个集合中的元素的数据
        list.removeAll(root);
        //转换
        List<MenuVo> menuVos = menuTransfer.toVO(list);
        List<MenuVo> rootVo = menuTransfer.toVO(root);
        //找二级 数据 并且遍历一层删一层
        rootVo.forEach(item->getChildren(item,menuVos));
        return rootVo;

    }

    /**
     * 中间表 来拿id
     * @param adminId
     * @return
     */
    @Override
    public List<Menu> getMenusByAdminId(Long adminId) {
        //根据员工表找到对应的角色id
        List<Long> roleIds = adminRoleMapper.selectList(new QueryWrapper<AdminRole>().lambda().eq(AdminRole::getAdminId, adminId)).stream().map(AdminRole::getRoleId).collect(Collectors.toList());
        //根据角色id拿到对应的权限id
        Set<Long> menuIds = roleMenuMapper.selectList(new QueryWrapper<RoleMenu>().lambda().in(RoleMenu::getRoleId, roleIds)).stream().map(RoleMenu::getMenuId).collect(Collectors.toSet());
        //根据权限ids 封装到对象 返回
        List<Menu> menus = menuMapper.selectBatchIds(menuIds);
        return menus;
    }


    /**
     * 找孩子
     */
    public void getChildren(MenuVo menuVo, List<MenuVo> list) {
        List<MenuVo> second = list.stream().filter(item -> item.getParentId().longValue() == menuVo.getId().longValue()).sorted((t, t1) -> t.getMenuSort() - t1.getMenuSort()).collect(Collectors.toList());
        if (second != null && second.size() > 0) {
            menuVo.setChildren(second);
            list.removeAll(second);
            second.forEach(item -> {
                getChildren(item, list);
            });

        }

    }
}
