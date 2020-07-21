package com.perfect.manager.controller.sys.config.module;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.perfect.bean.entity.sys.config.module.SModuleButtonEntity;
import com.perfect.bean.pojo.result.JsonResult;
import com.perfect.bean.pojo.result.UpdateResult;
import com.perfect.bean.result.utils.v1.ResultUtil;
import com.perfect.bean.vo.sys.config.module.SModuleButtonVo;
import com.perfect.common.annotations.SysLogAnnotion;
import com.perfect.common.exception.InsertErrorException;
import com.perfect.common.exception.UpdateErrorException;
import com.perfect.core.service.sys.config.module.IModuleButtonService;
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
@RequestMapping(value = "/api/v1/modulebutton")
@Slf4j
@Api("模块按钮表相关")
public class ModuleButtonController extends BaseController {

    @Autowired
    private IModuleButtonService service;

    @Autowired
    private RestTemplate restTemplate;

    @SysLogAnnotion("根据查询条件，获取模块按钮表信息")
    @ApiOperation("根据参数id，获取模块按钮表信息")
    @PostMapping("/list")
    @ResponseBody
    public ResponseEntity<JsonResult<IPage<SModuleButtonVo>>> list(@RequestBody(required = false) SModuleButtonVo searchCondition) {
        IPage<SModuleButtonVo> entity = service.selectPage(searchCondition);
        return ResponseEntity.ok().body(ResultUtil.OK(entity));
    }

    @SysLogAnnotion("模块按钮表数据更新保存")
    @ApiOperation("根据参数id，获取模块按钮表信息")
    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<JsonResult<SModuleButtonVo>> save(@RequestBody(required = false) SModuleButtonEntity bean) {

        if(service.update(bean).isSuccess()){
            return ResponseEntity.ok().body(ResultUtil.OK(service.selectByid(bean.getId()),"更新成功"));
        } else {
            throw new UpdateErrorException("保存的数据已经被修改，请查询后重新编辑更新。");
        }
    }

    @SysLogAnnotion("模块按钮表数据新增保存")
    @ApiOperation("根据参数id，获取模块按钮表信息")
    @PostMapping("/insert")
    @ResponseBody
    public ResponseEntity<JsonResult<SModuleButtonVo>> insert(@RequestBody(required = false) SModuleButtonEntity bean) {
        if(service.insert(bean).isSuccess()){
            return ResponseEntity.ok().body(ResultUtil.OK(service.selectByid(bean.getId()),"插入成功"));
        } else {
            throw new InsertErrorException("新增保存失败。");
        }
    }

    @SysLogAnnotion("模块按钮表数据逻辑物理删除，部分数据")
    @ApiOperation("根据参数id，逻辑删除数据")
    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<JsonResult<String>> delete(@RequestBody(required = false) List<SModuleButtonVo> searchConditionList) {
        service.realDeleteByIdsIn(searchConditionList);
        return ResponseEntity.ok().body(ResultUtil.OK("OK"));
    }

    @SysLogAnnotion("模块按钮表排序后保存")
    @ApiOperation("list数据的保存")
    @PostMapping("/save_list")
    @ResponseBody
    public ResponseEntity<JsonResult<List<SModuleButtonVo>>> saveList(@RequestBody(required = false) List<SModuleButtonVo> beanList) {
        UpdateResult<List<SModuleButtonVo>> result = service.saveList(beanList);
        if(result.isSuccess()){
            return ResponseEntity.ok().body(ResultUtil.OK(result.getData(),"更新成功"));
        } else {
            throw new UpdateErrorException("保存的数据已经被修改，请查询后重新编辑更新。");
        }
    }
}
