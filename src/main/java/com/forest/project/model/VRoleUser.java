package com.forest.project.model;

import javax.persistence.*;

@Table(name = "v_role_user")
public class VRoleUser {
    /**
     * 主键
     */
    private String userid;

    /**
     * 姓名
     */
    private String username;

    /**
     * 编号
     */
    private String usercode;

    /**
     * 主键
     */
    private String roleid;

    /**
     * 名称
     */
    private String rolename;

    /**
     * 编码
     */
    private String rolecode;

    /**
     * 账号
     */
    private String account;

    /**
     * 电话
     */
    private String tel;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门
     */
    private String orgid;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 是否在用
     */
    private String inuse;

    /**
     * 名称
     */
    private String orgname;

    /**
     * 获取主键
     *
     * @return userid - 主键
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置主键
     *
     * @param userid 主键
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * 获取姓名
     *
     * @return username - 姓名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置姓名
     *
     * @param username 姓名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取编号
     *
     * @return usercode - 编号
     */
    public String getUsercode() {
        return usercode;
    }

    /**
     * 设置编号
     *
     * @param usercode 编号
     */
    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    /**
     * 获取主键
     *
     * @return roleid - 主键
     */
    public String getRoleid() {
        return roleid;
    }

    /**
     * 设置主键
     *
     * @param roleid 主键
     */
    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    /**
     * 获取名称
     *
     * @return rolename - 名称
     */
    public String getRolename() {
        return rolename;
    }

    /**
     * 设置名称
     *
     * @param rolename 名称
     */
    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    /**
     * 获取编码
     *
     * @return rolecode - 编码
     */
    public String getRolecode() {
        return rolecode;
    }

    /**
     * 设置编码
     *
     * @param rolecode 编码
     */
    public void setRolecode(String rolecode) {
        this.rolecode = rolecode;
    }

    /**
     * 获取账号
     *
     * @return account - 账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置账号
     *
     * @param account 账号
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取电话
     *
     * @return tel - 电话
     */
    public String getTel() {
        return tel;
    }

    /**
     * 设置电话
     *
     * @param tel 电话
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取部门
     *
     * @return orgid - 部门
     */
    public String getOrgid() {
        return orgid;
    }

    /**
     * 设置部门
     *
     * @param orgid 部门
     */
    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

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
     * 获取名称
     *
     * @return orgname - 名称
     */
    public String getOrgname() {
        return orgname;
    }

    /**
     * 设置名称
     *
     * @param orgname 名称
     */
    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }
}