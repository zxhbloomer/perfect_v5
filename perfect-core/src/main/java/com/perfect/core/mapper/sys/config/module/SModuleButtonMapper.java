package com.perfect.core.mapper.sys.config.module;

import java.util.List;

import com.perfect.bean.entity.sys.config.dict.SDictDataEntity;
import com.perfect.bean.entity.sys.config.module.SModuleButtonEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.perfect.bean.entity.sys.config.module.SModuleButtonEntity;
import com.perfect.bean.vo.sys.config.module.SModuleButtonVo;

/**
 * <p>
 * 模块按钮表 Mapper 接口
 * </p>
 *
 * @author zxh
 * @since 2019-08-16
 */
@Repository
public interface SModuleButtonMapper extends BaseMapper<SModuleButtonEntity> {

    /**
     * 页面查询列表
     * @param page
     * @param searchCondition
     * @return
     */
    @Select("                         "
        + "       SELECT                                                        "
        + "           t1.id,                                                    "
        + "           t1.parent_id,                                             "
        + "           t2.code as module_code,                                   "
        + "           t2.name as module_name,                                   "
        + "           t1.code,                                                  "
        + "           t1.name,                                                  "
        + "           t1.sort,                                                  "
        + "           t1.perms,                                                 "
        + "           t2.name as module_name,                                   "
        + "           t1.parent_id,                                             "
        + "           t1.c_id,                                                  "
        + "           t1.c_time,                                                "
        + "           t1.u_id   ,                                               "
        + "           t1.u_time  ,                                              "
        + "           t1.dbversion,                                             "
        + "           t3.max_sort,                                              "
        + "           t3.min_sort                                               "
        + "       FROM                                                          "
        + "           s_module_button AS t1                                     "
        + "   left join s_module AS t2 on t1.parent_id = t2.id                  "
        + "  	INNER JOIN (                                                    "
        + "  		SELECT                                                      "
        + "  			count(1) - 1 AS max_sort,                               "
        + "  			0 AS min_sort,                                          "
        + "  			subt1.parent_id                                         "
        + "  		FROM                                                        "
        + "  			s_module_button subt1                                   "
        + "  		GROUP BY                                                    "
        + "  			subt1.parent_id                                         "
        + "  	) t3 ON t1.parent_id = t3.parent_id                             "
        + "  where true "
        + "    and (t2.code like CONCAT ('%',#{p1.module_code,jdbcType=VARCHAR},'%') or #{p1.module_code,jdbcType=VARCHAR} is null) "
        + "    and (t2.name like CONCAT ('%',#{p1.module_name,jdbcType=VARCHAR},'%') or #{p1.module_name,jdbcType=VARCHAR} is null) "
        + "             ")
    IPage<SModuleButtonVo> selectPage(Page<SModuleButtonVo> page, @Param("p1") SModuleButtonVo searchCondition);

    /**
     * 按条件获取所有数据，没有分页
     * @param searchCondition
     * @return
     */
    @Select("                "
        + "       SELECT                                                        "
        + "           t1.id,                                                    "
        + "           t1.parent_id,                                             "
        + "           t2.code as module_code,                                   "
        + "           t2.name as module_name,                                   "
        + "           t1.code,                                                  "
        + "           t1.name,                                                  "
        + "           t1.sort,                                                  "
        + "           t1.perms,                                                 "
        + "           t2.name as module_name,                                   "
        + "           t1.parent_id,                                             "
        + "           t1.c_id,                                                  "
        + "           t1.c_time,                                                "
        + "           t1.u_id   ,                                               "
        + "           t1.u_time  ,                                              "
        + "           t1.dbversion,                                             "
        + "           t3.max_sort,                                              "
        + "           t3.min_sort                                               "
        + "       FROM                                                          "
        + "           s_module_button AS t1                                     "
        + "   left join s_module AS t2 on t1.parent_id = t2.id                  "
        + "  	INNER JOIN (                                                    "
        + "  		SELECT                                                      "
        + "  			count(1) - 1 AS max_sort,                               "
        + "  			0 AS min_sort,                                          "
        + "  			subt1.parent_id                                    "
        + "  		FROM                                                      "
        + "  			s_module_button subt1                                     "
        + "  		GROUP BY                                                  "
        + "  			subt1.parent_id                                    "
        + "  	) t3 ON t1.parent_id = t3.parent_id                     "
        + "  where true "
        + "    and (t2.code like CONCAT ('%',#{p1.module_code,jdbcType=VARCHAR},'%') or #{p1.module_code,jdbcType=VARCHAR} is null) "
        + "    and (t2.name like CONCAT ('%',#{p1.module_name,jdbcType=VARCHAR},'%') or #{p1.module_name,jdbcType=VARCHAR} is null) "
        + "            ")
    List<SModuleButtonVo> select(@Param("p1") SModuleButtonVo searchCondition);

