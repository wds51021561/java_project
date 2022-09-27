package com.javasm.storage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.javasm.storage.myEnum.StorageEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

/**
 * <p>
 * 出入库表
 * </p>
 *
 * @author yy
 * @since 2022-06-30
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StorageForm implements Serializable {

    private static final long serialVersionUID = 1L;

    public StorageForm(String storageCode,Integer storageType,String storageStaff){
        this.storageCode = storageCode;
        this.storageState = StorageEnum.INPUT.get();
        this.storageStaff = storageStaff;
        this.storageType = storageType;
    }

    /**
     * 出入仓库单id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 出入仓库单编号
     */
    private String storageCode;

    /**
     * 订单编号
     */
    private String orderCode;

    /**
     * 出入库id
     */
    private Integer storageId;

    /**
     * 出入库类型
     */
    private Integer storageType;

    /**
     * 出入库状态
     */
    private Integer storageState;

    /**
     * 出入库员
     */
    private String storageStaff;

    /**
     * 备注
     */
    private String remark;

    /**
     * 出入库单创建时间
     */
    private LocalDateTime createTime;

    /**
     * 出入库单修改时间
     */
    private LocalDateTime modifyTime;


}
