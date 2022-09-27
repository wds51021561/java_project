package com.javasm.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.javasm.common.cache.CacheService;
import com.javasm.common.cache.JsonUtils;
import com.javasm.common.cache.KeyUtils;
import com.javasm.domin.entity.LoginAdmin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;


@Component
@Slf4j
@RequiredArgsConstructor
public class TokenService {


    private static final String secret = "sfsdfsdaf";


    private final CacheService cacheService;


    /**
     * 把token 实体类用户信息存入到redis中 并生成token数值
     * @param loginAdmin
     * @return
     */
    public String setLoginAdminAndCreateToken(LoginAdmin loginAdmin) {
        //获取到uuid
        String uuid = UUID.randomUUID().toString();
        loginAdmin.setUuid(uuid);
        //缓存用户信息 存入redis
        cacheService.setCache(KeyUtils.LOGIN_ADMIN + uuid, JsonUtils.obj2Str(loginAdmin));
        //生成token
        return createToken(uuid);
    }


    /**
     * 获取登录人的信息
     * @return
     */

    public LoginAdmin getLoginAdmin() {
        //拿到uuid
        String uuidFromToken = getUUIDFromToken(ServletUtils.getRequest());
        //根据文件名以及uuid  找到对应redis中的value数值
        String cache = cacheService.getCache(KeyUtils.LOGIN_ADMIN + uuidFromToken);
        //将value拿到 将json转换成对象
        LoginAdmin loginAdmin = JsonUtils.str2Obj(cache, LoginAdmin.class);
        //返回
        return loginAdmin;
    }


    public void setLoginAdmin(LoginAdmin loginAdmin) {
        cacheService.setCache(KeyUtils.LOGIN_ADMIN + loginAdmin.getUuid(), JsonUtils.obj2Str(loginAdmin));
    }
    /**
     * 获得登录的Id值
     * @return
     */
    public Long getAdminId() {
        return getLoginAdmin().getAdmin().getId();
    }

    /**
     * 是否是管理员
     */
    public boolean isAdmin() {
        return getLoginAdmin().getAdmin().getIsAdmin();
    }

    /**
     * 生成Token的方法
     *
     * @param uuid
     * @return authxx : Bearer + token
     */
    public String createToken(String uuid) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("ha")
                    //存入claim
                    .withClaim("uuid", uuid)
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            log.error("token生成失败");
        }
        return "";
    }

    /**
     * 解析Token
     * @param token
     * @return
     */
    public DecodedJWT getDecodedJWT(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("ha")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return jwt;
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
            log.error("token解析失败");
        }
        return null;
    }


    /**
     * 根据token数值 拿到对应的uuid
     * @param request
     * @return
     */
    public String getUUIDFromToken(HttpServletRequest request) {
        //获取访问时候的头
        String authorization = request.getHeader("Authorization");
        //拼接 取第二个
        String s = authorization.split(" ")[1];
        //解析token
        DecodedJWT decodedJWT = getDecodedJWT(s);
        //获取存入到的uuid 并返回
        String uuid = decodedJWT.getClaim("uuid").asString();
        return uuid;
    }


}
