package com.perfect.manager.controller.master.rabc.permission.dept;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.perfect.bean.entity.master.org.MDeptEntity;
import com.perfect.bean.pojo.result.JsonResult;
import com.perfect.bean.result.utils.v1.ResultUtil;
import com.perfect.bean.vo.master.org.MDeptExportVo;
import com.perfect.bean.vo.master.org.MDeptVo;
import com.perfect.common.annotations.SysLogAnnotion;
import com.perfect.common.exception.InsertErrorException;
import com.perfect.common.exception.UpdateErrorException;
import com.perfect.common.utils.bean.BeanUtilsSupport;
import com.perfect.core.service.master.org.IMDeptService;
import com.perfect.core.service.master.rabc.permission.IMPermissionService;
import com.perfect.excel.export.ExcelUtil;
import com.perfect.framework.base.controller.v1.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author zhangxh
 */
@RestController
@RequestMapping(value = "/api/v1/permission/dept")
@Slf4j
@Api("部门权限相关")
public class PermissionDeptController extends BaseController {

    @Autowired
    private IMPermissionService permissionService;

    @Autowired
    private RestTemplate restTemplate;

    @SysLogAnnotion("根据查询条件，获取部门主表信息")
    @ApiOperation("根据参数id，获取部门主表信息")
    @PostMapping("/list")
    @ResponseBody
    public ResponseEntity<JsonResult<IPage<MDeptVo>>> list(@RequestBody(required = false)
        MDeptVo searchCondition)  {
        IPage<MDeptVo> entity = permissionService.selectPage(searchCondition);
        return ResponseEntity.ok().body(ResultUtil.OK(entity));
    }

    @SysLogAnnotion("部门主表数据更新保存")
    @ApiOperation("根据参数id，获取部门主表信息")
    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<JsonResult<MDeptVo>> save(@RequestBody(required = false) MDeptEntity bean) {

        if(permissionService.update(bean).isSuccess()){
            return ResponseEntity.ok().body(ResultUtil.OK(permissionService.selectByid(bean.getId()),"更新成功"));
        } else {
            throw new UpdateErrorException("保存的数据已经被修改，请查询后重新编辑更新。");
        }
    }

    @SysLogAnnotion("部门主表数据新增保存")
    @ApiOperation("根据参数id，获取部门主表信息")
    @PostMapping("/insert")
    @ResponseBody
    public ResponseEntity<JsonResult<MDeptVo>> insert(@RequestBody(required = false) MDeptEntity bean) {
        if(permissionService.insert(bean).isSuccess()){
            return ResponseEntity.ok().body(ResultUtil.OK(permissionService.selectByid(bean.getId()),"插入成功"));
        } else {
            throw new InsertErrorException("新增保存失败。");
        }
    }

    @SysLogAnnotion("部门主表数据导出")
    @ApiOperation("根据选择的数据，部门主表数据导出")
    @PostMapping("/export_selection")
    public void exportSelection(@RequestBody(required = false) List<MDeptVo> searchConditionList, HttpServletResponse response) throws
        IOException {
        List<MDeptVo> searchResult = permissionService.selectIdsInForExport(searchConditionList);
        List<MDeptExportVo> rtnList = BeanUtilsSupport.copyProperties(searchResult, MDeptExportVo.class);
        ExcelUtil<MDeptExportVo> util = new ExcelUtil<>(MDeptExportVo.class);
        util.exportExcel("部门主表数据导出", "部门主表数据", rtnList, response);
    }

    @SysLogAnnotion("部门主表数据逻辑删除复原")
    @ApiOperation("根据参数id，逻辑删除复原数据")
    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<JsonResult<String>> delete(@RequestBody(required = false) List<MDeptVo> searchConditionList) {
        permissionService.deleteByIdsIn(searchConditionList);
        return ResponseEntity.ok().body(ResultUtil.OK("OK"));
    }
}