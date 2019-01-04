package com.forest.sy.service;
import com.forest.sy.model.SyApply;
import com.forest.core.Service;

/**
 * Created by CodeGenerator on 2018/09/10.
 */
public interface SyApplyService extends Service<SyApply> {
    /**
     * 送检清单
     * @param sqph
     * @return
     */
    String exportReport(String sqph);

    /**
     * 抽检批次清单
     * @param sqph
     * @return
     */
    String exportBill(String sqph);

    void updateFlowStatus(String sqph);
}
