package com.itheima.pinda.authority.biz.service.auth.impl;

import com.itheima.pinda.authority.biz.service.auth.ValidateCodeService;
import com.itheima.pinda.common.constant.CacheKey;
import com.itheima.pinda.exception.BizException;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import net.oschina.j2cache.CacheChannel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Service
public class ValidteCodeServiceImpl implements ValidateCodeService {
    @Autowired
    private CacheChannel cache;
    @Override
    public void create(String key, HttpServletResponse response) throws IOException {
        if(StringUtils.isEmpty(key)){
            throw BizException.validFail("验证码不能为空");
        }
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        response.setHeader(HttpHeaders.PRAGMA,"No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL,"No-cache");
        response.setDateHeader(HttpHeaders.EXPIRES,0L);
        Captcha captcha = new ArithmeticCaptcha(115,42);
        captcha.setCharType(2);
        cache.set(CacheKey.CAPTCHA,key, StringUtils.lowerCase((captcha.text())));
        captcha.out(response.getOutputStream());
    }
}
