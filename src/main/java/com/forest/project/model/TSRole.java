package com.forest.project.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_s_role")
public class TSRole {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String rolecode;

    private String rolename;

    /**
     * 修改人
     */
    @Column(name = "update_name")
    private String updateName;

    /**
     * 修改时间
     */
    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 修改人id
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 创建人
     */
    @Column(name = "create_name")
    private String createName;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 创建人id
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * @return ID
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
     * @return rolecode
     */
    public String getRolecode() {
        return rolecode;
    }

    /**
     * @param rolecode
     */
    public void setRolecode(String rolecode) {
        this.rolecode = rolecode;
    }

    /**
     * @return rolename
     */
    public String getRolename() {
        return rolename;
    }

    /**
     * @param rolename
     */
    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    /**
     * 获取修改人
     *
     * @return update_name - 修改人
     */
    public String getUpdateName() {
        return updateName;
    }

    /**
     * 设置修改人
     *
     * @param updateName 修改人
     */
    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    /**
     * 获取修改时间
     *
     * @return update_date - 修改时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置修改时间
     *
     * @param updateDate 修改时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取修改人id
     *
     * @return update_by - 修改人id
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置修改人id
     *
     * @param updateBy 修改人id
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取创建人
     *
     * @return create_name - 创建人
     */
    public String getCreateName() {
        return createName;
    }

    /**
     * 设置创建人
     *
     * @param createName 创建人
     */
    public void setCreateName(String createName) {
        this.createName = createName;
    }

    /**
     * 获取创建时间
     *
     * @return create_date - 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取创建人id
     *
     * @return create_by - 创建人id
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建人id
     *
     * @param createBy 创建人id
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}