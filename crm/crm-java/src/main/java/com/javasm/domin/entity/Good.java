package com.javasm.domin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.javasm.domin.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("base_good")
public class Good extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;



    /**
     * 商品名称
     */
    private String goodName;

    /**
     * 商品价格
     */
    private BigDecimal goodPrice;

    /**
     * 商品描述
     */
    private String goodDesc;

    /**
     * 商品页面静态化使用的 
     */
    private String goodContent;

    /**
     * 商品图片
     */
    private String goodImg;

    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 一级分类ID
     */
    private Long firstCategoryId;

    /**
     * 二级分类ID
     */
    private Long secondCategoryId;

    /**
     * 三级分类id
     */
    private Long threeCategoryId;




}
