package com.javasm.domin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_admin_role")
@AllArgsConstructor
@NoArgsConstructor
public class AdminRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 管理员id
     */
    private Long adminId;

    /**
     * 角色id
     */
    private Long roleId;


}
