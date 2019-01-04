package com.forest.home.dao;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

public interface HomeMapper {
    List<Map<String, Object>> callHomeDatas(Map<String, Object> paramsMap);
}