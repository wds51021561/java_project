package com.javasm.domin.vo;


import com.javasm.domin.vo.base.BaseVo;
import lombok.Data;

import java.util.List;


@Data

public class DeptVo extends BaseVo {

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门的上级部门id 如果是一级部门 则父id为0
     */
    private Long parentId;

    /**
     * 部门排序
     */
    private Integer deptSort;

    /**
     * 部门描述
     */
    private String deptDesc;


    /**
     * 是否有孩子
     */
    Boolean hasChildren;

    /**
     * 孩子 树状结构
     */
    List<DeptVo> children;

    /**
     * 是否是叶子节点  结果和hasChildren相反的值
     */

    Boolean isLeaf;


}
