package com.forest.project.model;

import javax.persistence.*;

@Table(name = "t_s_depart")
public class TSDepart {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String departname;

    private String parentdepartid;

    @Column(name = "org_code")
    private String orgCode;

    @Column(name = "org_type")
    private String orgType;

    private String mobile;

    private String fax;

    private String address;

    private String description;

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
     * @return departname
     */
    public String getDepartname() {
        return departname;
    }

    /**
     * @param departname
     */
    public void setDepartname(String departname) {
        this.departname = departname;
    }

    /**
     * @return parentdepartid
     */
    public String getParentdepartid() {
        return parentdepartid;
    }

    /**
     * @param parentdepartid
     */
    public void setParentdepartid(String parentdepartid) {
        this.parentdepartid = parentdepartid;
    }

    /**
     * @return org_code
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * @param orgCode
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * @return org_type
     */
    public String getOrgType() {
        return orgType;
    }

    /**
     * @param orgType
     */
    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    /**
     * @return mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}