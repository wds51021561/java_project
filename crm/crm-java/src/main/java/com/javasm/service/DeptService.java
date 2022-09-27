package com.javasm.service;

import com.javasm.common.page.PageResult;
import com.javasm.domin.criteria.DeptCriteria;
import com.javasm.domin.entity.Dept;
import com.javasm.domin.vo.DeptVo;
import com.javasm.service.base.BaseService;

import java.util.List;

public interface DeptService extends BaseService<Dept> {
    /**
     * 查询所有部门（Tree的方式 ）
     * @return
     */
    List<DeptVo> getDeptTree();
    /**
     * 查询所有部门 通过id找孩子（懒加载方式 ）
     * @return
     */
    List<DeptVo> getChildrenById(long id);

    /**
     * 分页条件查询
     *
     * @param deptCriteria
     * @return
     */
    PageResult<DeptVo> searchPage(DeptCriteria deptCriteria);

    /**
     *员工修改回显 部门
     */
    List<DeptVo> getDeptVoTree(Long id, List<Dept> list);


    /**
     * 级联递归删除
     *
     * @param id
     * @return
     */
    int deleteSelfAndChildren(Long id);


    /**
     * 通过一个部门父id 找到同级以及上一级 上上一级...
     *
     * @param parentId
     * @return
     */
    public List<DeptVo> getSuperByParent(Long parentId, List<DeptVo> list);
}
