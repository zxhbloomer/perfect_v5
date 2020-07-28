package com.perfect.core.serviceimpl.master.rabc.permission;

import com.perfect.bean.entity.master.rabc.permission.MPermissionEntity;
import com.perfect.core.mapper.master.rabc.permission.MPermissionMapper;
import com.perfect.core.service.base.v1.BaseServiceImpl;
import com.perfect.core.service.master.rabc.permission.IMPermissionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author zxh
 * @since 2020-07-27
 */
@Service
public class MPermissionServiceImpl extends BaseServiceImpl<MPermissionMapper, MPermissionEntity> implements
    IMPermissionService {

}
