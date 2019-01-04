package com.forest.home.service.impl;

import com.forest.home.dao.HomeMapper;
import com.forest.home.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/04/30.
 */
@Service
@Transactional
public class HomeServiceImpl implements HomeService {

    // 需要手工追加查询逻辑才添加
    @Autowired
    protected HomeMapper homeMapper;

    @Override
    public List<Map<String, Object>> callHomeDatas(Map<String, Object> paramsMap) {
        return homeMapper.callHomeDatas(paramsMap);
    }
}
