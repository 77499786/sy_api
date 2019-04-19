package com.forest.sy.model;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.forest.utils.FlowNodeData;
import com.google.common.base.Strings;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "sy_flowdata")
public class SyFlowdata {
    public static String KEYS_JYBH ="jybh";
    public static String KEYS_CURRENTNODE ="node_index";
    public static String KEYS_USER ="username";
    public static String KEYS_REMARK ="remark";
    public static String KEYS_NEXTUSER ="nextuser";
    public static String KEYS_REMAIN ="remainlevel";
    public static String KEYS_ISBACK ="isReback";
    public static String KEYS_NEXTNODE ="node_next";
    /**
     * 流程节点（打印）
     */
    public static int KEYS_FLOW_PRINT=8;

    /**
     * 流程节点（归档）
     */
    public static int KEYS_FLOW_LOCKED =9;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 检验编号
     */
    private String jybh;

    /**
     * 流程状态
     */
    private Integer currentstatus;

    /**
     * 是否已登记
     */
    private Integer sfdj;

    /**
     * 收件人
     */
    private String sjr;

    /**
     * 收样确认
     */
    @JSONField(format="yyyy-MM-dd")
    private Date syqr;

    /**
     * 检验室确认人
     */
    private String jysqrr;

    /**
     * 检验室确认
     */
    @JSONField(format="yyyy-MM-dd")
    private Date jyqr;

    /**
     * 样品残留量
     */
    private String ypcll;

    /**
     * 检验结论
     */
    private String jyjl;

    /**
     * 回收确认人
     */
    private String hsqrr;

    /**
     * 回收确认
     */
    @JSONField(format="yyyy-MM-dd")
    private Date hsqr;

    /**
     * 待办人
     */
    private String todoperson;

    /**
     * 检验人姓名
     */
    private String jyrxm;

    /**
     * 使用的主要工具
     */
    private String zygj;

    /**
     * 原始报告
     */
    private String yswj;

    /**
     * 原始委托
     */
    private String yswt;

    /**
     * 流程处理记录
     */
    private String lccljl;

    /**
     * 统计对象
     */
    private Integer tjdx;

    /**
     * 是否假兽药
     */
    private Integer sfjsy;

    /**
     * 假兽药结论
     */
    private String jsyjl;

    /**
     * 是否盲样
     */
    private Integer sfmy;

    /**
     * 流程实例ID
     */
    private String processid;

    /**
     * 领证人
     */
    private String sendto;

    /**
     * 领证日期/发送日期
     */
    @JSONField(format="yyyy-MM-dd")
    private Date postday;

    /**
     * 快递单号
     */
    private String express;

    /**
     * 创建人名称
     */
    private String creator;

    /**
     * 创建日期
     */
    @JSONField(format="yyyy-MM-dd HH:mm")
    private Date createtime;

    /**
     * 更新人名称
     */
    private String modifer;

    /**
     * 更新日期
     */
    @Column(name = "modifyTime")
    @JSONField(format="yyyy-MM-dd HH:mm")
    private Date modifytime;

    /**
     * 是否在用
     */
    private Integer inuse;

    /**
     * 流程处理记录
     */
    @Transient
    private List<FlowNodeData> flowdatas;

    /**
     * 检验结果明细
     */
    @Transient
    private List<SyResult> results;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取检验编号
     *
     * @return jybh - 检验编号
     */
    public String getJybh() {
        return jybh;
    }

    /**
     * 设置检验编号
     *
     * @param jybh 检验编号
     */
    public void setJybh(String jybh) {
        this.jybh = jybh;
    }

    /**
     * 获取流程状态
     *
     * @return currentstatus - 流程状态
     */
    public Integer getCurrentstatus() {
        return currentstatus;
    }

    /**
     * 设置流程状态
     *
     * @param currentstatus 流程状态
     */
    public void setCurrentstatus(Integer currentstatus) {
        this.currentstatus = currentstatus;
    }

    /**
     * 获取是否已登记
     *
     * @return sfdj - 是否已登记
     */
    public Integer getSfdj() {
        return sfdj;
    }

    /**
     * 设置是否已登记
     *
     * @param sfdj 是否已登记
     */
    public void setSfdj(Integer sfdj) {
        this.sfdj = sfdj;
    }

