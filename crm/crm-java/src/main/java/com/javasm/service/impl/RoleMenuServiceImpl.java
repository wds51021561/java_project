package com.javasm.service.impl;

import com.javasm.domin.entity.RoleMenu;
import com.javasm.service.RoleMenuService;
import com.javasm.service.base.impl.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleMenuServiceImpl extends BaseServiceImpl<RoleMenu> implements RoleMenuService {
}
