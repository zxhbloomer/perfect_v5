package com.perfect.core.serviceimpl.master.rabc.permission;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.perfect.bean.bo.log.operate.CustomOperateBo;
import com.perfect.bean.entity.master.org.MCompanyEntity;
import com.perfect.bean.entity.master.org.MGroupEntity;
import com.perfect.bean.entity.master.org.MOrgEntity;
import com.perfect.bean.entity.master.rabc.permission.MPermissionEntity;
import com.perfect.bean.pojo.result.CheckResult;
import com.perfect.bean.result.utils.v1.CheckResultUtil;
import com.perfect.bean.utils.common.tree.TreeUtil;
import com.perfect.bean.vo.common.component.NameAndValueVo;
import com.perfect.bean.vo.common.component.TreeNode;
import com.perfect.bean.vo.master.org.*;
import com.perfect.bean.vo.master.user.MStaffVo;
import com.perfect.common.annotations.LogByIdAnnotion;
import com.perfect.common.annotations.OperationLogAnnotion;
import com.perfect.common.constant.PerfectConstant;
import com.perfect.common.constant.PerfectDictConstant;
import com.perfect.common.enums.OperationEnum;
import com.perfect.core.mapper.master.org.MOrgMapper;
import com.perfect.core.mapper.master.rabc.permission.MPermissionMapper;
import com.perfect.core.service.base.v1.BaseServiceImpl;
import com.perfect.core.service.common.ICommonComponentService;
import com.perfect.core.service.master.rabc.permission.IMPermissionService;
import com.perfect.core.serviceimpl.master.org.MOrgServiceImpl;
import com.perfect.core.utils.mybatis.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private MOrgMapper mOrgMapper;

    @Autowired
    private MPermissionMapper mapper;

    @Autowired
    private ICommonComponentService iCommonComponentService;

    @Autowired
    private MOrgServiceImpl self;

    /** 组织entity数组 */
    //    List<MOrgEntity> entities;

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

    /**
     * 获取列表，查询所有数据
     *
     * @param searchCondition
     * @return
     */
    @Override
    public List<MOrgTreeVo> select(MOrgVo searchCondition) {
        searchCondition.setTenant_id(getUserSessionTenantId());
        // 查询 数据
        List<MOrgTreeVo> list = mapper.select(searchCondition);
        List<MOrgTreeVo> rtnList = TreeUtil.getTreeList(list);
        return rtnList;
    }

    /**
     * 获取所有的组织数据
     * @param searchCondition
     * @return
     */
    @Override
    public MOrgCountsVo getAllOrgDataCount(MOrgVo searchCondition) {
        MOrgCountsVo mOrgCountsVo = mapper.getAllOrgDataCount(searchCondition);
        return mOrgCountsVo;
    }

    /**
     * 获取组织数据
     * @param searchCondition
     * @return
     */
    @Override
    public List<MOrgTreeVo> getOrgs(MOrgVo searchCondition) {
        List<MOrgTreeVo> listOrg = select(searchCondition);
        return listOrg;
    }

    /**
     * 获取集团数据
     * @param searchCondition
     * @return
     */
    @Override
    public IPage<MGroupVo> getGroups(MOrgTreeVo searchCondition) {
        // 分页条件
        Page<MGroupEntity> pageCondition =
            new Page(searchCondition.getPageCondition().getCurrent(), searchCondition.getPageCondition().getSize());
        // 通过page进行排序
        PageUtil.setSort(pageCondition, searchCondition.getPageCondition().getSort());
        IPage<MGroupVo> listGroup = mapper.getGroupList(pageCondition, searchCondition);
        return listGroup;
    }

    /**
     * 获取企业数据
     * @param searchCondition
     * @return
     */
    @Override
    public IPage<MCompanyVo> getCompanies(MOrgTreeVo searchCondition) {
        // 分页条件
        Page<MCompanyEntity> pageCondition =
            new Page(searchCondition.getPageCondition().getCurrent(), searchCondition.getPageCondition().getSize());
        // 通过page进行排序
        PageUtil.setSort(pageCondition, searchCondition.getPageCondition().getSort());
        IPage<MCompanyVo> listcompany = mapper.getCompanyList(pageCondition, searchCondition);
        return listcompany;
    }

    /**
     * 获取部门数据
     * @param searchCondition
     * @return
     */
    @Override
    public IPage<MDeptVo> getDepts(MOrgTreeVo searchCondition) {
        // 分页条件
        Page<MDeptVo> pageCondition =
            new Page(searchCondition.getPageCondition().getCurrent(), searchCondition.getPageCondition().getSize());
        // 通过page进行排序
        PageUtil.setSort(pageCondition, searchCondition.getPageCondition().getSort());
        IPage<MDeptVo> listDept =  mapper.getDeptList(pageCondition, searchCondition);
        return listDept;
    }

    /**
     * 获取岗位数据
     * @param searchCondition
     * @return
     */
    @Override
    public IPage<MPositionVo> getPositions(MOrgTreeVo searchCondition) {
        // 分页条件
        Page<MDeptVo> pageCondition =
            new Page(searchCondition.getPageCondition().getCurrent(), searchCondition.getPageCondition().getSize());
        // 通过page进行排序
        PageUtil.setSort(pageCondition, searchCondition.getPageCondition().getSort());
        IPage<MPositionVo> listPosition =  mapper.getPositionList(pageCondition, searchCondition);
        return listPosition;
    }

    /**
     * 获取员工数据
     * @param searchCondition
     * @return
     */
    @Override
    public List<MStaffVo> getStaffs(MOrgVo searchCondition) {
        return null;
    }

    /**
     * 获取数据byid
     * @param id
     * @return
     */
    @Override
    public MOrgVo selectByid(Long id){
        return mapper.selectByid(id, getUserSessionTenantId());
    }

    /**
     * 查询添加的子结点是否合法
     *
     * @return
     */
    public Integer selectNodeInsertStatus(String code, String type) {
        // 查询 数据
        Integer count = mapper.selectNodeInsertStatus(code, type, getUserSessionTenantId());
        return count;
    }

    /**
     * 查询添加的子结点是否合法，子结点被重复选择使用的情况
     *
     * @return
     */
    public Integer getCountBySerial(MOrgEntity entity, Long equal_id, Long not_equal_id) {
        // 查询 数据
        Integer count = mapper.getCountBySerial(entity, equal_id, not_equal_id);
        return count;
    }

    /**
     * check逻辑
     * @return
     */
    public CheckResult checkLogic(MOrgEntity entity, String moduleType){
        Integer count = 0;
        switch (moduleType) {
            case CheckResult.INSERT_CHECK_TYPE:
                // 查看子结点是否正确：租户->集团->企业->部门->岗位->员工
                Integer countInsert = this.selectNodeInsertStatus(entity.getCode(),entity.getType());
                if(countInsert > 0){
                    String nodeTypeName = iCommonComponentService.getDictName(PerfectDictConstant.DICT_ORG_SETTING_TYPE, entity.getType());
                    return CheckResultUtil.NG("新增保存出错：新增的子结点类型不能是" + "【" + nodeTypeName + "】", countInsert);
                }
                // 查看当前结点是否已经被选择使用
                count = getCountBySerial(entity, null, null);
                if(count > 0){
                    return CheckResultUtil.NG("新增保存出错：您选择的子结点已经在组织架构中，请选择尚未被使用的组织。", count);
                }
                break;
            case CheckResult.UPDATE_CHECK_TYPE:
                // 查看子结点是否正确：租户->集团->企业->部门->岗位->员工
                Integer countUpdate = this.selectNodeInsertStatus(entity.getCode(),entity.getType());
                if(countUpdate > 0){
                    String nodeTypeName = iCommonComponentService.getDictName(PerfectDictConstant.DICT_ORG_SETTING_TYPE, entity.getType());
                    return CheckResultUtil.NG("新增保存出错：更新的当前结点类型不能是" + "【" + nodeTypeName + "】", countUpdate);
                }
                // 查看当前结点是否已经被选择使用
                count = getCountBySerial(entity, null, entity.getId());
                if(count > 0){
                    return CheckResultUtil.NG("新增保存出错：您选择的子结点已经在组织架构中，请选择尚未被使用的组织。", count);
                }
                break;
            default:
        }
        return CheckResultUtil.OK();
    }

    /**
     * 新增模式下，可新增子结点得类型
     * @return
     */
    @Override
    public List<NameAndValueVo> getCorrectTypeByInsertStatus(MOrgVo vo) {
        vo.setTenant_id(getUserSessionTenantId());
        // 查询 数据
        List<NameAndValueVo> rtn = mapper.getCorrectTypeByInsertStatus(vo);
        return rtn;
    }

    /**
     * 删除
     * @param entity
     * @return
     */
    @OperationLogAnnotion(
        name = PerfectConstant.OPERATION.M_ORG.OPER_DELETE,
        type = OperationEnum.DELETE,
        logById = @LogByIdAnnotion(
            name = PerfectConstant.OPERATION.M_ORG.OPER_DELETE,
            type = OperationEnum.DELETE,
            oper_info = "",
            table_name = PerfectConstant.OPERATION.M_ORG.TABLE_NAME,
            id = "#{entity.id}"
        )
    )

    /**
     * 根据code，进行 like 'code%'，匹配当前结点以及子结点
     * @param entity
     * @return
     */
    @Override
    public List<MOrgEntity> getDataByCode(MOrgEntity entity) {
        entity.setTenant_id(getUserSessionTenantId());
        List<MOrgEntity> rtnList = mapper.getDataByCode(entity);
        return rtnList;
    }

    /**
     * 拖拽保存
     * 未使用乐观锁，需要注意
     * @param beans
     * @return
     */
    @Override
    public Boolean dragsave(List<MOrgTreeVo> beans) {
        List<MOrgEntity> entities = new ArrayList<>();
        int code = 0;
        List<MOrgEntity> beanList = dragData2List(beans, null ,entities, code);
        /**
         * 注意调用方法，必须使用外部调用，激活aop，内部调用不能激活aop和注解
         */
        return self.dragsave2Db(beanList);
    }

    /**
     * 设置儿子个数
     * @return
     */
    private List<MOrgEntity> setParentSonCount(List<MOrgEntity> entities, Long parent_id) {
        for(MOrgEntity entity : entities){
            if(entity.getId().equals(parent_id)){
                entity.setSon_count(entity.getSon_count() == null ? 1 : entity.getSon_count() + 1);
            }
        }
        return entities;
    }

    /**
     * 拖拽数据规整
     * @param beans         ：循环的beans
     * @param parent_bean   ：父亲bean
     * @param entities      ：最终返回的list bean
     * @param code          ：
     * @return
     */
    private List<MOrgEntity> dragData2List(List<? extends TreeNode> beans, MOrgEntity parent_bean, List<MOrgEntity> entities, int code) {
        for (TreeNode bean : beans) {
            code = code + 1;
            MOrgEntity entity = new MOrgEntity();
            entity.setId(bean.getId());
            entity.setParent_id(bean.getParent_id());
            if(parent_bean == null) {
                entity.setCode(String.format("%04d", code));
            } else {
                entity.setCode(parent_bean.getCode() + String.format("%04d", code));
            }
            entities.add(entity);
            if(bean.getChildren().size() !=0){
                dragData2List(bean.getChildren(), entity, entities, 0);
            }
        }
        return entities;
    }

    /**
     * 获取员工清单，为穿梭框服务
     * @return
     */
    @Override
    public MStaffPositionTransferVo getStaffTransferList(MStaffTransferVo condition) {

        MStaffPositionTransferVo rtn = new MStaffPositionTransferVo();
        // 获取全部用户
        rtn.setStaff_all(mapper.getAllStaffTransferList(condition));
        // 获取该岗位已经设置过得用户
        List<Long> rtnList = mapper.getUsedStaffTransferList(condition);
        rtn.setStaff_positions(rtnList.toArray(new Long[rtnList.size()]));
        return rtn;
    }

    /**
     * 设置员工关系，删除剔除的员工，增加选择的员工
     * @param bean 员工id list
     * @return
     */
    @Override
    public MStaffPositionTransferVo setStaffTransfer(MStaffTransferVo bean) {
        // 操作日志bean初始化
        CustomOperateBo cobo = new CustomOperateBo();
        cobo.setName(PerfectConstant.OPERATION.M_STAFF_ORG.OPER_POSITION_STAFF);
        cobo.setPlatform(PerfectConstant.PLATFORM.PC);
        cobo.setType(OperationEnum.BATCH_UPDATE_INSERT_DELETE);


        // 查询出需要剔除的员工list
        List<MStaffPositionOperationVo> deleteMemgerList = mapper.selete_delete_member(bean);
        // 查询出需要添加的员工list
        List<MStaffPositionOperationVo> insertMemgerList = mapper.selete_insert_member(bean);

        // 执行保存逻辑，并返回员工数量
        return self.saveMemberList(deleteMemgerList, insertMemgerList, cobo, bean);
    }

    /**
     * 获取列表，页面查询
     *
     * @param searchCondition
     * @return
     */
    @Override
    public MStaffTabVo selectStaff(MStaffTabDataVo searchCondition) {
        MStaffTabVo mStaffTabVo = new MStaffTabVo();
        searchCondition.setTenant_id(getUserSessionTenantId());
        // 这个需要提前运行
        mStaffTabVo.setCurrentOrgStaffCount(getCurrentOrgStaffCount(searchCondition));

        /**
         * 表数据
         * 判断是当前组织下还是全部员工
         * 0:当前组织
         * 1：全部员工
         * */
        if (searchCondition.getActive_tabs_index() == 1){
            // 1：全部员工
            mStaffTabVo.setList(mapper.getAllOrgStaff(searchCondition));
        } else {
            // 0:当前组织
            mStaffTabVo.setList(mapper.selectStaff(searchCondition));
        }

        // count 数据
        mStaffTabVo.setAllOrgStaffCount(getAllOrgStaffCount(searchCondition));
        return mStaffTabVo;
    }

    /**
     * 获取当组织下员工count
     */
    @Override
    public Integer getCurrentOrgStaffCount(MStaffTabDataVo searchCondition) {
        return mapper.getCurrentOrgStaffCount(searchCondition);
    }

    /**
     * 获取所有员工count
     */
    @Override
    public Integer getAllOrgStaffCount(MStaffTabDataVo searchCondition) {
        /**
         * 考虑所有员工的方法
         * 1:根据code的定义规则，0001xxxx|xxxx|，每4位为一个层，所以找到第一组的4个
         * 2：并设置回code中去
         *
         * 2020.04.26 updated：该方法不适合，只需要判断，用户表中租户=参数，即可
         */
        //        String _code = searchCondition.getCode();
        //        _code = StrUtil.sub(_code, 0, 4);
        //        searchCondition.setCode(_code);
        return mapper.getAllOrgStaffCount(searchCondition);
    }
}
