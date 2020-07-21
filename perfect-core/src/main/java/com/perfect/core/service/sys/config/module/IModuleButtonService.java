package com.perfect.core.service.sys.config.module;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.perfect.bean.entity.sys.config.module.SModuleButtonEntity;
import com.perfect.bean.entity.sys.config.module.SModuleButtonEntity;
import com.perfect.bean.pojo.result.DeleteResult;
import com.perfect.bean.pojo.result.InsertResult;
import com.perfect.bean.pojo.result.UpdateResult;
import com.perfect.bean.vo.sys.config.dict.SDictDataVo;
import com.perfect.bean.vo.sys.config.module.SModuleButtonVo;

/**
 * <p>
 * 模块按钮表 服务类
 * </p>
 *
 * @author zxh
 * @since 2019-08-16
 */
public interface IModuleButtonService extends IService<SModuleButtonEntity> {
    /**
     * 获取列表，页面查询
     */
    IPage<SModuleButtonVo> selectPage(SModuleButtonVo searchCondition) ;

    /**
     * 获取所有数据
     */
    List<SModuleButtonVo> select(SModuleButtonVo searchCondition) ;

    /**
     * 获取所选id的数据
     */
    List<SModuleButtonVo> selectIdsIn(List<SModuleButtonVo> searchCondition) ;

    /**
     * 批量物理删除
     * @param searchCondition
     * @return
     */
    DeleteResult<Integer> realDeleteByIdsIn(List<SModuleButtonVo> searchCondition);

    /**
     * 插入一条记录（选择字段，策略插入）
     * @param entity 实体对象
     * @return
     */
    InsertResult<Integer> insert(SModuleButtonEntity entity);

    /**
     * 更新一条记录（选择字段，策略更新）
     * @param entity 实体对象
     * @return
     */
    UpdateResult<Integer> update(SModuleButtonEntity entity);

    /**
     * 查询by id，返回结果
     *
     * @param id
     * @return
     */
    SModuleButtonVo selectByid(Long id);

    /**
     * sort保存
     */
    UpdateResult<List<SModuleButtonVo>> saveList(List<SModuleButtonVo> data);
}
