package com.forest.crm.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import javax.persistence.*;

@Table(name = "crm_sales")
public class CrmSales {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 序号
     */
    private Integer xh;

    /**
     * 销售日期
     */
    @JSONField(format="yyyy-MM-dd")
    private Date xsrq;

    /**
     * 商品
     */
    private String sp;

    /**
     * 商品名称
     */
    @Transient
    private String spmc;

    /**
     * 顾客
     */
    private String gk;


    /**
     * 顾客姓名
     */
    @Transient
    private String gkxm;

    /**
     * 销售数量
     */
    private Integer xssl;

    /**
     * 成本单价
     */
    private Integer cbdj;

    /**
     * 销售单价
     */
    private Integer xsdj;

    /**
     * 销售总价
     */
    private Integer xszj;

    /**
     * 商品编码
     */
    private String spbm;

    /**
     * 快递单号
     */
    private String kddh;

    /**
     * 发单时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm")
    private Date fdsj;

    /**
     * 收单确认时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm")
    private Date sdqrsj;

    /**
     * 顾客反馈
     */
    private String gkfk;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人名称
     */
    private String creator;

    /**
     * 创建日期
     */
    private Date createtime;

    /**
     * 更新人名称
     */
    private String modifer;

    /**
     * 更新日期
     */
    @Column(name = "modifyTime")
    private Date modifytime;

    /**
     * 删除标志
     */
    private Integer isdeleted;

    /**
     * 结算标志
     */
    private Integer jsbz;

    /**
     * 收费标志
     */
    private Integer sfbz;

    /**
     * 快递费用
     */
    private Integer kdfy;

    /**
     * 开始日期
     */
    @Transient
    private Date start_day;

    /**
     * 结束日期
     */
    @Transient
    private Date stop_day;

    /**
     * 开始序号
     */
    @Transient
    private Integer start_xh;

    /**
     * 结束序号
     */
    @Transient
    private Integer stop_xh;
    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取销售日期
     *
     * @return xsrq - 销售日期
     */
    public Date getXsrq() {
        return xsrq;
    }

    /**
     * 设置销售日期
     *
     * @param xsrq 销售日期
     */
    public void setXsrq(Date xsrq) {
        this.xsrq = xsrq;
    }

    /**
     * 获取商品
     *
     * @return sp - 商品
     */
    public String getSp() {
        return sp;
    }

    /**
     * 设置商品
     *
     * @param sp 商品
     */
    public void setSp(String sp) {
        this.sp = sp;
    }

    /**
     * 获取顾客
     *
     * @return gk - 顾客
     */
    public String getGk() {
        return gk;
    }

    /**
     * 设置顾客
     *
     * @param gk 顾客
     */
    public void setGk(String gk) {
        this.gk = gk;
    }

    /**
     * 获取销售数量
     *
     * @return xssl - 销售数量
     */
    public Integer getXssl() {
        return xssl;
    }

    /**
     * 设置销售数量
     *
     * @param xssl 销售数量
     */
    public void setXssl(Integer xssl) {
        this.xssl = xssl;
    }

    /**
     * 获取成本单价
     *
     * @return cbdj - 成本单价
     */
    public Integer getCbdj() {
        return cbdj;
    }

    /**
     * 设置成本单价
     *
     * @param cbdj 成本单价
     */
    public void setCbdj(Integer cbdj) {
        this.cbdj = cbdj;
    }

    /**
     * 获取销售单价
     *
     * @return xsdj - 销售单价
     */
    public Integer getXsdj() {
        return xsdj;
    }

    /**
     * 设置销售单价
     *
     * @param xsdj 销售单价
     */
    public void setXsdj(Integer xsdj) {
        this.xsdj = xsdj;
    }

    /**
     * 获取销售总价
     *
     * @return xszj - 销售总价
     */
    public Integer getXszj() {
        return xszj;
    }

    /**
     * 设置销售总价
     *
     * @param xszj 销售总价
     */
    public void setXszj(Integer xszj) {
        this.xszj = xszj;
    }

    /**
     * 获取商品编码
     *
     * @return spbm - 商品编码
     */
    public String getSpbm() {
        return spbm;
    }

    /**
     * 设置商品编码
     *
     * @param spbm 商品编码
     */
    public void setSpbm(String spbm) {
        this.spbm = spbm;
    }

    /**
     * 获取快递单号
     *
     * @return kddh - 快递单号
     */
    public String getKddh() {
        return kddh;
    }

    /**
     * 设置快递单号
     *
     * @param kddh 快递单号
     */
    public void setKddh(String kddh) {
        this.kddh = kddh;
    }

    /**
     * 获取发单时间
     *
     * @return fdsj - 发单时间
     */
    public Date getFdsj() {
        return fdsj;
    }

    /**
     * 设置发单时间
     *
     * @param fdsj 发单时间
     */
    public void setFdsj(Date fdsj) {
        this.fdsj = fdsj;
    }

    /**
     * 获取收单确认时间
     *
     * @return sdqrsj - 收单确认时间
     */
    public Date getSdqrsj() {
        return sdqrsj;
    }

    /**
     * 设置收单确认时间
     *
     * @param sdqrsj 收单确认时间
     */
    public void setSdqrsj(Date sdqrsj) {
        this.sdqrsj = sdqrsj;
    }

    /**
     * 获取顾客反馈
     *
     * @return gkfk - 顾客反馈
     */
    public String getGkfk() {
        return gkfk;
    }

    /**
     * 设置顾客反馈
     *
     * @param gkfk 顾客反馈
     */
    public void setGkfk(String gkfk) {
        this.gkfk = gkfk;
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
     * 获取删除标志
     *
     * @return isdeleted - 删除标志
     */
    public Integer getIsdeleted() {
        return isdeleted;
    }

    /**
     * 设置删除标志
     *
     * @param isdeleted 删除标志
     */
    public void setIsdeleted(Integer isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Date getStart_day() {
        return start_day;
    }

    public void setStart_day(Date start_day) {
        this.start_day = start_day;
    }

    public Date getStop_day() {
        return stop_day;
    }

    public void setStop_day(Date stop_day) {
        this.stop_day = stop_day;
    }

    public Integer getJsbz() {
        return jsbz;
    }

    public void setJsbz(Integer jsbz) {
        this.jsbz = jsbz;
    }

    public Integer getKdfy() {
        return kdfy;
    }

    public void setKdfy(Integer kdfy) {
        this.kdfy = kdfy;
    }

    public String getSpmc() {
        return spmc;
    }

    public void setSpmc(String spmc) {
        this.spmc = spmc;
    }

    public String getGkxm() {
        return gkxm;
    }

    public void setGkxm(String gkxm) {
        this.gkxm = gkxm;
    }

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    public Integer getStart_xh() {
        return start_xh;
    }

    public void setStart_xh(Integer start_xh) {
        this.start_xh = start_xh;
    }

    public Integer getStop_xh() {
        return stop_xh;
    }

    public void setStop_xh(Integer stop_xh) {
        this.stop_xh = stop_xh;
    }

    public Integer getSfbz() {
        return sfbz;
    }

    public void setSfbz(Integer sfbz) {
        this.sfbz = sfbz;
    }
}