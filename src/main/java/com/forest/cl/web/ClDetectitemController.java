package com.forest.cl.web;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.cl.model.ClDetectitem;
import com.forest.cl.service.ClDetectitemService;
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
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Date;

/**
* Created by CodeGenerator on 2018/08/22.
*/
@RestController
@RequestMapping("/cl/detectitem")
public class ClDetectitemController extends BaseController {
    @Resource
    private ClDetectitemService clDetectitemService;

    @PostMapping("/delete")
    public Result delete(@RequestBody()  ClDetectitem clDetectitem) {
        clDetectitemService.deleteById(clDetectitem.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/save")
    public Result save( @RequestBody() ClDetectitem clDetectitem,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        String message ="数据保存成功。";
        super.getUserAccount();
        clDetectitem.setModifer(_userid);
        clDetectitem.setModifytime(new Date());
        if(StringUtils.isEmpty(clDetectitem.getId())){
            clDetectitem.setCreator(_userid);
            clDetectitem.setCreatetime(new Date());
            clDetectitemService.save(clDetectitem);
            message ="新增数据成功。";
        } else {
            clDetectitemService.update(clDetectitem);
            message ="数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  ClDetectitem clDetectitem) {
        clDetectitem = clDetectitemService.findById(clDetectitem.getId());
        return ResultGenerator.genSuccessResult(clDetectitem);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<ClDetectitem> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(ClDetectitem.class);
        Example.Criteria criteria =  condition.createCriteria();
        // 根据页面的查询条件编辑查询条件
        criteria.andEqualTo("inuse",1);
        condition.orderBy("itemcode").asc();
        List<ClDetectitem> list = clDetectitemService.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
