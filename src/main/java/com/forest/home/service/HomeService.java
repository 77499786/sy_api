package com.forest.home.service;

import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/04/30.
 */
public interface HomeService {

    List<Map<String, Object>> callHomeDatas(Map<String, Object> paramsMap); // 调用存储过程
}
