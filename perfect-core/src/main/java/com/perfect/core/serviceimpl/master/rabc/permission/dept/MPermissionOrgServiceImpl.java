package com.perfect.core.serviceimpl.master.rabc.permission.dept;

import com.perfect.bean.entity.master.rabc.permission.MPermissionEntity;
import com.perfect.bean.utils.common.tree.TreeUtil;
import com.perfect.bean.vo.master.org.MOrgTreeVo;
import com.perfect.core.mapper.master.org.MOrgMapper;
import com.perfect.core.mapper.master.rabc.permission.dept.MPermissionOrgMapper;
import com.perfect.core.service.base.v1.BaseServiceImpl;
import com.perfect.core.service.master.rabc.permission.dept.IMPermissionOrgService;
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
        // 查询 数据
        List<MOrgTreeVo> list = mapper.getTreeList(searchCondition);
        List<MOrgTreeVo> rtnList = TreeUtil.getTreeList(list);
        return rtnList;
    }
}
