package com.itheima.pind.goods.dto;

import com.itheima.pind.goods.entity.GoodsInfo;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class GoodsInfoPageDTO extends GoodsInfo {
    /**
     * 商品创建日期
     */
    private LocalDateTime startCreateTime;
    /**
     * 商品删除时间
     */
    private LocalDateTime endCreateTime;
}
