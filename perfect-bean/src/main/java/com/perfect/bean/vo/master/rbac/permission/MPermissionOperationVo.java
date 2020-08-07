package com.perfect.bean.vo.master.rbac.permission;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 权限页面操作表
 * </p>
 *
 * @author zxh
 * @since 2020-08-07
 */
@Data
@NoArgsConstructor
@ApiModel(value = "权限页面操作表", description = "权限页面操作表")
@EqualsAndHashCode(callSuper=false)
public class MPermissionOperationVo implements Serializable {

    private static final long serialVersionUID = -5957459096487742464L;

    private Long id;

    /**
     * 页面id
     */
    private Long page_id;

    /**
     * 类型：PAGE：主页面上，TABLE：表格上，POPUP：弹出框上
     */
    private String type;

    /**
     * 按钮id
     */
    private Long function_id;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 权限标识
     */
    private String perms;

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
