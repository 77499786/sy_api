package com.forest.project.web;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.project.model.FrameFiles;
import com.forest.project.service.FrameFilesService;
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
* Created by CodeGenerator on 2018/07/29.
*/
@RestController
@RequestMapping("/frame/files")
public class FrameFilesController extends BaseController {
    @Resource
    private FrameFilesService frameFilesService;
    private String _userid = "admin";

    @PostMapping("/delete")
    public Result delete(@RequestBody()  FrameFiles frameFiles) {
        frameFilesService.deleteById(frameFiles.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/save")
    public Result save( @RequestBody() FrameFiles frameFiles,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        String message ="";
        frameFiles.setModifer(_userid);
        frameFiles.setModifytime(new Date());
        if(StringUtils.isEmpty(frameFiles.getId())){
            frameFiles.setCreator(_userid);
            frameFiles.setCreatetime(new Date());
            frameFilesService.save(frameFiles);
            message ="新增数据成功。";
        } else {
            frameFilesService.update(frameFiles);
            message ="数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  FrameFiles frameFiles) {
        frameFiles = frameFilesService.findById(frameFiles.getId());
        return ResultGenerator.genSuccessResult(frameFiles);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<FrameFiles> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(FrameFiles.class);
        // TODO 根据页面的查询条件编辑查询条件

        List<FrameFiles> list = frameFilesService.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
