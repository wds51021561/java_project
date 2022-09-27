package com.javasm.domin.criteria;

import com.javasm.domin.criteria.base.BaseQueryCriteria;
import lombok.Data;

@Data
public class GoodCriteria extends BaseQueryCriteria {
    /**
     * 商品的搜索
     */

    //商品名称
    private String goodName;
    //商品描述
    private String goodDesc;
    //品牌id
    private Long brandId;
}
