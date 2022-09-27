package com.javasm.transfer;


import com.javasm.domin.entity.Dept;
import com.javasm.domin.vo.DeptVo;
import com.javasm.mapper.DeptMapper;
import com.javasm.transfer.base.BaseTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class DeptTransfer extends BaseTransfer<Dept, DeptVo> {


    private final DeptMapper deptMapper;

    @Override
    public List<DeptVo> toVO(List<Dept> list) {
        List<DeptVo> deptVos = super.toVO(list);
        deptVos.forEach(deptVo -> {
            int childrenCount = deptMapper.getChildrenCount(deptVo.getId());
            deptVo.setHasChildren(childrenCount > 0);
            deptVo.setIsLeaf(childrenCount <= 0);
        });
        return deptVos;
    }


    /**
     *拿到所有的
     */
    public List<DeptVo> addChildrenProperties(List<Dept> list) {
        List<DeptVo> deptVos = super.toVO(list);
        return deptVos;
    }
}
