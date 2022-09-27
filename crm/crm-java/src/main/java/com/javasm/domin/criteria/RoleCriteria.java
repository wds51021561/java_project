package com.javasm.domin.criteria;

import com.javasm.domin.criteria.base.BaseQueryCriteria;
import lombok.Data;

@Data
public class RoleCriteria  extends BaseQueryCriteria {
    /**
     * 角色查询条件
     */

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleDesc;

}
