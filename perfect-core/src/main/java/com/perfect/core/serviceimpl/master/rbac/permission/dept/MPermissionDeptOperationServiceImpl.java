package com.perfect.core.serviceimpl.master.rbac.permission.dept;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.perfect.bean.entity.master.rbac.permission.MPermissionEntity;
import com.perfect.bean.entity.master.rbac.permission.MPermissionMenuEntity;
import com.perfect.bean.entity.master.rbac.permission.MPermissionOperationEntity;
import com.perfect.bean.entity.master.rbac.permission.MPermissionPagesEntity;
import com.perfect.bean.utils.common.tree.TreeUtil;
import com.perfect.bean.vo.master.rbac.permission.MPermissionOperationVo;
import com.perfect.bean.vo.master.rbac.permission.MPermissionVo;
import com.perfect.bean.vo.master.rbac.permission.operation.OperationMenuDataVo;
import com.perfect.bean.vo.master.rbac.permission.operation.OperationMenuVo;
import com.perfect.core.mapper.master.rbac.permission.MPermissionMapper;
import com.perfect.core.mapper.master.rbac.permission.MPermissionMenuMapper;
import com.perfect.core.mapper.master.rbac.permission.MPermissionOperationMapper;
import com.perfect.core.mapper.master.rbac.permission.MPermissionPagesMapper;
import com.perfect.core.mapper.master.rbac.permission.dept.MPermissionDeptOperationMapper;
import com.perfect.core.service.base.v1.BaseServiceImpl;
import com.perfect.core.service.master.rbac.permission.IMPermissionOperationService;
import com.perfect.core.service.master.rbac.permission.IMPermissionService;
import com.perfect.core.service.master.rbac.permission.dept.IMPermissionDeptOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author zxh
 * @since 2020-07-27
 */
