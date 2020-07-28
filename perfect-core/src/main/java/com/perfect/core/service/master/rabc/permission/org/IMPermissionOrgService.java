package com.perfect.core.service.master.rabc.permission.org;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.perfect.bean.entity.master.org.MOrgEntity;
import com.perfect.bean.entity.master.rabc.permission.MPermissionEntity;
import com.perfect.bean.vo.common.component.NameAndValueVo;
import com.perfect.bean.vo.master.org.*;
import com.perfect.bean.vo.master.user.MStaffVo;

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

    /**
     * 获取所有数据
     */
    List<MOrgTreeVo> select(MOrgVo searchCondition) ;

    /**
     * 获取所有的组织以及子组织数量，仅仅是数量
     * @param searchCondition
     * @return
     */
    MOrgCountsVo getAllOrgDataCount(MOrgVo searchCondition);

    /**
     * 获取组织数据
     * @param searchCondition
     * @return
     */
    List<MOrgTreeVo> getOrgs(MOrgVo searchCondition);

    /**
     * 获取集团数据
     * @param searchCondition
     * @return
     */
    IPage<MGroupVo> getGroups(MOrgTreeVo searchCondition);

    /**
     * 获取企业数据
     * @param searchCondition
     * @return
     */
    IPage<MCompanyVo> getCompanies(MOrgTreeVo searchCondition);

    /**
     * 获取部门数据
     * @param searchCondition
     * @return
     */
    IPage<MDeptVo> getDepts(MOrgTreeVo searchCondition);

    /**
     * 获取岗位数据
     * @param searchCondition
     * @return
     */
    IPage<MPositionVo> getPositions(MOrgTreeVo searchCondition);

    /**
     * 获取员工数据
     * @param searchCondition
     * @return
     */
    List<MStaffVo> getStaffs(MOrgVo searchCondition);

    /**
     * 获取数据byid
     * @param id
     * @return
     */
    MOrgVo selectByid(Long id);

    /**
     * 新增模式下，可新增子结点得类型
     * @return
     */
    List<NameAndValueVo> getCorrectTypeByInsertStatus(MOrgVo vo);

    /**
     * 根据code，进行 like 'code%'，匹配当前结点以及子结点
     * @param vo
     * @return
     */
    List<MOrgEntity> getDataByCode(MOrgEntity vo);

    /**
     * 拖拽保存
     * @param bean
     * @return
     */
    Boolean dragsave(List<MOrgTreeVo> bean);

    /**
     * 获取员工清单，为穿梭框服务
     * @return
     */
    MStaffPositionTransferVo getStaffTransferList(MStaffTransferVo condition);

    /**
     * 保存穿梭框数据，员工岗位设置
     * @return
     */
    MStaffPositionTransferVo setStaffTransfer(MStaffTransferVo bean);

    /**
     * 获取员工列表，页面查询
     */
    MStaffTabVo selectStaff(MStaffTabDataVo searchCondition) ;

    /**
     * 获取当组织下员工count
     */
    Integer getCurrentOrgStaffCount(MStaffTabDataVo searchCondition) ;

    /**
     * 获取所有员工count
     */
    Integer getAllOrgStaffCount(MStaffTabDataVo searchCondition) ;
}
