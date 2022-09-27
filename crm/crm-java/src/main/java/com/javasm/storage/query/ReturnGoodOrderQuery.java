package com.javasm.storage.query;

import lombok.Data;

@Data
public class ReturnGoodOrderQuery extends BaseQuery {

    private Integer orderCode;
    private Integer states;

}
