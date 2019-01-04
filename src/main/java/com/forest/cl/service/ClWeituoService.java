package com.forest.cl.service;
import com.forest.cl.model.ClWeituo;
import com.forest.core.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/08/22.
 */
public interface ClWeituoService extends Service<ClWeituo> {

    List<Map<String,Object>> getremaindatas(Map<String, Object> param);

    void initFlowData(String jybh);

    void submitProcess(Map<String, Object> params);

    String exportReport(String bhs);

    List<ClWeituo> getSjdw(String keyword);

    List<ClWeituo> getScdw(String keyword);

    List<ClWeituo> getWtdw(String keyword);

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
