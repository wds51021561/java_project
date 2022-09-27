package com.javasm.storage.req;

import lombok.Data;
import org.springframework.lang.NonNull;

import java.util.List;

@Data
public class AddReturnGoodStorageForm {
    /**
     * 添加销售入库单: 业务订单编码, 退货仓库id, 退货商品串码, 入库备注
     */
    @NonNull
    private String returnGoodOrderCode;
    @NonNull
    private Integer storageId;
    @NonNull
    private String remark;
    @NonNull
    private List<GoodInfo> goodInfos;

    @Data
    public static class GoodInfo{
        private Integer goodId;
        private String goodSerial;
    }
}
