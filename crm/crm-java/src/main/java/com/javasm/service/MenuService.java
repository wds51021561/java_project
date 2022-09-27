package com.javasm.service;

import com.javasm.common.page.PageResult;
import com.javasm.domin.criteria.MenuCriteria;
import com.javasm.domin.entity.Menu;
import com.javasm.domin.vo.MenuVo;
import com.javasm.service.base.BaseService;

import java.util.List;

public interface MenuService extends BaseService<Menu> {

    /**
     * 获得所有的菜单通过树型展示  分页
     * @return
     */
    PageResult<MenuVo> getMenuTree(MenuCriteria menuCriteria);


    /**
     * 不分页的情况
     * @return
     */
    List<MenuVo> getAllMenuTree();

    /**
     * 根据员工id拿到中间表的id
     * @param adminId
     * @return
     */
    List<Menu> getMenusByAdminId(Long adminId);
}
