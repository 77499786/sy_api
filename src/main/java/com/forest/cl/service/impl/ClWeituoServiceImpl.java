package com.forest.cl.service.impl;

import com.forest.cl.dao.ClWeituoMapper;
import com.forest.cl.model.ClFlowdata;
import com.forest.cl.model.ClFlowdefine;
import com.forest.cl.model.ClResult;
import com.forest.cl.model.ClWeituo;
import com.forest.cl.service.*;
import com.forest.core.AbstractService;
import com.forest.project.model.FrameEmployee;
import com.forest.project.service.FrameEmployeeService;
import com.forest.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

//import org.jodconverter.DocumentConverter;

/**
 * Created by CodeGenerator on 2018/08/22.
 */
@Service
@Transactional
public class ClWeituoServiceImpl extends AbstractService<ClWeituo> implements ClWeituoService {

    // 需要手工追加查询逻辑才添加
    @Resource
    private ClWeituoMapper clWeituoMapper;
    @Resource
    private ClDetectitemService clDetectitemService;
    @Resource
    private ClResultService clResultService;
    @Resource
    private ClFlowdataService clFlowdataService;
    @Resource
    private ClFlowdefineService clFlowdefineService;
    @Resource
    private FrameEmployeeService frameEmployeeService;
//    @Autowired
//    private DocumentConverter documentConverter;