    /**
     * 没有分页，按id筛选条件
     * @param searchCondition
     * @return
     */
    @Select("<script>"
        + "       SELECT                                                        "
        + "           t1.id,                                                    "
        + "           t1.parent_id,                                             "
        + "           t2.code as module_code,                                   "
        + "           t2.name as module_name,                                   "
        + "           t1.code,                                                  "
        + "           t1.name,                                                  "
        + "           t1.sort,                                                  "
        + "           t1.perms,                                                 "
        + "           t2.name as module_name,                                   "
        + "           t1.parent_id,                                             "
        + "           t1.c_id,                                                  "
        + "           t1.c_time,                                                "
        + "           t1.u_id   ,                                               "
        + "           t1.u_time  ,                                              "
        + "           t1.dbversion,                                             "
        + "           t3.max_sort,                                              "
        + "           t3.min_sort                                               "
        + "       FROM                                                          "
        + "           s_module_button AS t1                                     "
        + "   left join s_module AS t2 on t1.parent_id = t2.id                  "
        + "  	INNER JOIN (                                                    "
        + "  		SELECT                                                      "
        + "  			count(1) - 1 AS max_sort,                               "
        + "  			0 AS min_sort,                                          "
        + "  			subt1.parent_id                                    "
        + "  		FROM                                                      "
        + "  			s_module_button subt1                                     "
        + "  		GROUP BY                                                  "
        + "  			subt1.parent_id                                    "
        + "  	) t3 ON t1.parent_id = t3.parent_id                     "
        + "  where t1.id in "
        + "        <foreach collection='p1' item='item' index='index' open='(' separator=',' close=')'>"
        + "         #{item.id}  "
        + "        </foreach>"
        + "  </script>")

    List<SModuleButtonVo> selectIdsIn(@Param("p1") List<SModuleButtonVo> searchCondition);


    /**
     * 按id查询
     * @param id
     * @return
     */
    @Select("        "
        + "       SELECT                                                        "
        + "           t1.id,                                                    "
        + "           t1.parent_id,                                             "
        + "           t2.code as module_code,                                   "
        + "           t2.name as module_name,                                   "
        + "           t1.code,                                                  "
        + "           t1.name,                                                  "
        + "           t1.sort,                                                  "
        + "           t1.perms,                                                 "
        + "           t2.name as module_name,                                   "
        + "           t1.parent_id,                                             "
        + "           t1.c_id,                                                  "
        + "           t1.c_time,                                                "
        + "           t1.u_id   ,                                               "
        + "           t1.u_time  ,                                              "
        + "           t1.dbversion,                                             "
        + "           t3.max_sort,                                              "
        + "           t3.min_sort                                               "
        + "       FROM                                                          "
        + "           s_module_button AS t1                                     "
        + "   left join s_module AS t2 on t1.parent_id = t2.id                  "
        + "  	INNER JOIN (                                                    "
        + "  		SELECT                                                      "
        + "  			count(1) - 1 AS max_sort,                               "
        + "  			0 AS min_sort,                                          "
        + "  			subt1.parent_id                                         "
        + "  		FROM                                                        "
        + "  			s_module_button subt1                                   "
        + "  		GROUP BY                                                    "
        + "  			subt1.parent_id                                         "
        + "  	) t3 ON t1.parent_id = t3.parent_id                             "
        + "    where true                                                       "
        + "      and t1.id =  #{p1}                                             "
        + "        ")
    SModuleButtonVo selectId(@Param("p1") Long id);

    /**
     * 按条件获取所有数据，没有分页
     * @param parent_id
     * @return
     */
    @Select("    "
        + " select t.* "
        + "   from s_module_button t "
        + "  where true "
        + "    and t.parent_id =  #{p1}   "
        + "    and (t.id  =  #{p2} or #{p2} is null)   "
        + "    and (t.id  <> #{p3} or #{p3} is null)   "
        + "    and t.code = #{p4}   "
        + "      ")
    List<SModuleButtonEntity> selectByCode(@Param("p1") Long parent_id, @Param("p2") Long equal_id, @Param("p3") Long not_equal_id, @Param("p4") String code );

    /**
     * 获取排序最大序号
     */
    @Select("    "
            + "   SELECT  "
            + "     (MAX(IFNULL(t.sort, 0)) + 1) AS sort "
            + "     FROM s_module_button t "
            + "    WHERE t.parent_id =  #{p1}"
            + " GROUP BY t.parent_id"
            + "      ")
    SModuleButtonEntity getSortNum(@Param("p1") Long parent_id);

}
