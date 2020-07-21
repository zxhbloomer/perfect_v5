package com.perfect.core.serviceimpl.sys.config.module;

import java.util.List;

import com.perfect.bean.entity.sys.config.dict.SDictTypeEntity;
import com.perfect.bean.entity.sys.config.module.SModuleEntity;
import com.perfect.bean.entity.sys.config.resource.SResourceEntity;
import com.perfect.bean.pojo.result.CheckResult;
import com.perfect.bean.pojo.result.InsertResult;
import com.perfect.bean.pojo.result.UpdateResult;
import com.perfect.bean.result.utils.v1.CheckResultUtil;
import com.perfect.bean.result.utils.v1.InsertResultUtil;
import com.perfect.bean.result.utils.v1.UpdateResultUtil;
import com.perfect.bean.vo.sys.config.module.SModuleVo;
import com.perfect.common.constant.PerfectDictConstant;
import com.perfect.common.exception.BusinessException;
import com.perfect.core.mapper.sys.config.module.SModuleMapper;
import com.perfect.core.service.base.v1.BaseServiceImpl;
import com.perfect.core.service.sys.config.module.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfect.core.utils.mybatis.PageUtil;

/**
 * <p>
 * 模块表 服务实现类
 * </p>
 *
 * @author zxh
 * @since 2019-08-16
 */
@Service
public class SModuleServiceImpl extends BaseServiceImpl<SModuleMapper, SModuleEntity> implements IModuleService {

    @Autowired
    private SModuleMapper mapper;

