package com.forest.project.service;

import com.forest.project.model.FrameRole;
import com.forest.core.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/04/11.
 */
public interface FrameRoleService extends Service<FrameRole> {
  /**
   * 根据当前角色查出所有的子孙角色（含自己)
   *
   * @param frameRole
   * @return
   */
  List<FrameRole> getSubRoles(FrameRole frameRole);

  /**
   * 根据当前角色ID查出所有的子孙角色（含自己)
   *
   * @param roleId
   * @return
   */
  List<FrameRole> getSubRoles(String roleId);

  /**
   * 查询用户信息数据总数
   * @param param
   * @return
   */
  long queryMemberDataCnt(Map<String, Object> param);

  /**
   * 查询成员信息
   * @param param
   * @return
   */
  List<Map<String, Object>> queryMemberDatas(Map<String, Object> param);
}
