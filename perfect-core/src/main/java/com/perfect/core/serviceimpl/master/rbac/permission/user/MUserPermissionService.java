package com.perfect.core.serviceimpl.master.rbac.permission.user;

/**
 * 用户权限获取的逻辑实现
 * @ClassName: MUserPermissionService
 * @Description:
 * @Author: zxh
 * @date: 2020/8/26
 * @Version: 1.0
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.perfect.bean.bo.session.user.rbac.PermissionMenuBo;
import com.perfect.bean.bo.session.user.rbac.PermissionOperationBo;
import com.perfect.bean.entity.master.menu.MMenuEntity;
import com.perfect.bean.utils.common.tree.TreeUtil;
import com.perfect.core.mapper.master.menu.MMenuMapper;
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

    @Autowired
    private MMenuMapper mMenuMapper;

    /**
     * 菜单权限数据，顶部导航栏
     */
    @Override
    public List<PermissionMenuBo> getPermissionMenuTopNav(Long tenant_id) {
        List<PermissionMenuBo> rtn = mapper.getPermissionMenuTopNav(tenant_id);
        return rtn;
    }

    /**
     * 菜单权限数据
     */
    @Override
    public List<PermissionMenuBo> getPermissionMenu(Long staff_id, Long tenant_id) {
        /** 判断是否有自定义菜单 */

        /** 如果没有，获取该员工的权限：（部门权限+ 岗位权限+ 员工权限+ 角色权限）- 排除权限 */
        // 获取系统菜单
        List<PermissionMenuBo> sysMenus = mapper.getSystemMenu(tenant_id);
        // 部门权限defaultActive
        List<PermissionMenuBo> dept_permission_menu = mapper.getPermissionMenu(staff_id, tenant_id);
        // 岗位权限
        List<PermissionMenuBo> position_permission_menu = null;
        // 员工权限
        List<PermissionMenuBo> staff_permission_menu = null;
        // 角色权限
        List<PermissionMenuBo> roles_permission_menu = null;
        // 排除权限
        List<PermissionMenuBo> remove_permission_menu = null;
        /** 权限合并 */
        for(PermissionMenuBo vo:sysMenus) {
            // 部门权限
            PermissionMenuBo dept_permission_menu_results = filterData(dept_permission_menu, vo);
            // 岗位权限
            PermissionMenuBo position_permission_menu_results = filterData(position_permission_menu, vo);
            // 员工权限
            PermissionMenuBo staff_permission_menu_results = filterData(staff_permission_menu, vo);
            // 角色权限
            PermissionMenuBo roles_permission_menu_results = filterData(roles_permission_menu, vo);
            // 排除权限
            PermissionMenuBo remove_permission_menu_results = filterData(remove_permission_menu, vo);
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
        List<PermissionMenuBo> rtnList = TreeUtil.getTreeList(sysMenus, "menu_id");

        /** 递归菜单树，设置默认菜单 */

        return rtnList;
    }

    /**
     * 获取默认页面
     * @param tenant_id
     * @return
     */
    @Override
    public String getPermissionMenuDefaultPage(Long tenant_id) {
        /** 判断是否有自定义菜单 */

        /** 如果没有，获取default */
        MMenuEntity mMenuEntity = mMenuMapper.selectOne(new QueryWrapper<MMenuEntity>()
            .eq("tenant_id", tenant_id)
            .eq("default_open", true)
            .last("LIMIT 1")
        );

        /** 如果有
         * TODO：暂时未实现
         * */


        return mMenuEntity.getFull_path();
    }

    /**
     * 操作权限数据
     * @param staff_id
     * @param tenant_id
     * @return
     */
    @Override
    public List<PermissionOperationBo> getPermissionOperation(Long staff_id, Long tenant_id) {
        /** 获取操作权限数据 */
        List<PermissionOperationBo> list = mapper.getPermissionOperation(staff_id, tenant_id);
        return list;
    }

    /**
     * 查找集合中的数据，并返回
     * @param data
     * @param target_data
     * @return
     */
    private PermissionMenuBo filterData(List<PermissionMenuBo> data, PermissionMenuBo target_data){
        if(data == null) {
            return null;
        }
        Collection<PermissionMenuBo> filter = Collections2.filter(data, item -> item.getMenu_id().equals(target_data.getMenu_id()));
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
    private boolean getPermissionValue(PermissionMenuBo dept_permission_menu,
        PermissionMenuBo position_permission_menu,
        PermissionMenuBo staff_permission_menu,
        PermissionMenuBo roles_permission_menu,
        PermissionMenuBo remove_permission_menu
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
