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
public class StorageGood implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品出入库记录关联表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 商品库存id
     */
    private Integer goodStorageId;

    /**
     * 库存单编号
     */
    private String storageCode;


}
