package com.itheima.pinda.zuul.filter;

import com.itheima.pinda.auth.client.configuration.AuthClientConfiguration;
import com.itheima.pinda.auth.client.properties.AuthClientProperties;
import com.itheima.pinda.auth.client.utils.JwtTokenClientUtils;
import com.itheima.pinda.auth.utils.JwtUserInfo;
import com.itheima.pinda.base.R;
import com.itheima.pinda.context.BaseContextConstants;
import com.itheima.pinda.exception.BizException;
import com.itheima.pinda.utils.StrHelper;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.sun.prism.impl.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
@Slf4j
@Component
public class TokenContextFilter extends BaseFilter{
    @Autowired
    private AuthClientProperties properties;
    @Autowired
    private JwtTokenClientUtils jwtTokenClientUtils;
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        if(isIgnoreToken()){
            return null;
        }
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getHeader(properties.getUser().getHeaderName());
        JwtUserInfo jwtUserInfo = null;
        try{
            jwtUserInfo = jwtTokenClientUtils.getUserInfo(token);
        }catch (BizException e){
            errorResponse(e.getMessage(),e.getCode(),200);
        }catch (Exception e){
            errorResponse("解析token出错", R.FAIL_CODE,200);
        }

        if(jwtUserInfo != null){
            addHeader(ctx, BaseContextConstants.JWT_KEY_ACCOUNT,jwtUserInfo.getAccount());
            addHeader(ctx, BaseContextConstants.JWT_KEY_USER_ID,jwtUserInfo.getUserId());
            addHeader(ctx, BaseContextConstants.JWT_KEY_NAME,jwtUserInfo.getName());
            addHeader(ctx, BaseContextConstants.JWT_KEY_ORG_ID,jwtUserInfo.getOrgId());
            addHeader(ctx, BaseContextConstants.JWT_KEY_STATION_ID,jwtUserInfo.getStationId());
        }
        return null;

    }

    private void addHeader(RequestContext ctx,String name,Object value){
        if(StringUtils.isEmpty(value)){
            return;
        }
        ctx.addZuulRequestHeader(name, StrHelper.encode(value.toString()));
    }
}
