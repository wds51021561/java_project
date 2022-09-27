import req from "@/utils/request.js";

//审核
export let auditReq = {

    //根据表单查询审核记录
    reqAuditFormByStorageFormCode(code){
        return req.get(`/storage/storageAudit/reqAuditFormByStorageFormCode/${code}`);
    },
    //请求生成审核记录
    reqAddAuditForm(form){
        return req.post(`/storage/storageAudit/reqAddAuditForm`,form);
    }


}

//商品库存
export let goodStorageReq = {
    //根据出入库表单编码查询商品库存
    reqGoodStorageByStorageFormCode(code){
        return req.get(`/storage/goodStorage/reqGoodStorageByStorageFormCode/${code}`);
    }
}

//盘点记录
export let inventoryReq = {
    //请求分页查询盘点数据
    reqPageQuery(form){
        return req.get(`storage/inventory/pageQueryInventory`,{params:form})
    },

    //请求添加盘点记录
    reqAddInventory(addFrom) {
        return req.post(`storage/inventory/addInventory`,addFrom)
    },

}

//盘点记录商品
export let inventoryGoodReq = {
    //请求查询数据库中的库存记录
    reqInventoryGood(storageId) {
        return req.get(`storage/inventoryGood/queryInventoryGoodByStorageId/${storageId}`);
    },
    //根据盘点编号查询盘点商品
    reqInventoryGoodByCode(inventoryCode) {
        return req.get(`storage/inventoryGood/queryInventoryGoodByCode/${inventoryCode}`);
    }

}

//出入库表单
export let storageFormReq = {

    //通过表单编码查询表单信息
    reqStorageFormByStorageFormCode(code){
        return req.get(`/storage/StorageForm/reqStorageFormByStorageFormCode/${code}`);
    },

    //通过查询条件分页查询出入库表单
    reqPageStorageForm(queryForm){
        return req.get(`/storage/StorageForm/queryStorageForm`,{params:queryForm})
    },
    //请求生成入库单
    reqAddStorageForm(form){
        return req.post(`/storage/StorageForm/addSellReturnForm`,form);
    },
    reqAddSellStorageForm(form){
        return req.post(`/storage/StorageForm/addSellForm`,form);
    }

}

//字典
export let dicReq = {
    //查询仓库订单类型
    reqStorageType(){
        return req.get(`dic/getStorageType`);
    },
    //查询仓库类型
    reqStorage(){
        return req.get(`dic/getStorage`);
    },
    //请求订单状态
    reqReturnOrderStates(){
        return req.get(`dic/getOrderStates/0`);
    },
    //请求销售订单类型
    reqSellOrderStates(){
        return req.get(`dic/getOrderStates/1`);
    },
    //请求支付方式
    reqPayType(){
        return req.get(`dic/getPayType`);
    },
    reqOrderType(){
        return req.get(`dic/getOrderType`);
    }

}

//订单
export let orderReq = {
    //请求销售退货订单分页数据
    reqPageQuerySellReturnOrder(queryForm){
        return req.get(`storage/order/pageQuerySellReturnOrder`,{params:queryForm})
    },
    //请求订单商品数据
    reqSellOrderGood(id){
        return req.get(`storage/order/reqSellOrderGood/${id}`);
    },
    //请求销售订单
    reqSellOrder(form){
        return req.get(`storage/order/reqSellOrder`,{params:form});
    },


}
