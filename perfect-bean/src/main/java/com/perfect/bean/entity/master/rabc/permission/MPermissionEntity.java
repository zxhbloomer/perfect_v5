package com.perfect.bean.entity.master.rabc.permission;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author zxh
 * @since 2020-07-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_permission")
public class MPermissionEntity implements Serializable {

    private static final long serialVersionUID = 9099523212695938349L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 类型：10（部门权限）、20（岗位权限）、30（用户权限）、40（排除权限）
     */
    @TableField("type")
    private String type;

    /**
     * 关联单号类型
     */
    @TableField("serial_type")
    private String serial_type;

    /**
     * 关联单号
     */
    @TableField("serial_id")
    private Long serial_id;

    /**
     * 菜单名称
     */
    @TableField("name")
    private String name;

    /**
     * 关联单号
     */
    @TableField("menu_id")
    private Long menu_id;

    /**
     * 0:未使用，1:已使用
     */
    @TableField("status")
    private Boolean status;

    /**
     * 说明
     */
    @TableField("descr")
    private String descr;

    /**
     * 是否是已经删除(1:true-已删除,0:false-未删除)
     */
    @TableField("is_del")
    private Boolean is_del;

    /**
     * 是否禁用(1:true-已启用,0:false-未启用)
     */
    @TableField("is_enable")
    private Boolean is_enable;

    /**
     * 租户id
     */
    @TableField("tenant_id")
    private Long tenant_id;

    @TableField(value="c_id", fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NOT_EMPTY)
    private Long c_id;

    @TableField(value="c_time", fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NOT_EMPTY)
    private LocalDateTime c_time;

    @TableField(value="u_id", fill = FieldFill.INSERT_UPDATE)
    private Long u_id;

    @TableField(value="u_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime u_time;

    /**
     * 数据版本，乐观锁使用
     */
    @Version
    @TableField(value="dbversion", fill = FieldFill.INSERT_UPDATE)
    private Integer dbversion;


}
