package com.forest.crm.dao;

import java.util.List;
import java.util.Map;

public interface CrmMapper {
    List<Map<String, Object>> callCrmDatas(Map<String, Object> paramsMap);
}