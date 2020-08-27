package com.perfect.core.service.master.rbac.permission.user;

import com.perfect.bean.vo.master.rbac.permission.MPermissionOperationVo;
import com.perfect.bean.vo.master.rbac.permission.operation.OperationMenuDataVo;

import java.util.List;

/**
 * @ClassName:
 * @Description: 获取用户的权限
 * @Author: zxh
 * @date: 2020/8/26
 * @Version: 1.0
 */
public interface IMUserPermissionService  {
    /**
     * 菜单权限数据
     */
    List<OperationMenuDataVo> getPermissionMenu(Long staff_id, Long tenant_id);

    /**
     * 操作权限数据
     */
    List<MPermissionOperationVo> getPermissionOperation(Long staff_id, Long tenant_id);

}
