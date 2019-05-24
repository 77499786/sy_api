package com.forest.sy.service;
import com.forest.cl.model.ClWeituo;
import com.forest.sy.model.SyWeituo;
import com.forest.core.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/09/10.
 */
public interface SyWeituoService extends Service<SyWeituo> {

    void initFlowData(String jybh, Integer node);

    List<SyWeituo> getScdw(String sjdw);

    List<SyWeituo> getWtdw(String wtdw);

    List<SyWeituo> gethistoryDatas(Map<String,Object> params);

    Long gethistoryDataCnt(Map<String,Object> params);

    void submitProcess(Map<String, Object> params);

    String exportReport(String bhs);

    /**
     * 生成产品确认通知书
     * @param ypbh
     * @return
     */
    String exportRefer(String ypbh);

    /**
     * 生成委托检验合同
     * @param ypbh
     * @return
     */
    String exportContract(String ypbh);

}
