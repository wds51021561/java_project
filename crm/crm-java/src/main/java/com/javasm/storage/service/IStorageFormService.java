package com.javasm.storage.service;

import com.javasm.common.page.PageResult;
import com.javasm.storage.entity.StorageAudit;
import com.javasm.storage.entity.StorageForm;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javasm.storage.query.StorageFormQuery;
import com.javasm.storage.req.AddReturnGoodStorageForm;
import com.javasm.storage.vo.StorageFormVO;

/**
 * <p>
 * 出入库表 服务类
 * </p>
 *
 * @author yy
 * @since 2022-06-30
 */
public interface IStorageFormService extends IService<StorageForm> {
    /**
     * 更新库存单状态
     *
     * @param storageCode  库存单编号
     * @param storageAudit 审核记录
     * @return
     */
    Boolean updateStates(String storageCode, StorageAudit storageAudit);

    /**
     * 根据库单编码获取库单对象
     * @param code
     * @return
     */
    StorageForm reqStorageFormByStorageFormCode(String code);

    /**
     * 分页查询库单记录
     * @param queryForm
     * @return
     */
    PageResult<StorageFormVO> pageStorageForm(StorageFormQuery queryForm);

    /**
     * 验证此订单的库单是否已被创建
     * @param returnGoodOrderCode
     * @return
     */
    boolean isCreatedFormByCode(String returnGoodOrderCode);

    /**
     * 创建退货入库订单
     * @param addStorageForm
     * @return
     */
    boolean createReturnForm(AddReturnGoodStorageForm addStorageForm,String name);

    /**
     * 创建销售出库订单
     * @param addStorageForm
     * @param name
     * @return
     */
    boolean createSellForm(AddReturnGoodStorageForm addStorageForm, String name);
}
