import instance from "@/utils/request";

let menu = {

    /**
     * 查询所有数据
     */
    searchPage(searchForm) {
        return instance.get(`menu`, {params: searchForm})
    },


    /**
     * 获得所有的菜单 tree
     */

    getAllMenuTree() {
        return instance.get(`menu/tree`)
    },

    /**
     * 新增
     */
    addEntity(entity) {
        return instance.post(`menu`, entity);
    },
    /**
     * 修改
     */
    updateEntity(entity) {
        return instance.put(`menu`, entity);
    },

    /**
     * 删除
     */

    deleteById(id){
        return instance.delete(`menu/${id}`)
    },

    /**
     * 根据id查询 修改赋值
     */
    findById(id){
        return instance.get(`menu/${id}`)
    },

}
export default menu;