package com.perfect.bean.utils.common.tree;

import com.perfect.bean.vo.common.tree.ITreeNode;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 获取树型工具类
 *
 * @author zxh
 * @date 2019年 10月03日 11:33:46
 */
@Slf4j
public class TreeUtil {
    public static <T extends ITreeNode> List<T> getTreeList(List<T> listNodes) {
        /**
         * 按不同的深度保存数据：level（深度）
         */
        Map<Integer, List<T>> differentLevelData = new LinkedHashMap<>();
        for (T node : listNodes) {
            if (differentLevelData.containsKey(node.getLevel())) {
                List<T> valueList = differentLevelData.get(node.getLevel());
                valueList.add(node);
            } else {
                List<T> listBean = new ArrayList<>();
                listBean.add(node);
                differentLevelData.put(node.getLevel(), listBean);
            }
        }

        /**
         * 循环上述map，逆序循环 key=level，设置子结点，与父结点
         */
        List<T> rtnList = new ArrayList<>();
        ListIterator<Map.Entry<Integer, List<T>>> lt =
            new ArrayList<Map.Entry<Integer, List<T>>>(differentLevelData.entrySet())
                .listIterator(differentLevelData.size());
        while (lt.hasPrevious()) {
            Map.Entry<Integer, List<T>> entry = lt.previous();
            int level = entry.getKey();
            List<T> beans = entry.getValue();
            for (T bean : beans) {
                if (bean.getParent_id() == null) {
                    rtnList.add(bean);
                    continue;
                } else {
                    // 1：获取父亲结点，设置子结点数据
                    int parentLevel = level - 1;
                    List<T> parentList = differentLevelData.get(parentLevel);
                    T parentBean = getParentLevelData(parentList, bean.getParent_id());
                    List<T> childrenList = new ArrayList<>();
                    childrenList.add(bean);
                    if(parentBean != null){
                        if (parentBean.getChildren() == null) {
                            parentBean.setChildren(childrenList);
                        } else {
                            parentBean.getChildren().addAll(childrenList);
                        }
                    } else {
                        rtnList.add(bean);
                    }
                    // 2：设置父结点发生错误，2json时发生递归
                    // bean.setParent(parentBean);
                }
            }
        }

        return rtnList;
    }

    /**
     * 查找父结点的bean，并返回
     * 
     * @param beans
     * @param parentid
     * @param <T>
     * @return
     */
    private static <T extends ITreeNode> T getParentLevelData(List<T> beans, Long parentid) {
        if(beans != null) {
            for (T bean : beans) {
                if (bean.getId().equals(parentid)) {
                    bean.setLeaf(false);
                    return bean;
                }
            }
        }
        return null;
    }
}
