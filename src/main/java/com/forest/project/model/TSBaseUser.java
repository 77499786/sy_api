package com.forest.project.model;

import javax.persistence.*;

@Table(name = "t_s_base_user")
public class TSBaseUser {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "activitiSync")
    private Short activitisync;

    private String browser;

    private String password;

    private String realname;

    private Short status;

    private String userkey;

    private String username;

    private String departid;

    /**
     * 删除状态
     */
    @Column(name = "delete_flag")
    private Short deleteFlag;

    private byte[] signature;

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
     * @return activitiSync
     */
    public Short getActivitisync() {
        return activitisync;
    }

    /**
     * @param activitisync
     */
    public void setActivitisync(Short activitisync) {
        this.activitisync = activitisync;
    }

    /**
     * @return browser
     */
    public String getBrowser() {
        return browser;
    }

    /**
     * @param browser
     */
    public void setBrowser(String browser) {
        this.browser = browser;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return realname
     */
    public String getRealname() {
        return realname;
    }

    /**
     * @param realname
     */
    public void setRealname(String realname) {
        this.realname = realname;
    }

    /**
     * @return status
     */
    public Short getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * @return userkey
     */
    public String getUserkey() {
        return userkey;
    }

    /**
     * @param userkey
     */
    public void setUserkey(String userkey) {
        this.userkey = userkey;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return departid
     */
    public String getDepartid() {
        return departid;
    }

    /**
     * @param departid
     */
    public void setDepartid(String departid) {
        this.departid = departid;
    }

    /**
     * 获取删除状态
     *
     * @return delete_flag - 删除状态
     */
    public Short getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * 设置删除状态
     *
     * @param deleteFlag 删除状态
     */
    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * @return signature
     */
    public byte[] getSignature() {
        return signature;
    }

    /**
     * @param signature
     */
    public void setSignature(byte[] signature) {
        this.signature = signature;
    }
}