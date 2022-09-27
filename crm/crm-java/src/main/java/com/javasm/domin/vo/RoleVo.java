package com.javasm.domin.vo;

import com.javasm.domin.vo.base.BaseVo;
import lombok.Data;


@Data
public class RoleVo extends BaseVo {


    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleDesc;

}
