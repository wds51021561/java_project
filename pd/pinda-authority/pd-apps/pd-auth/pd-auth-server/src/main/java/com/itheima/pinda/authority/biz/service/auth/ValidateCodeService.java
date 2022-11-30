package com.itheima.pinda.authority.biz.service.auth;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ValidateCodeService {
    /**
     * 创建验证码方法
     * @param key
     * @param response
     * @throws IOException
     */
    void create(String key, HttpServletResponse response)throws IOException;

    /**
     * 检验验证码
     * @param key
     * @param code
     * @return
     */
    boolean check(String key, String code);
}
