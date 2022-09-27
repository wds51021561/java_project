package com.javasm.service;

import com.javasm.common.page.PageResult;
import com.javasm.domin.criteria.RoleCriteria;
import com.javasm.domin.entity.Role;
import com.javasm.domin.vo.RoleVo;
import com.javasm.service.base.BaseService;

import java.util.List;

public interface RoleService extends BaseService<Role> {

    /**
     *角色分页查询
     */
    PageResult<RoleVo> searchPage(RoleCriteria roleCriteria);

    RoleVo findById(Long id);


    /**
     * 给角色赋予权限
     * @param roleId
     * @param menuIds
     * @return
     */
    int setRoleMenu(Long roleId, List<Long> menuIds);

    /**
     * 获取角色的权限（不全的） 只用于前端展示而已
     */

    List<Long> getRoleTreeMenuForShow(Long roleId);
}
