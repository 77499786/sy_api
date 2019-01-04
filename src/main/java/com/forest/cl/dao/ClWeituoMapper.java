package com.forest.cl.dao;

import com.forest.cl.model.ClWeituo;
import com.forest.core.Mapper;

import java.util.List;
import java.util.Map;

public interface ClWeituoMapper extends Mapper<ClWeituo> {
    List<Map<String, Object>> callRemainDatas(Map<String, Object> paramsMap);
    List<ClWeituo> getSjdwDatas(String keyword);
    List<ClWeituo> getScdwDatas(String keyword);
    List<ClWeituo> getWtdwDatas(String keyword);
    List<String> getSingleItem(String col, String val);

    /**
     * 查询残留综合信息
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> queryRemainInfors(Map<String, Object> paramsMap);

    /**
     * 查询残留综合信息数据量
     * @param paramsMap
     * @return
     */
    Long queryRemainCounts(Map<String, Object> paramsMap);

    List<Map<String, Object>> queryList(Map<String, Object> paramsMap);
}