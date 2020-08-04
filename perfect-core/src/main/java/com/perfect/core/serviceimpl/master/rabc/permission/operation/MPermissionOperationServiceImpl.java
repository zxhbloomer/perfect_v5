package com.perfect.core.serviceimpl.master.rabc.permission.operation;

import com.perfect.bean.entity.master.rabc.permission.MPermissionEntity;
import com.perfect.bean.utils.common.tree.TreeUtil;
import com.perfect.bean.vo.master.rabc.permission.operation.OperationMenuDataVo;
import com.perfect.bean.vo.master.rabc.permission.operation.OperationMenuPageFunctionVo;
import com.perfect.bean.vo.master.rabc.permission.operation.OperationMenuVo;
import com.perfect.core.mapper.master.rabc.permission.MPermissionMapper;
import com.perfect.core.mapper.master.rabc.permission.operation.MPermissionOperationMapper;
import com.perfect.core.service.base.v1.BaseServiceImpl;
import com.perfect.core.service.master.rabc.permission.operation.IMPermissionOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author zxh
 * @since 2020-07-27
 */
@Service
public class MPermissionOperationServiceImpl extends BaseServiceImpl<MPermissionMapper, MPermissionEntity> implements
    IMPermissionOperationService {

    @Autowired
    private MPermissionOperationMapper mapper;

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
        List<OperationMenuDataVo> rtnList = TreeUtil.getTreeList(list);
        // 获取按钮清单
        List<Long> root_ids = new ArrayList<>();
        rtnList.stream()
            .collect(Collectors.toMap(OperationMenuDataVo::getRoot_id, Function.identity(), (oldValue, newValue) -> oldValue))
            .values()
            .stream()
            .forEach(item -> root_ids.add(item.getRoot_id()));
        searchCondition.setRoot_ids((Long[]) root_ids.toArray(new Long[root_ids.size()]));
        List<OperationMenuPageFunctionVo> pageFunctionVoList = mapper.getAllMenuButton(searchCondition);

        mMenuVo.setMenu_data(rtnList);
        mMenuVo.setMenu_buttons(pageFunctionVoList);

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
}
