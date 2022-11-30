package com.itheima.pinda.authority.biz.service.auth.impl;

import com.itheima.pinda.authority.biz.service.auth.ValidateCodeService;
import com.itheima.pinda.common.constant.CacheKey;
import com.itheima.pinda.exception.BizException;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {
    @Autowired
    private CacheChannel cache;
    @Override
    public void create(String key, HttpServletResponse response) throws IOException {
        if(StringUtils.isEmpty(key)){
            throw BizException.validFail("验证码不能为空");
        }
        //设置数据响应类型
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        //设置http头
        response.setHeader(HttpHeaders.PRAGMA,"No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL,"No-cache");
        response.setDateHeader(HttpHeaders.EXPIRES,0L);
        //创建验证码
        Captcha captcha = new ArithmeticCaptcha(115,42);
        //设置验证码类型
        captcha.setCharType(2);
        //缓存验证码
        cache.set(CacheKey.CAPTCHA,key, StringUtils.lowerCase((captcha.text())));
        //输入输出验证码图像
        captcha.out(response.getOutputStream());
    }

    @Override
    public boolean check(String key, String code) {
        CacheObject cacheObject = cache.get(CacheKey.CAPTCHA, key);
        //获取验证码
        if(cacheObject.getValue() == null){
            throw BizException.validFail("验证码已过期");
        }
        if(!StringUtils.equalsIgnoreCase(code,String.valueOf(cacheObject.getValue()))){
            throw BizException.validFail("验证失败");
        }
        cache.evict(CacheKey.CAPTCHA,key);
        return true;
    }
}
