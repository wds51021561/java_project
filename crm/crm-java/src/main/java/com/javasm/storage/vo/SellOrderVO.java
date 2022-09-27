package com.javasm.storage.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 销售销售
 */
@Data
public class SellOrderVO {
    private Integer id;
    private String orderCode;
    private String orderType;
    private String payType;
    private String orderStates;
    private LocalDateTime createTime;

}
