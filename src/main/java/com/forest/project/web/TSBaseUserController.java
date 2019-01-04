package com.forest.project.web;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.project.model.TSBaseUser;
import com.forest.project.service.TSBaseUserService;
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
@RequestMapping("/t/s/base/user")
public class TSBaseUserController {
    @Resource
    private TSBaseUserService tSBaseUserService;

    @PostMapping("/add")
    public Result add( @RequestBody() @Valid TSBaseUser tSBaseUser,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        tSBaseUserService.save(tSBaseUser);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody()  TSBaseUser tSBaseUser) {
        tSBaseUserService.deleteById(tSBaseUser.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update( @RequestBody() @Valid TSBaseUser tSBaseUser,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        tSBaseUserService.update(tSBaseUser);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  TSBaseUser tSBaseUser) {
        tSBaseUser = tSBaseUserService.findById(tSBaseUser.getId());
        return ResultGenerator.genSuccessResult(tSBaseUser);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<TSBaseUser> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(TSBaseUser.class);
        // TODO 根据页面的查询条件编辑查询条件
        List<TSBaseUser> list = tSBaseUserService.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
