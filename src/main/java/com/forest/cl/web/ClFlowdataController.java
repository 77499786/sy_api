package com.forest.cl.web;
import com.forest.cl.model.ClFlowdata;
import com.forest.cl.model.ClResult;
import com.forest.cl.model.ClResultsPage;
import com.forest.cl.service.ClFlowdataService;
import com.forest.cl.service.ClResultService;
import com.forest.core.BaseController;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.utils.DateUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
* Created by CodeGenerator on 2018/08/22.
*/
@RestController
@RequestMapping("/cl/flowdata")
public class ClFlowdataController extends BaseController {
    @Resource
    private ClFlowdataService clFlowdataService;
    @Resource
    private ClResultService clResultService;

    @PostMapping("/delete")
    public Result delete(@RequestBody()  ClFlowdata clFlowdata) {
        clFlowdataService.deleteById(clFlowdata.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/save")
    public Result save( @RequestBody() ClFlowdata clFlowdata,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        String message ="数据保存成功。";
        super.getUserAccount();
        clFlowdata.setModifer(_userid);
        clFlowdata.setModifytime(new Date());
        if(StringUtils.isEmpty(clFlowdata.getId())){
            clFlowdata.setCreator(_userid);
            clFlowdata.setCreatetime(new Date());
            clFlowdataService.save(clFlowdata);
            message ="新增数据成功。";
        } else {
            clFlowdataService.update(clFlowdata);
            message ="数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  ClFlowdata clFlowdata) {
        if(Strings.isNullOrEmpty(clFlowdata.getId())){
            clFlowdata = clFlowdataService.findBy("jybh",clFlowdata.getJybh());
        } else {
            clFlowdata = clFlowdataService.findById(clFlowdata.getId());
        }
        return ResultGenerator.genSuccessResult(clFlowdata);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<ClFlowdata> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(ClFlowdata.class);
        Example.Criteria criteria =  condition.createCriteria();
        // 根据页面的查询条件编辑查询条件
        String jybh = super.formatString(super.getItem(pageInfo, "jybh"));
        if( !Strings.isNullOrEmpty(jybh)){
            criteria.andLike("jybh", String.format("%%%s%%",jybh));
        }
        List<ClFlowdata> list = clFlowdataService.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 样品管理用多环节收样确认
     * @param request
     * @return
     */
    @PostMapping("/confirm")
    public Result confirm(HttpServletRequest request) {
        String jpbhs = request.getParameter("bhlist");
        int optype = Integer.valueOf(request.getParameter("optype"));
        String[] jpbhlist = jpbhs.split(",");
        String message = null;

        Condition condition = new Condition(ClFlowdata.class);
        Example.Criteria criteria =  condition.createCriteria();
        criteria.andIn("jybh", Arrays.asList(jpbhlist));
        ClFlowdata e = new ClFlowdata();
        switch (optype){
            case 1:
                e.setSyqr(DateUtils.getDate());
                message = String.format("确认完成%d检验委托的%s", jpbhlist.length, "样品入库");
                break;
            case 5:
                e.setJyqr(DateUtils.getDate());
                e.setJysqrr(_userid);
                message = String.format("确认完成%d检验委托的%s", jpbhlist.length, "检验室收样确认");
                break;
            case 9:
                e.setHsqr(DateUtils.getDate());
                message = String.format("确认完成%d检验委托的%s", jpbhlist.length, "残留回收");
                break;
        }
        clFlowdataService.updateByCondition(e, condition);
        return ResultGenerator.genSuccessResult(message);
    }

    /**
     * 保存检验人员信息
     * @param request
     * @return
     */
    @PostMapping("/savefenpei")
    public Result savefenpei(@RequestParam(value = "jyry") String jyry,
                             @RequestParam(value = "jpbhlist") String jpbhs,
                             HttpServletRequest request) {
        String message = null;
        message = String.format("数据保存成功。");

        Condition condition = new Condition(ClFlowdata.class);
        Example.Criteria criteria =  condition.createCriteria();
        if( !Strings.isNullOrEmpty(jyry)){
            criteria.andIn("jybh", Arrays.asList(jpbhs.split(",")));
        }
        ClFlowdata e = new ClFlowdata();
        e.setTodoperson(jyry);
        clFlowdataService.updateByCondition(e, condition);

        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/savemanage")
    public Result savemanage(@RequestParam(value = "optype") String optype,
                             @RequestParam(value = "jpbhlist") String jpbhs,
                             HttpServletRequest request) {
        String message = null;
        message = String.format("数据保存成功。");

        if (Strings.isNullOrEmpty(optype) || !Arrays.asList("1","2","3").contains(optype)) {
            return ResultGenerator.genFailResult("参数错误!");
        }
        Condition condition = new Condition(ClFlowdata.class);
        Example.Criteria criteria =  condition.createCriteria();
        criteria.andIn("jybh", Arrays.asList(jpbhs.split(",")));
        ClFlowdata e = new ClFlowdata();
        switch(optype){
            case "1":
                e.setSyqr(DateUtils.getDate());
                break;
            case "2":
                e.setJyqr(DateUtils.getDate());
                super.getUserAccount();
                e.setJysqrr(_userid);
                break;
            case "3":
                e.setHsqr(DateUtils.getDate());
                break;
        }
        clFlowdataService.updateByCondition(e, condition);

        return ResultGenerator.genSuccessResult(message);
    }

    //  设置隐藏标志
    @PostMapping("/savehide")
    public Result savehide(HttpServletRequest request) {
        String message = "数据处理成功。";

        // 获取需要隐藏的检验编号、保存
        String bhlist =  request.getParameter("bhlist");
        if(StringUtils.isNotBlank(bhlist)){
            Condition condition = new Condition(ClFlowdata.class);
            Example.Criteria criteria =  condition.createCriteria();
            criteria.andIn("jybh", Arrays.asList(bhlist.split(",")));
            ClFlowdata e = new ClFlowdata();
            e.setTjdx(0);
            clFlowdataService.updateByCondition(e, condition);
        }

        String removelist =  request.getParameter("removelist");
        if(StringUtils.isNotBlank(removelist)){
            List<String> dellist =  Arrays.asList(removelist.split(","));;
            if(dellist.size() > 0){
                Condition conditionreset = new Condition(ClFlowdata.class);
                Example.Criteria criteriaReset =  conditionreset.createCriteria();
                criteriaReset.andIn("jybh", dellist);
                ClFlowdata e = new ClFlowdata();
                e.setTjdx(1);
                clFlowdataService.updateByCondition(e, conditionreset);
            }
        }

        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/saveresults")
    public Result saveresults(@RequestBody() ClResultsPage page, HttpServletRequest request) {
        String message = null;
        message = String.format("%d条残留检验结果信息保存成功", page.getClResultList().size());
        List<ClResult> lst = page.getClResultList();
        lst.forEach(e->{
            e.setSortno(lst.indexOf(e) + 1);
        });
        Condition condition = new Condition(ClFlowdata.class);
        condition.createCriteria().andEqualTo("jybh", page.getClFlowdata().getJybh());
        clResultService.deleteByCondition(condition);
        lst.forEach(e->{
            clResultService.save(e);
        });
//        clResultService.save(lst);
        ClFlowdata clFlowdata = clFlowdataService.findBy("jybh",page.getClFlowdata().getJybh());
        clFlowdata.setJyjl(page.getClFlowdata().getJyjl());
        clFlowdataService.update(clFlowdata);
        return ResultGenerator.genSuccessResult(message);
    }

    /**
     * 报告发放数据保存
     * @param clFlowdata
     * @param bindingResult
     * @return
     */
    @PostMapping("/savesend")
    public Result savesend(@RequestBody() ClFlowdata clFlowdata, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        String message ="数据保存成功。";
        super.getUserAccount();
        ClFlowdata entity = clFlowdataService.findBy("jybh", clFlowdata.getJybh());
        entity.setSendto(clFlowdata.getSendto());
        entity.setPostday(clFlowdata.getPostday());
        entity.setExpress(clFlowdata.getExpress());
        clFlowdata.setModifer(_userid);
        clFlowdata.setModifytime(new Date());
        clFlowdataService.update(clFlowdata);
        message ="数据更新成功。";
        return ResultGenerator.genSuccessResult(message);
    }

}
