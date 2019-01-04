package com.forest.sy.service.impl;

import com.forest.sy.dao.SyJymdMapper;
import com.forest.sy.model.SyJymd;
import com.forest.sy.service.SyJymdService;
import com.forest.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by CodeGenerator on 2018/08/21.
 */
@Service
@Transactional
public class SyJymdServiceImpl extends AbstractService<SyJymd> implements SyJymdService {
    // 需要手工追加查询逻辑才添加
    //Resource
    //private SyJymdMapper syJymdMapper;

}
