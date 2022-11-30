package com.itheima.pind.goods.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.itheima.pinda.base.entity.Entity;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pd_goods_info")
public class GoodsInfo extends Entity<Long> {
    private static final long serialVersionUID = 1L;
    /**
     * 商品代码
     */
    private String code;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 国条码
     */
    private String barCode;
    /**
     * 品牌表id
     */
    private Integer brandId;
    /**
     * 商品一级分类id
     */
    private Integer oneCategoryId;
    /**
     * 商品二级分类id
     */
    private Integer towCategoryId;
    /**
     * 商品三级分类id
     */
    private Integer threeCategoryId;
    /**
     * 商品供应商id
     */
    private Integer supplierId;
    /**
     * 商品价格
     */
    private BigDecimal price;
    /**
     * 商品加权平均成本
     */
    private BigDecimal averageCost;
    /**
     * 商品上架状态
     */
    private boolean publishStatus;
    /**
     * 商品审核状态
     */
    private boolean auditStatus;
    /**
     * 商品重量
     */
    private Float weight;
    /**
     * 商品长度
     */
    private Float length;
    /**
     * 商品高度
     */
    private Float height;
    /**
     * 商品宽度
     */
    private Float width;
    /**
     * 商品颜色
     */
    private String color;
    /**
     * 商品生产日期
     */
    private LocalDateTime productionDate;
    /**
     * 商品有效日期
     */
    private Integer shelfLife;
    /**
     * 商品描述
     */
    private String descript;
}
