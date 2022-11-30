package com.itheima.pinda.authority.biz.dao.auth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.pinda.authority.dto.auth.ResourceQueryDTO;
import com.itheima.pinda.authority.entity.auth.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceMapper extends BaseMapper<Resource> {
    /**
     * 查询用户可见的资源
     * @param resource
     * @return
     */
    List<Resource> findVisibleResource(ResourceQueryDTO resource);
}
