package com.forest.cl.service.impl;

import com.forest.cl.dao.ClResultMapper;
import com.forest.cl.model.ClResult;
import com.forest.cl.service.ClResultService;
import com.forest.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by CodeGenerator on 2018/08/22.
 */
@Service
@Transactional
public class ClResultServiceImpl extends AbstractService<ClResult> implements ClResultService {
    // 需要手工追加查询逻辑才添加
    //Resource
    //private ClResultMapper clResultMapper;

}
