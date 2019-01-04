package com.forest.project.web;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.project.model.VRoleUser;
import com.forest.project.service.VRoleUserService;
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

/**
* Created by CodeGenerator on 2018/04/13.
*/
@RestController
@RequestMapping("/v/role/user")
public class VRoleUserController extends BaseController<VRoleUser> {
    @Resource
    private VRoleUserService vRoleUserService;

    @PostMapping("/detail")
    public Result detail(@RequestBody VRoleUser vRoleUser) {
        vRoleUser = vRoleUserService.findById(vRoleUser.getId());
        return ResultGenerator.genSuccessResult(vRoleUser);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<VRoleUser> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(VRoleUser.class);
        Example.Criteria criteria = condition.createCriteria();
        String inuse = super.formatString(super.getItem(pageInfo, "inuse"));
        if(!Strings.isNullOrEmpty(inuse)){
            criteria.andEqualTo("inuse", "1");
        }
        // 根据页面的查询条件编辑查询条件
        if(pageInfo.getList() != null && StringUtils.isNotEmpty(pageInfo.getList().get(0).getRoleid())){
            criteria.andEqualTo("roleid",pageInfo.getList().get(0).getRoleid());
            if(!Strings.isNullOrEmpty(pageInfo.getList().get(0).getUsername())){
                criteria.andLike("username", "%".concat(pageInfo.getList().get(0).getUsername()).concat("%"));
            }
            condition.orderBy("account");
        }
        if(pageInfo.getList() != null && StringUtils.isNotEmpty(pageInfo.getList().get(0).getUserid())){
            criteria.andEqualTo("userid",pageInfo.getList().get(0).getUserid());
            condition.orderBy("rolecode");
        }

        List<VRoleUser> list = vRoleUserService.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
