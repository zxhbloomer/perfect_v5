package com.perfect.core.mapper.sys.config.module;

import java.util.List;

import com.perfect.bean.entity.sys.config.dict.SDictTypeEntity;
import com.perfect.bean.entity.sys.config.module.SModuleEntity;
import com.perfect.bean.vo.sys.config.module.SModuleVo;
import com.perfect.common.constant.PerfectDictConstant;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 模块表 Mapper 接口
 * </p>
 *
 * @author zxh
 * @since 2019-08-16
 */
@Repository
public interface SModuleMapper extends BaseMapper<SModuleEntity> {

    /**
     * 页面查询列表
     * @param page
     * @param searchCondition
     * @return
     */
    @Select("   <script>   "
        + "     SELECT                                                        "
        + "         t1.id,                                                  "
        + "         t1.code,                                                "
        + "         ifnull(t4.rowcount,0) rowcount,                         "
        + "         t1.type,                                                "
        + "         t3.label as type_name,                                  "
        + "         t1.name,                                                "
        + "         t1.template_id,                                         "
        + "         t1.path,                                                "
        + "         t1.route_name,                                          "
        + "         t1.meta_title,                                          "
        + "         t1.meta_icon,                                           "
        + "         t1.component,                                           "
        + "         t1.perms,                                           "
        + "         t1.descr,                                               "
        + "         t1.is_del,                                               "
        + "         t1.c_id,                                                "
        + "         t1.c_time,                                              "
        + "         t1.u_id,                                                "
        + "         t1.u_time,                                              "
        + "         t1.dbversion,                                           "
        + "         t2.type AS template_type,                               "
        + "         t2.name AS template_name,                               "
        + "         t2.uri AS template_uri,                                 "
        + "         t2.base AS template_base,                               "
        + "         t2.file_size AS template_size,                               "
        + "         t2.extension AS template_extension,                     "
        + "         t2.descr AS template_descr,                             "
        + "         t2.context AS template_context,                         "
        + "         t2.is_del AS template_isdel                              "
        + "     FROM                                                        "
        + "         s_module AS t1                                          "
        + "         LEFT JOIN s_resource AS t2 ON t1.template_id = t2.id    "
        + "         LEFT JOIN v_dict_info AS t3 ON t3.code = '" + PerfectDictConstant.DICT_SYS_MODULE_TYPE + "' and t3.dict_value = t1.type    "
        + "         LEFT JOIN                                               "
        + "                   (                                             "
        + "                     select t.parent_id,                         "
        + "                            count(1) as rowcount                 "
        + "                       from s_module_button t                    "
        + "                   group by t.parent_id                          "
        + "                   ) t4 on t4.parent_id = t1.id                  "
        + "  where true "
        + "    and (t1.name like CONCAT ('%',#{p1.name,jdbcType=VARCHAR},'%') or #{p1.name,jdbcType=VARCHAR} is null) "
        + "    and (t1.code like CONCAT ('%',#{p1.code,jdbcType=VARCHAR},'%') or #{p1.code,jdbcType=VARCHAR} is null) "
        + "   <if test='p1.types != null and p1.types.length!=0' >"
        + "    and t1.type in "
        + "        <foreach collection='p1.types' item='item' index='index' open='(' separator=',' close=')'>"
        + "         #{item}  "
        + "        </foreach>   "
        + "   </if>   "
        + "    and (t1.is_del =#{p1.is_del,jdbcType=VARCHAR} or #{p1.is_del,jdbcType=VARCHAR} is null) "
        + "  </script>    ")
    IPage<SModuleVo> selectPage(Page<SModuleVo> page, @Param("p1") SModuleVo searchCondition);

    /**
     * 按条件获取所有数据，没有分页
     * @param searchCondition
     * @return
     */
    @Select("<script>"
        + " select t.* "
        + "   from s_module t "
        + "  where true "
        + "    and (t.name like CONCAT ('%',#{p1.name,jdbcType=VARCHAR},'%') or #{p1.name,jdbcType=VARCHAR} is null) "
        + "    and (t.code like CONCAT ('%',#{p1.code,jdbcType=VARCHAR},'%') or #{p1.code,jdbcType=VARCHAR} is null) "
        + "   <if test='p1.types.length!=0' >"
        + "    and t.type in "
        + "        <foreach collection='p1.types' item='item' index='index' open='(' separator=',' close=')'>"
        + "         #{item}  "
        + "        </foreach>"
        + "   </if>"
        + "    and (t.is_del =#{p1.is_del,jdbcType=VARCHAR} or #{p1.is_del,jdbcType=VARCHAR} is null) "
        + "  </script>")
    List<SModuleEntity> select(@Param("p1") SModuleVo searchCondition);

    /**
     * 没有分页，按id筛选条件
     * @param searchCondition
     * @return
     */
    @Select("<script>"
        + " select t.* "
        + "   from s_module t "
        + "  where t.id in "
        + "        <foreach collection='p1' item='item' index='index' open='(' separator=',' close=')'>"
        + "         #{item.id}  "
        + "        </foreach>"
        + "  </script>")
    List<SModuleEntity> selectIdsIn(@Param("p1") List<SModuleVo> searchCondition);


