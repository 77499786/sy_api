package com.forest.sy.web;
import com.forest.core.BaseModel;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.project.model.FrameDictionary;
import com.forest.project.model.FrameEmployee;
import com.forest.project.model.FrameRolemember;
import com.forest.project.service.FrameDictionaryService;
import com.forest.project.service.FrameEmployeeService;
import com.forest.project.service.FrameRolememberService;
import com.forest.sy.model.SyCompany;
import com.forest.sy.service.SyCompanyService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.Map;

/**
* Created by CodeGenerator on 2018/09/10.
*/
@RestController
@RequestMapping("/sy/company")
public class SyCompanyController extends BaseController {
    @Resource
    private SyCompanyService syCompanyService;
    @Resource
    private FrameEmployeeService frameEmployeeService;
    @Resource
    private FrameRolememberService frameRolememberService;
    @Resource
    private FrameDictionaryService frameDictionaryService;

    @PostMapping("/delete")
    public Result delete(@RequestBody()  SyCompany syCompany) {
        syCompanyService.deleteById(syCompany.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/save")
    public Result save( @RequestBody() SyCompany syCompany,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        String message ="数据保存成功。";
        super.getUserAccount();
        syCompany.setModifer(_userid);
        syCompany.setModifytime(new Date());
        if(StringUtils.isEmpty(syCompany.getId())){
            addCompanyUser(syCompany);
            syCompany.setCreator(_userid);
            syCompany.setCreatetime(new Date());
            syCompanyService.save(syCompany);
            message ="新增数据成功。";
        } else {
            syCompanyService.update(syCompany);
            // 密码变更时，更新用户信息
            FrameEmployee user = frameEmployeeService.getByUserId(syCompany.getUserid());
            if(!user.getPassword().equals(syCompany.getQymm())){
                user.setPassword(syCompany.getQymm());
                user.setModifer(_userid);
                user.setModifytime(new Date());
                frameEmployeeService.update(user);
            }
            message ="数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  SyCompany syCompany) {
        if(!Strings.isNullOrEmpty(syCompany.getUserid())){
            syCompany = syCompanyService.findBy("userid",syCompany.getUserid());
            if(syCompany != null) {
                FrameEmployee user = frameEmployeeService.getByUserId(syCompany.getUserid());
                syCompany.setQymm(user.getPassword());
            }
        } else {
            syCompany = syCompanyService.findById(syCompany.getId());
        }
        return ResultGenerator.genSuccessResult(syCompany);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<SyCompany> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(SyCompany.class);
        Example.Criteria criteria =  condition.createCriteria();
        // 根据页面的查询条件编辑查询条件
        String keystr = String.valueOf(super.getItem(pageInfo, "qymc"));
        if(!Strings.isNullOrEmpty(keystr)){
            criteria.orLike("qymc", String.format("%%%s%%", keystr));
            criteria.orLike("qydz", String.format("%%%s%%", keystr));
            criteria.orLike("scxkz", String.format("%%%s%%", keystr));
            criteria.orLike("gmp", String.format("%%%s%%", keystr));
            criteria.orLike("userid", String.format("%%%s%%", keystr));
        }

        List<SyCompany> list = syCompanyService.findByCondition(condition);

        Condition conditionDic = new Condition(FrameDictionary.class);
        conditionDic.createCriteria().andEqualTo("def", "SCFW");
        Map<String,String> dicmap = new HashMap<String, String>();
        frameDictionaryService.findByCondition(conditionDic).forEach(d ->{
            dicmap.put(d.getId(), d.getName());
        });
        list.forEach(e->{
            if(!Strings.isNullOrEmpty(e.getUserid())){
                FrameEmployee user = frameEmployeeService.getByUserId(e.getUserid());
                e.setQymm(user.getPassword());
            }
            if(!Strings.isNullOrEmpty(e.getScfw())) {
                e.setScfwmc(dicmap.get(e.getScfw()));
            }
        });

        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 增加用户数据、角色用户关联数据
     * @param syCompany 企业基本信息
     */
    private void addCompanyUser(SyCompany syCompany){
        // 先增加企业账号
        FrameEmployee user = new FrameEmployee();
        user.setUserid(syCompany.getQyzh());
        user.setPassword(syCompany.getQymm());
        user.setOrgId(BaseModel.ENTERPRISE_UNIT_ID);
        user.setInuse(BaseModel.INUSE.TRUE.getValue().toString());
        user.setName(syCompany.getQymc());
        user.setModifer(_userid);
        user.setModifytime(new Date());
        user.setCreator(_userid);
        user.setCreatetime(new Date());
        frameEmployeeService.save(user);
        // 保存角色配置
        FrameRolemember rm = new FrameRolemember();
        rm.setUserId(user.getId());
        rm.setRoleId(BaseModel.ENTERPRISE_ROLE_ID);
        rm.setModifer(_userid);
        rm.setModifytime(new Date());
        rm.setCreator(_userid);
        rm.setCreatetime(new Date());
        rm.setInuse(BaseModel.INUSE.TRUE.getValue().toString());
        frameRolememberService.save(rm);
    }

}
