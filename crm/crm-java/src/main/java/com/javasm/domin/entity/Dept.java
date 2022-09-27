package com.javasm.domin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.javasm.domin.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("base_dept")
public class Dept extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;



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




}
