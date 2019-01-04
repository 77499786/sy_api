package com.forest.home.web;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.home.model.HomeDictionary;
import com.forest.home.service.HomeDictionaryService;
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
* Created by CodeGenerator on 2018/07/10.
*/
@RestController
@RequestMapping("/home/dictionary")
public class HomeDictionaryController extends BaseController {
    @Resource
    private HomeDictionaryService homeDictionaryService;

    @PostMapping("/delete")
    public Result delete(@RequestBody() HomeDictionary homeDictionary) {
        homeDictionaryService.deleteById(homeDictionary.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/save")
    public Result save( @RequestBody() HomeDictionary homeDictionary,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        String message ="数据保存成功。";
        if(StringUtils.isEmpty(homeDictionary.getId())){
            homeDictionaryService.save(homeDictionary);
            message ="新增数据成功。";
        } else {
            homeDictionaryService.update(homeDictionary);
            message ="数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  HomeDictionary homeDictionary) {
        homeDictionary = homeDictionaryService.findById(homeDictionary.getId());
        return ResultGenerator.genSuccessResult(homeDictionary);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<HomeDictionary> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(HomeDictionary.class);
        // 根据页面的查询条件编辑查询条件
        if(pageInfo.getList()!= null && pageInfo.getList().size() > 0){
            String ty =pageInfo.getList().get(0).getDictionaryType();
            if(StringUtils.isNotBlank(ty)){
                condition.createCriteria().andEqualTo("dictionaryType", ty);
            }
        }
        condition.orderBy("dictionaryCode").asc();
        List<HomeDictionary> list = homeDictionaryService.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
