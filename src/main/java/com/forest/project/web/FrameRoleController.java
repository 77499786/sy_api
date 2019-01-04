package com.forest.project.web;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.project.model.BaseQuery;
import com.forest.project.model.FrameRole;
import com.forest.project.model.FrameRolemember;
import com.forest.project.model.TreeData;
import com.forest.project.service.FrameRoleService;
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

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
* Created by CodeGenerator on 2018/04/11.
*/
@RestController
@RequestMapping("/frame/role")
public class FrameRoleController extends BaseController {
    @Resource
    private FrameRoleService frameRoleService;
//    private String _userid = "admin";

    @PostMapping("/delete")
    public Result delete(@RequestBody()  FrameRole frameRole) {
        frameRoleService.deleteById(frameRole.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/save")
    public Result save( @RequestBody() FrameRole frameRole,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        super.getUserAccount();
        String message ="数据保存成功。";
        frameRole.setModifer(_userid);
        frameRole.setModifytime(new Date());
        if(StringUtils.isEmpty(frameRole.getRoleId())){
            frameRole.setRoleId(null);
        }
        if(StringUtils.isEmpty(frameRole.getId())){
            frameRole.setCreator(_userid);
            frameRole.setCreatetime(new Date());
            frameRoleService.save(frameRole);
            message ="新增数据成功。";
        } else {
            frameRoleService.update(frameRole);
            message ="数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(frameRole);
    }


    @PostMapping("/detail")
    public Result detail(@RequestBody  FrameRole frameRole) {
        frameRole = frameRoleService.findById(frameRole.getId());
        if(frameRole.getRoleId()!= null){
            frameRole.setSupername(frameRoleService.findById(frameRole.getRoleId()).getName());
        }
        return ResultGenerator.genSuccessResult(frameRole);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<FrameRole> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(FrameRole.class);
        // 根据页面的查询条件编辑查询条件
        if(pageInfo.getList() != null && StringUtils.isNotEmpty(pageInfo.getList().get(0).getName())){
            condition.createCriteria().andLike("name","%"+pageInfo.getList().get(0).getName()+"%");
        }
        condition.orderBy("code");

        List<FrameRole> list = frameRoleService.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/listtree")
    public Result listTreeData(@RequestBody() @Valid PageInfo<FrameRole> pageInfo, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(FrameRole.class);
        // 根据页面的查询条件编辑查询条件
        if(pageInfo.getList() != null && pageInfo.getList().size()> 0){
            FrameRole res = pageInfo.getList().get(0);
            if(StringUtils.isNotEmpty(res.getId())){
                condition.createCriteria().andEqualTo("roleId",res.getId());
            }
            condition.orderBy("code");
        }

        List<FrameRole> list = frameRoleService.findByCondition(condition);
        List<TreeData> treeDatas = new ArrayList<TreeData>();
        List<FrameRole> topList =  list.stream().filter(r->r.getRoleId()== null).collect(Collectors.toList());
        for (FrameRole menu : topList) {
            TreeData treeData = new TreeData();
            treeData.setId(menu.getId());
            treeData.setCode(menu.getCode());
            treeData.setName(menu.getName());
            treeData.setParent(menu.getRoleId());
            treeData.setChildren(getchildrens(list, menu.getId()));
            treeData.setLeaf(treeData.getChildren().size() <= 0);
            treeDatas.add(treeData);
        }
        PageInfo<TreeData> resultpage = new PageInfo<TreeData>();
        resultpage.setList(treeDatas);
        return ResultGenerator.genSuccessResult(resultpage);
    }

    @PostMapping("/listmembers")
    public Result listmembers(@RequestBody() @Valid PageInfo<BaseQuery> pageInfo, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Map<String, Object> param = new HashMap<String, Object>();
        String keystr = "keyword";
        String keyword = (String) super.getItem(pageInfo, keystr);
        if (!Strings.isNullOrEmpty(keyword)) {
            param.put(keystr, keyword);
        }
        keystr = "roleid";
        String roleid = (String) super.getItem(pageInfo, keystr);
        if (!Strings.isNullOrEmpty(roleid)) {
            param.put(keystr, roleid);
        }
        long cnt = frameRoleService.queryMemberDataCnt(param);
        param.put("startindex",(pageInfo.getPageNum()-1)* pageInfo.getPageSize());
        param.put("stopindex",pageInfo.getPageSize());
//        param.put("orderby", "w.jybh desc");
        List<Map<String, Object>> list = frameRoleService.queryMemberDatas(param);
        PageInfo<Map<String, Object>> ret = new PageInfo<>();
        ret.setList(list);
        ret.setTotal(cnt);
        return ResultGenerator.genSuccessResult(ret);
    }


    private List<TreeData> getchildrens(List<FrameRole> datas, String pid){
        List<TreeData> childs = new ArrayList<>();
        for (FrameRole entity : datas) {
            if((pid == null && entity.getRoleId() == null) ||   pid.equals(entity.getRoleId())) {
                TreeData treeData = new TreeData();
                treeData.setId(entity.getId());
                treeData.setCode(entity.getCode());
                treeData.setName(entity.getName());
                treeData.setParent(entity.getRoleId());
                List<TreeData> chiledren = getchildrens(datas, treeData.getId());
                treeData.setChildren(chiledren);
                treeData.setLeaf(treeData.getChildren().size() <= 0 );
                childs.add(treeData);
            }
        }
        Collections.sort(childs, Comparator.comparing(TreeData::getCode));
        return childs;
    }

}
