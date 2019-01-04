package com.forest.sy.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sy_sign")
public class SySign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String user;

    private String keytitle;

    @Column(name = "imgPath")
    private String imgpath;

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
     * 是否在用
     */
    private Integer inuse;

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
     * @return user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return keytitle
     */
    public String getKeytitle() {
        return keytitle;
    }

    /**
     * @param keytitle
     */
    public void setKeytitle(String keytitle) {
        this.keytitle = keytitle;
    }

    /**
     * @return imgPath
     */
    public String getImgpath() {
        return imgpath;
    }

    /**
     * @param imgpath
     */
    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
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
}