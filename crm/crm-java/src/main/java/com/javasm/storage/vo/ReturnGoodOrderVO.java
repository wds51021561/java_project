package com.javasm.storage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 销售入库订单: 订单id,退货单编号,退货单状态,订单日期
 */
@Data
public class ReturnGoodOrderVO {


    private Integer returnGoodOrderId;
    private String returnGoodOrderCode;
    private String states;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "UTC")
    private LocalDateTime returnGoodOrderDate;


}
