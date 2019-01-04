package com.forest.project.web;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.project.model.VRole;
import com.forest.project.service.VRoleService;
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
import java.util.List;

/**
* Created by CodeGenerator on 2018/04/10.
*/
@RestController
@RequestMapping("/v/role")
public class VRoleController {
    @Resource
    private VRoleService vRoleService;

    @PostMapping("/add")
    public Result add(@RequestBody() VRole vRole, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        vRoleService.save(vRole);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody()  VRole vRole) {
        vRoleService.deleteById(vRole.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update( @RequestBody() @Valid VRole vRole,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        vRoleService.update(vRole);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  VRole vRole) {
        vRole = vRoleService.findById(vRole.getId());
        return ResultGenerator.genSuccessResult(vRole);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<VRole> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(VRole.class);
        //  根据页面的查询条件编辑查询条件
        if(StringUtils.isNotEmpty(pageInfo.getList().get(0).getName())){
            condition.createCriteria().andIsNotNull("name").andLike("name","%"+pageInfo.getList().get(0).getName()+"%");
        }

        List<VRole> list = vRoleService.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
