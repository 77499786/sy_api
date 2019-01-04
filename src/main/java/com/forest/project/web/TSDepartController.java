package com.forest.project.web;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.project.model.TSDepart;
import com.forest.project.service.TSDepartService;
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

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Created by CodeGenerator on 2018/04/09.
*/
@RestController
@RequestMapping("/t/s/depart")
public class TSDepartController {
    @Resource
    private TSDepartService tSDepartService;

    @PostMapping("/add")
    public Result add(@RequestBody() @Valid TSDepart tSDepart, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        tSDepartService.save(tSDepart);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody()  TSDepart tSDepart) {
        tSDepartService.deleteById(tSDepart.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update( @RequestBody() @Valid TSDepart tSDepart,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        tSDepartService.update(tSDepart);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  TSDepart tSDepart) {
        tSDepart = tSDepartService.findById(tSDepart.getId());
        return ResultGenerator.genSuccessResult(tSDepart);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<TSDepart> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(TSDepart.class);
        // TODO 根据页面的查询条件编辑查询条件
        if(StringUtils.isNotEmpty(pageInfo.getList().get(0).getDepartname())){
            condition.createCriteria().andIsNotNull("departname").andLike("departname","%"+pageInfo.getList().get(0).getDepartname()+"%");
        }

        List<TSDepart> list = tSDepartService.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/list2")
    public Result list2( @RequestBody() @Valid PageInfo<TSDepart> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
//        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        StringBuilder sqlwhere = new StringBuilder();
        //  根据页面的查询条件编辑查询条件
        if(StringUtils.isNotEmpty(pageInfo.getList().get(0).getDepartname())){
            sqlwhere.append(" where departname like '%").append(pageInfo.getList().get(0).getDepartname()).append("%'");
        }
        Map<String, Object > param = new HashMap<String, Object>();
        param.put("procIndex", "depart");
        param.put("sqlWhere", sqlwhere.toString());
        List<Map<String, Object>> list = tSDepartService.callProcure(param);
        PageInfo<Map<String, Object>> pageInfo2 = new PageInfo<Map<String, Object>>();
//        pageInfo2.setTotal(page.getTotal());
        pageInfo2.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo2);
    }

}
