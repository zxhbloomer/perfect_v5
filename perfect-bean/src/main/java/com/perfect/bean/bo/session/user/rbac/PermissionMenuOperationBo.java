package com.perfect.bean.bo.session.user.rbac;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单、权限数据
 * @ClassName: PermissionBo
 * @Description:
 * @Author: zxh
 * @date: 2019/11/14
 * @Version: 1.0
 */
@Data
@ApiModel(value = "菜单、权限数据", description = "菜单、权限数据")
@EqualsAndHashCode(callSuper=false)
public class PermissionMenuOperationBo implements Serializable {

    private static final long serialVersionUID = 4041344270066977596L;

    /**
     * 会话ID
     */
    private String session_id;

    /**
     * staff_ID
     */
    private Long staff_id;

    /**
     * 租户id
     */
    private Long tenant_id;

    /**
     * 菜单权限数据
     */
    private List<PermissionMenuBo> user_permission_top_nav;

    /**
     * 菜单权限数据
     */
    private List<PermissionMenuBo> user_permission_menu;

    /**
     * 操作权限数据
     */
    private List<PermissionOperationBo> user_permission_operation;

    /**
     * 默认页面
     */
    private String default_page;
}
