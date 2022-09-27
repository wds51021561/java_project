package com.javasm.domin.vo;

import com.javasm.domin.vo.base.BaseVo;
import lombok.Data;

@Data
public class BrandVo  extends BaseVo {
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
