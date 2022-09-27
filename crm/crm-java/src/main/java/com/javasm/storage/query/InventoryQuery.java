package com.javasm.storage.query;

import lombok.Data;

@Data
public class InventoryQuery extends BaseQuery{
   private String storageStaff;
   private Integer storageId;
}
