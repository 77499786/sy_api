package com.forest.project.web;

import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.project.model.FrameRolemember;
import com.forest.project.service.FrameRolememberService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

/**
 * Created by CodeGenerator on 2018/04/11.
 */
@RestController
@RequestMapping("/frame/rolemember")
public class FrameRolememberController extends BaseController {
    @Resource
    private FrameRolememberService frameRolememberService;
//    private String _userid = "admin";

    @PostMapping("/delete")
    public Result delete(@RequestBody() FrameRolemember frameRolemember) {
        frameRolememberService.deleteById(frameRolemember.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/saveusers")
    public Result saveusers(@RequestBody List<FrameRolemember> frameRolemembers) {
        super.getUserAccount();
        String message = "数据保存成功。";
        String roleid = null;
        // 查询全部原始数据
        List<String> userids = new ArrayList<String>();
        for (FrameRolemember frameRolemember : frameRolemembers) {
            roleid = frameRolemember.getRoleId();
            userids.add(frameRolemember.getUserId());
        }
        Condition condition = new Condition(FrameRolemember.class);
        condition.createCriteria().andEqualTo("roleId", roleid).andIn("userId", userids);
        List<FrameRolemember> list_old = frameRolememberService.findByCondition(condition);

        // 逐一更新原始数据
        Iterator<FrameRolemember> iterator = frameRolemembers.iterator();
        while (iterator.hasNext()) {
            FrameRolemember frameRolemember = iterator.next();
            if( list_old.stream().anyMatch(u -> frameRolemember.getUserId().equals(u.getUserId()))){
                FrameRolemember old = list_old.stream()
                        .filter(u -> frameRolemember.getUserId().equals(u.getUserId())).findFirst().get();
                old.setModifer(_userid);
                old.setModifytime(new Date());
                old.setInuse(frameRolemember.getInuse());
                frameRolememberService.update(old);
                iterator.remove();
            }
        }
        // 没有的数据追加
        for (FrameRolemember frameRolemember : frameRolemembers) {
            frameRolemember.setModifer(_userid);
            frameRolemember.setModifytime(new Date());
            frameRolemember.setCreator(_userid);
            frameRolemember.setCreatetime(new Date());
            frameRolememberService.save(frameRolemember);
        }
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/save")
    public Result save(@RequestBody() FrameRolemember frameRolemember, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        super.getUserAccount();
        String message = "数据保存成功。";
        frameRolemember.setModifer(_userid);
        frameRolemember.setModifytime(new Date());
        if (StringUtils.isEmpty(frameRolemember.getId())) {
            frameRolemember.setCreator(_userid);
            frameRolemember.setCreatetime(new Date());
            frameRolememberService.save(frameRolemember);
            message = "新增数据成功。";
        } else {
            frameRolememberService.update(frameRolemember);
            message = "数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody FrameRolemember frameRolemember) {
        frameRolemember = frameRolememberService.findById(frameRolemember.getId());
        return ResultGenerator.genSuccessResult(frameRolemember);
    }

    @PostMapping("/list")
    public Result list(@RequestBody() @Valid PageInfo<FrameRolemember> pageInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(), true);
        Condition condition = new Condition(FrameRolemember.class);
        Example.Criteria criteria =  condition.createCriteria();
        // 根据页面的查询条件编辑查询条件
        String roleId = super.formatString(super.getItem(pageInfo, "roleId"));
        if(!Strings.isNullOrEmpty(roleId)){
            criteria.andEqualTo("roleId", roleId);
        }
        String userId = super.formatString(super.getItem(pageInfo, "userId"));
        if(!Strings.isNullOrEmpty(userId)){
            criteria.andEqualTo("userId", userId);
        }

        String inuse = super.formatString(super.getItem(pageInfo, "inuse"));
        if(!Strings.isNullOrEmpty(inuse)){
            criteria.andEqualTo("inuse", inuse);
        }
        List<FrameRolemember> list = frameRolememberService.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
