import instance from "@/utils/request";

let admin={
    /**
     * 拿到所有部门 tree的形式
     */
    getAllDeptTree() {
        return instance.get(`dept/tree`)
    },
    /**
     *  搜索框 拿到所有部门 懒加载  获取部门的一级数据通过id找孩子
     */
    getChildrenById(id) {
        return instance(`dept/${id}/children`);
    },

    /**
     * 分页条件查询
     */
    findPage(searchParams){
        return instance.get(`admin`,{params: searchParams})
    },


    /**
     * 删除
     */
    deleteById(id){
        return instance.delete(`admin/${id}`)
    },
    /**
     * 批量删除
     */
    batchDelete(ids){
        return instance.delete(`admin/batch/${ids}`)
    },
    /**
     * 获得json的 省市区信息
     */

    getAreaChildrenById(id) {
        return instance(`area/${id}/children`);
    },
    /**
     * 添加信息
     */
    addEntity(entity) {
        return instance.post(`admin`, entity);
    },
    /**
     * 修改信息
     */
    updateEntity(entity) {
        return instance.put(`admin`, entity);
    },
    /**
     * 修改根据id查询
     */

    findById(id) {
        return instance.get(`admin/${id}`)
    },
    /**
     * 修改数据 部门回显
     */
    getDeptTree(id) {
        return instance.get(`admin/findParentByDeptId/${id}`)
    }




}
export  default admin;