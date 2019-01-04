package com.forest.sy.service.impl;

import com.forest.crm.dao.CrmMapper;
import com.forest.crm.service.CrmService;
import com.forest.sy.dao.SyMapper;
import com.forest.sy.service.SyService;
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
public class SyServiceImpl implements SyService {

    // 需要手工追加查询逻辑才添加
    @Autowired
    protected SyMapper syMapper;

    @Override
    public List<Map<String, Object>> callsyDatas(Map<String, Object> paramsMap) {
        return syMapper.callsyDatas(paramsMap);
    }

    @Override
    public List<Map<String, Object>> querySyDatas(Map<String, Object> paramsMap) {
        return syMapper.querySyDatas(paramsMap);
    }

    @Override
    public Long querySyDataCnt(Map<String, Object> paramsMap) {
        return syMapper.querySyDataCnt(paramsMap);
    }

    @Override
    public List<String> getSySingleItem(String col, String val){
        return syMapper.getSySingleItem(col, val);
    }
}
