package com.forest.home.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import javax.persistence.*;

@Table(name = "home_income")
public class HomeIncome {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 进账日期
     */
    @JSONField(format="yyyy-MM-dd")
    private Date jzrq;

    /**
     * 收入类型
     */
    private String srlx;

    /**
     * 金额
     */
    private Integer money;

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
     * 获取进账日期
     *
     * @return jzrq - 进账日期
     */
    public Date getJzrq() {
        return jzrq;
    }

    /**
     * 设置进账日期
     *
     * @param jzrq 进账日期
     */
    public void setJzrq(Date jzrq) {
        this.jzrq = jzrq;
    }

    /**
     * 获取收入类型
     *
     * @return srlx - 收入类型
     */
    public String getSrlx() {
        return srlx;
    }

    /**
     * 设置收入类型
     *
     * @param srlx 收入类型
     */
    public void setSrlx(String srlx) {
        this.srlx = srlx;
    }

    /**
     * 获取金额
     *
     * @return money - 金额
     */
    public Integer getMoney() {
        return money;
    }

    /**
     * 设置金额
     *
     * @param money 金额
     */
    public void setMoney(Integer money) {
        this.money = money;
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
}