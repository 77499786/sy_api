package com.forest.sy.service;

import java.util.List;
import java.util.Map; /**
 * Created by CodeGenerator on 2018/09/10.
 */
public interface SyService {

    /**
     * 存储过程方式查询兽药数据
     * @param param
     * @return
     */
    List<Map<String,Object>> callsyDatas(Map<String, Object> param);

    /**
     * Mybatis SQL查询兽药检验数据
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> querySyDatas(Map<String, Object> paramsMap);

    /**
     * Mybatis SQL查询兽药检验数据总数
     * @param paramsMap
     * @return
     */
    Long querySyDataCnt(Map<String, Object> paramsMap);

    /**
     *
     * @param col
     * @param val
     * @return
     */
    List<String> getSySingleItem(String col, String val);
}
