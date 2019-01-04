package com.forest.sy.service.impl;

import com.forest.sy.dao.SyResultMapper;
import com.forest.sy.model.SyResult;
import com.forest.sy.service.SyResultService;
import com.forest.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by CodeGenerator on 2018/09/10.
 */
@Service
@Transactional
public class SyResultServiceImpl extends AbstractService<SyResult> implements SyResultService {
    // 需要手工追加查询逻辑才添加
    //Resource
    //private SyResultMapper syResultMapper;

}
