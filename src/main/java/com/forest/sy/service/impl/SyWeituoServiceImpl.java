package com.forest.sy.service.impl;

import com.forest.core.AbstractService;
import com.forest.core.BaseModel;
import com.forest.project.model.FrameEmployee;
import com.forest.project.service.FrameEmployeeService;
import com.forest.sy.dao.SyWeituoMapper;
import com.forest.sy.model.*;
import com.forest.sy.service.*;
import com.forest.utils.*;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by CodeGenerator on 2018/09/10.
 */
@Service
@Transactional
public class SyWeituoServiceImpl extends AbstractService<SyWeituo> implements SyWeituoService {
  @Resource
  private SyFlowdefineService syFlowdefineService;
  @Resource
  private SyFlowdataService syFlowdataService;
  // 需要手工追加查询逻辑才添加
  @Resource
  private SyWeituoMapper syWeituoMapper;
  @Resource
  private FrameEmployeeService frameEmployeeService;
  @Resource
  private SyResultService syResultService;
  @Resource
  private SyJymdService syJymdService;

  @Override
  public void initFlowData(String jybh, Integer node) {

    Condition condition = new Condition(SyFlowdefine.class);
    Example.Criteria criteria = condition.createCriteria();
    criteria.andLessThan(SyFlowdefine.KEYS_INDEXNO, SyFlowdefine.STOP_INDEX);
    condition.orderBy(SyFlowdefine.KEYS_INDEXNO).asc();
    List<SyFlowdefine> defines = syFlowdefineService.findByCondition(condition);

    SyFlowdata entity = syFlowdataService.findBy(SyFlowdata.KEYS_JYBH, jybh);
    if (entity == null) {
      entity = new SyFlowdata();
      entity.setInuse(1);
      if (node == null) {
        entity.setCurrentstatus(SyFlowdefine.START_INDEX);
      } else {
        entity.setCurrentstatus(node);
      }
      entity.setSfdj(0);
      entity.setJybh(String.valueOf(jybh));
      entity.setLccljl(ScsyFlowUtil.syFlowDefines(defines));
      syFlowdataService.save(entity);
    }
  }

  @Override
  public List<SyWeituo> getScdw(String keyword) {
    return syWeituoMapper.getScdwDatas(keyword);
  }

  @Override
  public List<SyWeituo> getWtdw(String keyword) {
    return syWeituoMapper.getWtdwDatas(keyword);
  }

  @Override
  public List<SyWeituo> gethistoryDatas(Map<String, Object> params) {
    return syWeituoMapper.gethistoryDatas(params);
  }

  @Override
  public Long gethistoryDataCnt(Map<String, Object> params) {
    return syWeituoMapper.gethistoryDataCnt(params);
  }

