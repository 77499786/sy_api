package com.forest.sy.web;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.project.model.FrameSetting;
import com.forest.project.service.FrameSettingService;
import com.forest.sy.model.SyApply;
import com.forest.sy.model.SyApplyWt;
import com.forest.sy.service.SyApplyService;
import com.forest.sy.service.SyApplyWtService;
import com.forest.utils.FileUtils;
import com.forest.utils.ScsyReportUtil;
import com.forest.utils.StringUtil;
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
@RequestMapping("/sy/apply")
public class SyApplyController extends BaseController {
    @Resource
    private SyApplyService syApplyService;
    @Resource
    private FrameSettingService frameSettingService;
    @Resource
    private SyApplyWtService syApplyWtService;

    @PostMapping("/delete")
    public Result delete(@RequestBody()  SyApply syApply) {
        syApplyService.deleteById(syApply.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/addnew")
    public Result addnew(@RequestBody SyApply syApply) {
        super.getUserAccount();
        String sqphprix = null;
        if(null == syApply.getIscjwt() || 0 == syApply.getIscjwt()){
            sqphprix = "B".concat(super.getSyJynd().substring(2));
            syApply.setIscjwt(0);
        } else {
            sqphprix = "C".concat(super.getSyJynd().substring(2));
        }
        Condition condition = new Condition(SyApply.class);
        Example.Criteria criteria =  condition.createCriteria();
        // 根据页面的查询条件编辑查询条件
        super.getUserAccount();
        criteria.andLike("sqph",sqphprix + "%");
        condition.orderBy("sqph").desc();
        List<SyApply> list = syApplyService.findByCondition(condition);
        Integer currentno = 1;
        if(list.size() > 0){
            currentno = Integer.valueOf(list.get(0).getSqph().replaceAll(sqphprix, ""));
            currentno++;
        }
        syApply.setCompid(_userid);
        syApply.setSqph(sqphprix+StringUtil.alignLeft(currentno.toString(),4, "0"));
        syApply.setModifer(_userid);
        syApply.setModifytime(new Date());
        syApply.setCreator(_userid);
        syApply.setCreatetime(new Date());
        syApplyService.save(syApply);
        return ResultGenerator.genSuccessResult(syApply);
    }

    @PostMapping("/save")
    public Result save( @RequestBody() SyApply syApply,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        String message ="数据保存成功。";
        super.getUserAccount();
        syApply.setModifer(_userid);
        syApply.setModifytime(new Date());
        if(StringUtils.isEmpty(syApply.getId())){
            syApply.setCreator(_userid);
            syApply.setCreatetime(new Date());
            syApplyService.save(syApply);
            message ="新增数据成功。";
        } else {
            syApplyService.update(syApply);
            if(9 == syApply.getStatus()) {
                // 更新相关检验进度数据
                syApplyService.updateFlowStatus(syApply.getSqph());
            }
            message ="数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/savesp")
    public Result savesp( @RequestBody() SyApply syApply,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        String message ="审核完成。";
        SyApply apply = syApplyService.findBy("sqph", syApply.getSqph());
        apply.setSprq(syApply.getSprq());
        apply.setSpxx(syApply.getSpxx());
        apply.setStatus(syApply.getStatus());
        super.getUserAccount();
        apply.setModifer(_userid);
        apply.setModifytime(new Date());
        syApplyService.update(syApply);
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  SyApply syApply) {
        if(Strings.isNullOrEmpty(syApply.getId())) {
            syApply = syApplyService.findBy("sqph", syApply.getSqph());
        } else {
            syApply = syApplyService.findById(syApply.getId());
        }
        return ResultGenerator.genSuccessResult(syApply);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<SyApply> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(SyApply.class);
        Example.Criteria criteria =  condition.createCriteria();
        // 根据页面的查询条件编辑查询条件
        Integer iscjwt = (Integer) super.getItem(pageInfo, "iscjwt");
        if (null != iscjwt) {
            criteria.andEqualTo("iscjwt", iscjwt);
        }
        if(null == iscjwt || 0 == iscjwt) {
            super.getUserAccount();
            criteria.andEqualTo("compid", _userid);
        }
        condition.orderBy("sqph").desc();
        List<SyApply> list = syApplyService.findByCondition(condition);
        list.forEach(d -> {
            Condition c = new Condition(SyApplyWt.class);
            c.createCriteria().andEqualTo("sqph", d.getSqph());
            d.setYpjs(syApplyWtService.findByCondition(c).size());
        });
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 打印样品清单
     * @param syApply
     * @return
     */
    @PostMapping("/print")
    public Result print(@RequestBody  SyApply syApply, HttpServletRequest request) {
        // 打印报告
        String filepath = this.syApplyService.exportReport(syApply.getSqph());
        if(FileUtils.exists(ScsyReportUtil.getSystemRootPath() + filepath)){
            request.setAttribute("filepath",filepath);
            return ResultGenerator.genSuccessResult(filepath);
        } else {
            return ResultGenerator.genFailResult("样品清单打印失败。");
        }
    }

    /**
     * 打印样品清单
     * @param syApply
     * @return
     */
    @PostMapping("/printcj")
    public Result printcj(@RequestBody  SyApply syApply, HttpServletRequest request) {
        // 打印报告
        String filepath = this.syApplyService.exportBill(syApply.getSqph());
        if(FileUtils.exists(ScsyReportUtil.getSystemRootPath() + filepath)){
            request.setAttribute("filepath",filepath);
            return ResultGenerator.genSuccessResult(filepath);
        } else {
            return ResultGenerator.genFailResult("抽检清单打印失败。");
        }
    }

}
