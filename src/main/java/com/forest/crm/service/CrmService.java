package com.forest.crm.service;

import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/04/30.
 */
public interface CrmService {

    List<Map<String, Object>> callCrmDatas(Map<String, Object> paramsMap); // 调用存储过程
}
