package com.itheima.pinda.authority.biz.service.auth.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.itheima.pinda.auth.server.utils.JwtTokenServerUtils;
import com.itheima.pinda.auth.utils.JwtUserInfo;
import com.itheima.pinda.auth.utils.Token;
import com.itheima.pinda.authority.biz.service.auth.ResourceService;
import com.itheima.pinda.authority.biz.service.auth.UserService;
import com.itheima.pinda.authority.dto.auth.LoginDTO;
import com.itheima.pinda.authority.dto.auth.ResourceQueryDTO;
import com.itheima.pinda.authority.dto.auth.UserDTO;
import com.itheima.pinda.authority.entity.auth.Resource;
import com.itheima.pinda.authority.entity.auth.User;
import com.itheima.pinda.base.R;
import com.itheima.pinda.dozer.DozerUtils;
import com.itheima.pinda.exception.code.ExceptionCode;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthManager {
    @Autowired
    private JwtTokenServerUtils jwtTokenServerUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private DozerUtils dozerUtils;

    /**
     * 登录验证
     * @param account
     * @param password
     * @return
     */
    public R<LoginDTO> login(@NotEmpty(message = "用户名不能为空") String account,
                             @NotEmpty(message = "密码不能为空") String password){
        //验证用户账号密码
        R<User> result = checkUser(account,password);
        //判断是否查询到了用户
        if(result.getIsError()){
            //没有查询到用户，返回对应的错误代码及消息
            return R.fail(result.getCode(),result.getMsg());
        }
        User user = result.getData();

        //创建token
        Token token = this.generateUserToken(user);
        //获取账号资源列表，即可以使用的功能
        List<Resource> resourceList = this.resourceService.findVisibleResource(ResourceQueryDTO.builder().userId(user.getId()).build());
        //获取账号权限列表，即功能的增删改权限
        List<String> permissionsList = null;
        if(resourceList !=null && resourceList.size() > 0){
            permissionsList = resourceList.stream().map(Resource::getCode).collect(Collectors.toList());
        }
        // 登录成功后将登录对象的信息封装为DTO传递给前端
        LoginDTO loginDTO = LoginDTO.builder()
                .user(this.dozerUtils.map(user, UserDTO.class))
                .token(token)
                .permissionsList(permissionsList)
                .build();
        return R.success(loginDTO);
    }

    /**
     * 创建token
     * @param user
     * @return
     */
    private Token generateUserToken(User user) {
        JwtUserInfo userInfo = new JwtUserInfo(user.getId(),user.getAccount(),user.getName(),user.getOrgId(),user.getStationId());
        Token token = this.jwtTokenServerUtils.generateUserToken(userInfo,null);
        log.info("token = {}",token.getToken());
        return token;
    }

    /**
     * 验证用户账号密码
     * 先通过账号从数据库中查询到获取用户对象
     * 对输入的密码进行md5加密
     * 从用户对象中获取密码信息，与加密后的密码进行比对
     * @param account 用户账号
     * @param password 用户密码
     * @return
     */
    private R<User> checkUser(String account, String password) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        User user = this.userService.getOne(wrapper.eq(User::getAccount,account));
        // 密码加密
        String passwordMd5 = DigestUtils.md5Hex(password);

        // 验证用户是否存在，以及密码是否正确
        if (user == null || !user.getPassword().equals(passwordMd5)) {
            // 密码错误，返回错误代码
            return R.fail(ExceptionCode.JWT_USER_INVALID);
        }
        // 用户存在且密码正确，返回用户对象和成功代码
        return R.success(user);
    }

}
