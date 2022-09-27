package com.javasm.controller;

import com.javasm.common.http.AxiosResult;
import com.javasm.common.page.PageResult;
import com.javasm.common.perm.HasPerm;
import com.javasm.common.util.TreeUtils;
import com.javasm.controller.base.BaseController;
import com.javasm.domin.criteria.DeptCriteria;
import com.javasm.domin.entity.Dept;

import com.javasm.domin.vo.DeptVo;
import com.javasm.service.DeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("dept")
@RequiredArgsConstructor//和@Autowired效果一样
public class DeptController extends BaseController {


    private final DeptService deptService;

    /**
     * 查询所有
     */
    @GetMapping
    public AxiosResult<PageResult<DeptVo>> list(DeptCriteria deptCriteria) {
        PageResult<DeptVo> pageResult = deptService.searchPage(deptCriteria);
        return AxiosResult.success(pageResult);
    }

    /**
     * 根据id查询
     */
    @GetMapping("{id}")
    public AxiosResult<Map<String, Object>> findById(@PathVariable Long id) {
        Dept byId = deptService.getById(id);
        List<DeptVo> parents = deptService.getSuperByParent(byId.getParentId(), new ArrayList<>());
        //构建Tree
        List<DeptVo> deptVos = TreeUtils.bulidTree(parents);
        Map<String, Object> map = new HashMap<>();
        map.put("obj", byId);
        map.put("elements", deptVos);
        return AxiosResult.success(map);
    }

    /**
     * 添加
     */
    @PostMapping
    @HasPerm(perm = "dept:add")
    public AxiosResult<Void> add(@RequestBody Dept Dept) {
        return toAxios(deptService.save(Dept));
    }

    /**
     * 修改
     */
    @PutMapping
    @HasPerm(perm = "dept:edit")
    public AxiosResult<Void> update(@RequestBody Dept Dept) {
        return toAxios(deptService.update(Dept));
    }

    /**
     * 删除
     */
    @DeleteMapping("{id}")
    @HasPerm(perm = "dept:delete")
    public AxiosResult<Void> deleteById(@PathVariable Long id) {
        return toAxios(deptService.deleteSelfAndChildren(id));
    }


    /**
     * 拿到所有的部门  （Tree 的方式）
     * @return
     */
    @GetMapping("tree")
    public AxiosResult<List<DeptVo>> getDeptTree() {
        List<DeptVo> list = deptService.getDeptTree();
        return AxiosResult.success(list);
    }

    /**
     * 拿到所有的部门 （懒加载）  通过id找孩子
     * @return
     */
    @GetMapping("{id}/children")
    public AxiosResult<List<DeptVo>> getChildrenById(@PathVariable long id) {
        return AxiosResult.success(deptService.getChildrenById(id));
    }



    @GetMapping("findParentByDeptId/{id}")
    public AxiosResult<List<DeptVo>> findParentById(@PathVariable Long id) {
        List<DeptVo> list = deptService.getDeptVoTree(id, new ArrayList<>());
        List<DeptVo> deptVos = TreeUtils.bulidTree(list);
        return AxiosResult.success(deptVos);
    }

}
