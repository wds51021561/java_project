package com.itheima.pinda.authority.controller;

import com.itheima.pinda.authority.biz.service.auth.ValidateCodeService;
import com.itheima.pinda.authority.biz.service.auth.impl.AuthManager;
import com.itheima.pinda.authority.dto.auth.LoginDTO;
import com.itheima.pinda.authority.dto.auth.LoginParamDTO;
import com.itheima.pinda.base.BaseController;
import com.itheima.pinda.base.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@Api(value = "UserAuthController",tags = "登录")
@RequestMapping("/anno")
public class LoginController extends BaseController {
    @Autowired
    private ValidateCodeService validateCodeService;
    @Autowired
    private AuthManager authManager;

    /**
     * 创建/刷新验证码，创建完成后，通过response将验证码图片传递给客户端。
     * @param key
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "验证码",notes = "验证码")
    @GetMapping(value = "/captcha",produces = "image/png")
    public void captcha(@RequestParam(value = "key") String key, HttpServletResponse response) throws IOException {
        this.validateCodeService.create(key,response);
    }

    /**
     * 登录接口
     * @param login
     * @return
     */
    @ApiOperation(value = "登录",notes = "登录")
    @PostMapping(value = "/login")
    public R<LoginDTO> login(@Validated @RequestBody LoginParamDTO login){
        log.info("account={}",login.getAccount());
        if(validateCodeService.check(login.getKey(),login.getCode())){
            //当验证码通过是，执行具体的登录认证逻辑
            //检验账号 密码是否正确
            //生成jwt令牌
            //将用户的对应权限进行缓存（用于前端页面展示）
            //见用户对应的权限进行缓存（用于后端网关鉴权）
            //生成返回前端的dto对象
            R<LoginDTO> loginDto = authManager.login(login.getAccount(), login.getPassword());
            return loginDto;
        }
        return this.success(null);
    }
}