    /**
     * 获取收件人
     *
     * @return sjr - 收件人
     */
    public String getSjr() {
        return sjr;
    }

    /**
     * 设置收件人
     *
     * @param sjr 收件人
     */
    public void setSjr(String sjr) {
        this.sjr = sjr;
    }

    /**
     * 获取收样确认
     *
     * @return syqr - 收样确认
     */
    public Date getSyqr() {
        return syqr;
    }

    /**
     * 设置收样确认
     *
     * @param syqr 收样确认
     */
    public void setSyqr(Date syqr) {
        this.syqr = syqr;
    }

    /**
     * 获取检验室确认人
     *
     * @return jysqrr - 检验室确认人
     */
    public String getJysqrr() {
        return jysqrr;
    }

    /**
     * 设置检验室确认人
     *
     * @param jysqrr 检验室确认人
     */
    public void setJysqrr(String jysqrr) {
        this.jysqrr = jysqrr;
    }

    /**
     * 获取检验室确认
     *
     * @return jyqr - 检验室确认
     */
    public Date getJyqr() {
        return jyqr;
    }

    /**
     * 设置检验室确认
     *
     * @param jyqr 检验室确认
     */
    public void setJyqr(Date jyqr) {
        this.jyqr = jyqr;
    }

    /**
     * 获取样品残留量
     *
     * @return ypcll - 样品残留量
     */
    public String getYpcll() {
        return ypcll;
    }

    /**
     * 设置样品残留量
     *
     * @param ypcll 样品残留量
     */
    public void setYpcll(String ypcll) {
        this.ypcll = ypcll;
    }

    /**
     * 获取检验结论
     *
     * @return jyjl - 检验结论
     */
    public String getJyjl() {
        return jyjl;
    }

    /**
     * 设置检验结论
     *
     * @param jyjl 检验结论
     */
    public void setJyjl(String jyjl) {
        this.jyjl = jyjl;
    }

    /**
     * 获取回收确认人
     *
     * @return hsqrr - 回收确认人
     */
    public String getHsqrr() {
        return hsqrr;
    }

    /**
     * 设置回收确认人
     *
     * @param hsqrr 回收确认人
     */
    public void setHsqrr(String hsqrr) {
        this.hsqrr = hsqrr;
    }

    /**
     * 获取回收确认
     *
     * @return hsqr - 回收确认
     */
    public Date getHsqr() {
        return hsqr;
    }

    /**
     * 设置回收确认
     *
     * @param hsqr 回收确认
     */
    public void setHsqr(Date hsqr) {
        this.hsqr = hsqr;
    }

    /**
     * 获取待办人
     *
     * @return todoperson - 待办人
     */
    public String getTodoperson() {
        return todoperson;
    }

    /**
     * 设置待办人
     *
     * @param todoperson 待办人
     */
    public void setTodoperson(String todoperson) {
        this.todoperson = todoperson;
    }

    /**
     * 获取使用的主要工具
     *
     * @return zygj - 使用的主要工具
     */
    public String getZygj() {
        return zygj;
    }

    /**
     * 设置使用的主要工具
     *
     * @param zygj 使用的主要工具
     */
    public void setZygj(String zygj) {
        this.zygj = zygj;
    }

    /**
     * 获取原始报告
     *
     * @return yswj - 原始报告
     */
    public String getYswj() {
        return yswj;
    }

    /**
     * 设置原始报告
     *
     * @param yswj 原始报告
     */
    public void setYswj(String yswj) {
        this.yswj = yswj;
    }

    /**
     * 获取流程处理记录
     *
     * @return lccljl - 流程处理记录
     */
    public String getLccljl() {
        return lccljl;
    }

    /**
     * 设置流程处理记录
     *
     * @param lccljl 流程处理记录
     */
    public void setLccljl(String lccljl) {
        this.lccljl = lccljl;
    }

    /**
     * 获取统计对象
     *
     * @return tjdx - 统计对象
     */
    public Integer getTjdx() {
        return tjdx;
    }

    /**
     * 设置统计对象
     *
     * @param tjdx 统计对象
     */
    public void setTjdx(Integer tjdx) {
        this.tjdx = tjdx;
    }

    /**
     * 获取是否假兽药
     *
     * @return sfjsy - 是否假兽药
     */
    public Integer getSfjsy() {
        return sfjsy;
    }

