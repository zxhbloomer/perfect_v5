package com.perfect.core.serviceimpl.master.rbac.permission.user;/**
 * @ClassName: MUserPermissionService
 * @Description: TODO
 * @Author: zxh
 * @date: 2020/8/26
 * @Version: 1.0
 */

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.perfect.bean.vo.master.rbac.permission.operation.OperationFunctionInfoVo;
import com.perfect.bean.vo.master.rbac.permission.operation.OperationMenuDataVo;
import com.perfect.core.mapper.master.rbac.permission.user.MUserPermissionMapper;
import com.perfect.core.service.master.rbac.permission.user.IMUserPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @ClassName: MUserPermissionService
 * @Description: TODO
 * @Author: zxh
 * @date: 2020/8/26
 * @Version: 1.0
 */
@Service
@Slf4j
public class MUserPermissionService implements IMUserPermissionService {

    @Autowired
    private MUserPermissionMapper mapper;

    /**
     * 菜单权限数据
     */
    @Override
    public List<OperationMenuDataVo> getPermissionMenu(Long staff_id, Long tenant_id) {
        /** 判断是否有自定义菜单 */


        /** 如果没有，获取该员工的权限：（部门权限+ 岗位权限+ 员工权限+ 角色权限）- 排除权限 */
        // 获取系统菜单
        List<OperationMenuDataVo> sysMenus = mapper.getSystemMenu(tenant_id);
        // 部门权限
        List<OperationMenuDataVo> dept_permission_menu = mapper.getPermissionMenu(staff_id, tenant_id);
        // 岗位权限
        List<OperationMenuDataVo> position_permission_menu = null;
        // 员工权限
        List<OperationMenuDataVo> staff_permission_menu = null;
        // 排除权限
        List<OperationMenuDataVo> remove_permission_menu = null;
        /** 权限合并 */
        List<OperationMenuDataVo> permission_menu = Lists.newArrayList();
        for(OperationMenuDataVo vo:sysMenus) {
            // 部门权限
            Collection<OperationMenuDataVo> dept_permission_menu_results = Collections2.filter(dept_permission_menu, item -> item.getMenu_id().equals(vo.getMenu_id()));
            // 岗位权限
            Collection<OperationMenuDataVo> position_permission_menu_results = Collections2.filter(position_permission_menu, item -> item.getMenu_id().equals(vo.getMenu_id()));
            // 员工权限
            Collection<OperationMenuDataVo> staff_permission_menu_results = Collections2.filter(staff_permission_menu, item -> item.getMenu_id().equals(vo.getMenu_id()));
            // 排除权限
            Collection<OperationMenuDataVo> remove_permission_menu_results = Collections2.filter(remove_permission_menu, item -> item.getMenu_id().equals(vo.getMenu_id()));
            System.out.println("xxxx");
        }

        /** 如果有*/


        return permission_menu;
    }

    /**
     * 操作权限数据
     */
    @Override
    public List<OperationFunctionInfoVo> getPermissionOperation(Long staff_id) {
        return null;
    }
}
