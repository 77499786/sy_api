package com.forest.project.service.impl;

import com.forest.core.AbstractService;
import com.forest.project.model.TSBaseUser;
import com.forest.project.service.TSBaseUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by CodeGenerator on 2018/04/09.
 */
@Service
@Transactional
public class TSBaseUserServiceImpl extends AbstractService<TSBaseUser> implements TSBaseUserService {
    // 需要手工追加查询逻辑才添加
    //Resource
    //private TSBaseUserMapper tSBaseUserMapper;

}