    /**
     * 获取列表，页面查询
     * 
     * @param searchCondition
     * @return
     */
    @Override
    public IPage<SModuleVo> selectPage(SModuleVo searchCondition) {
        // 分页条件
        Page<SModuleVo> pageCondition =
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
    public List<SModuleEntity> select(SModuleVo searchCondition) {
        // 查询 数据
        List<SModuleEntity> list = mapper.select(searchCondition);
        return list;
    }

    /**
     * 获取列表，根据id查询所有数据
     * 
     * @param searchCondition
     * @return
     */
    @Override
    public List<SModuleEntity> selectIdsIn(List<SModuleVo> searchCondition) {
        // 查询 数据
        List<SModuleEntity> list = mapper.selectIdsIn(searchCondition);
        return list;
    }

    /**
     * 批量导入逻辑
     * 
     * @param entityList
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveBatches(List<SModuleEntity> entityList) {
        return super.saveBatch(entityList, 500);
    }

    /**
     * 批量删除复原
     * 
     * @param searchCondition
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIdsIn(List<SModuleVo> searchCondition) {
        List<SModuleEntity> list = mapper.selectIdsIn(searchCondition);
        list.forEach(bean -> {
            bean.setIs_del(!bean.getIs_del());
        });
        saveOrUpdateBatch(list, 500);
    }

    /**
     * 插入一条记录（选择字段，策略插入）
     * 
     * @param entity 实体对象
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public InsertResult<Integer> insert(SModuleEntity entity) {
        // 插入前check
        CheckResult cr = checkLogic(entity, CheckResult.INSERT_CHECK_TYPE);
        if (cr.isSuccess() == false) {
            throw new BusinessException(cr.getMessage());
        }
        SModuleEntity cleanUpEntity = cleanUpBean(entity);
        // 插入逻辑保存
        return InsertResultUtil.OK(mapper.insert(cleanUpEntity));
    }

    /**
     * 更新一条记录（选择字段，策略更新）
     * 
     * @param entity 实体对象
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public UpdateResult<Integer> update(SModuleEntity entity) {
        // 更新前check
        CheckResult cr = checkLogic(entity, CheckResult.UPDATE_CHECK_TYPE);
        if (cr.isSuccess() == false) {
            throw new BusinessException(cr.getMessage());
        }
        SModuleEntity cleanUpEntity = cleanUpBean(entity);
        // 更新逻辑保存
        cleanUpEntity.setC_id(null);
        cleanUpEntity.setC_time(null);
        return UpdateResultUtil.OK(mapper.updateById(cleanUpEntity));
    }

    /**
     * 查询by id，返回结果
     *
     * @param id
     * @return
     */
    @Override
    public SModuleVo selectByid(Long id) {
        // 查询 数据
        return mapper.selectId(id);
    }

    /**
     * 查询by code，返回结果
     *
     * @param code
     * @return
     */
    public List<SModuleEntity> selectByCode(String code, Long equal_id, Long not_equal_id) {
        // 查询 数据
        List<SModuleEntity> list = mapper.selectByCode(code, equal_id, not_equal_id);
        return list;
    }

    /**
     * 查询by 名称，返回结果
     *
     * @param name
     * @return
     */
    public List<SModuleEntity> selectByName(String name, Long equal_id, Long not_equal_id) {
        // 查询 数据
        List<SModuleEntity> list = mapper.selectByName(name, equal_id, not_equal_id);
        return list;
    }

    /**
     * 查询by 请求地址，返回结果
     */
    public List<SModuleEntity> selectByPath(String path, Long equal_id, Long not_equal_id) {
        // 查询 数据
        List<SModuleEntity> list = mapper.selectByPath(path, equal_id, not_equal_id);
        return list;
    }

    /**
     * 查询by 请求地址，返回结果
     */
    public List<SModuleEntity> selectByRoute_name(String route_name, Long equal_id, Long not_equal_id) {
        // 查询 数据
        List<SModuleEntity> list = mapper.selectByRoute_name(route_name, equal_id, not_equal_id);
        return list;
    }

    /**
     * bean整理，按type
     *
     * @return
     */
    public SModuleEntity cleanUpBean(SModuleEntity entity) {
        switch(entity.getType()){
            case PerfectDictConstant.DICT_SYS_MODULE_TYPE_PAGE :
                // 页面
                entity.setPath(null);
                entity.setRoute_name(null);
                entity.setMeta_title(null);
                entity.setMeta_icon(null);
                entity.setComponent(null);
                entity.setAffix(null);
                break;
            case PerfectDictConstant.DICT_SYS_MODULE_TYPE_MENU :
                // 菜单
                entity.setTemplate_id(null);
                break;
            default :
                // 目录
                entity.setTemplate_id(null);
                entity.setComponent(null);
                break;
        }

        return entity;
    }

    /**
     * check逻辑，模块编号 or 模块名称 不能重复
     * 
     * @return
     */
    public CheckResult checkLogic(SModuleEntity entity, String moduleType) {
        switch(moduleType){
            case CheckResult.INSERT_CHECK_TYPE :
                List<SModuleEntity> listCode_insertCheck = selectByCode(entity.getCode(), null, null);
                List<SModuleEntity> listName_insertCheck = selectByName(entity.getName(), null, null);
                // 新增场合，不能重复
                if (listCode_insertCheck.size() >= 1) {
                    // 模块编号不能重复
                    return CheckResultUtil.NG("新增保存出错：模块编号出现重复", listCode_insertCheck);
                }
                if (listName_insertCheck.size() >= 1) {
                    // 模块名称不能重复
                    return CheckResultUtil.NG("新增保存出错：模块名称出现重复", listName_insertCheck);
                }
                if(PerfectDictConstant.DICT_SYS_MODULE_TYPE_MENU.equals(entity.getType())
                        || PerfectDictConstant.DICT_SYS_MODULE_TYPE_CONTENTS.equals(entity.getType()) ){
                    List<SModuleEntity> path_insertCheck = selectByPath(entity.getPath(), null, null);
                    List<SModuleEntity> route_name_insertCheck = selectByRoute_name(entity.getRoute_name(), null, null);
                }
                break;
            case CheckResult.UPDATE_CHECK_TYPE :
                List<SModuleEntity> listCode_updCheck = selectByCode(entity.getCode(), null, entity.getId());
                List<SModuleEntity> listName_updCheckk = selectByName(entity.getName(), null, entity.getId());
                // 更新场合，不能重复设置
                if (listCode_updCheck.size() >= 1) {
                    // 模块编号不能重复
                    return CheckResultUtil.NG("更新保存出错：模块编号出现重复", listCode_updCheck);
                }
                if (listName_updCheckk.size() >= 1) {
                    // 模块名称不能重复
                    return CheckResultUtil.NG("更新保存出错：模块名称出现重复", listName_updCheckk);
                }
                if(PerfectDictConstant.DICT_SYS_MODULE_TYPE_MENU.equals(entity.getType())
                    || PerfectDictConstant.DICT_SYS_MODULE_TYPE_CONTENTS.equals(entity.getType()) ){
                    List<SModuleEntity> path_insertCheck = selectByPath(entity.getPath(), null, entity.getId());
                    List<SModuleEntity> route_name_insertCheck = selectByRoute_name(entity.getRoute_name(), null, entity.getId());
                }
                break;
            default :

        }

        return CheckResultUtil.OK();
    }

    /**
     * 根据模块名称查询资源文件找到json进行转换成excel导出
     * @param code
     */
    @Override
    public SModuleVo getTemplateBeanByModuleName(String code){
        // 查询 数据
        SModuleVo vo = mapper.selectTemplateName(code);
        return vo;
    }
}
