package com.perfect.core.service.sys.vue;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.perfect.bean.entity.sys.vue.SVuePageSettingEntity;
import com.perfect.bean.pojo.result.DeleteResult;
import com.perfect.bean.pojo.result.InsertResult;
import com.perfect.bean.pojo.result.UpdateResult;
import com.perfect.bean.vo.sys.vue.SVuePageSettingVo;

import java.util.List;

/**
 * <p>
 * vue页面配置表 服务类
 * </p>
 *
 * @author zxh
 * @since 2019-08-23
 */
public interface ISVuePageSettingService extends IService<SVuePageSettingEntity> {
    /**
     * 获取列表，页面查询
     */
    IPage<SVuePageSettingVo> selectPage(SVuePageSettingVo searchCondition) ;

    /**
     * 获取所有数据
     */
    List<SVuePageSettingVo> select(SVuePageSettingVo searchCondition) ;


    /**
     * 查询by id，返回结果
     *
     * @param id
     * @return
     */
    SVuePageSettingVo selectByid(Long id);


    /**
     * 插入一条记录（选择字段，策略插入）
     * @param entity 实体对象
     * @return
     */
    InsertResult<Integer> insert(SVuePageSettingEntity entity);

    /**
     * 更新一条记录（选择字段，策略更新）
     * @param entity 实体对象
     * @return
     */
    UpdateResult<Integer> update(SVuePageSettingEntity entity);

    /**
     * 通过name查询
     *
     */
    List<SVuePageSettingVo> selectByName(String name);

    /**
     * 通过key查询
     *
     */
    List<SVuePageSettingVo> selectByCode(String code);

    /**
     * 批量物理删除
     * @param searchCondition
     * @return
     */
    DeleteResult<Integer> realDeleteByIdsIn(List<SVuePageSettingVo> searchCondition);
}
