package com.javasm.storage.query;

import lombok.Data;

@Data
public class SellOrderQuery extends BaseQuery{
    /**
     * 订单代码,订单类型,支付方式,订单状态
     */
    private Integer orderCode;
    private Integer orderType;
    private Integer payType;
    private Integer orderStates;



}
