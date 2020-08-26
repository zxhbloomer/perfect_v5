package com.perfect.core.mapper.master.rbac.permission.user;

import com.perfect.bean.bo.rbac.permission.user.SysMenuBo;
import com.perfect.bean.vo.master.rbac.permission.operation.OperationMenuDataVo;
import com.perfect.common.constant.PerfectDictConstant;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: 获取用户权限
 * @Description: TODO
 * @Author: zxh
 * @date: 2020/8/26
 * @Version: 1.0
 */
@Repository
public interface MUserPermissionMapper {

    @Select("                                                                                                            "
        + "                    WITH recursive tab1 AS (                                                "
        + "                        SELECT                                                              "
        + "                               t0.id AS menu_id,                                            "
        + "                               t0.tenant_id,                                                "
        + "                               t0.parent_id,                                                "
        + "                               1 LEVEL,                                                     "
        + "                               t0.NAME,                                                     "
        + "                               t0.NAME AS depth_name,                                       "
        + "                               cast(t0.id AS CHAR ( 50 )) depth_id                          "
        + "                          FROM m_menu t0                                                    "
        + "                         WHERE t0.parent_id IS NULL                                         "
        + "                        UNION ALL                                                           "
        + "                        SELECT                                                              "
        + "                               t2.id AS menu_id,                                            "
        + "                               t2.tenant_id,                                                "
        + "                               t2.parent_id,                                                "
        + "                               t1.LEVEL + 1 AS LEVEL,                                       "
        + "                               t2.NAME,                                                     "
        + "                               CONCAT( t1.depth_name, '>', t2.NAME ) depth_name,            "
        + "                               CONCAT(                                                      "
        + "                                   cast(                                                    "
        + "                                   t1.depth_id AS CHAR ( 50 )),                             "
        + "                                   ',',                                                     "
        + "                                   cast(                                                    "
        + "                                   t2.id AS CHAR ( 50 ))) depth_id                          "
        + "                          FROM m_menu t2,                                                   "
        + "                               tab1 t1                                                      "
        + "                         WHERE t2.parent_id = t1.menu_id                                    "
        + "                        )                                                                   "
        + "                SELECT                                                                      "
        + "                       t2.id,                                                               "
        + "                       t2.is_default,                                                       "
        + "                       t1.menu_id,                                                          "
        + "                       t1.menu_id AS `value`,                                               "
        + "                       t1.NAME,                                                             "
        + "                       t1.NAME AS label,                                                    "
        + "                       t1.parent_id,                                                        "
        + "                       t2.root_id,                                                          "
        + "                       t1.LEVEL,                                                            "
        + "                       t2.type,                                                             "
        + "                       t2.visible,                                                          "
        + "                       t2.page_id,                                                          "
        + "                       t2.page_code,                                                        "
        + "                       t2.full_path,                                                        "
        + "                       t2.route_name,                                                       "
        + "                       t2.meta_title,                                                       "
        + "                       t2.meta_icon,                                                        "
        + "                       t2.component,                                                        "
        + "                       t2.affix,                                                            "
        + "                       t2.tenant_id                                                         "
        + "                  FROM tab1 t1                                                              "
        + "            INNER JOIN m_menu t2 ON t1.menu_id = t2.id                                      "
        + "                   AND t1.tenant_id = t2.tenant_id                                          "
        + "                 WHERE TRUE                                                                 "
        + "                   AND ( t2.tenant_id = #{p1} or #{p1} is null )                            "
        + "              ORDER BY t2.CODE;                                                             "
        + "                ")
    List<SysMenuBo> getSystemMenu(@Param("p1")Long tenant_id);

    /**
     * 获取菜单权限
     * @param staff_id
     * @param tenant_id
     * @return
     */
    @Select("                                                                                                            "
        + "             SELECT                                                                                           "
        + "                	   t2.id,                                                                                    "
        + "                	   t2.is_enable,                                                                             "
        + "                	   t2.is_default,                                                                            "
        + "                	   t1.menu_id,                                                                               "
        + "                	   t1.menu_id AS `value`,                                                                    "
        + "                	   t1.NAME,                                                                                  "
        + "                	   t1.NAME AS label,                                                                         "
        + "                	   t1.parent_id,                                                                             "
        + "                	   t2.root_id,                                                                               "
        + "                	   t1.LEVEL,                                                                                 "
        + "                	   t2.type,                                                                                  "
        + "                	   t2.visible,                                                                               "
        + "                	   t2.page_id,                                                                               "
        + "                	   t2.page_code,                                                                             "
        + "                	   t2.full_path,                                                                             "
        + "                	   t2.route_name,                                                                            "
        + "                	   t2.meta_title,                                                                            "
        + "                	   t2.meta_icon,                                                                             "
        + "                	   t2.component,                                                                             "
        + "                	   t2.affix,                                                                                 "
        + "                	   t2.tenant_id                                                                              "
        + "               FROM                                                                                           "
        + "                	   v_permission_tree t1                                                                      "
        + "         INNER JOIN m_permission_menu t2 ON t1.menu_id = t2.menu_id                                           "
        + "                AND t1.tenant_id = t2.tenant_id                                                               "
        + "         INNER JOIN m_permission t3 on t3.id = t2.permission_id                                               "
        + "         INNER JOIN m_staff t4 on t3.serial_type = '" + PerfectDictConstant.DICT_ORG_SETTING_TYPE_DEPT_SERIAL_TYPE + "' "
        + "         	   and t3.serial_id = t4.dept_id                                                                 "
        + "         	   and t4.id = #{p1}                                                                             "
        + "                and t3.`status` = true                                                                        "
        + "              WHERE TRUE                                                                                      "
        + "                AND ( t2.tenant_id = #{p2} or #{p2} is null )                                                 "
        + "                AND ( t2.is_enable = true )                                                                   "
        + "           ORDER BY t2.CODE                                                                                   "
        + "                ")
    List<OperationMenuDataVo> getPermissionMenu(@Param("p1") Long staff_id,@Param("p2")Long tenant_id);

}
