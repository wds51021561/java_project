package com.javasm.storage.query;

import lombok.Data;

@Data
public class StorageFormQuery extends BaseQuery {
    /**
     * 查询入库单: 库单编号, 库单类型, 库单状态
     */
    private String storageFormCode;
    private Integer storageFormType;
    private Integer states;

}
