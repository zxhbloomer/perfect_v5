package com.perfect.core.service.master.rabc.permission.org;

import com.baomidou.mybatisplus.extension.service.IService;
import com.perfect.bean.entity.master.rabc.permission.MPermissionEntity;
import com.perfect.bean.vo.master.org.MOrgTreeVo;
import com.perfect.bean.vo.master.org.MOrgVo;

import java.util.List;

/**
 * <p>
 * 权限类页面左侧的树 服务类
 * </p>
 *
 * @author zxh
 * @since 2020-07-27
 */
public interface IMPermissionOrgService extends IService<MPermissionEntity> {
    /**
     * 获取所有数据，左侧树数据
     */
    List<MOrgTreeVo> getTreeList(MOrgTreeVo searchCondition) ;

}
