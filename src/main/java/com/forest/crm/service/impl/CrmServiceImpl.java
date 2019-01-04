package com.forest.crm.service.impl;

import com.forest.crm.service.CrmService;
import com.forest.crm.dao.CrmMapper;
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
public class CrmServiceImpl implements CrmService {

    // 需要手工追加查询逻辑才添加
    @Autowired
    protected CrmMapper crmMapper;

    @Override
    public List<Map<String, Object>> callCrmDatas(Map<String, Object> paramsMap) {
        return crmMapper.callCrmDatas(paramsMap);
    }
}
