package com.forest.project.web;
import com.forest.core.ProjectConstant;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.project.model.FrameRole;
import com.forest.project.model.FrameRoleresource;
import com.forest.project.service.FrameRoleService;
import com.forest.project.service.FrameRoleresourceService;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Date;

/**
* Created by CodeGenerator on 2018/04/11.
*/
@RestController
@RequestMapping("/frame/roleresource")
public class FrameRoleresourceController extends BaseController {
    @Resource
    private FrameRoleresourceService frameRoleresourceService;
    @Resource
    private FrameRoleService frameRoleService;
//    private String _userid = "admin";

    @PostMapping("/delete")
    public Result delete(@RequestBody() FrameRoleresource frameRoleresource) {
        frameRoleresourceService.deleteById(frameRoleresource.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/save")
    public Result save( @RequestBody() FrameRoleresource frameRoleresource,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        super.getUserAccount();
        String message ="数据保存成功。";
        frameRoleresource.setModifer(_userid);
        frameRoleresource.setModifytime(new Date());
        if(StringUtils.isEmpty(frameRoleresource.getId())){
            frameRoleresource.setCreator(_userid);
            frameRoleresource.setCreatetime(new Date());
            frameRoleresourceService.save(frameRoleresource);
            message ="新增数据成功。";
        } else {
            frameRoleresourceService.update(frameRoleresource);
            message ="数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  FrameRoleresource frameRoleresource) {
        frameRoleresource = frameRoleresourceService.findById(frameRoleresource.getId());
        return ResultGenerator.genSuccessResult(frameRoleresource);
    }

    @PostMapping("/savelist")
    public Result savelist( @RequestBody() @Valid PageInfo<FrameRoleresource> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        super.getUserAccount();
        // 更新旧数据全部设置为无效
        List<FrameRoleresource> list = pageInfo.getList();
        String roleid = list.get(0).getRoleId();
        Condition condition = new Condition(FrameRoleresource.class);
        condition.createCriteria().andEqualTo("roleId", roleid);
        FrameRoleresource t = new FrameRoleresource();
        t.setModifytime(new Date());
        t.setModifer(_userid);
        t.setInuse(ProjectConstant.USE_STATUS.STOPPED.getIndex());
        frameRoleresourceService.updateByCondition(t, condition);
        //
        List<FrameRoleresource> list_old = frameRoleresourceService.findByCondition(condition);
        for (FrameRoleresource entity: list_old) {
            Iterator<FrameRoleresource> iter = list.iterator();
            while(iter.hasNext()){
                FrameRoleresource n = iter.next();
                if(entity.getRoleId().equals(n.getRoleId()) && entity.getResourceId().equals(n.getResourceId())){
                    entity.setModifytime(new Date());
                    entity.setModifer(_userid);
                    entity.setInuse(ProjectConstant.USE_STATUS.INUSE.getIndex());
                    iter.remove();
                    frameRoleresourceService.update(entity);
                    break;
                }
            }
        }
        for (FrameRoleresource entity: list) {
            entity.setModifytime(new Date());
            entity.setModifer(_userid);
            entity.setCreatetime(new Date());
            entity.setCreator(_userid);
            entity.setInuse(ProjectConstant.USE_STATUS.INUSE.getIndex());
            frameRoleresourceService.save(entity);
        }
        return ResultGenerator.genSuccessResult("数据保存成功。");
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<FrameRoleresource> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(FrameRoleresource.class);
        // 根据页面的查询条件编辑查询条件
        List<FrameRoleresource> params = pageInfo.getList();
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("inuse", ProjectConstant.USE_STATUS.INUSE.getIndex());
        if(params.size() > 0 && params.get(0) != null  &&  StringUtils.isNotEmpty(params.get(0).getRoleId())){
            FrameRole r = new FrameRole();
            r.setId(params.get(0).getRoleId());
            List<String> roleids = new ArrayList<>();
            List<FrameRole> roles = frameRoleService.getSubRoles(r);
            for (FrameRole role : roles){
                roleids.add(role.getId());
            }
            criteria.andIn("roleId",roleids);
        }
        if(params.size() > 0 && params.get(0) != null  &&  StringUtils.isNotEmpty(params.get(0).getResourceId())){
            criteria.andEqualTo("resourceId",params.get(0).getResourceId());
        }
        List<FrameRoleresource> list = frameRoleresourceService.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
