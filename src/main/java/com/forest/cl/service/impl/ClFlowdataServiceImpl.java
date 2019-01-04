package com.forest.cl.service.impl;

import com.forest.cl.dao.ClFlowdataMapper;
import com.forest.cl.model.ClFlowdata;
import com.forest.cl.service.ClFlowdataService;
import com.forest.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by CodeGenerator on 2018/08/22.
 */
@Service
@Transactional
public class ClFlowdataServiceImpl extends AbstractService<ClFlowdata> implements ClFlowdataService {
    // 需要手工追加查询逻辑才添加
    //Resource
    //private ClFlowdataMapper clFlowdataMapper;

}