    /**
     * 按id查询
     * @param id
     * @return
     */
    @Select(" "
        + "     SELECT                                                        "
        + "         t1.id,                                                  "
        + "         t1.code,                                                "
        + "         t4.rowcount,                                            "
        + "         t1.type,                                                "
        + "         t3.label as type_name,                                  "
        + "         t1.name,                                                "
        + "         t1.template_id,                                         "
        + "         t1.path,                                                "
        + "         t1.route_name,                                          "
        + "         t1.meta_title,                                          "
        + "         t1.meta_icon,                                           "
        + "         t1.component,                                           "
        + "         t1.perms,                                               "
        + "         t1.descr,                                               "
        + "         t1.is_del,                                               "
        + "         t1.c_id,                                                "
        + "         t1.c_time,                                              "
        + "         t1.u_id,                                                "
        + "         t1.u_time,                                              "
        + "         t1.dbversion,                                           "
        + "         t2.type AS template_type,                               "
        + "         t2.name AS template_name,                               "
        + "         t2.uri AS template_uri,                                 "
        + "         t2.base AS template_base,                               "
        + "         t2.file_size AS template_size,                               "
        + "         t2.extension AS template_extension,                     "
        + "         t2.descr AS template_descr,                             "
        + "         t2.context AS template_context,                         "
        + "         t2.is_del AS template_isdel                              "
        + "     FROM                                                        "
        + "         s_module AS t1                                          "
        + "         LEFT JOIN s_resource AS t2 ON t1.template_id = t2.id    "
        + "         LEFT JOIN v_dict_info AS t3 ON t3.code = '" + PerfectDictConstant.DICT_SYS_MODULE_TYPE + "' and t3.dict_value = t1.type    "
        + "         LEFT JOIN                                               "
        + "                   (                                             "
        + "                     select t.parent_id,                         "
        + "                            count(1) as rowcount                 "
        + "                       from s_module_button t                    "
        + "                   group by t.parent_id                          "
        + "                   ) t4 on t4.parent_id = t1.id                  "
        + "  where t1.id =  #{p1}"
        + "        ")
    SModuleVo selectId(@Param("p1") Long id);

    /**
     * 按条件获取所有数据，没有分页
     * @param code
     * @return
     */
    @Select("    "
        + " select t.* "
        + "   from s_module t "
        + "  where true "
        + "    and t.code =  #{p1}   "
        + "    and (t.id  =  #{p2} or #{p2} is null)   "
        + "    and (t.id  <> #{p3} or #{p3} is null)   "
        + "    and t.is_del =  0   "
        + "      ")
    List<SModuleEntity> selectByCode(@Param("p1") String code, @Param("p2") Long equal_id, @Param("p3") Long not_equal_id);

    /**
     * 按条件获取所有数据，没有分页
     * @param name
     * @return
     */
    @Select("    "
        + " select t.* "
        + "   from s_module t "
        + "  where true "
        + "    and t.name =  #{p1}  "
        + "    and (t.id  =  #{p2} or #{p2} is null)   "
        + "    and (t.id  <> #{p3} or #{p3} is null)   "
        + "    and t.is_del =  0   "
        + "      ")
    List<SModuleEntity> selectByName(@Param("p1") String name, @Param("p2") Long equal_id, @Param("p3") Long not_equal_id);

    @Select("    "
        + " select t.* "
        + "   from s_module t "
        + "  where true "
        + "    and t.path =  #{p1}  "
        + "    and (t.id  =  #{p2} or #{p2} is null)   "
        + "    and (t.id  <> #{p3} or #{p3} is null)   "
        + "      ")
    List<SModuleEntity> selectByPath(@Param("p1") String path, @Param("p2") Long equal_id, @Param("p3") Long not_equal_id);

    @Select("    "
        + " select t.* "
        + "   from s_module t "
        + "  where true "
        + "    and t.route_name =  #{p1}  "
        + "    and (t.id  =  #{p2} or #{p2} is null)   "
        + "    and (t.id  <> #{p3} or #{p3} is null)   "
        + "      ")
    List<SModuleEntity> selectByRoute_name(@Param("p1") String route_name, @Param("p2") Long equal_id, @Param("p3") Long not_equal_id);

    /**
     * 参照资源信息
     * @param code
     * @return
     */
    @Select(" "
        + "     SELECT                                                        "
        + "         t1.id,                                                  "
        + "         t1.code,                                                "
        + "         t1.type,                                                "
        + "         t1.name,                                                "
        + "         t1.template_id,                                         "
        + "         t1.path,                                                "
        + "         t1.route_name,                                          "
        + "         t1.meta_title,                                          "
        + "         t1.meta_icon,                                           "
        + "         t1.component,                                           "
        + "         t1.descr,                                               "
        + "         t1.is_del,                                               "
        + "         t1.c_id,                                                "
        + "         t1.c_time,                                              "
        + "         t1.u_id,                                                "
        + "         t1.u_time,                                              "
        + "         t1.dbversion,                                           "
        + "         t2.type AS template_type,                               "
        + "         t2.NAME AS template_name,                               "
        + "         t2.uri AS template_uri,                                 "
        + "         t2.base AS template_base,                               "
        + "         t2.file_size AS template_size,                               "
        + "         t2.extension AS template_extension,                     "
        + "         t2.descr AS template_descr,                             "
        + "         t2.context AS template_context,                         "
        + "         t2.is_del AS template_isdel                              "
        + "     FROM                                                        "
        + "         s_module AS t1                                          "
        + "         inner join s_resource AS t2 on t1.template_id = t2.id    "
        + "  where t1.code =  #{p1}"
        + "        ")
    SModuleVo selectTemplateName(@Param("p1") String code);
}