  @Override
  public void submitProcess(Map<String, Object> params) {

    // 退回操作时，更新当前节点信息
    boolean isreback = false;
    if (params.containsKey(SyFlowdata.KEYS_ISBACK)) {
      isreback = (boolean) params.get(SyFlowdata.KEYS_ISBACK);
    }

    String jybhs = String.valueOf(params.get(SyFlowdata.KEYS_JYBH));
    List<SyFlowdata> entitys = getFlowLogData(jybhs);

    if (isreback) {
      // 退回处理
      Integer nd = (Integer) params.get(SyFlowdata.KEYS_NEXTNODE);
      for (SyFlowdata e : entitys) {
        e.setCurrentstatus(nd);
        syFlowdataService.update(e);
      }
      return;
    }

    // 正常提交处理
    for (SyFlowdata e : entitys) {
      // 登记处理
      if (params.containsKey(SyFlowdata.KEYS_CURRENTNODE)
          && !e.getCurrentstatus().equals((Integer) params.get(SyFlowdata.KEYS_CURRENTNODE))) {
        e.setSfdj(Integer.valueOf(1));
      }

      // 更新待办人
      if (params.containsKey(SyFlowdata.KEYS_NEXTUSER)) {
        e.setTodoperson(String.valueOf(params.get(SyFlowdata.KEYS_NEXTUSER)));
      }
      // 更新样品残留量
      if (params.containsKey(SyFlowdata.KEYS_REMAIN)) {
        e.setYpcll(String.valueOf(params.get(SyFlowdata.KEYS_REMAIN)));
      }

      // 更新流程处理记录
      List<FlowNodeData> logs = ScsyFlowUtil.readFlowData(e.getJybh(), e.getLccljl());
      Iterator<FlowNodeData> it = logs.iterator();
      while (it.hasNext()) {
        FlowNodeData nd = it.next();
        if (nd.getNode().equals(e.getCurrentstatus())) {
          nd.setJybh(e.getJybh());
          nd.setDoTime(DateUtils.formatTime());
          if (params.containsKey(SyFlowdata.KEYS_USER)) {
            nd.setDone(String.valueOf(params.get(SyFlowdata.KEYS_USER)));
          }
          // 处理结果
          if (params.containsKey(SyFlowdata.KEYS_REMARK)) {
            nd.setResult(String.valueOf(params.get(SyFlowdata.KEYS_REMARK)));
          }
          if (StringUtils.isNotBlank(e.getTodoperson())) {
            FrameEmployee user = frameEmployeeService.findBy("userid", e.getTodoperson());
            nd.setNextuser(user.getName());
          }
          // 获取下一节点设置为当前节点
          e.setCurrentstatus(nd.getNextnd());
//					// 分配任务后 更新“ 检验人”信息
//					while(it.hasNext()){
//						FlowNodeData nextnd = it.next();
//						if(StringUtils.isNotEmpty(e.getTodoperson()) && nextnd.getNode()== e.getCurrentstatus()){
//							nextnd.setDone(e.getTodoperson());
//							break;
//						}
//					}
          break;
        }
      }
      e.setLccljl(ScsyFlowUtil.flowLogs(logs));
      syFlowdataService.update(e);

      // 归档处理时 归档PDF报告
      if (e.getCurrentstatus() == SyFlowdefine.STOP_INDEX) {
        String fromFile = ScsyReportUtil.getRealReportPath() + ScsyReportUtil.SY_SUBDICTIONARY + e.getJybh() + ScsyReportUtil.PDF_SUFFIX;
        FileUtils.moveFile(fromFile, ScsyReportUtil.getRealArchiveReportPath() + ScsyReportUtil.SY_SUBDICTIONARY);
        fromFile = ScsyReportUtil.getRealReportPath() + ScsyReportUtil.SY_SUBDICTIONARY + e.getJybh() + ScsyReportUtil.DOC_SUFFIX;
        FileUtils.moveFile(fromFile, ScsyReportUtil.getRealArchiveReportPath() + ScsyReportUtil.SY_SUBDICTIONARY);
      }
    }
  }

  @Override
  public String exportReport(String jpbhs) {
    // jasper报表模式
    return exportWordReport(jpbhs);
  }

  @Override
  public String exportRefer(String ypbh) {
    SyWeituo syWeituo = super.findBy("jybh", ypbh);
    Map<String, Object> datamap = new HashMap<String, Object>();
    datamap.put("生产单位", syWeituo.getScdw());
    datamap.put("联系人", syWeituo.getScdwlxr());
    datamap.put("联系电话", syWeituo.getScdw());
    datamap.put("抽检单位", syWeituo.getCjdw());
    datamap.put("抽检日期", DateUtils.formatDate(syWeituo.getCjrq()));
    datamap.put("被抽单位", syWeituo.getSydw());
    datamap.put("检品名称", syWeituo.getYpmc());
    datamap.put("商品名称", syWeituo.getSpmc());
    datamap.put("规格", syWeituo.getGg());
    datamap.put("批准文号", syWeituo.getPzwh());
    datamap.put("批号", syWeituo.getPh());
    return AsposeUtil.createChoujiandanPdf(ypbh, datamap);
  }

  @Override
  public String exportContract(String ypbh) {
    SyWeituo syWeituo = super.findBy("ypbh", ypbh);
    Map<String, Object> datamap = new HashMap<String, Object>();
    datamap.put("检品编号", syWeituo.getJybh());
    datamap.put("检品名称", syWeituo.getYpmc());
    datamap.put("商品名称", syWeituo.getSpmc());
    datamap.put("批号", syWeituo.getPh());
    datamap.put("批准文号", syWeituo.getPzwh());
    datamap.put("剂型", ScsyResourceUtil.getDicitionary(syWeituo.getScdw()));
    datamap.put("规格", syWeituo.getGg());
    datamap.put("包装", ScsyResourceUtil.getDicitionary(syWeituo.getBz()));
    datamap.put("代表量", syWeituo.getDbl());
    datamap.put("检品数量", syWeituo.getJpsl());
    datamap.put("生产单位", syWeituo.getScdw());
    datamap.put("药品分类", ScsyResourceUtil.getDicitionary(syWeituo.getYplx()));
    datamap.put("申请类型", "未知");
    datamap.put("检验目的", ScsyResourceUtil.getCheckTargets(syWeituo.getJymd()));
    datamap.put("检验依据", syWeituo.getJyyj());
    datamap.put("检验项目", ScsyResourceUtil.getDicitionary(syWeituo.getJyxm()));
    datamap.put("是否加快", BaseModel.SFJJ.getName(syWeituo.getSfjj()));
    datamap.put("是否同意分包", BaseModel.TYFB.getName(syWeituo.getTyfb()));
    datamap.put("抽检日期", DateUtils.formatDate(syWeituo.getCjrq()));
    datamap.put("生产单位", syWeituo.getScdw());
    datamap.put("抽检单位", syWeituo.getCjdw());
    datamap.put("被抽单位", syWeituo.getSydw());
    datamap.put("委托客户单位", syWeituo.getWtdw());
    datamap.put("委托客户地址", syWeituo.getWtdwdz());
    datamap.put("电话", syWeituo.getWtdwdh());
    datamap.put("传真", syWeituo.getWtdwcz());
    datamap.put("邮编", syWeituo.getWtdwyb());
    datamap.put("分包名称", "扩展字段");
    return AsposeUtil.exportContractPdf(ypbh, datamap);
  }

