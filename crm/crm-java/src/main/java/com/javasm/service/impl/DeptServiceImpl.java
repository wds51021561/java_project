package com.javasm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javasm.common.page.PageResult;
import com.javasm.common.util.TreeUtils;
import com.javasm.domin.criteria.DeptCriteria;
import com.javasm.domin.entity.Dept;
import com.javasm.domin.vo.DeptVo;
import com.javasm.mapper.DeptMapper;
import com.javasm.service.DeptService;
import com.javasm.service.base.impl.BaseServiceImpl;
import com.javasm.transfer.DeptTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DeptServiceImpl extends BaseServiceImpl<Dept> implements DeptService {

    private final DeptMapper deptMapper;


    private final DeptTransfer deptTransfer;

    /**
     * 分页条件查询
     *
     * @param deptCriteria
     * @return
     */
    @Override
    public PageResult<DeptVo> searchPage(DeptCriteria deptCriteria) {
        PageHelper.startPage(deptCriteria.getCurrentPage(), deptCriteria.getPageSize());
        LambdaQueryWrapper<Dept> lambda = new QueryWrapper<Dept>().lambda();
        if (deptCriteria.isQuery()) {
            //        如果是查询 查询的不一定是第一级数据 所以不能拼接一级的条件
            if (!StringUtils.isEmpty(deptCriteria.getDeptName())) {
                lambda.like(Dept::getDeptName, deptCriteria.getDeptName());
            }
            if (!StringUtils.isEmpty(deptCriteria.getStartTime()) && !StringUtils.isEmpty(deptCriteria.getEndTime())) {
                lambda.between(Dept::getCreateTime, deptCriteria.getStartTime(), deptCriteria.getEndTime());
            }
        } else {
            //如果不是条件查询 则查询的是第一级
            lambda.eq(Dept::getParentId, 0).orderByAsc(Dept::getDeptSort);
        }
        List<Dept> depts = deptMapper.selectList(lambda);
        PageInfo<Dept> pageInfo = new PageInfo<>(depts);
        return new PageResult<DeptVo>(pageInfo.getTotal(), deptTransfer.toVO(depts));
    }

    /**
     * 获得所有的部门 （Tree方式）
     */
    @Override
    public List<DeptVo> getDeptTree() {
        List<Dept> list = this.list();
        List<DeptVo> deptVos = deptTransfer.addChildrenProperties(list);
        List<DeptVo> deptVos1 = TreeUtils.bulidTree(deptVos);
        return deptVos1;

    }

    /**
     * 拿到所有的部门 （懒加载方式） 通过id找孩子
     */
    @Override
    public List<DeptVo> getChildrenById(long id) {
        List<Dept> depts = deptMapper.selectList(new QueryWrapper<Dept>().lambda().eq(Dept::getParentId, id).orderByAsc(Dept::getDeptSort));
        return deptTransfer.toVO(depts);
    }

    /**
     * 员工修改回显部门
     */
    @Override
    public List<DeptVo> getDeptVoTree(Long id, List<Dept> list) {
        //获得部门id
        Dept dept = getById(id);
        //获得父id
        Long parentId = dept.getParentId();
        //拿到所有的一级部门
        List<Dept> depts = deptMapper.selectList(new QueryWrapper<Dept>().lambda().eq(Dept::getParentId, parentId));
        list.addAll(depts);
        //如果父id ==0
        if (parentId==0){
            //转换成vo
            return deptTransfer.toVO(list);
        }else {
            //如果不等于0 继续调用
            return getDeptVoTree(parentId,list);
        }

    }

    /**
     * 级联递归删除
     *
     * @param id
     * @return
     */
    @Override
    public int deleteSelfAndChildren(Long id) {
        //递归找到所有要删除的id
        List<Long> deleteIds = new ArrayList<>();
        deleteIds.add(id);
        setChildrenId(deleteIds, id);
        return batchDeleteByIds(deleteIds);
    }

    @Override
    public List<DeptVo> getSuperByParent(Long parentId, List<DeptVo> list) {
        //如果查询的就是一级数据 则直接返回一级即可
        if (parentId.longValue() == 0) {
            list.addAll(this.getChildrenById(0L));
            return list;
        } else {
            list.addAll(this.getChildrenById(parentId));
            return getSuperByParent(getById(parentId).getParentId(), list);
        }
    }

    /**
     * 递归封装要删除的孩子
     * @param ids
     * @param id
     */
    private void setChildrenId(List<Long> ids, Long id) {
        List<DeptVo> children = getChildrenById(id);
        children.forEach(item -> {
            ids.add(item.getId());
            setChildrenId(ids, item.getId());
        });
    }
}
