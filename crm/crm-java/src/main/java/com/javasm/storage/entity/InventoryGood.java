package com.javasm.storage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author yy
 * @since 2022-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class InventoryGood implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 盘点id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 盘点商品id
     */
    private Integer goodId;

    /**
     * 盘点商品串号
     */
    private String goodSerial;

    /**
     * 关联盘点记录编码
     */
    private String inventoryCode;

    /**
     * 应有库存
     */
    private Integer predict;

    /**
     * 实际库存
     */
    private Integer actual;
    /**
     * 差异原因
     */
    private String remark;


}
