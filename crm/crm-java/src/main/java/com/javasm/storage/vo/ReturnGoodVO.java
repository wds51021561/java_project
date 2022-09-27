package com.javasm.storage.vo;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * 作者:yy
 * 日期:2022/7/3 20:39
 * 描述:
 */
@Data
public class ReturnGoodVO {
    /**
     * 商品id,商品名称,商品型号,商品颜色,商品串口(null)
     */
    private Integer id;
    private String goodName;
    private String goodType;
    private String goodColor;
    private String goodSerial;
}