    @Override
    public void submitProcess(Map<String, Object> params) {

        // 退回操作时，更新当前节点信息
        boolean isreback = false;
        if( params.containsKey(ClFlowdata.KEYS_ISBACK)){
            isreback = (boolean) params.get(ClFlowdata.KEYS_ISBACK);
        }

        String jybhs = String.valueOf(params.get(ClFlowdata.KEYS_JYBH));
        List<ClFlowdata> entitys= getFlowLogData(jybhs);

        if(isreback){
            // 退回处理
            Integer nd = (Integer) params.get(ClFlowdata.KEYS_NEXTNODE);
            for(ClFlowdata e : entitys){
                e.setCurrentstatus(nd);
                clFlowdataService.update(e);
            }
            return ;
        }

        // 正常提交处理
        for(ClFlowdata e : entitys){
            // 登记处理
            if( params.containsKey(ClFlowdata.KEYS_CURRENTNODE)
                    && !e.getCurrentstatus().equals( (Integer) params.get(ClFlowdata.KEYS_CURRENTNODE)) ){
                e.setSfdj(Integer.valueOf(1));
            }

            // 更新待办人
            if( params.containsKey(ClFlowdata.KEYS_NEXTUSER)){
                e.setTodoperson(String.valueOf(params.get(ClFlowdata.KEYS_NEXTUSER)));
            }
            // 更新样品残留量
            if(params.containsKey(ClFlowdata.KEYS_REMAIN)){
                e.setYpcll(String.valueOf(params.get(ClFlowdata.KEYS_REMAIN)));
            }

            // 更新流程处理记录
            List<FlowNodeData> logs =	ScsyFlowUtil.readFlowData(e.getJybh(), e.getLccljl());
            Iterator<FlowNodeData> it = logs.iterator();
            while(it.hasNext()){
                FlowNodeData nd = it.next();
                if(nd.getNode().equals(e.getCurrentstatus())){
                    nd.setJybh(e.getJybh());
                    nd.setDoTime(DateUtils.formatTime());
                    if(params.containsKey(ClFlowdata.KEYS_USER)){
                        FrameEmployee u =  (FrameEmployee) params.get(ClFlowdata.KEYS_USER);
                        nd.setDone(u.getName());
                    }
                    // 处理结果
                    if(params.containsKey(ClFlowdata.KEYS_REMARK)){
                        nd.setResult(String.valueOf(params.get(ClFlowdata.KEYS_REMARK)));
                    }
                    if(StringUtils.isNotBlank(e.getTodoperson())){
                        FrameEmployee user = frameEmployeeService.findBy("userid", e.getTodoperson());
                        nd.setNextuser(user.getName());
                    }
                    // 获取下一节点设置为当前节点
                    if( null == params.get(ClFlowdata.KEYS_NEXTNODE)) {
                        e.setCurrentstatus(nd.getNextnd());
                    } else {
                        e.setCurrentstatus((Integer) params.get(ClFlowdata.KEYS_NEXTNODE));
                    }
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
            clFlowdataService.update(e);

            // 归档处理时 归档PDF报告
            if(e.getCurrentstatus() == ClFlowdefine.GUIDANG_INDEX){
                String fromFile = ScsyReportUtil.getRealReportPath() + ScsyReportUtil.CL_SUBDICTIONARY + e.getJybh() + ScsyReportUtil.PDF_SUFFIX;
                FileUtils.moveFile(fromFile, ScsyReportUtil.getRealArchiveReportPath().concat(ScsyReportUtil.CL_SUBDICTIONARY));
                fromFile = ScsyReportUtil.getRealReportPath() + ScsyReportUtil.CL_SUBDICTIONARY + e.getJybh() + ScsyReportUtil.DOC_SUFFIX;
                FileUtils.moveFile(fromFile, ScsyReportUtil.getRealArchiveReportPath().concat(ScsyReportUtil.CL_SUBDICTIONARY));
            }
        }
    }

    @Override
    public List<Map<String, Object>> getremaindatas(Map<String, Object> param) {
        return clWeituoMapper.callRemainDatas(param);
    }

    @Override
    public Long queryRemainCounts(Map<String, Object> param){
        return clWeituoMapper.queryRemainCounts(param);
    }

    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> paramsMap){
        return  clWeituoMapper.queryList(paramsMap);
    }
    @Override
    public String exportReport(String jpbhs) {
        // jasper报表模式
        if(ConStant.REPORT_TYPE_JASPER.equals(ConStant.REPORT_TYPE)){
            return null;
        } else {
            return exportWord2Pdf(jpbhs);
        }
    }

    @Override
    public List<ClWeituo> getSjdw(String keyword) {
        return clWeituoMapper.getSjdwDatas(keyword);
    }

    @Override
    public List<ClWeituo> getScdw(String keyword) {
        return clWeituoMapper.getScdwDatas(keyword);
    }

    @Override
    public List<String> getSingleItem(String col, String val){
        return clWeituoMapper.getSingleItem(col, val);
    }
    @Override
    public List<ClWeituo> getWtdw(String keyword) {
        return clWeituoMapper.getWtdwDatas(keyword);
    }

    @Override
    public List<Map<String, Object>> queryRemainInfors(Map<String, Object> paramsMap) {
        return clWeituoMapper.queryRemainInfors(paramsMap);
    }

    /**
     * 读取残留检验委托基础信息到Map对象中
     * @param e
     * @return
     */
    private Map<String, Object> getweituoInfo(ClWeituo e){
        Map<String, Object> infors = new HashMap<String, Object>();
//		infors.put("报告日期", DateUtil.getCurrentDate());  // 当前日期
        infors.put("报告日期", getPrintDate(e.getJybh()));

        infors.put("样品编号", e.getYpbh());
        infors.put("送样单编号", e.getSydbh());
        infors.put("样品名称", e.getYpmc());
        infors.put("检验编号", e.getJybh());
        infors.put("样品量",e.getYpsl());
        infors.put("包封情况", ScsyResourceUtil.getDicitionary(e.getBfqk()));
        infors.put("保存情况", ScsyResourceUtil.getDicitionary(e.getBcqk()));
        infors.put("运输情况", ScsyResourceUtil.getDicitionary(e.getYsqk()));
        infors.put("检验依据", e.getCjyj());
//		infors.put("取样方式",e.getQyfs());
//		infors.put("抽样场所",e.getCjcs());
        infors.put("抽样日期", DateUtils.formatDate(e.getCjrq()));
        infors.put("生产单位", e.getSjwmc());
        infors.put("送样单位",e.getWtkhw());
        infors.put("收检日期",DateUtils.formatDate(e.getSjrq()));

        infors.put("检验项目", getJyxmName(e.getJyxm()));

        infors.put("检品名称", e.getYpmc());
        infors.put("检品编号", e.getJybh());

        return infors;
    }

    /**
     * 按报表格式进行报告生成
     * @param jpbhs
     * @return
     */
    private String exportWord2Pdf(String jpbhs){
        // office模式
        List<String> filenamelst = new ArrayList<String>();
        StringBuilder bhlist = new StringBuilder();
        Condition condition = new Condition(ClWeituo.class);
        Example.Criteria criteria =  condition.createCriteria();
        criteria.andIn("jybh",Arrays.asList(jpbhs.split(",")));
        List<ClWeituo> datas = super.findByCondition(condition);
        for(ClWeituo e: datas){
            Map<String, Object> infors = getweituoInfo(e);

            Condition conditionResult = new Condition(ClResult.class);
            conditionResult.createCriteria().andEqualTo("jybh", e.getJybh());
            List<ClResult> rets = clResultService.findByCondition(conditionResult);
            List<Map<String, String>> results = new ArrayList<Map<String, String>>();
            for(ClResult ret : rets){
                Map<String, String> lst = new HashMap<String, String>();
                List<String> keys = Arrays.asList(ret.getDetectItem().replace("[", "")
                    .replace("]","").replaceAll("\"","").split(","));
                Collections.reverse(keys);
                lst.put("检测项目", ScsyResourceUtil.getDicitionary(keys.get(0)));
                lst.put("检测数据",ret.getDetectData());
                lst.put("检测限",ret.getDetectLimit());
                lst.put("最高残留量",ret.getTopResidue());
                lst.put("检测结果",ret.getDetectResult());
                results.add(lst);
            }
            infors.put("检验结果", results);
            String filepath = AsposeUtil.createPdfReport(e.getJybh(),infors);  //ScsyReportOfficeUtil.exportRemainReport(e.getJybh(), infors, results);
            if(StringUtils.isNotBlank(filepath)){
                if(bhlist.length() > 0){
                    bhlist.append(",");
                }
                bhlist.append(e.getJybh());
                filenamelst.add(filepath);
            }
        }
        // 更新检验处理进度
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(ClFlowdata.KEYS_ISBACK, false);
        params.put(ClFlowdata.KEYS_CURRENTNODE, ClFlowdata.KEYS_FLOW_PRINT);
        // 提交/退回节点
        params.put(ClFlowdata.KEYS_NEXTNODE, ClFlowdata.KEYS_FLOW_LOCKED);
        params.put(ClFlowdata.KEYS_JYBH, bhlist);
//        // 更新当前进度情况(进入归档环节)
//        this.submitProcess(params);
        return ScsyReportUtil.mergePdfFiles(filenamelst, ScsyReportUtil.CL_SUBDICTIONARY);
    }

    /**
     * 获取流程记录数据（退回后历史信息清空）
     */
    public List<FlowNodeData> getFlowLogs(String jpbh) {
        List<FlowNodeData> logs = new ArrayList<FlowNodeData>();
        List<ClFlowdata> lst= getFlowLogData(jpbh);
        int node = lst.get(0).getCurrentstatus();
        logs = ScsyFlowUtil.readFlowData(jpbh, lst.get(0).getLccljl());
        // 读取当前节点的顺序序号
        int index = ClFlowdefine.STOP_INDEX;
        for(FlowNodeData e : logs){
            if(e.getNode() == node){
                index = e.getNo();
            }
        }
        // 顺序序号后节点的历史信息不显示
        for(FlowNodeData e : logs){
            if(e.getNo() >= index){
                e.setDone("");
                e.setDoTime("");
                e.setResult("");
            }
        }
//		for(FlowNodeData e : logs){
//			if(e.getNode() >= node){
//				e.setDone("");
//				e.setDoTime("");
//				e.setResult("");
//			}
//		}
        return logs;
    }

    /**
     * 获取报告日期（已归档，读取打印操作时间作报告日期；否则读取当前日期）
     * @param jpbh 检验编号
     * @return 报告日期
     */
    private String getPrintDate(String jpbh){
        List<ClFlowdata> entitys= getFlowLogData(jpbh);
        for(ClFlowdata e : entitys){
            if (e.getCurrentstatus() ==10){
                // 已归档，读取打印操作时间作报告日期
                List<FlowNodeData> lst =  ScsyFlowUtil.readFlowData(jpbh, e.getLccljl());
                for(FlowNodeData entity : lst){
                    if(entity.getNextnd() ==10){
                        return entity.getDoTime().substring(0, 10);
                    }
                }
            }
        }
        // 未归档，读取当前日期作为报告日期
        return DateUtils.formatDate();
    }
    /**
     * 根据检验编号获取检验综合信息
     * @param jybhs
     * @return
     */
    private List<ClFlowdata> getFlowLogData(String jybhs){
        Condition condition = new Condition(ClFlowdata.class);
        Example.Criteria criteria =  condition.createCriteria();
        criteria.andIn(ClFlowdata.KEYS_JYBH, Arrays.asList(jybhs.split(",")));
        return  this.clFlowdataService.findByCondition(condition);
    }

    @Override
    public void initFlowData(String jybh) {

        Condition condition = new Condition(ClFlowdefine.class);
        Example.Criteria criteria =  condition.createCriteria();
        criteria.andLessThan(ClFlowdefine.KEYS_INDEXNO, ClFlowdefine.STOP_INDEX);
        condition.orderBy(ClFlowdefine.KEYS_INDEXNO).asc();
        List<ClFlowdefine> defines = clFlowdefineService.findByCondition(condition);

        ClFlowdata entity = clFlowdataService.findBy(ClFlowdata.KEYS_JYBH, jybh);
        if(entity == null){
            entity = new ClFlowdata();
            entity.setInuse(1);
            entity.setCurrentstatus(ClFlowdefine.START_INDEX);
            entity.setSfdj(0);
            entity.setJybh(String.valueOf(jybh));
            entity.setLccljl(ScsyFlowUtil.flowDefines(defines));
            clFlowdataService.save(entity);
        }
    }

    public void saveResults(List<ClResult> canliuResultList, ClFlowdata flowdata) {
        //删除原始数据
        Condition condition = new Condition(ClResult.class);
        Example.Criteria criteria =  condition.createCriteria();
        criteria.andEqualTo(ClFlowdata.KEYS_JYBH, flowdata.getJybh());
        this.clResultService.deleteByCondition(condition);
        //保存新数据
        int index = 1;
        for(ClResult e : canliuResultList){
            e.setJybh(flowdata.getJybh());
            e.setSortno(index++);
            clResultService.save(e);
        }
        // 保存检验结论、残留量
        ClFlowdata entity = clFlowdataService.findBy(ClFlowdata.KEYS_JYBH, flowdata.getJybh());
        if(null!= entity){
            entity.setYpcll(flowdata.getYpcll());
            entity.setJyjl(flowdata.getJyjl());
            clFlowdataService.update(entity);
        }
    }
    private String getJyxmName(String jyxm){
        List<String> names = new ArrayList<>();
        String [] xms = jyxm.replace("[","")
            .replace("]","").replaceAll("\"", "").split(",");
        Arrays.asList(xms).forEach(x ->{
            names.add(ScsyResourceUtil.getDicitionary(x));
        });
        return names.toString();
    }
}
