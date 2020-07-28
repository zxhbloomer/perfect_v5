package com.perfect.manager.controller.master.rabc.permission.org;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.perfect.bean.pojo.result.JsonResult;
import com.perfect.bean.result.utils.v1.ResultUtil;
import com.perfect.bean.vo.master.org.MDeptVo;
import com.perfect.bean.vo.master.org.MOrgCountsVo;
import com.perfect.bean.vo.master.org.MOrgTreeVo;
import com.perfect.bean.vo.master.org.MOrgVo;
import com.perfect.common.annotations.SysLogAnnotion;
import com.perfect.core.service.master.rabc.permission.org.IMPermissionOrgService;
import com.perfect.framework.base.controller.v1.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author zhangxh
 */
@RestController
@RequestMapping(value = "/api/v1/permission/org")
@Slf4j
@Api("权限类页面左侧的树")
public class PermissionOrgController extends BaseController {

    @Autowired
    private IMPermissionOrgService service;

    @Autowired
    private RestTemplate restTemplate;

    @SysLogAnnotion("根据查询条件，获取组织机构信息")
    @ApiOperation("获取组织机构树数据")
    @PostMapping("/tree/list")
    @ResponseBody
    public ResponseEntity<JsonResult<List<MOrgTreeVo>>> treeList(@RequestBody(required = false) MOrgTreeVo searchCondition) {
        List<MOrgTreeVo> vo = service.getTreeList(searchCondition);
        return ResponseEntity.ok().body(ResultUtil.OK(vo));
    }

    @SysLogAnnotion("根据查询条件，获取所有的组织以及子组织数量，仅仅是数量")
    @ApiOperation("根据查询条件，获取所有的组织以及子组织数量，仅仅是数量")
    @PostMapping("/count")
    @ResponseBody
    public ResponseEntity<JsonResult<MOrgCountsVo>> getAllOrgDataCount(@RequestBody(required = false) MOrgVo searchCondition)  {
        MOrgCountsVo vo = service.getAllOrgDataCount(searchCondition);
        return ResponseEntity.ok().body(ResultUtil.OK(vo));
    }

    @SysLogAnnotion("根据查询条件，获取组织架构主表信息")
    @ApiOperation("根据查询条件，获取组织架构主表信息")
    @PostMapping("/list")
    @ResponseBody
    public ResponseEntity<JsonResult<List<MOrgTreeVo>>> getOrgs(@RequestBody(required = false) MOrgVo searchCondition)  {
        List<MOrgTreeVo> list = service.getOrgs(searchCondition);
        return ResponseEntity.ok().body(ResultUtil.OK(list));
    }

    @SysLogAnnotion("根据查询条件，获取部门信息")
    @ApiOperation("根据查询条件，获取部门信息")
    @PostMapping("/depts")
    @ResponseBody
    public ResponseEntity<JsonResult<IPage<MDeptVo>>> getDepts(@RequestBody(required = false) MOrgTreeVo searchCondition)  {
        IPage<MDeptVo> list = service.getDepts(searchCondition);
        return ResponseEntity.ok().body(ResultUtil.OK(list));
    }
}
