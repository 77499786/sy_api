package com.forest.sy.web;
import com.forest.core.ProjectConstant;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.sy.model.SyResult;
import com.forest.sy.service.SyFlowdataService;
import com.forest.sy.service.SyResultService;
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
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Date;

/**
* Created by CodeGenerator on 2018/09/10.
*/
@RestController
@RequestMapping("/sy/result")
public class SyResultController extends BaseController {
    @Resource
    private SyResultService syResultService;

    @PostMapping("/delete")
    public Result delete(@RequestBody()  SyResult syResult) {
        syResultService.deleteById(syResult.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/save")
    public Result save( @RequestBody() SyResult syResult,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        String message ="数据保存成功。";
        super.getUserAccount();
        syResult.setModifer(_userid);
        syResult.setModifytime(new Date());
        if(StringUtils.isEmpty(syResult.getId())){
            syResult.setCreator(_userid);
            syResult.setCreatetime(new Date());
            syResultService.save(syResult);
            message ="新增数据成功。";
        } else {
            syResultService.update(syResult);
            message ="数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  SyResult syResult) {
        syResult = syResultService.findById(syResult.getId());
        return ResultGenerator.genSuccessResult(syResult);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<SyResult> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(SyResult.class);
        Example.Criteria criteria =  condition.createCriteria();
        // 根据页面的查询条件编辑查询条件
        String jybh = (String)super.getItem(pageInfo,"jybh");
        if(Strings.isNullOrEmpty(jybh)){
            return ResultGenerator.genFailResult("参数错误");
        }
        criteria.andEqualTo("jybh", jybh);
        condition.orderBy("sortno").asc();
        List<SyResult> list = syResultService.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/saveresults")
    public Result saveresults(@RequestBody() @Valid PageInfo<SyResult> pageInfo, BindingResult bindingResult, HttpServletRequest request) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        super.getUserAccount();
        String message ="检验结果数据保存成功。";
        List<SyResult> newdatas = pageInfo.getList();
        // 读取检验编号
        String jybh = super.formatString(super.getItem(pageInfo, "jybh"));
        // 读取已有数据
        Condition condition = new Condition(SyResult.class);
        Example.Criteria criteria =  condition.createCriteria();
        criteria.andEqualTo("jybh", jybh);
        List<SyResult> exists_datas = syResultService.findByCondition(condition);
        // 已有数据修改（逻辑删除、更新）
        exists_datas.forEach(e -> {
            SyResult syResult = getEntityById(newdatas, e.getId());
            if (syResult == null) {
                e.setModifytime(new Date());
                e.setModifer(_userid);
                e.setInuse(Integer.valueOf(ProjectConstant.USE_STATUS.NOTUSE.getIndex()));
                syResultService.update(e);
            } else {
                e.setJyzx(syResult.getJyzx());
                e.setJyxx(syResult.getJyxx());
                e.setBzgd(syResult.getBzgd());
                e.setJybh(syResult.getJybh());
                e.setJyjg1(syResult.getJyjg1());
                e.setJyjg2(syResult.getJyjg2());
                e.setJyjg3(syResult.getJyjg3());
                e.setInuse(Integer.valueOf(ProjectConstant.USE_STATUS.INUSE.getIndex()));
                e.setModifer(_userid);
                e.setModifytime(new Date());
                syResultService.update(e);
                newdatas.removeIf(n -> e.getId().equals(n.getId()));
            }
        });
        // 新数据追加
        newdatas.forEach(syResult -> {
            if(Strings.isNullOrEmpty(syResult.getId())){
                syResult.setCreator(_userid);
                syResult.setCreatetime(new Date());
                syResult.setInuse(Integer.valueOf(ProjectConstant.USE_STATUS.INUSE.getIndex()));
                syResult.setId(null);
                syResultService.save(syResult);
            } else {
                SyResult e = syResultService.findById(syResult.getId());
                e.setJyzx(syResult.getJyzx());
                e.setJyxx(syResult.getJyxx());
                e.setBzgd(syResult.getBzgd());
                e.setJybh(syResult.getJybh());
                e.setJyjg1(syResult.getJyjg1());
                e.setJyjg2(syResult.getJyjg2());
                e.setJyjg3(syResult.getJyjg3());
                e.setInuse(Integer.valueOf(ProjectConstant.USE_STATUS.INUSE.getIndex()));
                e.setModifer(_userid);
                e.setModifytime(new Date());
                syResultService.update(e);
            }
        });
        return ResultGenerator.genSuccessResult(message);
    }

    private SyResult getEntityById(List<SyResult> datas,String id ){
        for ( SyResult e: datas) {
            if (id.equals(e.getId())) {
                return e;
            }
        }
        return null;
    }
}
