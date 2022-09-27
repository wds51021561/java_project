package com.javasm.service;

import com.javasm.common.page.PageResult;
import com.javasm.domin.criteria.AdminCriteria;
import com.javasm.domin.entity.Admin;
import com.javasm.domin.vo.AdminVo;
import com.javasm.service.base.BaseService;

import java.util.Map;

public interface AdminService extends BaseService<Admin> {

    /**
     * 分页条件查询
     */
    PageResult<AdminVo> searchPage(AdminCriteria adminCriteria);


    /**
     * 添加员工 并添加角色
     */
    int saveAdminAndRoles(Admin admin);

    /**
     * 修改 获取角色
     */
    Admin getAdminAndRoleIdsById(Long id);



    /**
     * 更新用户和用户的角色
     * @param admin
     * @return
     */
    int updateAdminAndRoles(Admin admin);

    /**
     * 登录功能 通过过用户名查询用户
     */
    Admin doLogin(String username);
    /**
     * 获得用户信息
     * 菜单 本身信息 按钮级别权限
     *
     * @return
     */
    Map<String, Object> getAdminInfo();
}
