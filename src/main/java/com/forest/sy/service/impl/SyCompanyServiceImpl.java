package com.forest.sy.service.impl;

import com.forest.sy.dao.SyCompanyMapper;
import com.forest.sy.model.SyCompany;
import com.forest.sy.service.SyCompanyService;
import com.forest.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by CodeGenerator on 2018/09/10.
 */
@Service
@Transactional
public class SyCompanyServiceImpl extends AbstractService<SyCompany> implements SyCompanyService {
    // 需要手工追加查询逻辑才添加
    //Resource
    //private SyCompanyMapper syCompanyMapper;

}
