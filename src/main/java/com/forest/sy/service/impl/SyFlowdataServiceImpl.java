package com.forest.sy.service.impl;

import com.forest.sy.dao.SyFlowdataMapper;
import com.forest.sy.model.SyFlowdata;
import com.forest.sy.service.SyFlowdataService;
import com.forest.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by CodeGenerator on 2018/09/10.
 */
@Service
@Transactional
public class SyFlowdataServiceImpl extends AbstractService<SyFlowdata> implements SyFlowdataService {
    // 需要手工追加查询逻辑才添加
    //Resource
    //private SyFlowdataMapper syFlowdataMapper;

}
