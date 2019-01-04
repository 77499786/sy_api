package com.forest.project.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "frame_organization")
public class FrameOrganization {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 上级部门
     */
    @Column(name = "org_id")
    private String orgId;

    /**
     * 上级部门名称
     */
    @Transient
    private String supername;
    /**
     * 部门分类
     */
    private String orgtype;

    /**
     * 地址
     */
    private String address;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 修改人
     */
    private String modifer;

    /**
     * 修改时间
     */
    @Column(name = "modifyTime")
    private Date modifytime;

    /**
     * 是否在用
     */
    private String inuse;

    /**
     * 说明
     */
    private String content;

    /**
     * 备注
     */
    private String remark;

    /**
     * 上级组织名称
     */
    @Transient
    private String parentname;

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
     * 获取编码
     *
     * @return code - 编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置编码
     *
     * @param code 编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取上级部门
     *
     * @return org_id - 上级部门
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * 设置上级部门
     *
     * @param orgId 上级部门
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * 获取部门分类
     *
     * @return orgtype - 部门分类
     */
    public String getOrgtype() {
        return orgtype;
    }

    /**
     * 设置部门分类
     *
     * @param orgtype 部门分类
     */
    public void setOrgtype(String orgtype) {
        this.orgtype = orgtype;
    }

    /**
     * 获取创建人
     *
     * @return creator - 创建人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人
     *
     * @param creator 创建人
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 获取创建时间
     *
     * @return createtime - 创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置创建时间
     *
     * @param createtime 创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 获取修改人
     *
     * @return modifer - 修改人
     */
    public String getModifer() {
        return modifer;
    }

    /**
     * 设置修改人
     *
     * @param modifer 修改人
     */
    public void setModifer(String modifer) {
        this.modifer = modifer;
    }

    /**
     * 获取修改时间
     *
     * @return modifyTime - 修改时间
     */
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * 设置修改时间
     *
     * @param modifytime 修改时间
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    /**
     * 获取是否在用
     *
     * @return inuse - 是否在用
     */
    public String getInuse() {
        return inuse;
    }

    /**
     * 设置是否在用
     *
     * @param inuse 是否在用
     */
    public void setInuse(String inuse) {
        this.inuse = inuse;
    }

    /**
     * 获取说明
     *
     * @return content - 说明
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置说明
     *
     * @param content 说明
     */
    public void setContent(String content) {
        this.content = content;
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

    public String getParentname() {
        return parentname;
    }

    public void setParentname(String parentname) {
        this.parentname = parentname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSupername(String supername) {
        this.supername = supername;
    }

    public String getSupername() {
        return supername;
    }
}