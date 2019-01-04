package com.forest.home.web;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.home.model.HomeDictionaryType;
import com.forest.home.service.HomeDictionaryTypeService;
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
* Created by CodeGenerator on 2018/07/10.
*/
@RestController
@RequestMapping("/home/dictionary/type")
public class HomeDictionaryTypeController extends BaseController {
    @Resource
    private HomeDictionaryTypeService homeDictionaryTypeService;
    private String _userid = "admin";

    @PostMapping("/delete")
    public Result delete(@RequestBody()  HomeDictionaryType homeDictionaryType) {
        homeDictionaryTypeService.deleteById(homeDictionaryType.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/save")
    public Result save( @RequestBody() HomeDictionaryType homeDictionaryType,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        String message = "数据保存成功。";
        homeDictionaryType.setModifer(_userid);
        homeDictionaryType.setModifytime(new Date());
        if(StringUtils.isEmpty(homeDictionaryType.getId())){
            homeDictionaryType.setCreator(_userid);
            homeDictionaryType.setCreatetime(new Date());
            homeDictionaryTypeService.save(homeDictionaryType);
            message ="新增数据成功。";
        } else {
            homeDictionaryTypeService.update(homeDictionaryType);
            message ="数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  HomeDictionaryType homeDictionaryType) {
        homeDictionaryType = homeDictionaryTypeService.findById(homeDictionaryType.getId());
        return ResultGenerator.genSuccessResult(homeDictionaryType);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<HomeDictionaryType> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(HomeDictionaryType.class);

        List<HomeDictionaryType> list = homeDictionaryTypeService.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
