package com.forest.project.web;
import com.forest.core.BaseController;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.project.model.FrameDictionary;
import com.forest.project.model.FrameResource;
import com.forest.project.model.TreeData;
import com.forest.project.service.FrameDictionaryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
* Created by CodeGenerator on 2018/04/11.
*/
@RestController
@RequestMapping("/frame/dictionary")
public class FrameDictionaryController extends BaseController {
    @Resource
    private FrameDictionaryService frameDictionaryService;
//    private String _userid = "admin";

    @PostMapping("/delete")
    public Result delete(@RequestBody()  FrameDictionary frameDictionary) {
        frameDictionaryService.deleteById(frameDictionary.getId());
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/save")
    public Result save( @RequestBody() FrameDictionary frameDictionary,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        super.getUserAccount();
        String message ="数据保存成功。";
        frameDictionary.setModifer(_userid);
        frameDictionary.setModifytime(new Date());
        if(StringUtils.isEmpty(frameDictionary.getDictionaryId())){
            frameDictionary.setDictionaryId(null);
        }
        if(StringUtils.isEmpty(frameDictionary.getId())){
            frameDictionary.setCreator(_userid);
            frameDictionary.setCreatetime(new Date());
            frameDictionaryService.save(frameDictionary);
            message ="新增数据成功。";
        } else {
            frameDictionaryService.update(frameDictionary);
            message ="数据更新成功。";
        }
        return ResultGenerator.genSuccessResult(message);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody  FrameDictionary frameDictionary) {
        frameDictionary = frameDictionaryService.findById(frameDictionary.getId());
        return ResultGenerator.genSuccessResult(frameDictionary);
    }

    @PostMapping("/listdic")
    public Result listdic(@RequestBody() @Valid PageInfo<FrameDictionary> pageInfo) {
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(),true);
        Condition condition = new Condition(FrameDictionary.class);
        Example.Criteria criteria = condition.createCriteria();
        String def = (String)super.getItem(pageInfo, "def");
        if(!Strings.isNullOrEmpty(def)){
            criteria.andIn("def", Arrays.asList(def.split(",")));
        }
        String pid = (String)super.getItem(pageInfo, "dictionaryId");
        if(!Strings.isNullOrEmpty(pid)){
            criteria.andEqualTo("dictionaryId", pid);
        } else {
            criteria.andIsNull("dictionaryId");
        }
        String name = (String) super.getItem(pageInfo, "name");
        if(!Strings.isNullOrEmpty(name)){
            criteria.andLike("name", String.format("%%%s%%", name));
        }
        condition.orderBy("code").asc();
        // 根据页面的查询条件编辑查询条件
        List<FrameDictionary> list = frameDictionaryService.findByCondition(condition);

        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/list")
    public Result list( @RequestBody() @Valid PageInfo<FrameDictionary> pageInfo,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Condition condition = new Condition(FrameDictionary.class);
        Example.Criteria criteria = condition.createCriteria();
        // 根据页面的查询条件编辑查询条件
        List<FrameDictionary> list = frameDictionaryService.findByCondition(condition);
        List<TreeData> treeDatas = new ArrayList<TreeData>();
        Map<String, List<FrameDictionary>> map = list.stream().collect(Collectors.groupingBy(FrameDictionary::getDef));
        for (Map.Entry<String, List<FrameDictionary>> entry: map.entrySet()) {
            TreeData treeData = new TreeData();
            treeData.setId(entry.getKey());
            treeData.setLeaf(false);
            treeData.setCode(entry.getKey());
            treeData.setName(entry.getValue().get(0).getRemark());
            treeData.setSortno(0);
            System.out.println(entry.getValue().size());
            treeData.setChildren(getchildrens(entry.getValue(), null));
            treeDatas.add(treeData);
        }
        PageInfo<TreeData>  pageData = new PageInfo<TreeData>();
        pageData.setList(treeDatas);
        return ResultGenerator.genSuccessResult(pageData);
    }

    @PostMapping("/listtree")
    public Result listTreeData(@RequestBody() @Valid PageInfo<TreeData> pageInfo, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        Condition condition = new Condition(FrameDictionary.class);
        // 根据页面的查询条件编辑查询条件
        if(pageInfo.getList() != null && pageInfo.getList().size()> 0){
            TreeData dic = pageInfo.getList().get(0);
            if(StringUtils.isNotEmpty(dic.getId())){
                condition.createCriteria().andEqualTo("dictionaryId",dic.getId()).orEqualTo("def", dic.getId());
                condition.orderBy("sortno");
            } else {
                condition.createCriteria().andEqualTo("dictionaryId",dic.getId()).orEqualTo("def", dic.getId());
                condition.orderBy("sortno");
                condition.setDistinct(true);
            }
        }

        List<FrameDictionary> list = frameDictionaryService.findByCondition(condition);
        List<TreeData> treeDatas = new ArrayList<TreeData>();
        list.forEach(d -> {
            if (Strings.isNullOrEmpty(d.getDictionaryId())){
                TreeData treeData = new TreeData();
                treeData.setId(d.getId());
                treeData.setCode(d.getCode());
                treeData.setName(d.getName());
                treeData.setParent(d.getDictionaryId());

                List<TreeData> childs =  getchildrens(list, d.getId());
                if(childs != null && childs.size()> 0){
                    treeData.setLeaf(false);
                    treeData.setChildren(childs);
                } else {
                    treeData.setLeaf(true);
                }
                treeDatas.add(treeData);
            }
        });

//        for (FrameDictionary menu : list) {
//            TreeData treeData = new TreeData();
//            treeData.setId(menu.getId());
//            treeData.setCode(menu.getCode());
//            treeData.setName(menu.getName());
//            treeData.setParent(menu.getDictionaryId());
//
//            Condition condition1 = new Condition(FrameDictionary.class);
//            condition1.createCriteria().andEqualTo("dictionaryId", menu.getId());
//            List<FrameDictionary> childs =  frameDictionaryService.findByCondition(condition1);
//            if(childs != null && childs.size()> 0){
//                treeData.setLeaf(false);
//            } else {
//                treeData.setLeaf(true);
//            }
//            treeDatas.add(treeData);
//        }
        pageInfo.setList(treeDatas);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    private List<TreeData> getchildrens(List<FrameDictionary> dics, String pid){
        List<TreeData> childs = new ArrayList<>();
        for (FrameDictionary dic : dics) {
            if((pid == null && dic.getDictionaryId() == null) ||  (pid != null && pid.equals(dic.getDictionaryId()))) {
                TreeData treeData = new TreeData();
                treeData.setId(dic.getId());
                treeData.setCode(dic.getCode());
                treeData.setName(dic.getName());
                treeData.setParent(dic.getDictionaryId());
                treeData.setChildren(getchildrens(dics, treeData.getId()));
                treeData.setSortno(dic.getSortno());
//                int childcount = dics.stream().filter(d-> d.getDictionaryId()== dic.getId()).collect(Collectors.toList()).size();
                treeData.setLeaf(treeData.getChildren().size() <= 0 );
                childs.add(treeData);
            }
        }
        Collections.sort(childs, Comparator.comparing(TreeData::getSortno));
//         childs.sort((TreeData t1, TreeData t2) -> t1.getSortno().compareTo(t2.getSortno()));
        return childs;
    }
}
