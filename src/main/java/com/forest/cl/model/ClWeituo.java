package com.forest.cl.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import javax.persistence.*;

@Table(name = "cl_weituo")
public class ClWeituo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

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
     * 送样单编号
     */
    private String sydbh;

    /**
     * 样品编号
     */
    private String ypbh;

    /**
     * 检验编号
     */
    private String jybh;

    /**
     * 样品名称
     */
    private String ypmc;

    /**
     * 证书编号
     */
    private String zsbh;

    /**
     * 包封情况
     */
    private String bfqk;

    /**
     * 包封情况名称
     */
    @Transient
    private String bfqkmc;
    /**
     * 保存情况
     */
    private String bcqk;
    /**
     * 保存情况名称
     */
    @Transient
    private String bcqkmc;
    /**
     * 运输情况
     */
    private String ysqk;
    /**
     * 运输情况名称
     */
    @Transient
    private String ysqkmc;
    /**
     * 样品数量
     */
    private String ypsl;

    /**
     * 抽检日期
     */
    @JSONField(format="yyyy-MM-dd")
    private Date cjrq;

    /**
     * 检验项目
     */
    private String jyxm;

    /**
     * 检验项目名称
     */
    @Transient
    private String jyxmmc;
    /**
     * 检验依据
     */
    private String jyyj;

    /**
     * 受检单位名称
     */
    private String sjwmc;

    /**
     * 取样方式
     */
    private String qyfs;

    /**
     * 取样方式名称
     */
    @Transient
    private String qyfsmc;
    /**
     * 受检单位地址
     */
    private String sjwdz;

    /**
     * 受检单位邮编
     */
    private String sjdwyb;

    /**
     * 受检单位联系人
     */
    private String sjdwlxr;

    /**
     * 受检单位电话
     */
    private String sjdwdh;

    /**
     * 抽样场所
     */
    private String cjcs;

    /**
     * 抽检依据
     */
    private String cjyj;

    /**
     * 委托客户单位
     */
    private String wtkhw;

    /**
     * 委托客户地址
     */
    private String wtkhdz;

    /**
     * 委托客户邮编
     */
    private String wtkhyb;

    /**
     * 委托客户电话
     */
    private String wtkhdh;

    /**
     * 委托客户联系人
     */
    private String wtkhlxr;

    /**
     * 允许分包
     */
    private Integer yxfb;

    /**
     * 委托人
     */
    private String wtr;

    /**
     * 委托日期
     */
    @JSONField(format="yyyy-MM-dd")
    private Date wtrq;

    /**
     * 任务来源
     */
    private String rwly;

    /**
     * 任务来源名称
     */
    @Transient
    private String rwlymc;

    /**
     * 生产单位
     */
    private String scdw;

    /**
     * 备注
     */
    private String remark;

    /**
     * 收检日期
     */
    @JSONField(format="yyyy-MM-dd")
    private Date sjrq;

    /**
     * 检验年度
     */
    private String cljynd;

    /**
     * 使用标志
     */
    private Integer inuse;

    /**
     * 批量任务件数
     */
    @Transient
    private Integer count;

    /**
     *  查询关键字
     */
    @Transient
    private String keyword;

    /**
     * 查询期间开始日期
     */
    @Transient
    private String startday;

    /**
     * 查询期间结束日期
     */
    @Transient
    private String stopday;

    /**
     * 原始文档
     */
    private byte[] yswt;

    /**
     * 流程状态
     */
    @Transient
    private Integer currentstatus;

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
     * 获取送样单编号
     *
     * @return sydbh - 送样单编号
     */
    public String getSydbh() {
        return sydbh;
    }

    /**
     * 设置送样单编号
     *
     * @param sydbh 送样单编号
     */
    public void setSydbh(String sydbh) {
        this.sydbh = sydbh;
    }

    /**
     * 获取样品编号
     *
     * @return ypbh - 样品编号
     */
    public String getYpbh() {
        return ypbh;
    }

    /**
     * 设置样品编号
     *
     * @param ypbh 样品编号
     */
    public void setYpbh(String ypbh) {
        this.ypbh = ypbh;
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
     * 获取样品名称
     *
     * @return ypmc - 样品名称
     */
    public String getYpmc() {
        return ypmc;
    }

    /**
     * 设置样品名称
     *
     * @param ypmc 样品名称
     */
    public void setYpmc(String ypmc) {
        this.ypmc = ypmc;
    }

    /**
     * 获取证书编号
     *
     * @return zsbh - 证书编号
     */
    public String getZsbh() {
        return zsbh;
    }

    /**
     * 设置证书编号
     *
     * @param zsbh 证书编号
     */
    public void setZsbh(String zsbh) {
        this.zsbh = zsbh;
    }

    /**
     * 获取包封情况
     *
     * @return bfqk - 包封情况
     */
    public String getBfqk() {
        return bfqk;
    }

    /**
     * 设置包封情况
     *
     * @param bfqk 包封情况
     */
    public void setBfqk(String bfqk) {
        this.bfqk = bfqk;
    }

    /**
     * 获取保存情况
     *
     * @return bcqk - 保存情况
     */
    public String getBcqk() {
        return bcqk;
    }

    /**
     * 设置保存情况
     *
     * @param bcqk 保存情况
     */
    public void setBcqk(String bcqk) {
        this.bcqk = bcqk;
    }

    /**
     * 获取运输情况
     *
     * @return ysqk - 运输情况
     */
    public String getYsqk() {
        return ysqk;
    }

    /**
     * 设置运输情况
     *
     * @param ysqk 运输情况
     */
    public void setYsqk(String ysqk) {
        this.ysqk = ysqk;
    }

    /**
     * 获取样品数量
     *
     * @return ypsl - 样品数量
     */
    public String getYpsl() {
        return ypsl;
    }

    /**
     * 设置样品数量
     *
     * @param ypsl 样品数量
     */
    public void setYpsl(String ypsl) {
        this.ypsl = ypsl;
    }

    /**
     * 获取抽检日期
     *
     * @return cjrq - 抽检日期
     */
    public Date getCjrq() {
        return cjrq;
    }

    /**
     * 设置抽检日期
     *
     * @param cjrq 抽检日期
     */
    public void setCjrq(Date cjrq) {
        this.cjrq = cjrq;
    }

    /**
     * 获取检验项目
     *
     * @return jyxm - 检验项目
     */
    public String getJyxm() {
        return jyxm;
    }

    /**
     * 设置检验项目
     *
     * @param jyxm 检验项目
     */
    public void setJyxm(String jyxm) {
        this.jyxm = jyxm;
    }

    /**
     * 获取检验依据
     *
     * @return jyyj - 检验依据
     */
    public String getJyyj() {
        return jyyj;
    }

    /**
     * 设置检验依据
     *
     * @param jyyj 检验依据
     */
    public void setJyyj(String jyyj) {
        this.jyyj = jyyj;
    }

    /**
     * 获取受检单位名称
     *
     * @return sjwmc - 受检单位名称
     */
    public String getSjwmc() {
        return sjwmc;
    }

    /**
     * 设置受检单位名称
     *
     * @param sjwmc 受检单位名称
     */
    public void setSjwmc(String sjwmc) {
        this.sjwmc = sjwmc;
    }

    /**
     * 获取取样方式
     *
     * @return qyfs - 取样方式
     */
    public String getQyfs() {
        return qyfs;
    }

    /**
     * 设置取样方式
     *
     * @param qyfs 取样方式
     */
    public void setQyfs(String qyfs) {
        this.qyfs = qyfs;
    }

    /**
     * 获取受检单位地址
     *
     * @return sjwdz - 受检单位地址
     */
    public String getSjwdz() {
        return sjwdz;
    }

    /**
     * 设置受检单位地址
     *
     * @param sjwdz 受检单位地址
     */
    public void setSjwdz(String sjwdz) {
        this.sjwdz = sjwdz;
    }

    /**
     * 获取受检单位邮编
     *
     * @return sjdwyb - 受检单位邮编
     */
    public String getSjdwyb() {
        return sjdwyb;
    }

    /**
     * 设置受检单位邮编
     *
     * @param sjdwyb 受检单位邮编
     */
    public void setSjdwyb(String sjdwyb) {
        this.sjdwyb = sjdwyb;
    }

    /**
     * 获取受检单位联系人
     *
     * @return sjdwlxr - 受检单位联系人
     */
    public String getSjdwlxr() {
        return sjdwlxr;
    }

    /**
     * 设置受检单位联系人
     *
     * @param sjdwlxr 受检单位联系人
     */
    public void setSjdwlxr(String sjdwlxr) {
        this.sjdwlxr = sjdwlxr;
    }

    /**
     * 获取受检单位电话
     *
     * @return sjdwdh - 受检单位电话
     */
    public String getSjdwdh() {
        return sjdwdh;
    }

    /**
     * 设置受检单位电话
     *
     * @param sjdwdh 受检单位电话
     */
    public void setSjdwdh(String sjdwdh) {
        this.sjdwdh = sjdwdh;
    }

    /**
     * 获取抽样场所
     *
     * @return cjcs - 抽样场所
     */
    public String getCjcs() {
        return cjcs;
    }

    /**
     * 设置抽样场所
     *
     * @param cjcs 抽样场所
     */
    public void setCjcs(String cjcs) {
        this.cjcs = cjcs;
    }

    /**
     * 获取抽检依据
     *
     * @return cjyj - 抽检依据
     */
    public String getCjyj() {
        return cjyj;
    }

    /**
     * 设置抽检依据
     *
     * @param cjyj 抽检依据
     */
    public void setCjyj(String cjyj) {
        this.cjyj = cjyj;
    }

    /**
     * 获取委托客户单位
     *
     * @return wtkhw - 委托客户单位
     */
    public String getWtkhw() {
        return wtkhw;
    }

    /**
     * 设置委托客户单位
     *
     * @param wtkhw 委托客户单位
     */
    public void setWtkhw(String wtkhw) {
        this.wtkhw = wtkhw;
    }

    /**
     * 获取委托客户地址
     *
     * @return wtkhdz - 委托客户地址
     */
    public String getWtkhdz() {
        return wtkhdz;
    }

    /**
     * 设置委托客户地址
     *
     * @param wtkhdz 委托客户地址
     */
    public void setWtkhdz(String wtkhdz) {
        this.wtkhdz = wtkhdz;
    }

    /**
     * 获取委托客户邮编
     *
     * @return wtkhyb - 委托客户邮编
     */
    public String getWtkhyb() {
        return wtkhyb;
    }

    /**
     * 设置委托客户邮编
     *
     * @param wtkhyb 委托客户邮编
     */
    public void setWtkhyb(String wtkhyb) {
        this.wtkhyb = wtkhyb;
    }

    /**
     * 获取委托客户电话
     *
     * @return wtkhdh - 委托客户电话
     */
    public String getWtkhdh() {
        return wtkhdh;
    }

    /**
     * 设置委托客户电话
     *
     * @param wtkhdh 委托客户电话
     */
    public void setWtkhdh(String wtkhdh) {
        this.wtkhdh = wtkhdh;
    }

    /**
     * 获取委托客户联系人
     *
     * @return wtkhlxr - 委托客户联系人
     */
    public String getWtkhlxr() {
        return wtkhlxr;
    }

    /**
     * 设置委托客户联系人
     *
     * @param wtkhlxr 委托客户联系人
     */
    public void setWtkhlxr(String wtkhlxr) {
        this.wtkhlxr = wtkhlxr;
    }

    /**
     * 获取允许分包
     *
     * @return yxfb - 允许分包
     */
    public Integer getYxfb() {
        return yxfb;
    }

    /**
     * 设置允许分包
     *
     * @param yxfb 允许分包
     */
    public void setYxfb(Integer yxfb) {
        this.yxfb = yxfb;
    }

    /**
     * 获取委托人
     *
     * @return wtr - 委托人
     */
    public String getWtr() {
        return wtr;
    }

    /**
     * 设置委托人
     *
     * @param wtr 委托人
     */
    public void setWtr(String wtr) {
        this.wtr = wtr;
    }

    /**
     * 获取委托日期
     *
     * @return wtrq - 委托日期
     */
    public Date getWtrq() {
        return wtrq;
    }

    /**
     * 设置委托日期
     *
     * @param wtrq 委托日期
     */
    public void setWtrq(Date wtrq) {
        this.wtrq = wtrq;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取收检日期
     *
     * @return sjrq - 收检日期
     */
    public Date getSjrq() {
        return sjrq;
    }

    /**
     * 设置收检日期
     *
     * @param sjrq 收检日期
     */
    public void setSjrq(Date sjrq) {
        this.sjrq = sjrq;
    }

    /**
     * 获取检验年度
     *
     * @return cljynd - 检验年度
     */
    public String getCljynd() {
        return cljynd;
    }

    /**
     * 设置检验年度
     *
     * @param cljynd 检验年度
     */
    public void setCljynd(String cljynd) {
        this.cljynd = cljynd;
    }

    /**
     * 获取使用标志
     *
     * @return inuse - 使用标志
     */
    public Integer getInuse() {
        return inuse;
    }

    /**
     * 设置使用标志
     *
     * @param inuse 使用标志
     */
    public void setInuse(Integer inuse) {
        this.inuse = inuse;
    }

    /**
     * @return yswt
     */
    public byte[] getYswt() {
        return yswt;
    }

    /**
     * @param yswt
     */
    public void setYswt(byte[] yswt) {
        this.yswt = yswt;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getJyxmmc() {
        return jyxmmc;
    }

    public void setJyxmmc(String jyxmmc) {
        this.jyxmmc = jyxmmc;
    }

    public String getQyfsmc() {
        return qyfsmc;
    }

    public void setQyfsmc(String qyfsmc) {
        this.qyfsmc = qyfsmc;
    }

    public Integer getCurrentstatus() {
        return currentstatus;
    }

    public void setCurrentstatus(Integer currentstatus) {
        this.currentstatus = currentstatus;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getStartday() {
        return startday;
    }

    public void setStartday(String startday) {
        this.startday = startday;
    }

    public String getStopday() {
        return stopday;
    }

    public void setStopday(String stopday) {
        this.stopday = stopday;
    }

    public String getRwly() {
        return rwly;
    }

    public void setRwly(String rwly) {
        this.rwly = rwly;
    }

    public String getScdw() {
        return scdw;
    }

    public void setScdw(String scdw) {
        this.scdw = scdw;
    }

  public String getBfqkmc() {
    return bfqkmc;
  }

  public void setBfqkmc(String bfqkmc) {
    this.bfqkmc = bfqkmc;
  }

  public String getBcqkmc() {
    return bcqkmc;
  }

  public void setBcqkmc(String bcqkmc) {
    this.bcqkmc = bcqkmc;
  }

  public String getYsqkmc() {
    return ysqkmc;
  }

  public void setYsqkmc(String ysqkmc) {
    this.ysqkmc = ysqkmc;
  }

  public String getRwlymc() {
    return rwlymc;
  }

  public void setRwlymc(String rwlymc) {
    this.rwlymc = rwlymc;
  }
}