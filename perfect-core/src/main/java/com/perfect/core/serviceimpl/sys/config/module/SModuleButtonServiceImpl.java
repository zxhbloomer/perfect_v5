package com.perfect.core.serviceimpl.sys.config.module;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.perfect.bean.entity.sys.config.module.SModuleButtonEntity;
import com.perfect.bean.pojo.result.CheckResult;
import com.perfect.bean.pojo.result.DeleteResult;
import com.perfect.bean.pojo.result.InsertResult;
import com.perfect.bean.pojo.result.UpdateResult;
import com.perfect.bean.result.utils.v1.CheckResultUtil;
import com.perfect.bean.result.utils.v1.DeleteResultUtil;
import com.perfect.bean.result.utils.v1.InsertResultUtil;
import com.perfect.bean.result.utils.v1.UpdateResultUtil;
import com.perfect.bean.vo.sys.config.module.SModuleButtonVo;
import com.perfect.common.exception.BusinessException;
import com.perfect.common.exception.UpdateErrorException;
import com.perfect.common.utils.bean.BeanUtilsSupport;
import com.perfect.core.mapper.sys.config.module.SModuleButtonMapper;
import com.perfect.core.service.base.v1.BaseServiceImpl;
import com.perfect.core.service.sys.config.module.IModuleButtonService;
import com.perfect.core.utils.mybatis.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 模块表 服务实现类
 * </p>
 *
 * @author zxh
 * @since 2019-08-16
 */
@Service
public class SModuleButtonServiceImpl extends BaseServiceImpl<SModuleButtonMapper, SModuleButtonEntity> implements IModuleButtonService {

    @Autowired
    private SModuleButtonMapper mapper;

    /**
     * 获取列表，页面查询
     * 
     * @param searchCondition
     * @return
     */
    @Override
    public IPage<SModuleButtonVo> selectPage(SModuleButtonVo searchCondition) {
        // 分页条件
        Page<SModuleButtonVo> pageCondition =
            new Page(searchCondition.getPageCondition().getCurrent(), searchCondition.getPageCondition().getSize());
        // 通过page进行排序
        PageUtil.setSort(pageCondition, searchCondition.getPageCondition().getSort());
        return mapper.selectPage(pageCondition, searchCondition);
    }

    /**
     * 获取列表，查询所有数据
     * 
     * @param searchCondition
     * @return
     */
    @Override
    public List<SModuleButtonVo> select(SModuleButtonVo searchCondition) {
        // 查询 数据
        List<SModuleButtonVo> list = mapper.select(searchCondition);
        return list;
    }

    /**
     * 获取列表，根据id查询所有数据
     * 
     * @param searchCondition
     * @return
     */
    @Override
    public List<SModuleButtonVo> selectIdsIn(List<SModuleButtonVo> searchCondition) {
        // 查询 数据
        List<SModuleButtonVo> list = mapper.selectIdsIn(searchCondition);
        return list;
    }

    /**
     * 批量删除
     *
     * @param searchCondition
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public DeleteResult<Integer> realDeleteByIdsIn(List<SModuleButtonVo> searchCondition) {
        List<Long> idList = new ArrayList<>();
        searchCondition.forEach(bean -> {
            idList.add(bean.getId());
        });
        int result=mapper.deleteBatchIds(idList);
        return DeleteResultUtil.OK(result);
    }

    /**
     * 插入一条记录（选择字段，策略插入）
     * 
     * @param entity 实体对象
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public InsertResult<Integer> insert(SModuleButtonEntity entity) {
        // 插入前check
        CheckResult cr = checkLogic(entity, CheckResult.INSERT_CHECK_TYPE);
        if (cr.isSuccess() == false) {
            throw new BusinessException(cr.getMessage());
        }
        // 设置：字典键值和字典排序
        SModuleButtonEntity data = mapper.getSortNum(entity.getParent_id());
        if (null == data) {
            entity.setSort(0);
        } else {
            entity.setSort(data.getSort());
        }
        // 插入逻辑保存
        return InsertResultUtil.OK(mapper.insert(entity));
    }

    /**
     * 更新一条记录（选择字段，策略更新）
     * 
     * @param entity 实体对象
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public UpdateResult<Integer> update(SModuleButtonEntity entity) {
        // 更新前check
        CheckResult cr = checkLogic(entity, CheckResult.UPDATE_CHECK_TYPE);
        if (cr.isSuccess() == false) {
            throw new BusinessException(cr.getMessage());
        }
        // 更新逻辑保存
        entity.setC_id(null);
        entity.setC_time(null);
        return UpdateResultUtil.OK(mapper.updateById(entity));
    }

    /**
     * 查询by id，返回结果
     *
     * @param id
     * @return
     */
    @Override
    public SModuleButtonVo selectByid(Long id) {
        // 查询 数据
        return mapper.selectId(id);
    }

    /**
     * 查询by code，返回结果
     *
     * @param code
     * @return
     */
    public List<SModuleButtonEntity> selectByCode(Long parent_id, Long equal_id, Long not_equal_id, String code ) {
        // 查询 数据
        List<SModuleButtonEntity> list = mapper.selectByCode(parent_id, equal_id, not_equal_id, code);
        return list;
    }

    /**
     * check逻辑，模块编号 or 模块名称 不能重复
     * 
     * @return
     */
    public CheckResult checkLogic(SModuleButtonEntity entity, String moduleType) {
        switch(moduleType){
            case CheckResult.INSERT_CHECK_TYPE :
                List<SModuleButtonEntity> listCode_insertCheck = selectByCode(entity.getParent_id(), null, null, entity.getCode());
                // 新增场合，不能重复
                if (listCode_insertCheck.size() >= 1) {
                    // 按钮编号不能重复
                    return CheckResultUtil.NG("新增保存出错：按钮编号出现重复", listCode_insertCheck);
                }

                break;
            case CheckResult.UPDATE_CHECK_TYPE :
                List<SModuleButtonEntity> listCode_updCheck = selectByCode(entity.getParent_id(), null, entity.getId(), entity.getCode());
                // 更新场合，不能重复设置
                if (listCode_updCheck.size() >= 1) {
                    // 模块编号不能重复
                    return CheckResultUtil.NG("更新保存出错：模块编号出现重复", listCode_updCheck);
                }
                break;
            default :

        }

        return CheckResultUtil.OK();
    }

    /**
     * sort保存
     *
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public UpdateResult<List<SModuleButtonVo>> saveList(List<SModuleButtonVo> data) {
        List<SModuleButtonVo> resultList = new ArrayList<>();
        // 乐观锁 dbversion
        for(SModuleButtonVo bean:data){
            // 复制到新的entity
            SModuleButtonEntity entity = (SModuleButtonEntity) BeanUtilsSupport.copyProperties(bean, SModuleButtonEntity.class);
            UpdateResult updateResult = this.update(entity);
            if(updateResult.isSuccess()){
                SModuleButtonVo searchData = this.selectByid(bean.getId());
                resultList.add(searchData);
            } else {
                throw new UpdateErrorException("保存的数据已经被修改，请查询后重新编辑更新。");
            }
        }
        return UpdateResultUtil.OK(resultList);
    }

}
