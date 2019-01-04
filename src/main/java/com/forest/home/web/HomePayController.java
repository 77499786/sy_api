package com.forest.home.web;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.home.model.HomePay;
import com.forest.home.service.HomePayService;
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
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Date;

/**
* Created by CodeGenerator on 2018/07/10.
*/
@RestController
@RequestMapping("/home/pay")
public class HomePayController extends BaseController {
    @Resource
    private HomePayService homePayService;
    private String _userid = "admin";

    @PostMapping("/delete")
    public Result delete(@RequestBody() HomePay homePay) {
        homePayService.deleteById(homePay.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/save")
    public Result save( @RequestBody() HomePay homePay,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        String message ="数据保存成功。";
        homePay.setModifer(_userid);
        homePay.setModifytime(new Date());
        if(StringUtils.isEmpty(homePay.getId())){
            homePay.setCreator(_userid);
            homePay.setCreatetime(new Date());
            homePayService.save(homePay);
            message ="新增数据成功。";
        } else {
            homePayService.update(homePay);
            message ="数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  HomePay homePay) {
        homePay = homePayService.findById(homePay.getId());
        return ResultGenerator.genSuccessResult(homePay);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<HomePay> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(HomePay.class);
        Example.Criteria criteria = condition.createCriteria();
        // 根据页面的查询条件编辑查询条件
        if(pageInfo.getList()!= null && pageInfo.getList().size() > 0){
            String typeid =pageInfo.getList().get(0).getZclx();
            if(StringUtils.isNotBlank(typeid)){
                criteria.andEqualTo("zclx", typeid);
            }
            HomePay res = pageInfo.getList().get(0);
            if(res.getZffs()!=null){
                criteria.andEqualTo("zffs",res.getZffs());
            }
            Date start = res.getStart_day();
            if(start == null){
                start = ForestDateUtils.formatShortDate("2010-01-01");
            }
            Date stop = res.getStop_day();
            if(stop == null){
                stop = ForestDateUtils.formatShortDate("2049-12-31");
            }
            criteria.andBetween("zcrq",start, DateUtils.addDays(stop,1));
        }
        condition.orderBy("zcrq").desc();
        List<HomePay> list = homePayService.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
