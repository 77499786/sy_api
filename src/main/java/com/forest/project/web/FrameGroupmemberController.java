package com.forest.project.web;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.project.model.FrameGroupmember;
import com.forest.project.service.FrameGroupmemberService;
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
@RequestMapping("/frame/groupmember")
public class FrameGroupmemberController extends BaseController {
    @Resource
    private FrameGroupmemberService frameGroupmemberService;
//    private String _userid = "admin";

    @PostMapping("/delete")
    public Result delete(@RequestBody() FrameGroupmember frameGroupmember) {
        frameGroupmemberService.deleteById(frameGroupmember.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/save")
    public Result save( @RequestBody() FrameGroupmember frameGroupmember,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        super.getUserAccount();
        String message ="数据保存成功。";
        frameGroupmember.setModifer(_userid);
        frameGroupmember.setModifytime(new Date());
        if(StringUtils.isEmpty(frameGroupmember.getId())){
            frameGroupmember.setCreator(_userid);
            frameGroupmember.setCreatetime(new Date());
            frameGroupmemberService.save(frameGroupmember);
            message ="新增数据成功。";
        } else {
            frameGroupmemberService.update(frameGroupmember);
            message ="数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  FrameGroupmember frameGroupmember) {
        frameGroupmember = frameGroupmemberService.findById(frameGroupmember.getId());
        return ResultGenerator.genSuccessResult(frameGroupmember);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<FrameGroupmember> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(FrameGroupmember.class);
        // TODO 根据页面的查询条件编辑查询条件

        List<FrameGroupmember> list = frameGroupmemberService.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
