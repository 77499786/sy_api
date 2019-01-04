package com.forest.cl.service.impl;

import com.forest.cl.dao.ClFlowdefineMapper;
import com.forest.cl.model.ClFlowdefine;
import com.forest.cl.service.ClFlowdefineService;
import com.forest.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by CodeGenerator on 2018/08/22.
 */
@Service
@Transactional
public class ClFlowdefineServiceImpl extends AbstractService<ClFlowdefine> implements ClFlowdefineService {
    // 需要手工追加查询逻辑才添加
    //Resource
    //private ClFlowdefineMapper clFlowdefineMapper;

}
