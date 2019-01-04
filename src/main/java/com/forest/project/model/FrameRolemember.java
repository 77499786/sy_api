package com.forest.project.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "frame_rolemember")
public class FrameRolemember {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 角色
     */
    @Column(name = "role_id")
    private String roleId;

    /**
     * 用户
     */
    @Column(name = "user_id")
    private String userId;

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
     * 获取角色
     *
     * @return role_id - 角色
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * 设置角色
     *
     * @param roleId 角色
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取用户
     *
     * @return user_id - 用户
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户
     *
     * @param userId 用户
     */
    public void setUserId(String userId) {
        this.userId = userId;
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
}