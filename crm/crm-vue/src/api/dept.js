import instance from "@/utils/request";

/**
 * 分页条件查询
 */
export function findPage(searchParams) {
    return instance.get(`dept`, {params: searchParams})
}


/**
 * 通过id找孩子
 */

export function getChildrenById(id) {
    return instance.get(`dept/${id}/children`);
}

/**
 * 通过id删除
 */
export function deleteById(id) {
    return instance.delete(`dept/${id}`)
}

/**
 * 添加功能
 */
export function addEntity(entity) {
    return instance.post(`dept`, entity);
}

/**
 * 通过id查询
 */
export function findById(id) {
    return instance.get(`dept/${id}`);
}

/**
 * 修改实体
 */
export function updateEntity(entity) {
    return instance.put(`dept`, entity);
}
