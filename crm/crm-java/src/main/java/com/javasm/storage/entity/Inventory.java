package com.javasm.storage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 盘点记录
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 盘点记录编码
     */
    private String inventoryCode;

    /**
     * 盘点仓库id
     */
    private Integer storageId;

    /**
     * 盘点员
     */
    private String storageStaff;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime modifyTime;


}