@Service
public class MPermissionDeptOperationServiceImpl extends BaseServiceImpl<MPermissionMapper, MPermissionEntity> implements
    IMPermissionDeptOperationService {

    @Autowired
    private MPermissionDeptOperationMapper mapper;

    @Autowired
    private MPermissionMenuMapper mPermissionMenuMapper;

    @Autowired
    private MPermissionPagesMapper mPermissionPagesMapper;

    @Autowired
    private MPermissionOperationMapper mPermissionOperationMapper;

    @Autowired
    private IMPermissionService imPermissionService;

    @Autowired
    private IMPermissionOperationService imPermissionOperationService;

    /**
     * 获取列表，查询所有数据
     *
     * @param searchCondition
     * @return
     */
    @Override
    public OperationMenuVo getTreeData(OperationMenuDataVo searchCondition) {
        OperationMenuVo mMenuVo = new OperationMenuVo();
        // 查询 菜单 数据
        List<OperationMenuDataVo> list = mapper.select(searchCondition);
        setDepthId(list);
        // 设置树bean
        List<OperationMenuDataVo> rtnList = TreeUtil.getTreeList(list, "menu_id");

        mMenuVo.setMenu_data(rtnList);
        return mMenuVo;
    }

    /**
     * 格式化depth_id，parent_depth_id成数组
     * @param list
     */
    private void setDepthId(List<OperationMenuDataVo> list){
        // 循环结果，格式化depth_id，parent_depth_id成数组
        for (OperationMenuDataVo vo:list) {
            // 格式化depth_id
            if(vo.getDepth_id() != null) {
                String[] split_depth_id = vo.getDepth_id().split(",");
                List<Long> depth_id_array = new ArrayList<>();
                for (int i = 0; i < split_depth_id.length; i++) {
                    depth_id_array.add(Long.valueOf(split_depth_id[i]));
                }
                vo.setDepth_id_array(depth_id_array);
            }
            // 格式化parent_depth_id
            if(vo.getParent_depth_id() != null) {
                String[] split_parent_depth_id = vo.getParent_depth_id().split(",");
                List<Long> parent_depth_id_array = new ArrayList<>();
                for (int i = 0; i < split_parent_depth_id.length; i++) {
                    parent_depth_id_array.add(Long.valueOf(split_parent_depth_id[i]));
                }
                vo.setParent_depth_id_array(parent_depth_id_array);
            }
        }
    }

    /**
     * 复制选中的菜单
     *
     * @param searchCondition
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setSystemMenuData2PermissionData(OperationMenuDataVo searchCondition) {

        // 表复制，m_menu->m_permission_menu
        copyMmenu2MPermissionMenu(searchCondition);
        // 表复制，s_pages->m_permission_pages
        copySpages2MPermissionPages(searchCondition);
        // 表复制，s_pages_function->m_permission_operation
        copyMPermissionOperation2MPermissionOperation(searchCondition);
        // 以上复制完成后，更新m_permission.menu_id
        MPermissionVo vo = imPermissionService.selectByid(searchCondition.getPermission_id());
        vo.setMenu_id(searchCondition.getRoot_id());
        imPermissionService.update(vo);
    }

    /**
     * 表复制，m_menu->m_permission_menu
     * @param searchCondition
     * @return
     */
    private int copyMmenu2MPermissionMenu(OperationMenuDataVo searchCondition) {
        // m_menu --copy-->m_permission_menu
        MPermissionMenuEntity entity = new MPermissionMenuEntity();
        entity.setTenant_id(searchCondition.getTenant_id());
        entity.setC_id(searchCondition.getC_id());
        entity.setU_id(searchCondition.getU_id());
        entity.setC_time(LocalDateTime.now());
        entity.setU_time(LocalDateTime.now());
        entity.setDbversion(0);
        entity.setMenu_id(searchCondition.getRoot_id());
        entity.setPermission_id(searchCondition.getPermission_id());
        int count = mPermissionMenuMapper.copyMMenu2MPermissionMenu(entity);
        return count;
    }

    /**
     * 表复制，s_pages->m_permission_pages
     * @param searchCondition
     * @return
     */
    private int copySpages2MPermissionPages(OperationMenuDataVo searchCondition) {
        // m_menu --copy-->m_permission_menu
        MPermissionPagesEntity entity = new MPermissionPagesEntity();
        entity.setTenant_id(searchCondition.getTenant_id());
        entity.setC_id(searchCondition.getC_id());
        entity.setU_id(searchCondition.getU_id());
        entity.setC_time(LocalDateTime.now());
        entity.setU_time(LocalDateTime.now());
        entity.setPermission_id(searchCondition.getPermission_id());
        entity.setDbversion(0);
        int count = mPermissionPagesMapper.copySPages2MPermissionPages(entity, searchCondition.getRoot_id());
        return count;
    }

    /**
     * 表复制，s_pages_function->m_permission_operation
     * @param searchCondition
     * @return
     */
    private int copyMPermissionOperation2MPermissionOperation(OperationMenuDataVo searchCondition) {
        // m_menu --copy-->m_permission_menu
        MPermissionOperationEntity entity = new MPermissionOperationEntity();
        entity.setTenant_id(searchCondition.getTenant_id());
        entity.setC_id(searchCondition.getC_id());
        entity.setU_id(searchCondition.getU_id());
        entity.setC_time(LocalDateTime.now());
        entity.setU_time(LocalDateTime.now());
        entity.setPermission_id(searchCondition.getPermission_id());
        entity.setDbversion(0);
        int count = mPermissionOperationMapper.copyMPermissionOperation2MPermissionOperation(entity, searchCondition.getRoot_id());
        return count;
    }

    /**
     * 保存权限操作数据
     * @param list
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void savePermission(List<MPermissionOperationVo> list) {
        List<Long> idList = new ArrayList<>();
        list.forEach(bean -> {
            idList.add(bean.getId());
        });
        List<MPermissionOperationEntity> operationEntities = imPermissionOperationService.listByIds(idList);
        // 转化成map
        Map<Long, MPermissionOperationEntity> operationEntityMap =  Maps.uniqueIndex(operationEntities, new Function <MPermissionOperationEntity,Long>() {
            @Override
            public Long apply(MPermissionOperationEntity entity) {
                return entity.getId();
            }});
        // 设置值
//        operationEntities.forEach(bean -> {
//            bean.setIs_enable()
//        });
        imPermissionOperationService.saveBatch(operationEntities);
    }
}