    /**
     * 设置是否假兽药
     *
     * @param sfjsy 是否假兽药
     */
    public void setSfjsy(Integer sfjsy) {
        this.sfjsy = sfjsy;
    }

    /**
     * 获取假兽药结论
     *
     * @return jsyjl - 假兽药结论
     */
    public String getJsyjl() {
        return jsyjl;
    }

    /**
     * 设置假兽药结论
     *
     * @param jsyjl 假兽药结论
     */
    public void setJsyjl(String jsyjl) {
        this.jsyjl = jsyjl;
    }

    /**
     * 获取是否盲样
     *
     * @return sfmy - 是否盲样
     */
    public Integer getSfmy() {
        return sfmy;
    }

    /**
     * 设置是否盲样
     *
     * @param sfmy 是否盲样
     */
    public void setSfmy(Integer sfmy) {
        this.sfmy = sfmy;
    }

    /**
     * 获取流程实例ID
     *
     * @return processid - 流程实例ID
     */
    public String getProcessid() {
        return processid;
    }

    /**
     * 设置流程实例ID
     *
     * @param processid 流程实例ID
     */
    public void setProcessid(String processid) {
        this.processid = processid;
    }

    /**
     * 获取领证人
     *
     * @return sendto - 领证人
     */
    public String getSendto() {
        return sendto;
    }

    /**
     * 设置领证人
     *
     * @param sendto 领证人
     */
    public void setSendto(String sendto) {
        this.sendto = sendto;
    }

    /**
     * 获取领证日期/发送日期
     *
     * @return postday - 领证日期/发送日期
     */
    public Date getPostday() {
        return postday;
    }

    /**
     * 设置领证日期/发送日期
     *
     * @param postday 领证日期/发送日期
     */
    public void setPostday(Date postday) {
        this.postday = postday;
    }

    /**
     * 获取快递单号
     *
     * @return express - 快递单号
     */
    public String getExpress() {
        return express;
    }

    /**
     * 设置快递单号
     *
     * @param express 快递单号
     */
    public void setExpress(String express) {
        this.express = express;
    }

    /**
     * 获取创建人名称
     *
     * @return creator - 创建人名称
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人名称
     *
     * @param creator 创建人名称
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 获取创建日期
     *
     * @return createtime - 创建日期
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置创建日期
     *
     * @param createtime 创建日期
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 获取更新人名称
     *
     * @return modifer - 更新人名称
     */
    public String getModifer() {
        return modifer;
    }

    /**
     * 设置更新人名称
     *
     * @param modifer 更新人名称
     */
    public void setModifer(String modifer) {
        this.modifer = modifer;
    }

    /**
     * 获取更新日期
     *
     * @return modifyTime - 更新日期
     */
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * 设置更新日期
     *
     * @param modifytime 更新日期
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    /**
     * 获取是否在用
     *
     * @return inuse - 是否在用
     */
    public Integer getInuse() {
        return inuse;
    }

    /**
     * 设置是否在用
     *
     * @param inuse 是否在用
     */
    public void setInuse(Integer inuse) {
        this.inuse = inuse;
    }

    public String getJyrxm() {
        return jyrxm;
    }

    public void setJyrxm(String jyrxm) {
        this.jyrxm = jyrxm;
    }

    public List<FlowNodeData> getFlowdatas() {
        flowdatas = JSONObject.parseArray(lccljl, FlowNodeData.class);
        return flowdatas;
    }

    public void setFlowdatas(List<FlowNodeData> flowdatas) {
        this.flowdatas = flowdatas;
        this.lccljl = JSONObject.toJSONString(flowdatas);
    }

    public String getYswt() {
        return yswt;
    }

    public void setYswt(String yswt) {
        this.yswt = yswt;
    }

    public List<SyResult> getResults() {
        return results;
    }

    public void setResults(List<SyResult> results) {
        this.results = results;
    }

    public String getQfrq() {
        for( FlowNodeData n : getFlowdatas()){
            if(n.getNode() == 8 && !Strings.isNullOrEmpty(n.getDoTime())) {
                return n.getDoTime().length() > 10 ?  n.getDoTime().substring(0, 10) :"";
            }
        }
        return "";
    }
}
