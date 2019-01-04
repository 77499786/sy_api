package com.forest.sy.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sy_company")
public class SyCompany {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 企业名称
     */
    private String qymc;

    /**
     * 用户信息id
     */
    private String userid;

    /**
     * 企业账号
     */
    @Transient
    private String qyzh;

    /**
     * 企业密码
     */
    @Transient
    private String qymm;

    /**
     * 座机
     */
    private String zjdh;

    /**
     * 手机
     */
    private String sjdh;

    /**
     * 企业地址
     */
    private String qydz;

    /**
     * 联系人
     */
    private String lxr;

    /**
     * 生产许可证
     */
    private String scxkz;

    /**
     * 生产范围
     */
    private String scfw;

    /**
     * 生产范围名称
     */
    @Transient
    private String scfwmc;

    /**
     * 生产许可证
     */
    private String gmp;

    /**
     * 邮编
     */
    private String zipcode;

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
     * 获取企业名称
     *
     * @return qymc - 企业名称
     */
    public String getQymc() {
        return qymc;
    }

    /**
     * 设置企业名称
     *
     * @param qymc 企业名称
     */
    public void setQymc(String qymc) {
        this.qymc = qymc;
    }

    /**
     * 获取用户信息id
     *
     * @return userid - 用户信息id
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置用户信息id
     *
     * @param userid 用户信息id
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * 获取座机
     *
     * @return zjdh - 座机
     */
    public String getZjdh() {
        return zjdh;
    }

    /**
     * 设置座机
     *
     * @param zjdh 座机
     */
    public void setZjdh(String zjdh) {
        this.zjdh = zjdh;
    }

    /**
     * 获取手机
     *
     * @return sjdh - 手机
     */
    public String getSjdh() {
        return sjdh;
    }

    /**
     * 设置手机
     *
     * @param sjdh 手机
     */
    public void setSjdh(String sjdh) {
        this.sjdh = sjdh;
    }

    /**
     * 获取企业地址
     *
     * @return qydz - 企业地址
     */
    public String getQydz() {
        return qydz;
    }

    /**
     * 设置企业地址
     *
     * @param qydz 企业地址
     */
    public void setQydz(String qydz) {
        this.qydz = qydz;
    }

    /**
     * 获取联系人
     *
     * @return lxr - 联系人
     */
    public String getLxr() {
        return lxr;
    }

    /**
     * 设置联系人
     *
     * @param lxr 联系人
     */
    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    /**
     * 获取生产许可证
     *
     * @return scxkz - 生产许可证
     */
    public String getScxkz() {
        return scxkz;
    }

    /**
     * 设置生产许可证
     *
     * @param scxkz 生产许可证
     */
    public void setScxkz(String scxkz) {
        this.scxkz = scxkz;
    }

    /**
     * 获取生产范围
     *
     * @return scfw - 生产范围
     */
    public String getScfw() {
        return scfw;
    }

    /**
     * 设置生产范围
     *
     * @param scfw 生产范围
     */
    public void setScfw(String scfw) {
        this.scfw = scfw;
    }

    /**
     * 获取生产许可证
     *
     * @return gmp - 生产许可证
     */
    public String getGmp() {
        return gmp;
    }

    /**
     * 设置生产许可证
     *
     * @param gmp 生产许可证
     */
    public void setGmp(String gmp) {
        this.gmp = gmp;
    }

    /**
     * 获取邮编
     *
     * @return zipcode - 邮编
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * 设置邮编
     *
     * @param zipcode 邮编
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
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

    public String getQyzh() {
        return qyzh;
    }

    public void setQyzh(String qyzh) {
        this.qyzh = qyzh;
    }

    public String getQymm() {
        return qymm;
    }

    public void setQymm(String qymm) {
        this.qymm = qymm;
    }

    public String getScfwmc() {
        return scfwmc;
    }

    public void setScfwmc(String scfwmc) {
        this.scfwmc = scfwmc;
    }
}