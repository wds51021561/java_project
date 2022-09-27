package com.javasm.domin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.javasm.domin.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("base_category")
public class Category extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;



    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类父id 如果是顶级分类 父id 为0
     */
    private Long parentId;

    /**
     * 分类等级 一共三级分类 1 2 3 
     */
    private Integer categoryLevel;




}
