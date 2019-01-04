package com.forest.project.service.impl;

import com.forest.project.dao.FrameRoleMapper;
import com.forest.project.model.FrameRole;
import com.forest.project.service.FrameRoleService;
import com.forest.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by CodeGenerator on 2018/04/11.
 */
@Service
@Transactional
public class FrameRoleServiceImpl extends AbstractService<FrameRole> implements FrameRoleService {
    // 需要手工追加查询逻辑才添加
    @Resource
    private FrameRoleMapper frameRoleMapper;

    @Override
    public List<FrameRole> getSubRoles(FrameRole frameRole) {
        return getSubRoles(frameRole.getId());
//        List<FrameRole> roles = new ArrayList<FrameRole>();
//        List<FrameRole> list = frameRoleMapper.selectAll();
//        roles.addAll(list.stream().filter(r->r.getId().equals(frameRole.getId())).collect(Collectors.toList()));
//        roles.addAll(getSubRoles(frameRole.getId(), list));
//        return roles;
    }

    @Override
    public List<FrameRole> getSubRoles(String roleId) {
        List<FrameRole> roles = new ArrayList<FrameRole>();
        List<FrameRole> list = frameRoleMapper.selectAll();
        roles.addAll(list.stream().filter(r->r.getId().equals(roleId)).collect(Collectors.toList()));
        roles.addAll(getSubRoles(roleId, list));
        return roles;
    }

    @Override
    public long queryMemberDataCnt(Map<String, Object> param) {
        return frameRoleMapper.queryMemberDataCnt(param);
    }

    @Override
    public List<Map<String, Object>> queryMemberDatas(Map<String, Object> param) {
        return frameRoleMapper.queryMemberDatas(param);
    }

    private List<FrameRole> getSubRoles(String pid, List<FrameRole> roles){
        List<FrameRole> list = new ArrayList<FrameRole>();
        for(FrameRole r : roles){
            if (pid.equals(r.getRoleId())){
                list.add(r);
                list.addAll(getSubRoles(r.getId(), roles));
            }
        }
        return list;
    }

}
