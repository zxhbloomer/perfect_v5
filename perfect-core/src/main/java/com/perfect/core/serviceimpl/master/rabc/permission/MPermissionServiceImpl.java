package com.perfect.core.serviceimpl.master.rabc.permission;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.perfect.bean.entity.master.rabc.permission.MPermissionEntity;
import com.perfect.bean.pojo.result.CheckResult;
import com.perfect.bean.pojo.result.InsertResult;
import com.perfect.bean.pojo.result.UpdateResult;
import com.perfect.bean.result.utils.v1.CheckResultUtil;
import com.perfect.bean.result.utils.v1.InsertResultUtil;
import com.perfect.bean.result.utils.v1.UpdateResultUtil;
import com.perfect.bean.vo.master.rabc.permission.MPermissionVo;
import com.perfect.common.exception.BusinessException;
import com.perfect.common.exception.InsertErrorException;
import com.perfect.common.exception.UpdateErrorException;
import com.perfect.common.utils.bean.BeanUtilsSupport;
import com.perfect.core.mapper.master.rabc.permission.MPermissionMapper;
import com.perfect.core.service.base.v1.BaseServiceImpl;
import com.perfect.core.service.master.rabc.permission.IMPermissionService;
import com.perfect.core.utils.mybatis.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author zxh
 * @since 2020-07-27
 */
@Service
public class MPermissionServiceImpl extends BaseServiceImpl<MPermissionMapper, MPermissionEntity> implements
    IMPermissionService {

    @Autowired
    private MPermissionMapper mapper;

    /**
     * 获取列表，页面查询
     *
     * @param searchCondition
     * @return
     */
    @Override
    public IPage<MPermissionVo> selectPage(MPermissionVo searchCondition) {
        // 分页条件
        Page<MPermissionVo> pageCondition =
            new Page(searchCondition.getPageCondition().getCurrent(), searchCondition.getPageCondition().getSize());
        // 通过page进行排序
        PageUtil.setSort(pageCondition, searchCondition.getPageCondition().getSort());
        return mapper.selectPage(pageCondition, searchCondition);
    }

    /**
     * 获取列表，根据id查询所有数据
     *
     * @param searchCondition
     * @return
     */
    @Override
    public List<MPermissionVo> selectIdsIn(List<MPermissionVo> searchCondition) {
        // 查询 数据
        List<MPermissionVo> list = mapper.selectIdsIn(searchCondition);
        return list;
    }

    /**
     * 批量删除复原
     * @param searchCondition
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void enableById(MPermissionVo searchCondition) {
        MPermissionVo vo = mapper.selectByid(searchCondition.getId());
        vo.setIs_enable(!vo.getIs_enable());
        MPermissionEntity entity = (MPermissionEntity)BeanUtilsSupport.copyProperties(vo, MPermissionEntity.class);
        saveOrUpdate(entity);
    }

    /**
     * 批量删除复原
     * @param searchCondition
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIdsIn(List<MPermissionVo> searchCondition) {
        List<MPermissionVo> list = mapper.selectIdsIn(searchCondition);
        list.forEach(
            bean -> {
                bean.setIs_del(!bean.getIs_del());
            }
        );
        List<MPermissionEntity> entities = BeanUtilsSupport.copyProperties(list, MPermissionEntity.class);
        saveOrUpdateBatch(entities, 500);
    }

    /**
     * 插入一条记录（选择字段，策略插入）
     * @param vo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public InsertResult<MPermissionVo> insert(MPermissionVo vo) {

        // 插入前check
        CheckResult cr = checkLogic(vo, CheckResult.INSERT_CHECK_TYPE);
        if (cr.isSuccess() == false) {
            throw new BusinessException(cr.getMessage());
        }
        // 插入逻辑保存
        MPermissionEntity entity = (MPermissionEntity)BeanUtilsSupport.copyProperties(vo, MPermissionEntity.class);
        vo.setIs_del(false);
        vo.setTenant_id(getUserSessionTenantId());
        int count = mapper.insert(entity);
        if(count == 0){
            throw new InsertErrorException("保存失败，请查询后重新再试。");
        }
        return InsertResultUtil.OK(selectByid(entity.getId()));
    }

    /**
     * 更新一条记录（选择字段，策略更新）
     * @param vo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public UpdateResult<MPermissionVo> update(MPermissionVo vo) {
        // 更新前check
        CheckResult cr = checkLogic(vo, CheckResult.UPDATE_CHECK_TYPE);
        if (cr.isSuccess() == false) {
            throw new BusinessException(cr.getMessage());
        }
        // 更新逻辑保存
        MPermissionEntity entity = (MPermissionEntity) BeanUtilsSupport.copyProperties(vo, MPermissionEntity.class);
        vo.setC_id(null);
        vo.setC_time(null);
        vo.setTenant_id(getUserSessionTenantId());
        int count = mapper.updateById(entity);
        if(count == 0){
            throw new UpdateErrorException("保存的数据已经被修改，请查询后重新编辑更新。");
        }
        return UpdateResultUtil.OK(selectByid(entity.getId()));
    }

    /**
     * 获取数据byid
     * @param id
     * @return
     */
    @Override
    public MPermissionVo selectByid(Long id){
        return mapper.selectByid(id);
    }

    /**
     * check逻辑
     * @return
     */
    public CheckResult checkLogic(MPermissionVo vo, String moduleType){
        switch (moduleType) {
            case CheckResult.INSERT_CHECK_TYPE:
                break;
            case CheckResult.UPDATE_CHECK_TYPE:
                break;
            default:
        }
        return CheckResultUtil.OK();
    }
}
