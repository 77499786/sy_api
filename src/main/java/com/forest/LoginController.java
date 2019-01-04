package com.forest;

import com.forest.core.BaseController;
import com.forest.core.ProjectConstant;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.project.model.*;
import com.forest.project.model.*;
import com.forest.project.service.*;
import com.forest.project.service.*;
import com.forest.utils.ValidateCodeUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by CodeGenerator on 2018/04/11.
 */
@RestController
@RequestMapping("/login")
public class LoginController extends BaseController {
    @Resource
    private FrameEmployeeService frameEmployeeService;
    @Resource
    private FrameRoleService frameRoleService;
    @Resource
    private FrameResourceService frameResourceService;
    @Resource
    private FrameRolememberService frameRolememberService;
    @Resource
    private FrameRoleresourceService frameRoleresourceService;

//    private String _userid = "admin";

    @PostMapping("/listtree")
    public Result listTreeData(HttpServletRequest request) {
        PageInfo<TreeData> resultpage = new PageInfo<TreeData>();
        super.getUserAccount();
        // 读取当前用户
        FrameEmployee employee = frameEmployeeService.findBy("userid", _userid);
        if (employee == null){
            return ResultGenerator.genSuccessResult(resultpage);
        }
        _userid = employee.getId();

        // 读取用户角色
        Condition condition = new Condition(FrameRolemember.class);
        condition.createCriteria().andEqualTo("userId", _userid).andEqualTo("inuse", "1");
        List<FrameRolemember> roles = frameRolememberService.findByCondition(condition);
        List<String> roleids = new ArrayList<>();
        roles.forEach((f) -> roleids.add(f.getRoleId()));
        for (FrameRolemember r: roles) {
            List<FrameRole> subroles = frameRoleService.getSubRoles(r.getRoleId());
            for (FrameRole role : subroles){
                roleids.add(role.getId());
            }
        }
        // 读取角色菜单
        Condition condition2 = new Condition(FrameRoleresource.class);
        Example.Criteria criteria = condition2.createCriteria();
        criteria.andEqualTo("inuse", ProjectConstant.USE_STATUS.INUSE.getIndex());
        if(roleids.size() > 0){
            criteria.andIn("roleId", roleids);
        } else {
            resultpage.setList(new ArrayList<TreeData>());
            return ResultGenerator.genSuccessResult(resultpage);
        }
        List<FrameRoleresource> rolemenus = frameRoleresourceService.findByCondition(condition2);
        Map<String,String> menus = new HashMap<String, String>();
        rolemenus.forEach((f) -> menus.put(f.getResourceId(), f.getResourceId()));
        // 读取全部菜单
        Condition condition3 = new Condition(FrameResource.class);
        condition3.createCriteria().andEqualTo("inuse", ProjectConstant.USE_STATUS.INUSE.getIndex())
                .andEqualTo("def","menu");
        List<FrameResource> list = frameResourceService.findByCondition(condition3);

        List<TreeData> treeDatas = new ArrayList<TreeData>();
        List<FrameResource> topList = list.stream().filter(r -> r.getResourceId() == null)
                .collect(Collectors.toList());
        Collections.sort(topList, Comparator.comparing(FrameResource::getSortno));
        for (FrameResource menu : topList) {
            TreeData treeData = new TreeData();
            treeData.setId(menu.getId());
            treeData.setCode(menu.getCode());
            treeData.setName(menu.getName());
            treeData.setIcon(menu.getIcon());
            treeData.setParent(menu.getResourceId());
            treeData.setSortno(menu.getSortno());
            treeData.setTag(menu.getUrl());
            treeData.setChildren(getchildrens(list, menu.getId(), menus));
            treeData.setLeaf(treeData.getChildren().size() <= 0);
            if(!treeData.isLeaf() || menus.containsKey(treeData.getId())){
                treeDatas.add(treeData);
            }
        }
        // 去除无效菜单
        resultpage.setList(treeDatas);
        return ResultGenerator.genSuccessResult(resultpage);
    }

    @RequestMapping("/refresh")
    public  Result getValidate(){
        ValidateCodeUtil.Validate v = ValidateCodeUtil.getRandomCode();
        return  ResultGenerator.genSuccessResult(v);
    }
    private List<TreeData> getchildrens(List<FrameResource> datas, String pid, Map<String,String> inuse_menus) {
        List<TreeData> childs = new ArrayList<>();
        List<FrameResource> submenus = datas.stream().filter(r -> pid.equals(r.getResourceId()))
                .collect(Collectors.toList());
        for (FrameResource entity : submenus) {
//            if ((pid == null && entity.getResourceId() == null) || pid.equals(entity.getResourceId())) {
                TreeData treeData = new TreeData();
                treeData.setId(entity.getId());
                treeData.setCode(entity.getCode());
                treeData.setName(entity.getName());
                treeData.setIcon(entity.getIcon());
                treeData.setTag(entity.getUrl());
                treeData.setParent(entity.getResourceId());
                List<TreeData> chiledren = getchildrens(datas, treeData.getId(), inuse_menus);
                treeData.setChildren(chiledren);
                treeData.setLeaf(treeData.getChildren().size() <= 0);
                treeData.setSortno(entity.getSortno());
                if(!treeData.isLeaf() || inuse_menus.containsKey(treeData.getId())){
                    childs.add(treeData);
                }
//            }
        }
        Collections.sort(childs, Comparator.comparing(TreeData::getSortno));
        return childs;
    }

}
