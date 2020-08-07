package com.perfect.bean.vo.master.rbac.permission;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 权限页面表
 * </p>
 *
 * @author zxh
 * @since 2020-08-07
 */
@Data
@NoArgsConstructor
@ApiModel(value = "权限页面表", description = "权限页面表")
@EqualsAndHashCode(callSuper=false)
public class MPermissionPagesVo implements Serializable {

    private static final long serialVersionUID = 3970592572301939064L;

    private Long id;

    /**
     * 配置vue export default  name时所使用的type：constants_program.P_VUE_SETTING
     */
    private String code;

    /**
     * 页面名称
     */
    private String name;

    /**
     * 模块地址：@/views/10_system/vuesetting/vue
     */
    private String component;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 页面的名称
     */
    private String meta_title;

    /**
     * 菜单中显示的icon
     */
    private String meta_icon;

    /**
     * 说明
     */
    private String descr;

    /**
     * 租户id
     */
    private Long tenant_id;

    private Long c_id;

    private LocalDateTime c_time;

    private Long u_id;

    private LocalDateTime u_time;

    /**
     * 数据版本，乐观锁使用
     */
    private Integer dbversion;
}
