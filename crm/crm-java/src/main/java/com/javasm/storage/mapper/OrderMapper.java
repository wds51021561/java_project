package com.javasm.storage.mapper;

import com.javasm.storage.query.ReturnGoodOrderQuery;
import com.javasm.storage.query.SellOrderQuery;
import com.javasm.storage.vo.ReturnGoodOrderVO;
import com.javasm.storage.vo.ReturnGoodVO;
import com.javasm.storage.vo.SellGoodVO;
import com.javasm.storage.vo.SellOrderVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {

    /**
     * 查询销售退单的VO类
     * @param orderQuery
     * @return
     */
    List<ReturnGoodOrderVO> pageQuerySellReturnOrder(ReturnGoodOrderQuery orderQuery);

    /**
     * 查询销售订单的商品VO对象
     * @param id 订单id;
     * @return
     */
    List<ReturnGoodVO> findSellOrderGood(Integer id);

    /**
     * 更新销售订单状态
     * @param orderCode
     * @param i
     * @return
     */
    Boolean updateSellOrder(@Param("orderCode") Integer orderCode,@Param("states") int i);

    /**
     * 分页查询销售订单
     * @param orderQuery
     * @return
     */
    List<SellOrderVO> pageQuerySellOrder(SellOrderQuery orderQuery);

    /**
     * 查询销售商品
     * @param id
     * @return
     */
    List<SellGoodVO> findSellGood(Integer id);
}
