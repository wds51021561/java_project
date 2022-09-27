package com.javasm.storage.vo;

import lombok.Data;

@Data
public class InventoryGoodVO {
    /**
     * id,商品串号,商品id,商品名,商品颜色,商品型号,库存应有,盘点实际有,差异原因
     */
    private Integer id;
    private Integer goodId;
    private String goodSerial;
    private String goodName;
    private String type;
    private String color;
    private String storage;
    private Integer predict;
    private Integer actual;
    private Integer difference;
    private String remark;

}
