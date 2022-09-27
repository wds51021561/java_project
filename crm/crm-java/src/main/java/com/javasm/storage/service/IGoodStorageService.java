package com.javasm.storage.service;

import com.javasm.storage.entity.GoodStorage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javasm.storage.entity.StorageForm;
import com.javasm.storage.req.AddReturnGoodStorageForm;
import com.javasm.storage.vo.AuditGoodVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yy
 * @since 2022-06-30
 */
public interface IGoodStorageService extends IService<GoodStorage> {
    /**
     * 更新商品状态
     * @param storageForm
     * @return
     */
    Boolean updateStates(StorageForm storageForm);

    /**
     * 根据库单编号查询商品
     * @param code
     * @return
     */
    List<GoodStorage> queryByStorageFormCode(String code);

    /**
     * 查询审核商品的VO类
     * @param code
     * @return
     */
    List<AuditGoodVO> queryAuditGoodVoByStorageFormCode(String code);

    /**
     * 判断是商品状态是否可以出退货入库
     * @param goodInfos
     * @return
     */
    boolean isReturnGoodStates(List<AddReturnGoodStorageForm.GoodInfo> goodInfos);

    /**
     * 根据GoodInfo获取商品库存对象
     * @param goodInfo
     * @return
     */
    public GoodStorage getByGoodInfo(AddReturnGoodStorageForm.GoodInfo goodInfo);

    /**
     * 判断是商品状态是否可以出销售出库
     * @param goodInfos
     * @return
     */
    boolean isSellGoodStates(List<AddReturnGoodStorageForm.GoodInfo> goodInfos,Integer storageId);
}
