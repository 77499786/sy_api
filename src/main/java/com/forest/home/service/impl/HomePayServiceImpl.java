package com.forest.home.service.impl;

import com.forest.home.service.HomePayService;
import com.forest.home.model.HomePay;
import com.forest.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by CodeGenerator on 2018/07/10.
 */
@Service
@Transactional
public class HomePayServiceImpl extends AbstractService<HomePay> implements HomePayService {
    // 需要手工追加查询逻辑才添加
    //Resource
    //private HomePayMapper homePayMapper;

}
