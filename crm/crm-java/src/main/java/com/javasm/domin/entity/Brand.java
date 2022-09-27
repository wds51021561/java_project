package com.javasm.domin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.javasm.domin.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("base_brand")
public class Brand extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;



    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 品牌描述
     */
    private String brandDesc;

    /**
     * 品牌LOGO
     */
    private String brandLogo;

    /**
     * 品牌官网
     */
    private String brandSite;




}
