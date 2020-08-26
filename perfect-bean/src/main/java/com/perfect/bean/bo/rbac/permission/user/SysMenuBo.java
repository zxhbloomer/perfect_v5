package com.perfect.bean.bo.rbac.permission.user;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName: UserSessionBo
 * @Description: 系统菜单
 * @Author: zxh
 * @date: 2019/11/14
 * @Version: 1.0
 */
@Data
@ApiModel(value = "系统菜单", description = "系统菜单")
@EqualsAndHashCode(callSuper=false)
public class SysMenuBo implements Serializable {

    private static final long serialVersionUID = -5238723479384279335L;

    /**
     * 菜单id
     */
    private Long menu_id;
    /**
     * 租户id
     */
    private Long tenant_id;
    /**
     * 上级结点id
     */
    private Long parent_id;
    /**
     * 层级
     */
    private int level;
}
