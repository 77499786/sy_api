package com.forest.sy.dao;

import java.util.List;
import java.util.Map;

public interface SyMapper {
    List<Map<String, Object>> callsyDatas(Map<String, Object> paramsMap);

    List<Map<String, Object>> querySyDatas(Map<String, Object> paramsMap);

    Long querySyDataCnt(Map<String, Object> paramsMap);

    List<String> getSySingleItem(String col, String val);

}
