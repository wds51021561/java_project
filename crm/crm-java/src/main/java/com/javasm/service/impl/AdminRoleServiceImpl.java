package com.javasm.service.impl;

import com.javasm.domin.entity.AdminRole;
import com.javasm.service.AdminRoleService;
import com.javasm.service.base.impl.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminRoleServiceImpl extends BaseServiceImpl<AdminRole> implements AdminRoleService {
}
