package com.forest.project.web;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.project.model.FrameOrganization;
import com.forest.project.model.TreeData;
import com.forest.project.service.FrameOrganizationService;
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
import java.util.*;
import java.util.stream.Collectors;

/**
* Created by CodeGenerator on 2018/04/10.
*/
@RestController
@RequestMapping("/frame/organization")
public class FrameOrganizationController extends BaseController {
    @Resource
    private FrameOrganizationService frameOrganizationService;

//    private String _userid = "admin";
    @PostMapping("/add")
    public Result add(@RequestBody() FrameOrganization frameOrganization, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        super.getUserAccount();
        frameOrganization.setCreator(_userid);
        frameOrganization.setCreatetime(new Date());
        frameOrganization.setModifer(_userid);
        frameOrganization.setModifytime(new Date());
        frameOrganizationService.save(frameOrganization);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody()  FrameOrganization frameOrganization) {
        frameOrganizationService.deleteById(frameOrganization.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update( @RequestBody() @Valid FrameOrganization frameOrganization,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        super.getUserAccount();
        frameOrganization.setModifer(_userid);
        frameOrganization.setModifytime(new Date());
        if(StringUtils.isEmpty(frameOrganization.getOrgId())){
            frameOrganization.setOrgId(null);
        }
        frameOrganizationService.update(frameOrganization);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/save")
    public Result save( @RequestBody() FrameOrganization frameOrganization,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        super.getUserAccount();
        String message ="数据保存成功。";
        frameOrganization.setModifer(_userid);
        frameOrganization.setModifytime(new Date());
        if(StringUtils.isEmpty(frameOrganization.getOrgId())){
            frameOrganization.setOrgId(null);
        }
        if(StringUtils.isEmpty(frameOrganization.getId())){
            frameOrganization.setCreator(_userid);
            frameOrganization.setCreatetime(new Date());
            frameOrganizationService.save(frameOrganization);
            message ="新增机构数据成功。";
        } else {
            frameOrganizationService.update(frameOrganization);
            message ="机构数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(message);
    }
    @PostMapping("/detail")
    public Result detail(@RequestBody  FrameOrganization frameOrganization) {
        frameOrganization = frameOrganizationService.findById(frameOrganization.getId());
        if(frameOrganization.getOrgId()!= null){
            frameOrganization.setSupername(frameOrganizationService.findById(frameOrganization.getOrgId()).getName());
        }
        return ResultGenerator.genSuccessResult(frameOrganization);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<FrameOrganization> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(FrameOrganization.class);
        // 根据页面的查询条件编辑查询条件
        if(pageInfo.getList() != null && StringUtils.isNotEmpty(pageInfo.getList().get(0).getName())){
            condition.createCriteria().andLike("name","%"+pageInfo.getList().get(0).getName()+"%");
        }
        condition.orderBy("code");
        List<FrameOrganization> list = frameOrganizationService.findByCondition(condition);
        List<FrameOrganization> all =  frameOrganizationService.findAll();
        Map<String, String> dic = new HashMap<String, String>();
        for (FrameOrganization e: all){
            dic.put(e.getId(), e.getName());
        }
        for (FrameOrganization e: list){
            e.setParentname(dic.get(e.getOrgId()));
        }
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/listtree")
    public Result listTreeData(@RequestBody() @Valid PageInfo<FrameOrganization> pageInfo, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(FrameOrganization.class);
        // 根据页面的查询条件编辑查询条件
        if(pageInfo.getList() != null && pageInfo.getList().size()> 0){
            FrameOrganization res = pageInfo.getList().get(0);
            if(StringUtils.isNotEmpty(res.getId())){
                condition.createCriteria().andEqualTo("orgId",res.getId());
            }
            if(StringUtils.isNotEmpty(res.getName())){
                condition.createCriteria().andLike("name","%"+res.getName()+"%");
            }
        }

        List<FrameOrganization> list = frameOrganizationService.findByCondition(condition);
        List<TreeData> treeDatas = new ArrayList<TreeData>();
        List<FrameOrganization> topList =  list.stream().filter(r->r.getOrgId()== null).collect(Collectors.toList());
        for (FrameOrganization e : topList) {
            TreeData treeData = new TreeData();
            treeData.setId(e.getId());
            treeData.setCode(e.getCode());
            treeData.setName(e.getName() );
            treeData.setParent(e.getOrgId());
            treeData.setChildren(getchildrens(list, e.getId()));
            treeData.setLeaf(treeData.getChildren().size() <= 0);
            treeDatas.add(treeData);
        }
        PageInfo<TreeData> resultpage = new PageInfo<TreeData>();
        resultpage.setList(treeDatas);
        return ResultGenerator.genSuccessResult(resultpage);
    }

    private List<TreeData> getchildrens(List<FrameOrganization> datas, String pid){
        List<TreeData> childs = new ArrayList<>();
        for (FrameOrganization entity : datas) {
            if((pid == null && entity.getOrgId() == null) ||  pid.equals(entity.getOrgId())) {
                TreeData treeData = new TreeData();
                treeData.setId(entity.getId());
                treeData.setCode(entity.getCode());
                treeData.setName(entity.getName());
                treeData.setParent(entity.getOrgId());
                List<TreeData> chiledren = getchildrens(datas, treeData.getId());
                treeData.setChildren(chiledren);
                treeData.setLeaf(treeData.getChildren().size() <= 0 );
                childs.add(treeData);
            }
        }
        return childs;
    }

}
