package com.forest.cl.web;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.cl.model.ClResult;
import com.forest.cl.service.ClResultService;
import com.forest.utils.ScsyResourceUtil;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Date;

/**
* Created by CodeGenerator on 2018/08/22.
*/
@RestController
@RequestMapping("/cl/result")
public class ClResultController extends BaseController {
    @Resource
    private ClResultService clResultService;

    @PostMapping("/delete")
    public Result delete(@RequestBody()  ClResult clResult) {
        clResultService.deleteById(clResult.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/save")
    public Result save( @RequestBody() ClResult clResult,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        String message ="数据保存成功。";
        super.getUserAccount();
        clResult.setModifer(_userid);
        clResult.setModifytime(new Date());
        if(StringUtils.isEmpty(clResult.getId())){
            clResult.setCreator(_userid);
            clResult.setCreatetime(new Date());
            clResultService.save(clResult);
            message ="新增数据成功。";
        } else {
            clResultService.update(clResult);
            message ="数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  ClResult clResult) {
        clResult = clResultService.findById(clResult.getId());
        return ResultGenerator.genSuccessResult(clResult);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<ClResult> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(ClResult.class);
        Example.Criteria criteria =  condition.createCriteria();
        // 根据页面的查询条件编辑查询条件
        String jybh = super.formatString(super.getItem(pageInfo, "jybh"));
        if( !Strings.isNullOrEmpty(jybh)){
            criteria.andLike("jybh",jybh);
        }
        condition.orderBy("sortno").asc();
        List<ClResult> list = clResultService.findByCondition(condition);

        list.forEach(e->{
            List<String> keys = Arrays.asList(e.getDetectItem().replace("[", "")
                .replace("]","").replaceAll("\"","").split(","));
            Collections.reverse(keys);
            e.setDetectItemName(ScsyResourceUtil.getDicitionary(keys.get(0)));
        });
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
