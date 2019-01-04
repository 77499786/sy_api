package com.forest.crm.web;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.crm.model.CrmGoods;
import com.forest.crm.service.CrmGoodsService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
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
* Created by CodeGenerator on 2018/08/06.
*/
@RestController
@RequestMapping("/crm/goods")
public class CrmGoodsController extends BaseController {
    @Resource
    private CrmGoodsService crmGoodsService;

    @PostMapping("/delete")
    public Result delete(@RequestBody()  CrmGoods crmGoods) {
        crmGoodsService.deleteById(crmGoods.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/save")
    public Result save( @RequestBody() CrmGoods crmGoods,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        super.getUserAccount();
        String message ="数据保存成功。";
        crmGoods.setModifer(_userid);
        crmGoods.setModifytime(new Date());
        if(StringUtils.isEmpty(crmGoods.getId())){
            crmGoods.setCreator(_userid);
            crmGoods.setCreatetime(new Date());
            crmGoodsService.save(crmGoods);
            message ="新增数据成功。";
        } else {
            crmGoodsService.update(crmGoods);
            message ="数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  CrmGoods crmGoods) {
        crmGoods = crmGoodsService.findById(crmGoods.getId());
        return ResultGenerator.genSuccessResult(crmGoods);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<CrmGoods> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(CrmGoods.class);
        Example.Criteria criteria =  condition.createCriteria();
        //  根据页面的查询条件编辑查询条件
        Object keysty = super.getItem(pageInfo, "spmc");
        if(keysty != null && !Strings.isNullOrEmpty(keysty.toString())){
            criteria.andLike("spmc","%"+ keysty +"%");
        }

        List<CrmGoods> list = crmGoodsService.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
