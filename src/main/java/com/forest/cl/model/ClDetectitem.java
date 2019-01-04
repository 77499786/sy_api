package com.forest.cl.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "cl_detectitem")
public class ClDetectitem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 项目编号
     */
    private String itemcode;

    /**
     * 项目名称
     */
    private String itemname;

    /**
     * 编号规则
     */
    private String itemrule;

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
     * 使用标志
     */
    private Integer inuse;

    /**
     * 备注
     */
    private String remark;

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
     * 获取项目编号
     *
     * @return itemcode - 项目编号
     */
    public String getItemcode() {
        return itemcode;
    }

    /**
     * 设置项目编号
     *
     * @param itemcode 项目编号
     */
    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    /**
     * 获取项目名称
     *
     * @return itemname - 项目名称
     */
    public String getItemname() {
        return itemname;
    }

    /**
     * 设置项目名称
     *
     * @param itemname 项目名称
     */
    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    /**
     * 获取编号规则
     *
     * @return itemrule - 编号规则
     */
    public String getItemrule() {
        return itemrule;
    }

    /**
     * 设置编号规则
     *
     * @param itemrule 编号规则
     */
    public void setItemrule(String itemrule) {
        this.itemrule = itemrule;
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