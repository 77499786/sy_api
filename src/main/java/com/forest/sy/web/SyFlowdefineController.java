package com.forest.sy.web;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.sy.model.SyFlowdefine;
import com.forest.sy.service.SyFlowdefineService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.validation.BindingResult;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Date;

/**
* Created by CodeGenerator on 2018/09/10.
*/
@RestController
@RequestMapping("/sy/flowdefine")
public class SyFlowdefineController extends BaseController {
    @Resource
    private SyFlowdefineService syFlowdefineService;

    @PostMapping("/delete")
    public Result delete(@RequestBody()  SyFlowdefine syFlowdefine) {
        syFlowdefineService.deleteById(syFlowdefine.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/save")
    public Result save( @RequestBody() SyFlowdefine syFlowdefine,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        String message ="数据保存成功。";
        super.getUserAccount();
        syFlowdefine.setModifer(_userid);
        syFlowdefine.setModifytime(new Date());
        if(StringUtils.isEmpty(syFlowdefine.getId())){
            syFlowdefine.setCreator(_userid);
            syFlowdefine.setCreatetime(new Date());
            syFlowdefineService.save(syFlowdefine);
            message ="新增数据成功。";
        } else {
            syFlowdefineService.update(syFlowdefine);
            message ="数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  SyFlowdefine syFlowdefine) {
        syFlowdefine = syFlowdefineService.findById(syFlowdefine.getId());
        return ResultGenerator.genSuccessResult(syFlowdefine);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<SyFlowdefine> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(SyFlowdefine.class);
        Example.Criteria criteria =  condition.createCriteria();
        //  根据页面的查询条件编辑查询条件
        condition.orderBy("indexno").asc();
        List<SyFlowdefine> list = syFlowdefineService.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
