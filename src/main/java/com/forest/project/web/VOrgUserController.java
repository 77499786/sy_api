package com.forest.project.web;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.project.model.VOrgUser;
import com.forest.project.service.VOrgUserService;
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
* Created by CodeGenerator on 2018/04/13.
*/
@RestController
@RequestMapping("/v/org/user")
public class VOrgUserController extends BaseController {
    @Resource
    private VOrgUserService vOrgUserService;

    @PostMapping("/detail")
    public Result detail(@RequestBody  VOrgUser vOrgUser) {
        vOrgUser = vOrgUserService.findById(vOrgUser.getId());
        return ResultGenerator.genSuccessResult(vOrgUser);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<VOrgUser> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(VOrgUser.class);
        // TODO 根据页面的查询条件编辑查询条件

        List<VOrgUser> list = vOrgUserService.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
