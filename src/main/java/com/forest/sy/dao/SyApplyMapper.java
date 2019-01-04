package com.forest.sy.dao;

import com.forest.core.Mapper;
import com.forest.sy.model.SyApply;

public interface SyApplyMapper extends Mapper<SyApply> {

  void updateFlowStatus(String sqph);
}