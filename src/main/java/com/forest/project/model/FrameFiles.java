package com.forest.project.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "frame_files")
public class FrameFiles {
    /**
     * 数据主键
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 文件标题
     */
    private String attachmenttitle;

    /**
     * 扩展名
     */
    private String extend;

    /**
     * 文件位置
     */
    private String realpath;

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
     * 说明内容
     */
    private byte[] attachmentcontent;

    /**
     * 备注
     */
    private String remark;

    /**
     * 获取数据主键
     *
     * @return ID - 数据主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置数据主键
     *
     * @param id 数据主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取文件标题
     *
     * @return attachmenttitle - 文件标题
     */
    public String getAttachmenttitle() {
        return attachmenttitle;
    }

    /**
     * 设置文件标题
     *
     * @param attachmenttitle 文件标题
     */
    public void setAttachmenttitle(String attachmenttitle) {
        this.attachmenttitle = attachmenttitle;
    }

    /**
     * 获取扩展名
     *
     * @return extend - 扩展名
     */
    public String getExtend() {
        return extend;
    }

    /**
     * 设置扩展名
     *
     * @param extend 扩展名
     */
    public void setExtend(String extend) {
        this.extend = extend;
    }

    /**
     * 获取文件位置
     *
     * @return realpath - 文件位置
     */
    public String getRealpath() {
        return realpath;
    }

    /**
     * 设置文件位置
     *
     * @param realpath 文件位置
     */
    public void setRealpath(String realpath) {
        this.realpath = realpath;
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
     * 获取说明内容
     *
     * @return attachmentcontent - 说明内容
     */
    public byte[] getAttachmentcontent() {
        return attachmentcontent;
    }

    /**
     * 设置说明内容
     *
     * @param attachmentcontent 说明内容
     */
    public void setAttachmentcontent(byte[] attachmentcontent) {
        this.attachmentcontent = attachmentcontent;
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