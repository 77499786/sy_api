package com.forest.project.web;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.project.model.FrameRoleresourceorg;
import com.forest.project.service.FrameRoleresourceorgService;
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
import java.util.Date;

/**
* Created by CodeGenerator on 2018/04/11.
*/
@RestController
@RequestMapping("/frame/roleresourceorg")
public class FrameRoleresourceorgController extends BaseController {
    @Resource
    private FrameRoleresourceorgService frameRoleresourceorgService;
//    private String _userid = "admin";

    @PostMapping("/delete")
    public Result delete(@RequestBody() FrameRoleresourceorg frameRoleresourceorg) {
        frameRoleresourceorgService.deleteById(frameRoleresourceorg.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/save")
    public Result save( @RequestBody() FrameRoleresourceorg frameRoleresourceorg,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        super.getUserAccount();
        String message ="数据保存成功。";
        frameRoleresourceorg.setModifer(_userid);
        frameRoleresourceorg.setModifytime(new Date());
        if(StringUtils.isEmpty(frameRoleresourceorg.getId())){
            frameRoleresourceorg.setCreator(_userid);
            frameRoleresourceorg.setCreatetime(new Date());
            frameRoleresourceorgService.save(frameRoleresourceorg);
            message ="新增数据成功。";
        } else {
            frameRoleresourceorgService.update(frameRoleresourceorg);
            message ="数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  FrameRoleresourceorg frameRoleresourceorg) {
        frameRoleresourceorg = frameRoleresourceorgService.findById(frameRoleresourceorg.getId());
        return ResultGenerator.genSuccessResult(frameRoleresourceorg);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<FrameRoleresourceorg> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(FrameRoleresourceorg.class);
        // TODO 根据页面的查询条件编辑查询条件

        List<FrameRoleresourceorg> list = frameRoleresourceorgService.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
