package com.forest.cl.web;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.cl.model.ClFlowdefine;
import com.forest.cl.service.ClFlowdefineService;
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
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Date;

/**
* Created by CodeGenerator on 2018/08/22.
*/
@RestController
@RequestMapping("/cl/flowdefine")
public class ClFlowdefineController extends BaseController {
    @Resource
    private ClFlowdefineService clFlowdefineService;

    @PostMapping("/delete")
    public Result delete(@RequestBody()  ClFlowdefine clFlowdefine) {
        clFlowdefineService.deleteById(clFlowdefine.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/save")
    public Result save( @RequestBody() ClFlowdefine clFlowdefine,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        String message ="数据保存成功。";
        super.getUserAccount();
        clFlowdefine.setModifer(_userid);
        clFlowdefine.setModifytime(new Date());
        if(StringUtils.isEmpty(clFlowdefine.getId())){
            clFlowdefine.setCreator(_userid);
            clFlowdefine.setCreatetime(new Date());
            clFlowdefineService.save(clFlowdefine);
            message ="新增数据成功。";
        } else {
            clFlowdefineService.update(clFlowdefine);
            message ="数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  ClFlowdefine clFlowdefine) {
        clFlowdefine = clFlowdefineService.findById(clFlowdefine.getId());
        return ResultGenerator.genSuccessResult(clFlowdefine);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<ClFlowdefine> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(ClFlowdefine.class);
        Example.Criteria criteria =  condition.createCriteria();
        // TODO 根据页面的查询条件编辑查询条件

        List<ClFlowdefine> list = clFlowdefineService.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 退回处理待选择页面显示
     * @param request
     * @return
     */
    @PostMapping(params = "getbacknode")
    public Result backnode(HttpServletRequest request) {
        // 查询可退回节点
        // 当前节点
        Integer node = Integer.valueOf(request.getParameter("node"));
        Condition condition = new Condition(ClFlowdefine.class);
        Example.Criteria criteria =  condition.createCriteria();
        criteria.andLessThan("indexno", node);
        List<ClFlowdefine> list = clFlowdefineService.findByCondition(condition);
        // 返回可退回节点
        return ResultGenerator.genSuccessResult(list);
    }
}