  /**
   * 用word模板方式打印报告
   *
   * @param jpbhs
   * @return
   */
  private String exportWordReport(String jpbhs) {
    Map<String, Map<String, Object>> datas = getJianpinInfo(jpbhs);
    List<String> filenamelst = new ArrayList<String>();
    datas.forEach((k, v) -> {
      filenamelst.add(AsposeUtil.createSyPdfReport(k, v));
    });
    // 更新检验处理进度
    if (1 == 0) {
      // 更新检验处理进度
      Map<String, Object> params = new HashMap<String, Object>();
      params.put(SyFlowdata.KEYS_ISBACK, false);
      params.put(SyFlowdata.KEYS_CURRENTNODE, SyFlowdata.KEYS_FLOW_PRINT);
      // 提交/退回节点
      params.put(SyFlowdata.KEYS_NEXTNODE, SyFlowdata.KEYS_FLOW_LOCKED);
      String jybhs = Arrays.toString(datas.keySet().toArray());
      params.put(SyFlowdata.KEYS_JYBH, jybhs.replace("[", "").replace("]", ""));
      // 更新当前进度情况(进入归档环节)
      this.submitProcess(params);
    }
    return ScsyReportUtil.mergePdfFiles(filenamelst, ScsyReportUtil.SY_SUBDICTIONARY);
  }

  /**
   * 读取综合检验信息
   *
   * @param jpbhs
   * @return
   */
  private Map<String, Map<String, Object>> getJianpinInfo(String jpbhs) {
    Map<String, Map<String, Object>> alldatas = new HashMap<>();
    StringBuilder bhlist = new StringBuilder();
    Condition condition = new Condition(SyWeituo.class);
    Example.Criteria criteria = condition.createCriteria();
    criteria.andIn("jybh", Arrays.asList(jpbhs.split(",")));
    List<SyWeituo> datas = super.findByCondition(condition);
    for (SyWeituo e : datas) {
      Map<String, Object> infors = getweituoInfo(e);
      infors.put("检验结果", getJianyanJieguoInfo(e.getJybh()));
      alldatas.put(e.getJybh(), infors);
    }
    return alldatas;
  }

  /**
   * 读取检验结果信息
   *
   * @param jpbh
   * @return
   */
  private List<Map<String, Object>> getJianyanJieguoInfo(String jpbh) {
    Condition conditionResult = new Condition(SyResult.class);
    conditionResult.createCriteria().andEqualTo("jybh", jpbh).andEqualTo("inuse", 1);
    conditionResult.orderBy("sortno").asc();
    List<SyResult> rets = syResultService.findByCondition(conditionResult);

    List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
    String jyzx = "";
    for (SyResult ret : rets) {
      if (!jyzx.equals(ret.getJyzx())) {
        if (!Strings.isNullOrEmpty(jyzx)) {
          results.add(new HashMap<String, Object>());
        }
        jyzx = ret.getJyzx();
        if (Strings.isNullOrEmpty(ret.getJyxx())) {
          Map<String, Object> lst = new HashMap<String, Object>();
          lst.put("检验项目", String.format("【%s】", jyzx));
          lst.put("标准规定", ret.getBzgd());
          lst.put("检验结果", ret.getJyjg1());
          lst.put("项目结论", ret.getXmjl());
          results.add(lst);
        } else {
          Map<String, Object> lstt = new HashMap<String, Object>();
          lstt.put("检验项目", String.format("【%s】", jyzx));
          lstt.put("标准规定", "");
          lstt.put("检验结果", "");
          lstt.put("项目结论", "");
          results.add(lstt);
          Map<String, Object> lst = new HashMap<String, Object>();
          lst.put("检验项目", ret.getJyxx());
          lst.put("标准规定", ret.getBzgd());
          lst.put("检验结果", ret.getJyjg1());
          lst.put("项目结论", ret.getXmjl());
          results.add(lst);
        }
      } else {
        Map<String, Object> lst = new HashMap<String, Object>();
//                    lst.put("检验总项", ret.getJyzx());
//                    String jyxm = Strings.isNullOrEmpty(ret.getJyxx()) ? ret.getJyzx() : ret.getJyzx().concat(":").concat(ret.getJyxx());
        lst.put("检验项目", ret.getJyxx());
        lst.put("标准规定", ret.getBzgd());
        lst.put("检验结果", ret.getJyjg1());
        lst.put("项目结论", ret.getXmjl());
        results.add(lst);
      }
    }
    results.add(new HashMap<String, Object>());
    return results;
  }

