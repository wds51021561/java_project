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
    return instance.get(`brand`, {params: searchParams})
}

/**
 * 通过id删除
 */

export function deleteById(id) {
    return instance.delete(`brand/${id}`)
}

/**
 * 批量删除
 */

export function batchDelete(ids) {
    return instance.delete(`brand/batch/${ids}`)
}

/**
 * 添加功能
 */
export function addEntity(entity) {
    return instance.post(`brand`, entity);
}

/**
 * 通过id进行查询
 */
export function findById(id) {
    return instance.get(`brand/${id}`);

}

/**
 * 修改实体
 */
export function updateEntity(entity) {
    return instance.put(`brand`,entity)
    
}
