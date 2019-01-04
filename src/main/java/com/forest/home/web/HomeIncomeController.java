package com.forest.home.web;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.home.model.HomeIncome;
import com.forest.home.service.HomeIncomeService;
import com.forest.utils.ForestDateUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.validation.BindingResult;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Date;

/**
* Created by CodeGenerator on 2018/07/10.
*/
@RestController
@RequestMapping("/home/income")
public class HomeIncomeController extends BaseController {
    @Resource
    private HomeIncomeService homeIncomeService;
    private String _userid = "admin";

    @PostMapping("/delete")
    public Result delete(@RequestBody() HomeIncome homeIncome) {
        homeIncomeService.deleteById(homeIncome.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/save")
    public Result save( @RequestBody() HomeIncome homeIncome,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        String message ="数据保存成功。";
        homeIncome.setModifer(_userid);
        homeIncome.setModifytime(new Date());
        if(StringUtils.isEmpty(homeIncome.getId())){
            homeIncome.setCreator(_userid);
            homeIncome.setCreatetime(new Date());
            homeIncomeService.save(homeIncome);
            message ="新增数据成功。";
        } else {
            homeIncomeService.update(homeIncome);
            message ="数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  HomeIncome homeIncome) {
        homeIncome = homeIncomeService.findById(homeIncome.getId());
        return ResultGenerator.genSuccessResult(homeIncome);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<HomeIncome> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(HomeIncome.class);
        // 根据页面的查询条件编辑查询条件
        if(pageInfo.getList()!= null && pageInfo.getList().size() > 0){
            String typeid =pageInfo.getList().get(0).getSrlx();
            if(StringUtils.isNotBlank(typeid)){
                condition.createCriteria().andEqualTo("srlx", typeid);
            }
            HomeIncome res = pageInfo.getList().get(0);
            Date start = res.getStart_day();
            if(start == null){
                start = ForestDateUtils.formatShortDate("2010-01-01");
            }
            Date stop = res.getStop_day();
            if(stop == null){
                stop = ForestDateUtils.formatShortDate("2049-12-31");
            }
            condition.createCriteria().andBetween("jzrq",start, DateUtils.addDays(stop,1));
        }
        condition.orderBy("jzrq").desc();

        List<HomeIncome> list = homeIncomeService.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
