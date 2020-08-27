package com.perfect.core.serviceimpl.master.rbac.permission.user;

/**
 * 用户权限获取的逻辑实现
 * @ClassName: MUserPermissionService
 * @Description:
 * @Author: zxh
 * @date: 2020/8/26
 * @Version: 1.0
 */

import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.perfect.bean.utils.common.tree.TreeUtil;
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
        // 角色权限
        List<OperationMenuDataVo> roles_permission_menu = null;
        // 排除权限
        List<OperationMenuDataVo> remove_permission_menu = null;
        /** 权限合并 */
        List<OperationMenuDataVo> permission_menu = Lists.newArrayList();
        for(OperationMenuDataVo vo:sysMenus) {
            // 部门权限
            OperationMenuDataVo dept_permission_menu_results = filterData(dept_permission_menu, vo);
            // 岗位权限
            OperationMenuDataVo position_permission_menu_results = filterData(position_permission_menu, vo);
            // 员工权限
            OperationMenuDataVo staff_permission_menu_results = filterData(staff_permission_menu, vo);
            // 角色权限
            OperationMenuDataVo roles_permission_menu_results = filterData(roles_permission_menu, vo);
            // 排除权限
            OperationMenuDataVo remove_permission_menu_results = filterData(remove_permission_menu, vo);
            /** 判断权限：（部门权限+ 岗位权限+ 员工权限+ 角色权限）- 排除权限 */
            vo.setIs_enable(getPermissionValue(dept_permission_menu_results,
                position_permission_menu_results,
                staff_permission_menu_results,
                roles_permission_menu_results,
                remove_permission_menu_results));
        }

        /** 如果有
         * TODO：暂时未实现
         * */


        /** 设置菜单树bean，并返回 */
        List<OperationMenuDataVo> rtnList = TreeUtil.getTreeList(sysMenus, "menu_id");
        return rtnList;
    }

    /**
     * 操作权限数据
     * @param staff_id
     * @param tenant_id
     * @return
     */
    @Override
    public List<OperationFunctionInfoVo> getPermissionOperation(Long staff_id, Long tenant_id) {
        return null;
    }

    /**
     * 查找集合中的数据，并返回
     * @param data
     * @param target_data
     * @return
     */
    private OperationMenuDataVo filterData(List<OperationMenuDataVo> data, OperationMenuDataVo target_data){
        if(data == null) {
            return null;
        }
        Collection<OperationMenuDataVo> filter = Collections2.filter(data, item -> item.getMenu_id().equals(target_data.getMenu_id()));
        return Iterables.getOnlyElement(filter);
    }

    /**
     * 判断权限：（部门权限+ 岗位权限+ 员工权限+ 角色权限）- 排除权限
     * @param dept_permission_menu          部门权限
     * @param position_permission_menu      岗位权限
     * @param staff_permission_menu         员工权限
     * @param roles_permission_menu         角色权限
     * @param remove_permission_menu        排除权限
     * @return
     */
    private boolean getPermissionValue(OperationMenuDataVo dept_permission_menu,
        OperationMenuDataVo position_permission_menu,
        OperationMenuDataVo staff_permission_menu,
        OperationMenuDataVo roles_permission_menu,
        OperationMenuDataVo remove_permission_menu
        ){
        boolean rtn = false;
        if(dept_permission_menu != null){
            rtn = rtn || dept_permission_menu.getIs_enable();
        }
        if(position_permission_menu != null){
            rtn = rtn || position_permission_menu.getIs_enable();
        }
        if(staff_permission_menu != null){
            rtn = rtn || staff_permission_menu.getIs_enable();
        }
        if(roles_permission_menu != null){
            rtn = rtn || roles_permission_menu.getIs_enable();
        }
        if(remove_permission_menu != null){
            rtn = rtn & remove_permission_menu.getIs_enable();
        }

        return  rtn;
    }
}
