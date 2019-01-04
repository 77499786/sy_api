package com.forest.project.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "v_org_user")
public class VOrgUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 主键
     */
    private String userdataid;

    /**
     * 编号
     */
    private String usercode;

    /**
     * 姓名
     */
    private String username;

    /**
     * 账号
     */
    private String userid;

    /**
     * 密码
     */
    private String password;

    /**
     * 部门
     */
    @Column(name = "org_id")
    private String orgId;

    /**
     * 电话
     */
    private String tel;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 性别
     */
    private String sex;

    /**
     * 是否在用
     */
    private String inuse;

    /**
     * 名称
     */
    private String orgname;

    private Integer setted;

    /**
     * 说明
     */
    private String content;

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
     * 获取主键
     *
     * @return userdataid - 主键
     */
    public String getUserdataid() {
        return userdataid;
    }

    /**
     * 设置主键
     *
     * @param userdataid 主键
     */
    public void setUserdataid(String userdataid) {
        this.userdataid = userdataid;
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
     * 获取账号
     *
     * @return userid - 账号
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置账号
     *
     * @param userid 账号
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取部门
     *
     * @return org_id - 部门
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * 设置部门
     *
     * @param orgId 部门
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
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
     * 获取出生日期
     *
     * @return birthday - 出生日期
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 设置出生日期
     *
     * @param birthday 出生日期
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取性别
     *
     * @return sex - 性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置性别
     *
     * @param sex 性别
     */
    public void setSex(String sex) {
        this.sex = sex;
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

    /**
     * @return setted
     */
    public Integer getSetted() {
        return setted;
    }

    /**
     * @param setted
     */
    public void setSetted(Integer setted) {
        this.setted = setted;
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
}