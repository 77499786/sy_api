package com.forest.project.dao;

import com.forest.core.Mapper;
import com.forest.project.model.FrameRole;

import java.util.List;
import java.util.Map;

public interface FrameRoleMapper extends Mapper<FrameRole> {
  List<Map<String, Object>> queryMemberDatas(Map<String, Object> param);

  long queryMemberDataCnt(Map<String, Object> param);
}