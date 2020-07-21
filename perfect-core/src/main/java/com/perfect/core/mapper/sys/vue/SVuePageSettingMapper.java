package com.perfect.core.mapper.sys.vue;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.perfect.bean.entity.sys.vue.SVuePageSettingEntity;
import com.perfect.bean.vo.sys.vue.SVuePageSettingVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 字典数据表 Mapper 接口
 * </p>
 *
 * @author zxh
 * @since 2020-04-03
 */
@Repository
public interface SVuePageSettingMapper extends BaseMapper<SVuePageSettingEntity> {

    /**
     * 页面查询列表
     * @param page
     * @param searchCondition
     * @return
     */
    @Select("                                                                                                            "
        + "  SELECT                                                                                                      "
        + "         t.*,                                                                                                 "
        + "         c_staff.name as c_name,                                                                              "
        + "         u_staff.name as u_name                                                                               "
        + "    FROM                                                                                                      "
        + "  	    s_vue_page_setting t                                                                                 "
        + "  LEFT JOIN m_staff c_staff ON t.c_id = c_staff.id                                                            "
        + "  LEFT JOIN m_staff u_staff ON t.u_id = u_staff.id                                                            "
        + "  where true                                                                                                  "
        + "    and (t.name like CONCAT ('%',#{p1.name,jdbcType=VARCHAR},'%') or #{p1.name,jdbcType=VARCHAR} is null)     "
        + "    and (t.meta_title  like CONCAT ('%',#{p1.meta_title,jdbcType=VARCHAR},'%') or #{p1.meta_title,jdbcType=VARCHAR} is null) "
        + "    and (t.code  like CONCAT ('%',#{p1.code,jdbcType=VARCHAR},'%') or #{p1.code,jdbcType=VARCHAR} is null)    "
        + "                                                                                                              ")
    IPage<SVuePageSettingVo> selectPage(Page page, @Param("p1") SVuePageSettingVo searchCondition);

    /**
     * 按条件获取所有数据，没有分页
     * @param searchCondition
     * @return
     */
    @Select("                                                                                                            "
        + "  SELECT                                                                                                      "
        + "         t.*,                                                                                                 "
        + "         c_staff.name as c_name,                                                                              "
        + "         u_staff.name as u_name                                                                               "
        + "  FROM                                                                                                        "
        + "  	s_vue_page_setting t                                                                                     "
        + "  LEFT JOIN m_staff c_staff ON t.c_id = c_staff.id                                                            "
        + "  LEFT JOIN m_staff u_staff ON t.u_id = u_staff.id                                                            "
        + "  where true                                                                                                  "
        + "    and (t.name like CONCAT ('%',#{p1.name,jdbcType=VARCHAR},'%') or #{p1.name,jdbcType=VARCHAR} is null)     "
        + "    and (t.meta_title  like CONCAT ('%',#{p1.meta_title,jdbcType=VARCHAR},'%') or #{p1.meta_title,jdbcType=VARCHAR} is null) "
        + "    and (t.code  like CONCAT ('%',#{p1.code,jdbcType=VARCHAR},'%') or #{p1.code,jdbcType=VARCHAR} is null)    "
        + "                                                                                                              ")
    List<SVuePageSettingVo> select(@Param("p1") SVuePageSettingVo searchCondition);

    /**
     * 没有分页，按id筛选条件
     * @param searchCondition
     * @return
     */
    @Select("   <script>   "
        + "  SELECT                                                                                        "
        + "       *                                                                                        "
        + "  FROM                                                                                          "
        + "  	s_vue_page_setting t                                                                       "
        + "  where t.id in                                                                                 "
        + "        <foreach collection='p1' item='item' index='index' open='(' separator=',' close=')'>    "
        + "         #{item.id}  "
        + "        </foreach>    "
        + "  </script>    ")
    List<SVuePageSettingVo> selectIdsIn(@Param("p1") List<SVuePageSettingVo> searchCondition);

    /**
     * 按条件获取所有数据，没有分页
     * @param name
     * @return
     */
    @Select("    "
        + "  SELECT                                                                                        "
        + "       *                                                                                        "
        + "  FROM                                                                                          "
        + "  	s_vue_page_setting t                                                                       "
        + "  where true                                                                                    "
        + "    and t.name =  #{p1}                                                                         "
        + "      ")
    List<SVuePageSettingVo> selectByName(@Param("p1") String name);

    /**
     * 按条件获取所有数据，没有分页
     * @param code
     * @return
     */
    @Select("    "
        + "  SELECT                                                                                        "
        + "       *                                                                                        "
        + "  FROM                                                                                          "
        + "  	s_vue_page_setting t                                                                       "
        + "  where true                                                                                    "
        + "    and t.code =  #{p1}                                                                         "
        + "      ")
    List<SVuePageSettingVo> selectByCode(@Param("p1") String code);

    /**
     * 按条件获取所有数据，没有分页
     * @param value
     * @return
     */
    @Select("    "
        + "  SELECT                                                                                        "
        + "       *                                                                                        "
        + "  FROM                                                                                          "
        + "  	s_vue_page_setting t                                                                       "
        + "  where true                                                                                    "
        + "    and t.value =  #{p1}                                                                        "
        + "      ")
    List<SVuePageSettingVo> selectByValue(@Param("p1") String value);

    /**
     * 按条件获取所有数据，没有分页
     * @param id
     * @return
     */
    @Select("    "
        + "  SELECT                                                                                                      "
        + "         t.*,                                                                                                 "
        + "         c_staff.name as c_name,                                                                              "
        + "         u_staff.name as u_name                                                                               "
        + "    FROM                                                                                                      "
        + "  	    s_vue_page_setting t                                                                                 "
        + "  LEFT JOIN m_staff c_staff ON t.c_id = c_staff.id                                                            "
        + "  LEFT JOIN m_staff u_staff ON t.u_id = u_staff.id                                                            "
        + "  where t.id =  #{p1}                                                                          "
        + "      ")
    SVuePageSettingVo selectId(@Param("p1") Long id);

}
