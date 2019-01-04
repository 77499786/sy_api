package com.forest.project.web;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.project.model.TSRole;
import com.forest.project.service.TSRoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.validation.BindingResult;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
* Created by CodeGenerator on 2018/04/09.
*/
@RestController
@RequestMapping("/t/s/role")
public class TSRoleController {
    @Resource
    private TSRoleService tSRoleService;

    @PostMapping("/add")
    public Result add(@RequestBody() @Valid TSRole tSRole, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        tSRoleService.save(tSRole);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody()  TSRole tSRole) {
        tSRoleService.deleteById(tSRole.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update( @RequestBody() @Valid TSRole tSRole,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        tSRoleService.update(tSRole);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  TSRole tSRole) {
        tSRole = tSRoleService.findById(tSRole.getId());
        return ResultGenerator.genSuccessResult(tSRole);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<TSRole> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(TSRole.class);
        // TODO 根据页面的查询条件编辑查询条件

        List<TSRole> list = tSRoleService.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
