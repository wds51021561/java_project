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
public class GoodStorage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品库存表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 商品类型id
     */
    private Integer goodId;

    /**
     * 商品串号
     */
    private String goodSerial;

    /**
     * 商品所在仓库
     */
    private Integer storageId;

    /**
     * 商品业务
     */
    private Integer goodBusiness;

    /**
     * 商品库存状态, 0-待入库,1-入库, 2-待出库,3出库
     */
    private Integer goodState;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime modifyTime;


}
