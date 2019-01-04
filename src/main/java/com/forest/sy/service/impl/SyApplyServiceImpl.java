package com.forest.sy.service.impl;

import com.forest.core.AbstractService;
import com.forest.project.model.FrameEmployee;
import com.forest.project.service.FrameEmployeeService;
import com.forest.sy.dao.SyApplyMapper;
import com.forest.sy.model.SyApply;
import com.forest.sy.model.SyApplyWt;
import com.forest.sy.model.SyWeituo;
import com.forest.sy.service.SyApplyService;
import com.forest.sy.service.SyApplyWtService;
import com.forest.sy.service.SyWeituoService;
import com.forest.utils.AsposeUtil;
import com.forest.utils.ConStant;
import com.forest.utils.ScsyReportJasperUtil;
import com.forest.utils.ScsyResourceUtil;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/09/10.
 */
@Service
@Transactional
public class SyApplyServiceImpl extends AbstractService<SyApply> implements SyApplyService {
    // 需要手工追加查询逻辑才添加
    @Resource
    private SyApplyMapper syApplyMapper;

    @Resource
    private SyApplyWtService syApplyWtService;

    @Resource
    private SyWeituoService syWeituoService;

    @Resource
    private FrameEmployeeService frameEmployeeService;
    @Override
    public String exportReport(String sqph) {
        // jasper报表模式
        if(ConStant.REPORT_TYPE_JASPER.equals(ConStant.REPORT_TYPE)){
            return exportJasperReport(sqph);
        } else {
            return exportWordReport(sqph);
        }
    }

    @Override
    public String exportBill(String sqph) {
        Map<String, Object> mapdatas = getMapDatas(sqph);
        mapdatas.put("抽检批号",sqph);
        return ScsyReportJasperUtil.createChoujianPiciPdf(sqph,mapdatas);
    }
    @Override
    public void updateFlowStatus(String sqph){
        syApplyMapper.updateFlowStatus(sqph);
    }

    private String exportJasperReport(String sqph) {
        Map<String, Object> mapdatas = getMapDatas(sqph);
        mapdatas.put("申请批号",sqph);
        String compid = super.findBy("sqph",sqph).getCompid();
        FrameEmployee frameEmployee = frameEmployeeService.findBy("userid", compid);
        mapdatas.put("生产单位",frameEmployee.getName());
        return ScsyReportJasperUtil.createYangpinQingdanPdf(sqph,mapdatas);
    }

    private String exportWordReport(String sqph) {
        Map<String, Object> mapdata = new HashMap<String, Object>();
        mapdata.put("申请批号",sqph);
        String compid = super.findBy("sqph",sqph).getCompid();
        FrameEmployee frameEmployee = frameEmployeeService.findBy("userid", compid);
        mapdata.put("生产单位",frameEmployee.getName());
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        Map<String, Object> mapdatas = getMapDatas(sqph);
        ((List<Map<String, Object>>)mapdatas.get("清单数据")).forEach((Map<String, Object> d) -> {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("样品编号", d.get("ypbh"));
            m.put("样品名称", d.get("ypmc"));
            m.put("规格", d.get("gg"));
            m.put("商品名称", d.get("spmc"));
            m.put("备注", d.get("remark"));
            m.put("批准文号", d.get("pzwh"));
            m.put("批号", d.get("ph"));
            m.put("生产单位", d.get("scdw"));
            m.put("被抽单位", d.get("sydw"));
            m.put("检验依据", d.get("jyyj"));
            m.put("检验部门", d.get("jybm"));
            datas.add(m);
        });
        return AsposeUtil.createApplyListPdf(sqph,mapdata, datas);
    }

    private Map<String, Object> getMapDatas(String sqph){
        // 读取信息(1、读取ypbh，2：读取委托信息）
        Condition condition = new Condition(SyApplyWt.class);
        Example.Criteria criteria =  condition.createCriteria();
        criteria.andEqualTo("sqph",sqph);
        List<SyApplyWt> list = syApplyWtService.findByCondition(condition);
        List<String> ypbhs = new ArrayList<String>();
        list.forEach(e->{
            ypbhs.add(e.getYpbh());
        });

        Map<String, Object> mapdatas = new HashMap<String, Object>();
        Condition condition_wt = new Condition(SyWeituo.class);
        Example.Criteria criteria_wt =  condition_wt.createCriteria();
        criteria_wt.andIn("ypbh",ypbhs);
        condition_wt.orderBy("jybh").asc();
        List<SyWeituo> datas = syWeituoService.findByCondition(condition_wt);
        List<Object> yplist = new ArrayList<Object>();
        for(SyWeituo e: datas){
            // 委托信息
            Map<String, Object> infors = getweituoInfo(e);
            yplist.add(infors);
        }
        mapdatas.put("清单数据",yplist);
        return mapdatas;
    }
    /**
     * 读取兽药检验委托基础信息到Map对象中
     * @param e
     * @return
     */
    private Map<String, Object> getweituoInfo(SyWeituo e){
        Map<String, Object> infors = new HashMap<String, Object>();
        infors.put("ypbh", formatNull(e.getYpbh()));
        infors.put("ypmc", formatNull(e.getYpmc()));
        infors.put("spmc", formatNull(e.getSpmc()));
        infors.put("jybh", formatNull(e.getJybh()));
        infors.put("gg", formatNull(e.getGg()));
        infors.put("jyyj", formatNull(e.getJyyj()));
        infors.put("scdw", formatNull(e.getScdw()));
        infors.put("sydw", formatNull(e.getSydw()));
        infors.put("pzwh", formatNull(e.getPzwh()));
        infors.put("ph", formatNull(e.getPzwh()));
        infors.put("remark", formatNull(e.getRemark()));

        infors.put("jybm", ScsyResourceUtil.getOrganization(e.getJybm()));
        return infors;
    }

  private String formatNull(String val){
    return Strings.isNullOrEmpty(val) ? "/":val;
  }


}
