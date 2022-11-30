package com.itheima.pinda.authority.biz.service.auth.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.pinda.authority.biz.dao.auth.ResourceMapper;
import com.itheima.pinda.authority.biz.service.auth.ResourceService;
import com.itheima.pinda.authority.dto.auth.ResourceQueryDTO;
import com.itheima.pinda.authority.entity.auth.Resource;
import com.itheima.pinda.common.constant.CacheKey;
import net.oschina.j2cache.CacheChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper,Resource> implements ResourceService  {
    @Autowired
    private CacheChannel cache;

    @Override
    public List<Resource> findVisibleResource(ResourceQueryDTO resource) {
        List<Resource> visibleResource = baseMapper.findVisibleResource(resource);
        if(visibleResource != null && visibleResource.size() > 0){
            List<String> userResource= visibleResource.stream().map(r -> {
                return r.getMethod() + r.getUrl();
            }).collect(Collectors.toList());
            cache.set(CacheKey.USER_RESOURCE,resource.getUserId().toString(),userResource);
        }
        return visibleResource;

    }
}
