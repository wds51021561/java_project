package com.javasm.storage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class StorageFormVO {

    /**
     * 库单VO: 库单id, 库单编码, 库单类型, 库单状态, 库单操作员, 库单创建时间
     */
    private Integer id;
    private String storageCode;
    private String storageFormType;
    private String storageFormStates;
    private String storage;
    private String storageStaff;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
