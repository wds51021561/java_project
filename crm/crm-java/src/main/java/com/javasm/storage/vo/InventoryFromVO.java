package com.javasm.storage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 盘点表单
 */
@Data
public class InventoryFromVO {
    /**
     * id,盘点表单编码,盘点仓库,盘点员,备注,创建时间
     */
    private Integer id;
    private String inventoryCode;
    private String storage;
    private String storageStaff;
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;
}
