package com.forest.project.web;

import com.forest.core.BaseController;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.project.model.FrameSetting;
import com.forest.project.service.FrameSettingService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
* Created by CodeGenerator on 2018/09/11.
*/
@RestController
@RequestMapping("/frame/setting")
public class FrameSettingController extends BaseController {
    @Resource
    private FrameSettingService frameSettingService;

    @PostMapping("/delete")
    public Result delete(@RequestBody() FrameSetting frameSetting) {
        frameSettingService.deleteById(frameSetting.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/save")
    public Result save(@RequestBody() FrameSetting frameSetting, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        String message ="数据保存成功。";
        super.getUserAccount();
        frameSetting.setModifer(_userid);
        frameSetting.setModifytime(new Date());
        if(StringUtils.isEmpty(frameSetting.getId())){
            frameSetting.setCreator(_userid);
            frameSetting.setCreatetime(new Date());
            frameSettingService.save(frameSetting);
            message ="新增数据成功。";
        } else {
            frameSettingService.update(frameSetting);
            message ="数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody FrameSetting frameSetting) {
        if(Strings.isNullOrEmpty(frameSetting.getCode())){
            frameSetting = frameSettingService.findById(frameSetting.getId());
        } else {
            frameSetting = frameSettingService.findBy("code", frameSetting.getCode());
        }
        return ResultGenerator.genSuccessResult(frameSetting);
    }

    @PostMapping("/list")
    public Result list(@RequestBody() @Valid PageInfo<FrameSetting> pageInfo, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(FrameSetting.class);
        Example.Criteria criteria =  condition.createCriteria();
        // TODO 根据页面的查询条件编辑查询条件

        List<FrameSetting> list = frameSettingService.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
