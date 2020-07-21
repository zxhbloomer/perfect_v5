package com.perfect.bean.entity.sys.config.module;

import com.baomidou.mybatisplus.annotation.*;
import com.perfect.bean.entity.base.entity.v1.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 模块：目录、菜单、页面
 * </p>
 *
 * @author zxh
 * @since 2019-09-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("s_module")
public class SModuleEntity extends BaseEntity<SModuleEntity> implements Serializable {

    private static final long serialVersionUID = 5988423556456014305L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 模块编号
     */
    @TableField("code")
    private String code;

    /**
     * 类型：（m目录 c菜单 p页面）
     */
    @TableField("type")
    private String type;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 描述
     */
    @TableField("descr")
    private String descr;

    /**
     * 模版id：资源文件中获取
     */
    @TableField("template_id")
    private Long template_id;

    /**
     * 请求地址
     */
    @TableField("path")
    private String path;

    /**
     * 路由名，需要唯一，很重要，且需要vue这里手工录入
     */
    @TableField("route_name")
    private String route_name;

    /**
     * 菜单名
     */
    @TableField("meta_title")
    private String meta_title;

    /**
     * 菜单名
     */
    @TableField("meta_icon")
    private String meta_icon;

    /**
     * 模块
     */
    @TableField("component")
    private String component;

    /**
     * 附在导航栏不可关闭
     */
    @TableField("affix")
    private Boolean affix;

    /**
     * 权限标识
     */
    @TableField("perms")
    private String perms;

    /**
     * 是否删除
     */
    @TableField("is_del")
    private Boolean is_del;

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
