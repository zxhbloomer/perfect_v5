package com.perfect.core.mapper.master.rabc.permission.dept;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.perfect.bean.entity.master.rabc.permission.MPermissionEntity;
import com.perfect.bean.vo.master.org.MOrgTreeVo;
import com.perfect.common.constant.PerfectDictConstant;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 权限类页面左侧的树 Mapper 接口
 * </p>
 *
 * @author zxh
 * @since 2020-07-27
 */
@Repository
public interface MPermissionOrgMapper extends BaseMapper<MPermissionEntity> {


    String COMMON_TREE_SELECT = "                                                                                                "
        + "         WITH recursive cte AS (                                                                             "
        + "               SELECT                                                                                        "
        + "                 t0.id,                                                                                      "
        + "                 t0.id as value,                                                                             "
        + "                 t0.tenant_id,                                                                              "
        + "                 t0.parent_id ,                                                                              "
        + "                 1 level,                                                                                    "
        + "                 cast(t0.id as char(50)) depth_id                                                                "
        + "               FROM                                                                                          "
        + "                       m_org t0                                                                              "
        + "               where t0.parent_id is null                                                                    "
        + "         UNION ALL                                                                                           "
        + "               SELECT                                                                                        "
        + "                 t2.id,                                                                                      "
        + "                 t2.id as value,                                                                             "
        + "                 t2.tenant_id,                                                                              "
        + "                 t2.parent_id,                                                                               "
        + "                 t1.level + 1 as level,                                                                      "
        + "                 CONCAT( cast(t1.depth_id as char(50)),',',cast(t2.id as char(50))) depth_id                         "
        + "               FROM                                                                                          "
        + "                       m_org t2,                                                                             "
        + "                       cte t1                                                                                "
        + "               WHERE                                                                                         "
        + "                 t2.parent_id = t1.id                                                                        "
        + "               )                                                                                             "
        + "                  select t1.* ,                                                                              "
        + "                         t2.code,                                                                            "
        + "                         t3.name as label,                                                                   "
        + "                         t3.name,                                                                            "
        + "                         t3.simple_name,                                                                     "
        + "                         t2.type,                                                                            "
        + "                         t4.label as type_text,                                                              "
        + "                         t2.son_count,                                                                       "
        + "                         t2.u_time,                                                                          "
        + "                         t2.dbversion ,                                                                      "
        + "                         t2.serial_id ,                                                                      "
        + "                         t2.serial_type,                                                                     "
        + "                         t5.dept_permission_count                                                            "
        + "                    from cte t1                                                                              "
        + "          inner join m_org t2 on t1.id = t2.id                                                               "
        + "           left join v_org_name t3 on t3.serial_type = t2.serial_type and t3.serial_id = t2.serial_id        "
        + "           left join v_dict_info t4 on t4.dict_value = t2.type and t4.code = '"+PerfectDictConstant.DICT_ORG_SETTING_TYPE+"'     "
        + "	          left join (                                                                                       "
        + "	                      select subt.serial_type,                                                              "
        + "	          						       subt.serial_id,                                                      "
        + "	          									 count(1) as dept_permission_count                              "
        + "	          						  from m_permission subt                                                    "
        + "	          					GROUP BY subt.serial_type,                                                      "
        + "	          					         subt.serial_id                                                         "
        + "	                     ) t5 on t2.serial_type = t5.serial_type and t2.serial_id = t5.serial_id                "
        + "                                                                          ";

    /**
     * 左侧树查询
     */
    @Select(" <script>    "
        + COMMON_TREE_SELECT
        + "  where true                                                                                              "
        + "    and (t1.tenant_id = #{p1.tenant_id,jdbcType=BIGINT} or #{p1.tenant_id,jdbcType=BIGINT} is null)       "
        + "    and (t2.code like CONCAT (#{p1.code,jdbcType=VARCHAR},'%') or #{p1.code,jdbcType=VARCHAR} is null)    "
        + "    and (t2.serial_type != '"+ PerfectDictConstant.DICT_ORG_SETTING_TYPE_POSITION_SERIAL_TYPE +"')        "
        + "   <if test='p1.codes != null and p1.codes.length!=0' >                                                   "
        + "    and t3.serial_type in                                                                                 "
        + "        <foreach collection='p1.codes' item='item' index='index' open='(' separator=',' close=')'>        "
        + "         #{item}                                                                                          "
        + "        </foreach>                                                                                        "
        + "   </if>                                                                                                  "
        + "   <if test='p1.current_code != null ' >                                                                  "
        + "    and (                                                                                                 "
        + "          case when length(t2.code) >= length(#{p1.current_code,jdbcType=VARCHAR}) then                   "
        + "                              t2.code like CONCAT (#{p1.current_code,jdbcType=VARCHAR},'%')               "
        + "          else true                                                                                       "
        + "           end                                                                                            "
        + "         )                                                                                                "
        + "   </if>                                                                                                  "
        + "  order by t2.code                                                                                        "
        + " </script>     ")
    List<MOrgTreeVo> getTreeList(@Param("p1") MOrgTreeVo searchCondition);

}
