package com.javasm.domin.vo;

import com.javasm.domin.vo.base.BaseVo;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodVo extends BaseVo {
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

    /**
     * 另一个表的商品名称
     */
    private String brandName;
}
