package com.forest.sy.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sy_apply")
public class SyApply {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 企业账号id
     */
    private String compid;

    /**
     * 申请批号
     */
    private String sqph;

    /**
     * 状态:0编辑中，1已上报，9已审核
     */
    private Integer status;

    /**
     * 申请时间
     */
    @JSONField(format="yyyy-MM-dd")
    private Date sqrq;

    /**
     * 审批日期
     */
    @JSONField(format="yyyy-MM-dd")
    private Date sprq;

    /**
     * 审批信息
     */
    private String spxx;

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
     * 样品件数
     */
    @Transient
    private Integer ypjs;

    /**
     * 是否抽检委托批次 1:是， 0：否
     */
    private Integer iscjwt;
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
     * 获取企业账号id
     *
     * @return compid - 企业账号id
     */
    public String getCompid() {
        return compid;
    }

    /**
     * 设置企业账号id
     *
     * @param compid 企业账号id
     */
    public void setCompid(String compid) {
        this.compid = compid;
    }

    /**
     * 获取申请批号
     *
     * @return sqph - 申请批号
     */
    public String getSqph() {
        return sqph;
    }

    /**
     * 设置申请批号
     *
     * @param sqph 申请批号
     */
    public void setSqph(String sqph) {
        this.sqph = sqph;
    }

    /**
     * 获取状态:0编辑中，1已上报，9已审核
     *
     * @return status - 状态:0编辑中，1已上报，9已审核
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态:0编辑中，1已上报，9已审核
     *
     * @param status 状态:0编辑中，1已上报，9已审核
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取申请时间
     *
     * @return sqrq - 申请时间
     */
    public Date getSqrq() {
        return sqrq;
    }

    /**
     * 设置申请时间
     *
     * @param sqrq 申请时间
     */
    public void setSqrq(Date sqrq) {
        this.sqrq = sqrq;
    }

    /**
     * 获取审批日期
     *
     * @return sprq - 审批日期
     */
    public Date getSprq() {
        return sprq;
    }

    /**
     * 设置审批日期
     *
     * @param sprq 审批日期
     */
    public void setSprq(Date sprq) {
        this.sprq = sprq;
    }

    /**
     * 获取审批信息
     *
     * @return spxx - 审批信息
     */
    public String getSpxx() {
        return spxx;
    }

    /**
     * 设置审批信息
     *
     * @param spxx 审批信息
     */
    public void setSpxx(String spxx) {
        this.spxx = spxx;
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

    public Integer getIscjwt() {
        return iscjwt;
    }

    public void setIscjwt(Integer iscjwt) {
        this.iscjwt = iscjwt;
    }

    public Integer getYpjs() {
        return ypjs;
    }

    public void setYpjs(Integer ypjs) {
        this.ypjs = ypjs;
    }
}