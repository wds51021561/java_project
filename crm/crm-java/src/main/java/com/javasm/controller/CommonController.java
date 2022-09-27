package com.javasm.controller;

import com.javasm.common.cache.CacheService;
import com.javasm.common.cache.KeyUtils;
import com.javasm.common.http.AxiosResult;
import com.javasm.common.http.EnumStatus;
import com.javasm.common.util.TokenService;
import com.javasm.domin.entity.Admin;
import com.javasm.domin.entity.LoginAdmin;
import com.javasm.service.AdminService;
import com.wf.captcha.GifCaptcha;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

/**
 * 登录的控制
 */
@RestController
@RequestMapping("common")
@RequiredArgsConstructor//和@Autowired效果一样
public class CommonController {

    @Autowired
    private CacheService cacheService;

    private final AdminService adminService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final TokenService tokenService;

    /**
     * 登录验证码
     */
    @GetMapping("getCaptcha/{uuid}")
    public AxiosResult<String> getCaptcha(@PathVariable String uuid) throws IOException {
        System.out.println(uuid);
        //向外写图片
        GifCaptcha specCaptcha = new GifCaptcha(130, 48, 6);
        String verCode = specCaptcha.text().toLowerCase();
        System.out.println(verCode);
        //存在reids中
        cacheService.setCacheWithDefaultTime(KeyUtils.CODE_PREFIX + uuid, verCode);
        return AxiosResult.success(specCaptcha.toBase64());

    }

    /**
     * 获得用户信息
     * 菜单 本身信息 按钮级别权限
     *
     * @return
     */
    @GetMapping("getAdminInfo")
    public AxiosResult<Map<String, Object>> getUserInfo() {
        System.out.println(tokenService.getLoginAdmin());
        Map<String, Object> map = adminService.getAdminInfo();
        return AxiosResult.success(map);
    }


    //登录
    @PostMapping("doLogin")
    public AxiosResult<String> doLogin(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String uuid = map.get("uuid");
        String password = map.get("password");
        String captcha = map.get("captcha");
        System.out.println(username + password);

        //判断验证码是否正确
        String cache = cacheService.getCache(KeyUtils.CODE_PREFIX + uuid);
        if (!cache.equalsIgnoreCase(captcha)) {
            //验证码不正确
            //添加日志  注释  因为没钱 100次
            //AsyncManager.getInstance().executor(RunnableFactory.insertLoginLog(username, 1, EnumStatus.CODE_ERROR.getMessage()));
            return AxiosResult.error(EnumStatus.CODE_ERROR);
        }
        //判断用户不存在
        Admin admin = adminService.doLogin(username);
        if (admin == null) {
            //用户不存在
            //添加日志
            //AsyncManager.getInstance().executor(RunnableFactory.insertLoginLog(username, 1, EnumStatus.ACCOUNT_ERROR.getMessage()));
            return AxiosResult.error(EnumStatus.ACCOUNT_ERROR);
        }
        //判断密码不正确
        boolean matches = bCryptPasswordEncoder.matches(password, admin.getAdminPwd());
        if (!matches) {
            //密码错误
            //添加日志
            //AsyncManager.getInstance().executor(RunnableFactory.insertLoginLog(username, 1, EnumStatus.PASSWOED_ERROR.getMessage()));
            return AxiosResult.error(EnumStatus.PASSWOED_ERROR);
        }

        //登录成功
        //添加日志
        //AsyncManager.getInstance().executor(RunnableFactory.insertLoginLog(username, 0, "登录成功"));

        //清除redis
        cacheService.clearCache(KeyUtils.CODE_PREFIX + uuid);

        //存入到token实体类中
        LoginAdmin loginAdmin = new LoginAdmin();
        loginAdmin.setAdmin(admin);

        //返回创建的token并携带着用户信息
        return AxiosResult.success(tokenService.setLoginAdminAndCreateToken(loginAdmin));
    }

}
