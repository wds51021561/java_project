package com.javasm.domin.criteria;

import com.javasm.domin.criteria.base.BaseQueryCriteria;
import lombok.Data;

@Data
public class BrandCriteria extends BaseQueryCriteria {
    /**
     * 品牌类型的搜索
     */

    //品牌名称
    private String brandName;
}
