import instance from "@/utils/request";

/**
 * 分页条件查询
 */
export function findPage(searchParams) {

    /*
    *  axios本身的写法
    *
    * */
    return instance.get(`role`, {params: searchParams})
}

/**
 * 通过id删除
 */

export function deleteById(id) {
    return instance.delete(`role/${id}`)
}

/**
 * 批量删除
 */

export function batchDelete(ids) {
    return instance.delete(`role/batch/${ids}`)
}

/**
 * 添加功能
 */
export function addEntity(entity) {
    return instance.post(`role`, entity);
}

/**
 * 通过id进行查询
 */
export function findById(id) {
    return instance.get(`role/${id}`);

}

/**
 * 修改实体
 */
export function updateEntity(entity) {
    return instance.put(`role`,entity)
    
}

/**
 * 查询所有的角色
 */
export function findAll() {
    return instance.get(`role/findAll`);
}
/**
 * 给角色赋予权限
 */

export function setRoleMenus(roleId, menuIds) {
    return instance.put(`role/${roleId}/menu`, menuIds)

}

/**
 * 通过角色Id获取角色的权限
 */
export function getMenuByRoleId(id) {
    return instance.get(`role/${id}/treeMenu`)
}