  /**
   * 根据检验编号获取检验综合信息
   *
   * @param jybhs
   * @return
   */
  private List<SyFlowdata> getFlowLogData(String jybhs) {
    Condition condition = new Condition(SyFlowdata.class);
    Example.Criteria criteria = condition.createCriteria();
    criteria.andIn(SyFlowdata.KEYS_JYBH, Arrays.asList(jybhs.split(",")));
    return this.syFlowdataService.findByCondition(condition);
  }

  /**
   * 读取兽药检验委托基础信息到Map对象中
   *
   * @param e
   * @return
   */
  private Map<String, Object> getweituoInfo(SyWeituo e) {
    Map<String, Object> infors = new HashMap<String, Object>();
    infors.put("报告日期", getPrintDate(e.getJybh()));

    infors.put("样品编号", formatNull(e.getYpbh()));
    infors.put("样品名称", formatNull(e.getYpmc()));
    infors.put("检验编号", formatNull(e.getJybh()));
    infors.put("样品量", formatNull(e.getDbl()));
    infors.put("生产单位", formatNull(e.getScdw()));
    infors.put("规格", formatNull(e.getGg()));
    infors.put("批号", formatNull(e.getPh()));
    infors.put("剂型", ScsyResourceUtil.getDicitionary(e.getJx()));
    infors.put("抽检单位", formatNull(e.getCjdw()));
    infors.put("样品数量", formatNull(e.getJpsl()));
    infors.put("被抽检单位", formatNull(e.getSydw()));
    infors.put("供样单位", formatNull(e.getSydw()));
    infors.put("代表量", formatNull(e.getDbl()));
    infors.put("检验依据", formatNull(e.getJyyj()));
    infors.put("检验项目", ScsyResourceUtil.getDicitionary(e.getJyxm()));
    infors.put("收样日期", DateUtils.formatDate(e.getSjrq()));
    infors.put("检验目的", ScsyResourceUtil.getCheckTargets(e.getJymd()));
//    infors.put("收样日期", DateUtils.formatDate(e.getSjrq()));
    infors.put("检品名称", formatNull(e.getYpmc()));
    infors.put("检品编号", formatNull(e.getJybh()));
    infors.put("申请类型", formatNull(e.getSqlxmc()));

    SyFlowdata f = syFlowdataService.findBy("jybh", e.getJybh());

    infors.put("所用主要仪器", formatNull(f.getZygj()));
    if (f.getSfjsy() > 0) {
      infors.put("检验结论", formatNull(f.getJsyjl()));
    } else {
      infors.put("检验结论", formatNull(f.getJyjl()));
    }
    infors.put("其他说明", "");
    infors.put("签发日期", f.getQfrq());
    infors.put("假兽药", f.getSfjsy() == 1 ? "是" : "否");
    return infors;
  }

  /**
   * 获取报告日期（已归档，读取打印操作时间作报告日期；否则读取当前日期）
   *
   * @param jpbh 检验编号
   * @return 报告日期
   */
  private String getPrintDate(String jpbh) {
    List<SyFlowdata> entitys = getFlowLogData(jpbh);
    for (SyFlowdata e : entitys) {
      if (e.getCurrentstatus() == 10) {
        // 已归档，读取打印操作时间作报告日期
        List<FlowNodeData> lst = ScsyFlowUtil.readFlowData(jpbh, e.getLccljl());
        for (FlowNodeData entity : lst) {
          if (entity.getNextnd() == 10) {
            return entity.getDoTime().substring(0, 10);
          }
        }
      }
    }
    // 未归档，读取当前日期作为报告日期
    return DateUtils.formatDate();
  }

  private String formatNull(String val) {
    return Strings.isNullOrEmpty(val) ? "/" : val;
  }

}
