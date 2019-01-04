package com.forest.crm.web;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.crm.model.CrmCustoms;
import com.forest.crm.model.CrmSales;
import com.forest.crm.service.CrmCustomsService;
import com.forest.crm.service.CrmSalesService;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Date;

/**
* Created by CodeGenerator on 2018/08/06.
*/
@RestController
@RequestMapping("/crm/customs")
public class CrmCustomsController extends BaseController<CrmCustoms> {
    @Resource
    private CrmCustomsService crmCustomsService;
    @Resource
    private CrmSalesService crmSalesService;

    @PostMapping("/delete")
    public Result delete(@RequestBody()  CrmCustoms crmCustoms) {
        crmCustomsService.deleteById(crmCustoms.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/save")
    public Result save( @RequestBody() CrmCustoms crmCustoms,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        super.getUserAccount();
        String message ="数据保存成功。";
        crmCustoms.setModifer(_userid);
        crmCustoms.setModifytime(new Date());
        if(StringUtils.isEmpty(crmCustoms.getId())){
            crmCustoms.setCreator(_userid);
            crmCustoms.setCreatetime(new Date());
            crmCustomsService.save(crmCustoms);
            message ="新增数据成功。";
        } else {
            crmCustomsService.update(crmCustoms);
            message ="数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  CrmCustoms crmCustoms) {
        crmCustoms = crmCustomsService.findById(crmCustoms.getId());
        return ResultGenerator.genSuccessResult(crmCustoms);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<CrmCustoms> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(CrmCustoms.class);
        Example.Criteria criteria =  condition.createCriteria();
        // 根据页面的查询条件编辑查询条件
        Object keysty = super.getItem(pageInfo, "gkxm");
        if(keysty != null && !Strings.isNullOrEmpty(keysty.toString())){
            criteria.andLike("gkxm","%"+ keysty +"%").orLike("lxdh","%"+ keysty +"%");
        }

        List<CrmCustoms> list = crmCustomsService.findByCondition(condition);
        for (CrmCustoms c: list) {
            Condition conditionC = new Condition(CrmSales.class);
            conditionC.createCriteria().andEqualTo("gk", c.getId());
            c.setSaleCount(crmSalesService.findByCondition(conditionC).size());
        }
        Collections.sort(list, new Comparator<CrmCustoms>() {
            public int compare(CrmCustoms o1, CrmCustoms o2) {
                return o2.getSaleCount() - o1.getSaleCount();
            }
        });
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
