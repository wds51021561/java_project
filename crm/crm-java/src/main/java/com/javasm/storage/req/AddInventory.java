package com.javasm.storage.req;

import com.javasm.storage.vo.InventoryGoodVO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 作者:yy
 * 日期:2022/7/4 14:01
 * 描述:
 */
@Data
public class AddInventory {
    private String staff;
    private Integer storageId;
    private LocalDateTime createTime;
    private String remark;
    private List<InventoryGoodVO> list;
}
