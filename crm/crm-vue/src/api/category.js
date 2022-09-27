import instance from "@/utils/request";

/**
 * 分页条件查询
 */
export function findPage(searchParams) {

    /*
    *
    *  axios本身的写法
    *
    * */
    return instance.get(`category`, {params: searchParams})
}

/**
 * 通过id删除
 */

export function deleteById(id) {
    return instance.delete(`category/${id}`)
}

/**
 * 批量删除
 */

export function batchDelete(ids) {
    return instance.delete(`category/batch/${ids}`)
}

/**
 * 添加功能
 */
export function addEntity(entity) {
    return instance.post(`category`, entity);
}

/**
 * 通过id进行查询
 */
export function findById(id) {
    return instance.get(`category/${id}`);

}

/**
 * 修改实体
 */
export function updateEntity(entity) {
    return instance.put(`category`,entity)
    
}

/**
 * 查询select中的数据
 */
export function getSelectData() {
    return instance.get(`category/selectTree`);
}
