package com.forest.sy.web;
import com.forest.core.BaseModel;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.sy.model.SyApplyWt;
import com.forest.sy.model.SyWeituo;
import com.forest.sy.service.SyApplyWtService;
import com.forest.sy.service.SyWeituoService;
import com.forest.utils.StringUtil;
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
* Created by CodeGenerator on 2018/09/10.
*/
@RestController
@RequestMapping("/sy/apply/wt")
public class SyApplyWtController extends BaseController {
    @Resource
    private SyApplyWtService syApplyWtService;
    @Resource
    private SyWeituoService syWeituoService;


    @PostMapping("/delete")
    public Result delete(@RequestBody()  SyApplyWt syApplyWt) {
        if(!Strings.isNullOrEmpty(syApplyWt.getId())){
            syApplyWt =syApplyWtService.findById(syApplyWt.getId());
            Condition conditionw = new Condition(SyWeituo.class);
            conditionw.createCriteria().andEqualTo("ypbh", syApplyWt.getYpbh());
            syWeituoService.deleteByCondition(conditionw);
            syApplyWtService.deleteById(syApplyWt.getId());
        } else {
            Condition condition = new Condition(SyApplyWt.class);
            condition.createCriteria().andEqualTo("ypbh", syApplyWt.getYpbh());
            syApplyWtService.deleteByCondition(condition);
            Condition conditionw = new Condition(SyWeituo.class);
            conditionw.createCriteria().andEqualTo("ypbh", syApplyWt.getYpbh());
            syWeituoService.deleteByCondition(conditionw);
        }
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/save")
    public Result save( @RequestBody() SyApplyWt syApplyWt,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        String message ="数据保存成功。";
        super.getUserAccount();
        syApplyWt.setModifer(_userid);
        syApplyWt.setModifytime(new Date());
        if(StringUtils.isEmpty(syApplyWt.getId())){
            syApplyWt.setCreator(_userid);
            syApplyWt.setCreatetime(new Date());
            syApplyWtService.save(syApplyWt);
            message ="新增数据成功。";
        } else {
            syApplyWtService.update(syApplyWt);
            message ="数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/getnewno")
    public Result getnewno(@RequestBody  SyApplyWt syApplyWt) {
        SyWeituo syWeituo = new SyWeituo();
        if (!Strings.isNullOrEmpty(syApplyWt.getYpbh())) {
            syWeituo = syWeituoService.findBy("ypbh", syApplyWt.getYpbh());
        }
        Condition condition = new Condition(SyApplyWt.class);
        Example.Criteria criteria =  condition.createCriteria();
        criteria.andEqualTo("sqph", syApplyWt.getSqph());
        condition.orderBy("ypbh").desc();
        List<SyApplyWt> list = syApplyWtService.findByCondition(condition);
        Integer bh = 1;
        if(list.size() > 0){
            bh = Integer.valueOf(list.get(0).getYpbh().replaceAll(syApplyWt.getSqph(), ""));
            bh++;
        }
        super.getUserAccount();
        syApplyWt.setYpbh(syApplyWt.getSqph() + StringUtil.alignLeft(bh.toString(),3,'0'));
        syApplyWt.setModifer(_userid);
        syApplyWt.setModifytime(new Date());
        syApplyWt.setInuse(BaseModel.INUSE.TRUE.getValue());
        syApplyWt.setCreator(_userid);
        syApplyWt.setCreatetime(new Date());
        syApplyWtService.save(syApplyWt);

        syWeituo.setJynd(super.getSyJynd());
        syWeituo.setYpbh(syApplyWt.getYpbh());
        syWeituo.setInuse(BaseModel.INUSE.TRUE.getValue());
        if (!Strings.isNullOrEmpty(syWeituo.getId())) {
            syWeituo.setId(null);
        }
        syWeituoService.save(syWeituo);

        return ResultGenerator.genSuccessResult(syWeituo);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  SyApplyWt syApplyWt) {
        syApplyWt = syApplyWtService.findById(syApplyWt.getId());
        return ResultGenerator.genSuccessResult(syApplyWt);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<SyApplyWt> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(SyApplyWt.class);
        Example.Criteria criteria =  condition.createCriteria();
        criteria.andEqualTo("sqph", pageInfo.getList().get(0).getSqph());
        //  根据页面的查询条件编辑查询条件
        condition.orderBy("ypbh").desc();
        List<SyApplyWt> list = syApplyWtService.findByCondition(condition);

        list.forEach(e->{
            e.setSyWeituo(syWeituoService.findBy("ypbh", e.getYpbh()));
        });
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
