package com.itheima.pinda.zuul.filter;

import cn.hutool.core.util.StrUtil;
import com.itheima.pinda.authority.dto.auth.ResourceQueryDTO;
import com.itheima.pinda.authority.entity.auth.Resource;
import com.itheima.pinda.base.R;
import com.itheima.pinda.common.constant.CacheKey;
import com.itheima.pinda.context.BaseContextConstants;
import com.itheima.pinda.exception.code.ExceptionCode;
import com.itheima.pinda.zuul.api.ResourceApi;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Slf4j
@Component
public class AccessFilter extends BaseFilter{
    @Autowired
    private CacheChannel cacheChannel;
    @Autowired
    private ResourceApi resourceApi;
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
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
        String requestURI = ctx.getRequest().getRequestURI();
        requestURI = StrUtil.subSuf(requestURI, zuulPrefix.length());
        requestURI = StrUtil.subSuf(requestURI,requestURI.indexOf("/",1));
        String method = ctx.getRequest().getMethod();
        String permission = method + requestURI;
        CacheObject resourceNeed2AuthObject = cacheChannel.get(CacheKey.RESOURCE,CacheKey.RESOURCE_NEED_TO_CHECK);
        List<String> resourceNeed2Auth = (List<String>) resourceNeed2AuthObject.getValue();
        //从缓存中未获取到资源
        if(resourceNeed2Auth == null){
            resourceNeed2Auth = resourceApi.list().getData();
            if(resourceNeed2Auth != null){
                cacheChannel.set(CacheKey.RESOURCE, CacheKey.RESOURCE_NEED_TO_CHECK,resourceNeed2Auth);
            }
        }
        if(resourceNeed2Auth != null){
            long count = resourceNeed2Auth.stream().filter(r -> {
                return permission.startsWith(r);
            }).count();
            if(count == 0){
                errorResponse(ExceptionCode.UNAUTHORIZED.getMsg(), ExceptionCode.UNAUTHORIZED.getCode(),200);
                return null;
            }
        }


        String userId = ctx.getZuulRequestHeaders().get(BaseContextConstants.JWT_KEY_USER_ID);
        CacheObject cacheObject = cacheChannel.get(CacheKey.USER_RESOURCE,userId);
        List<String> userResource = (List<String>) cacheObject.getValue();
        if(userResource == null){
            ResourceQueryDTO resourceQueryDTO = new ResourceQueryDTO();
            resourceQueryDTO.setUserId(new Long(userId));
            R<List<Resource>> resource = resourceApi.visible(resourceQueryDTO);
            if(resource.getData() != null){
                List<Resource> resourceList = resource.getData();
                userResource = resourceList.stream().map(r -> {
                    return r.getMethod() + r.getUrl();
                }).collect(Collectors.toList());
                cacheChannel.set(CacheKey.USER_RESOURCE,userId,userResource);
            }
        }

        long count = userResource.stream().filter(r -> {
            return permission.startsWith(r);
        }).count();
        if(count > 0){
            return null;
        }else {
            log.warn("用户{}没有访问{}资源的权限",userId,permission);
            errorResponse(ExceptionCode.UNAUTHORIZED.getMsg(), ExceptionCode.UNAUTHORIZED.getCode(), 200);
        }
        return null;
    }
}
