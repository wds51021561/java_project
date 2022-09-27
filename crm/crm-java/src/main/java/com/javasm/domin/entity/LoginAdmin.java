package com.javasm.domin.entity;

import lombok.Data;

import java.util.List;

@Data
public class LoginAdmin {

    /**
     * token的使用  保存到token之中
     */

    private String uuid;

    private Admin admin;

    private List<Menu> perms;
}
