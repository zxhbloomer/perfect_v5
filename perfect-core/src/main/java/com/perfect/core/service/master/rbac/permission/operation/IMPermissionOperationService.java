package com.perfect.core.service.master.rbac.permission.operation;

import com.baomidou.mybatisplus.extension.service.IService;
import com.perfect.bean.entity.master.rbac.permission.MPermissionEntity;
import com.perfect.bean.vo.master.rbac.permission.operation.OperationMenuDataVo;
import com.perfect.bean.vo.master.rbac.permission.operation.OperationMenuVo;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author zxh
 * @since 2020-07-27
 */
public interface IMPermissionOperationService extends IService<MPermissionEntity> {

    /**
     * 获取所有数据
     */
    OperationMenuVo getTreeData(OperationMenuDataVo searchCondition) ;

    /**
     * 复制选中的菜单
     */
    OperationMenuVo setSystemMenuData2PermissionData(OperationMenuDataVo searchCondition) ;
}
