package com.forest.sy.web;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.sy.model.SySign;
import com.forest.sy.service.SySignService;
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
@RequestMapping("/sy/sign")
public class SySignController extends BaseController {
    @Resource
    private SySignService sySignService;

    @PostMapping("/delete")
    public Result delete(@RequestBody()  SySign sySign) {
        sySignService.deleteById(sySign.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/save")
    public Result save( @RequestBody() SySign sySign,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        String message ="数据保存成功。";
        super.getUserAccount();
        sySign.setModifer(_userid);
        sySign.setModifytime(new Date());
        if(StringUtils.isEmpty(sySign.getId())){
            sySign.setCreator(_userid);
            sySign.setCreatetime(new Date());
            sySignService.save(sySign);
            message ="新增数据成功。";
        } else {
            sySignService.update(sySign);
            message ="数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  SySign sySign) {
        sySign = sySignService.findById(sySign.getId());
        return ResultGenerator.genSuccessResult(sySign);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<SySign> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(SySign.class);
        Example.Criteria criteria =  condition.createCriteria();
        // TODO 根据页面的查询条件编辑查询条件

        List<SySign> list = sySignService.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
