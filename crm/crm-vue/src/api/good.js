import instance from "@/utils/request";

let good={
    //分页查询
    searchPage(param){
        return instance.get(`good`,{params:param})

    },

    //拿到下拉框的品牌信息
    getAllBrand(){
        return instance.get(`brand/findAll`)
    },
    //通过id拿分类信息
    getCategoryByParentId(id){
        return instance.get(`category/findByPid?parentId=${id}`)
    },
    //添加
    addEntity(entity){
        return instance.post(`good`,entity)
    },
    //修改 根据id查询
    findById(id){
        return instance.get(`good/${id}`)
    },
    //修改
    updateEntity(entity){
        return instance.put(`good`,entity)
    }

}

export default good;