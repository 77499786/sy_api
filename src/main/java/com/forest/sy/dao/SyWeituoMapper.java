package com.forest.sy.dao;

import com.forest.core.Mapper;
import com.forest.sy.model.SyWeituo;

import java.util.List;
import java.util.Map;

public interface SyWeituoMapper extends Mapper<SyWeituo> {
    List<SyWeituo> getWtdwDatas(String keyword);

    List<SyWeituo> getScdwDatas(String keyword);

    List<SyWeituo> gethistoryDatas(Map<String,Object> params);

    Long gethistoryDataCnt(Map<String,Object> params);

}