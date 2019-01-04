package com.forest.sy.service.impl;

import com.forest.sy.dao.SyApplyWtMapper;
import com.forest.sy.model.SyApplyWt;
import com.forest.sy.service.SyApplyWtService;
import com.forest.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by CodeGenerator on 2018/09/10.
 */
@Service
@Transactional
public class SyApplyWtServiceImpl extends AbstractService<SyApplyWt> implements SyApplyWtService {
    // 需要手工追加查询逻辑才添加
    //Resource
    //private SyApplyWtMapper syApplyWtMapper;

}
