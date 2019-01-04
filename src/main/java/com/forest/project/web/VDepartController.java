package com.forest.project.web;
import com.forest.core.BaseController;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.project.model.VDepart;
import com.forest.project.service.VDepartService;
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
* Created by CodeGenerator on 2018/04/09.
*/
@RestController
@RequestMapping("/v/depart")
public class VDepartController extends BaseController {
    @Resource
    private VDepartService vDepartService;

    @PostMapping("/add")
    public Result add(@RequestBody() @Valid VDepart vDepart, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        vDepartService.save(vDepart);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody()  VDepart vDepart) {
        vDepartService.deleteById(vDepart.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update( @RequestBody() @Valid VDepart vDepart,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        vDepartService.update(vDepart);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  VDepart vDepart) {
        vDepart = vDepartService.findById(vDepart.getId());
        return ResultGenerator.genSuccessResult(vDepart);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<VDepart> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(VDepart.class);
        //  根据页面的查询条件编辑查询条件
        if(StringUtils.isNotEmpty(pageInfo.getList().get(0).getDepartname())){
            condition.createCriteria().andIsNotNull("departname").andLike("departname","%"+pageInfo.getList().get(0).getDepartname()+"%");
        }
        List<VDepart> list = vDepartService.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
