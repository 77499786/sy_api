package com.forest.sy.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sy_jymd")
public class SyJymd {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 创建人登录名称
     */
    private String creator;

    /**
     * 创建日期
     */
    private Date createtime;

    /**
     * 更新人登录名称
     */
    private String modifer;

    /**
     * 更新日期
     */
    @Column(name = "modifyTime")
    private Date modifytime;

    /**
     * 检验目的编码
     */
    @Column(name = "jymd_code")
    private String jymdCode;

    /**
     * 检验目的名称
     */
    @Column(name = "jymd_name")
    private String jymdName;

    /**
     * 编码规则
     */
    @Column(name = "jymd_bmgz")
    private String jymdBmgz;

    /**
     * 是否在用
     */
    private Integer inuse;

    /**
     * 备注
     */
    private String remark;

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
     * 获取创建人登录名称
     *
     * @return creator - 创建人登录名称
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人登录名称
     *
     * @param creator 创建人登录名称
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
     * 获取更新人登录名称
     *
     * @return modifer - 更新人登录名称
     */
    public String getModifer() {
        return modifer;
    }

    /**
     * 设置更新人登录名称
     *
     * @param modifer 更新人登录名称
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
     * 获取检验目的编码
     *
     * @return jymd_code - 检验目的编码
     */
    public String getJymdCode() {
        return jymdCode;
    }

    /**
     * 设置检验目的编码
     *
     * @param jymdCode 检验目的编码
     */
    public void setJymdCode(String jymdCode) {
        this.jymdCode = jymdCode;
    }

    /**
     * 获取检验目的名称
     *
     * @return jymd_name - 检验目的名称
     */
    public String getJymdName() {
        return jymdName;
    }

    /**
     * 设置检验目的名称
     *
     * @param jymdName 检验目的名称
     */
    public void setJymdName(String jymdName) {
        this.jymdName = jymdName;
    }

    /**
     * 获取编码规则
     *
     * @return jymd_bmgz - 编码规则
     */
    public String getJymdBmgz() {
        return jymdBmgz;
    }

    /**
     * 设置编码规则
     *
     * @param jymdBmgz 编码规则
     */
    public void setJymdBmgz(String jymdBmgz) {
        this.jymdBmgz = jymdBmgz;
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
}