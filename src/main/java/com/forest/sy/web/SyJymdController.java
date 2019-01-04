package com.forest.sy.web;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.sy.model.SyJymd;
import com.forest.sy.service.SyJymdService;
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
* Created by CodeGenerator on 2018/08/21.
*/
@RestController
@RequestMapping("/sy/jymd")
public class SyJymdController extends BaseController {
    @Resource
    private SyJymdService syJymdService;

    @PostMapping("/delete")
    public Result delete(@RequestBody()  SyJymd syJymd) {
        syJymdService.deleteById(syJymd.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/save")
    public Result save( @RequestBody() SyJymd syJymd,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        String message ="数据保存成功。";
        super.getUserAccount();
        syJymd.setModifer(_userid);
        syJymd.setModifytime(new Date());
        if(StringUtils.isEmpty(syJymd.getId())){
            syJymd.setCreator(_userid);
            syJymd.setCreatetime(new Date());
            syJymdService.save(syJymd);
            message ="新增数据成功。";
        } else {
            syJymdService.update(syJymd);
            message ="数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  SyJymd syJymd) {
        syJymd = syJymdService.findById(syJymd.getId());
        return ResultGenerator.genSuccessResult(syJymd);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<SyJymd> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(SyJymd.class);
        Example.Criteria criteria =  condition.createCriteria();
        // TODO 根据页面的查询条件编辑查询条件

        List<SyJymd> list = syJymdService.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
