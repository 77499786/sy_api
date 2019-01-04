package com.forest.crm.web;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.crm.model.CrmSales;
import com.forest.crm.service.CrmCustomsService;
import com.forest.crm.service.CrmGoodsService;
import com.forest.crm.service.CrmSalesService;
import com.forest.utils.ForestDateUtils;
import com.forest.utils.JsonUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
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
import java.util.Map;

/**
* Created by CodeGenerator on 2018/08/06.
*/
@RestController
@RequestMapping("/crm/sales")
public class CrmSalesController extends BaseController<CrmSales> {
    @Resource
    private CrmSalesService crmSalesService;
    @Resource
    private CrmGoodsService crmGoodsService;
    @Resource
    private CrmCustomsService crmCustomsService;

    @PostMapping("/delete")
    public Result delete(@RequestBody()  CrmSales crmSales) {
        crmSalesService.deleteById(crmSales.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/save")
    public Result save( @RequestBody() CrmSales crmSales,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        super.getUserAccount();
        String message ="数据保存成功。";
        crmSales.setModifer(_userid);
        crmSales.setModifytime(new Date());
        if(StringUtils.isEmpty(crmSales.getId())){
            crmSales.setCreator(_userid);
            crmSales.setCreatetime(new Date());
            crmSalesService.save(crmSales);
            message ="新增数据成功。";
        } else {
            crmSalesService.update(crmSales);
            message ="数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  CrmSales crmSales) {
        crmSales = crmSalesService.findById(crmSales.getId());
        return ResultGenerator.genSuccessResult(crmSales);
    }

    @PostMapping("/list")
    public Result list(@RequestBody() @Valid PageInfo<CrmSales> pageInfo, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(CrmSales.class);
        Example.Criteria criteria =  condition.createCriteria();
        //  根据页面的查询条件编辑查询条件
        String sp = super.formatString(super.getItem(pageInfo, "sp"));
        if( !Strings.isNullOrEmpty(sp)){
            criteria.andEqualTo("sp",sp);
        }
        String gk = super.formatString(super.getItem(pageInfo, "gk"));
        if(!Strings.isNullOrEmpty(gk)){
            criteria.andEqualTo("gk",gk);
        }
        Date start =(Date)(super.getItem(pageInfo, "start_day"));
        Date stop =(Date)(super.getItem(pageInfo, "stop_day"));
        if(start == null){
            start = ForestDateUtils.formatShortDate("2010-01-01");
        }
        if(stop == null){
            stop = ForestDateUtils.formatShortDate("2049-12-31");
        }
        criteria.andBetween("xsrq",start, DateUtils.addDays(stop,1));

        Integer startxh =(Integer)(super.getItem(pageInfo, "start_xh"));
        Integer stopxh =(Integer)(super.getItem(pageInfo, "stop_xh"));
        if(startxh == null){
            startxh = 0;
        }
        if(stopxh == null){
            stopxh = 999999;
        }
        criteria.andBetween("xh",startxh, stopxh);

        String remark = super.formatString(super.getItem(pageInfo, "remark"));
        if(!Strings.isNullOrEmpty(remark)){
            criteria.andLike("remark","%"+remark+"%");
        }

        Integer jsbz = (Integer) super.getItem(pageInfo, "jsbz");
        if(jsbz != null){
            criteria.andEqualTo("jsbz", jsbz);
        }

        Map<String, Object> sortmap = JsonUtil.string2Map( pageInfo.getOrderBy());
        sortmap.forEach((k,v)->{
            if (v.toString().toLowerCase().startsWith("asc")) {
                condition.orderBy(k).asc();
            } else if (v.toString().toLowerCase().startsWith("desc")) {
                condition.orderBy(k).desc();
            }
        });
        String sfbz = super.formatString(super.getItem(pageInfo, "sfbz"));
        if( !Strings.isNullOrEmpty(sfbz)){
            criteria.andEqualTo("sfbz",sfbz);
        }

        if(sortmap.size() <= 0){
            condition.orderBy("xsrq").desc();
        }
        List<CrmSales> list = crmSalesService.findByCondition(condition);

        for(CrmSales sale : list){
            sale.setSpmc(crmGoodsService.findById(sale.getSp()).getSpmc());
            sale.setGkxm(crmCustomsService.findById(sale.getGk()).getGkxm());
        }
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
