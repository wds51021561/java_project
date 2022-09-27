package com.javasm.storage.vo;

import lombok.Data;
/**
 * 出入库是商品VO: 商品id, 商品颜色, 商品名, 商品类型, 商品颜色
 */
@Data
public class AuditGoodVO {

    //商品id
    private Integer id;
    //商品名
    private String goodName;
    //商品型号
    private String type;
    //商品颜色
    private String color;
    //商品串口号
    private String goodSerial;
}
