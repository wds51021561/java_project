package com.javasm.controller;

import com.javasm.common.http.AxiosResult;
import com.javasm.common.page.PageResult;
import com.javasm.common.perm.HasPerm;
import com.javasm.controller.base.BaseController;
import com.javasm.domin.criteria.RoleCriteria;
import com.javasm.domin.entity.Role;
import com.javasm.domin.vo.RoleVo;

import com.javasm.service.RoleService;

import com.javasm.transfer.RoleTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("role")
@RequiredArgsConstructor//和@Autowired效果一样
public class RoleController extends BaseController {


    private final RoleService roleService;
    private final RoleTransfer roleTransfer;


    /**
     * 分页条件查询
     */
    @GetMapping
    public AxiosResult<PageResult<RoleVo>> list(RoleCriteria RoleCriteria){
        //分页条件查询
        return AxiosResult.success(roleService.searchPage(RoleCriteria));

    }

    /**
     * 查询所有的角色
     */
    @GetMapping("findAll")
    public AxiosResult<List<RoleVo>> findAll(){
        //查询所有的品牌
        List<Role> list = roleService.list();
        List<RoleVo> RoleVos = roleTransfer.toVO(list);
        return AxiosResult.success(RoleVos);

    }

    /**
     * 根据id查询
     */
    @GetMapping("{id}")
    public AxiosResult<Role> findById(@PathVariable Long id){
        Role byId = roleService.getById(id);
        return AxiosResult.success(byId);
    }
    /**
     *添加
     */
    @PostMapping
    @HasPerm(perm = "role:add")
    public AxiosResult<Void> add(@RequestBody  Role Role){
        return toAxios(roleService.save(Role));
    }

    /**
     * 修改
     */
    @PutMapping
    @HasPerm(perm = "role:edit")
    public AxiosResult<Void> update(@RequestBody  Role Role){
        return toAxios(roleService.update(Role));
    }

    /**
     * 删除
     */
    @DeleteMapping("{id}")
    @HasPerm(perm = "role:delete")
    public AxiosResult<Void> delete(@PathVariable  Long id){
        return toAxios(roleService.deleteById(id));
    }


    /**
     * 批量删除
     */

    @DeleteMapping("batch/{ids}")
    @HasPerm(perm = "role:batch")
    public AxiosResult<Void> batchDelete(@PathVariable List<Long> ids){
        return toAxios(roleService.batchDeleteByIds(ids));
    }

    /**
     * 给用户赋予权限
     */

    @PutMapping("{roleId}/menu")
    public AxiosResult<Void> setRoleMenu(@PathVariable Long roleId, @RequestBody List<Long> menuIds) {
        //拿到当前角色 id 以及全选的数组
        int row = roleService.setRoleMenu(roleId, menuIds);
        return toAxios(row);
    }

    /**
     * 获取角色的权限（不全的） 只用于前端展示而已
     */

    @GetMapping("{roleId}/treeMenu")
    public AxiosResult<List<Long>>getRoleTreeMenuForShow(@PathVariable Long roleId) {
        List<Long> list = roleService.getRoleTreeMenuForShow(roleId);
        return AxiosResult.success(list);
    }

}
