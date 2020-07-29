package com.perfect.core.serviceimpl.master.rabc.permission.org;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.perfect.bean.entity.master.org.MOrgEntity;
import com.perfect.bean.entity.master.rabc.permission.MPermissionEntity;
import com.perfect.bean.utils.common.tree.TreeUtil;
import com.perfect.bean.vo.master.org.MOrgTreeVo;
import com.perfect.common.constant.PerfectDictConstant;
import com.perfect.core.mapper.master.org.MOrgMapper;
import com.perfect.core.mapper.master.rabc.permission.org.MPermissionOrgMapper;
import com.perfect.core.service.base.v1.BaseServiceImpl;
import com.perfect.core.service.master.rabc.permission.org.IMPermissionOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限类页面左侧的树 服务实现类
 * </p>
 *
 * @author zxh
 * @since 2020-07-27
 */
@Service
public class MPermissionOrgServiceImpl extends BaseServiceImpl<MPermissionOrgMapper, MPermissionEntity> implements
    IMPermissionOrgService {

    @Autowired
    private MOrgMapper mOrgMapper;

    @Autowired
    private MPermissionOrgMapper mapper;


    /**
     * 获取所有数据，左侧树数据
     */
    @Override
    public List<MOrgTreeVo> getTreeList(MOrgTreeVo searchCondition) {
        searchCondition.setTenant_id(getUserSessionTenantId());
        switch (searchCondition.getType()) {
            case PerfectDictConstant.DICT_ORG_SETTING_TYPE_TENANT:
                // 组织机构
                break;
            case PerfectDictConstant.DICT_ORG_SETTING_TYPE_COMPANY:
                // 企业
                String[] company_codes = {
                    PerfectDictConstant.DICT_ORG_SETTING_TYPE_TENANT_SERIAL_TYPE,
                    PerfectDictConstant.DICT_ORG_SETTING_TYPE_GROUP_SERIAL_TYPE,
                    PerfectDictConstant.DICT_ORG_SETTING_TYPE_COMPANY_SERIAL_TYPE};
                searchCondition.setCodes(company_codes);
                break;
            case PerfectDictConstant.DICT_ORG_SETTING_TYPE_DEPT:
                // 部门
                String[] dept_codes = {
                    PerfectDictConstant.DICT_ORG_SETTING_TYPE_TENANT_SERIAL_TYPE,
                    PerfectDictConstant.DICT_ORG_SETTING_TYPE_GROUP_SERIAL_TYPE,
                    PerfectDictConstant.DICT_ORG_SETTING_TYPE_COMPANY_SERIAL_TYPE,
                    PerfectDictConstant.DICT_ORG_SETTING_TYPE_DEPT_SERIAL_TYPE
                };
                searchCondition.setCodes(dept_codes);
                // 获取code
                MOrgEntity mOrgEntity = mOrgMapper.selectOne(new QueryWrapper<MOrgEntity>()
                    .eq("serial_id",searchCondition.getSerial_id())
                    .eq("serial_type", searchCondition.getSerial_type())
                );
                String code = mOrgEntity.getCode().substring(0,8);
                searchCondition.setCode(code);
                searchCondition.setCurrent_code(mOrgEntity.getCode());
                break;
        };

        // 查询 数据
        List<MOrgTreeVo> list = mapper.getTreeList(searchCondition);
        List<MOrgTreeVo> rtnList = TreeUtil.getTreeList(list);
        return rtnList;
    }
}
