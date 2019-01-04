package com.forest.project.web;

import com.forest.core.ProjectConstant;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.core.BaseController;
import com.forest.project.model.FrameResource;
import com.forest.project.model.TreeData;
import com.forest.project.service.FrameResourceService;
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
 * Created by CodeGenerator on 2018/04/11.
 */
@RestController
@RequestMapping("/frame/resource")
public class FrameResourceController extends BaseController {
    @Resource
    private FrameResourceService frameResourceService;
//    private String _userid = "admin";

    @PostMapping("/delete")
    public Result delete(@RequestBody() FrameResource frameResource) {
        Condition condition = new Condition(FrameResource.class);
        condition.createCriteria().andEqualTo("def", "button").andEqualTo("resourceId", frameResource.getId());
        frameResourceService.deleteByCondition(condition);
        frameResourceService.deleteById(frameResource.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/save")
    public Result save(@RequestBody() FrameResource frameResource, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        super.getUserAccount();
        String message = "数据保存成功。";
        frameResource.setModifer(_userid);
        frameResource.setModifytime(new Date());
        if (StringUtils.isEmpty(frameResource.getResourceId())) {
            frameResource.setResourceId(null);
        }
        if (StringUtils.isEmpty(frameResource.getId())) {
            frameResource.setCreator(_userid);
            frameResource.setCreatetime(new Date());
            frameResourceService.save(frameResource);
            message = "新增数据成功。";
        } else {
            frameResourceService.update(frameResource);
            message = "数据更新成功。";
        }
        // 读取原始功能设置
        Condition condition = new Condition(FrameResource.class);
        condition.createCriteria().andEqualTo("resourceId", frameResource.getId())
                .andEqualTo("def", "button");
        List<FrameResource> oldlist = frameResourceService.findByCondition(condition);
        for (FrameResource r : oldlist) {
            if (frameResource.getFunctions() != null && frameResource.getFunctions().size() > 0) {
                List<FrameResource> filter = frameResource.getFunctions().stream().filter(
                        f -> f.getCode().equals(r.getCode()) ).collect(Collectors.toList());
                if (filter.size() > 0) {
                    FrameResource fr = filter.get(0);
                    if (!ProjectConstant.USE_STATUS.INUSE.getIndex().equals(r.getInuse())
                            || !fr.getSortno().equals(r.getSortno()) || !fr.getName().equals(r.getName())) {
                        r.setInuse(ProjectConstant.USE_STATUS.INUSE.getIndex());
                        r.setName(fr.getName());
                        r.setSortno(fr.getSortno());
                        r.setModifer(_userid);
                        r.setModifytime(new Date());
                        frameResourceService.update(r);
                    }
                    frameResource.getFunctions().remove(filter.get(0));
                    continue;
                }
            }
            r.setInuse(ProjectConstant.USE_STATUS.STOPPED.getIndex());
            r.setModifer(_userid);
            r.setModifytime(new Date());
            frameResourceService.update(r);
        }
        // 新增功能数据
        if (frameResource.getFunctions() != null && frameResource.getFunctions().size() > 0) {
            for (FrameResource r : frameResource.getFunctions()) {
                r.setDef("button");
                r.setInuse(ProjectConstant.USE_STATUS.INUSE.getIndex());
                r.setResourceId(frameResource.getId());
                r.setCreator(_userid);
                r.setCreatetime(new Date());
                r.setModifer(_userid);
                r.setModifytime(new Date());
                frameResourceService.save(r);
            }
        }
/*
        // 删除原始功能数据
        Condition condition = new Condition(FrameResource.class);
        condition.createCriteria().andEqualTo("resourceId", frameResource.getId())
                .andEqualTo("def","button");
        frameResourceService.deleteByCondition(condition);
        if(frameResource.getFunctions() != null) {
            // 保存新的功能数据
            for (FrameResource func : frameResource.getFunctions()) {
                func.setDef("button");
                func.setInuse("1");
                func.setCreator(_userid);
                func.setResourceId(frameResource.getId());
                func.setCreatetime(new Date());
                func.setModifer(_userid);
                func.setModifytime(new Date());
                frameResourceService.save(func);
            }
        }
*/
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody FrameResource frameResource) {
        frameResource = frameResourceService.findById(frameResource.getId());
        if (frameResource.getResourceId() != null) {
            frameResource.setSupername(frameResourceService.findById(frameResource.getResourceId()).getName());
        }
        // 读取功能列表数据
        Condition condition = new Condition(FrameResource.class);
        condition.createCriteria().andEqualTo("def", "button").andEqualTo("resourceId", frameResource.getId()).andEqualTo("inuse", ProjectConstant.USE_STATUS.INUSE.getIndex());
        condition.orderBy("sortno");
        frameResource.setFunctions(frameResourceService.findByCondition(condition));

        return ResultGenerator.genSuccessResult(frameResource);
    }

    @PostMapping("/list")
    public Result list(@RequestBody() @Valid PageInfo<FrameResource> pageInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(), true);
        Condition condition = new Condition(FrameResource.class);
        // 根据页面的查询条件编辑查询条件
        if (pageInfo.getList() != null && pageInfo.getList().size() > 0) {
            FrameResource res = pageInfo.getList().get(0);
            if (StringUtils.isNotEmpty(res.getName())) {
                condition.createCriteria().andLike("name", "%" + res.getName() + "%");
            }
            if (StringUtils.isNotEmpty(res.getResourceId())) {
                condition.createCriteria().andLike("resourceId", res.getResourceId());
            }
            if (StringUtils.isNotEmpty(res.getDef())) {
                condition.createCriteria().andEqualTo("def", res.getDef());
                condition.orderBy("sortno");
            }
        }

        List<FrameResource> list = frameResourceService.findByCondition(condition);
        for (FrameResource menu : list) {
            Condition condition1 = new Condition(FrameResource.class);
            condition1.createCriteria().andEqualTo("def", "button").andEqualTo("resourceId", menu.getId());
            menu.setFunctions(frameResourceService.findByCondition(condition1));
            FrameResource sp = frameResourceService.findById(menu.getResourceId());
            if (sp != null) {
                menu.setSupername(sp.getName());
            }
        }
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/listtree")
    public Result listTreeData(@RequestBody() @Valid PageInfo<FrameResource> pageInfo,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(), true);
        Condition condition = new Condition(FrameResource.class);
        // 根据页面的查询条件编辑查询条件
        if (pageInfo.getList() != null && pageInfo.getList().size() > 0) {
            FrameResource res = pageInfo.getList().get(0);
            if (StringUtils.isNotEmpty(res.getId())) {
                condition.createCriteria().andEqualTo("resourceId", res.getId());
            }
            if (StringUtils.isNotEmpty(res.getName())) {
                condition.createCriteria().andLike("name", "%" + res.getName() + "%");
            }
            if (StringUtils.isNotEmpty(res.getDef())) {
                condition.createCriteria().andEqualTo("def", res.getDef());
            }
            condition.orderBy("sortno");
        }

        List<FrameResource> list = frameResourceService.findByCondition(condition);
        List<TreeData> treeDatas = new ArrayList<TreeData>();
        List<FrameResource> topList = list.stream().filter(r -> r.getResourceId() == null).collect(Collectors.toList());
        for (FrameResource menu : topList) {
            TreeData treeData = new TreeData();
            treeData.setId(menu.getId());
            treeData.setCode(menu.getCode());
            treeData.setName(menu.getName());
            treeData.setIcon(menu.getIcon());
            treeData.setParent(menu.getResourceId());
            treeData.setSortno(menu.getSortno());
            treeData.setChildren(getchildrens(list, menu.getId()));
            treeData.setLeaf(treeData.getChildren().size() <= 0);
            treeDatas.add(treeData);
        }
        PageInfo<TreeData> resultpage = new PageInfo<TreeData>();
        resultpage.setList(treeDatas);
        return ResultGenerator.genSuccessResult(resultpage);
    }

    private List<TreeData> getchildrens(List<FrameResource> datas, String pid) {
        List<TreeData> childs = new ArrayList<>();
        for (FrameResource entity : datas) {
            if ((pid == null && entity.getResourceId() == null) || pid.equals(entity.getResourceId())) {
                TreeData treeData = new TreeData();
                treeData.setId(entity.getId());
                treeData.setCode(entity.getCode());
                treeData.setName(entity.getName());
                treeData.setIcon(entity.getIcon());
                treeData.setParent(entity.getResourceId());
                List<TreeData> chiledren = getchildrens(datas, treeData.getId());
                treeData.setChildren(chiledren);
                treeData.setLeaf(treeData.getChildren().size() <= 0);
                treeData.setSortno(entity.getSortno());
                childs.add(treeData);
            }
        }
        Collections.sort(childs, Comparator.comparing(TreeData::getSortno));
        return childs;
    }

}
