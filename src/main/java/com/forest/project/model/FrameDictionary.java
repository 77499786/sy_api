package com.forest.project.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "frame_dictionary")
public class FrameDictionary {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 分类
     */
    private String def;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 上级字典
     */
    @Column(name = "dictionary_id")
    private String dictionaryId;

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
     * 排序编号
     */
    private Integer sortno;
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
     * 获取分类
     *
     * @return def - 分类
     */
    public String getDef() {
        return def;
    }

    /**
     * 设置分类
     *
     * @param def 分类
     */
    public void setDef(String def) {
        this.def = def;
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
     * 获取上级字典
     *
     * @return dictionary_id - 上级字典
     */
    public String getDictionaryId() {
        return dictionaryId;
    }

    /**
     * 设置上级字典
     *
     * @param dictionaryId 上级字典
     */
    public void setDictionaryId(String dictionaryId) {
        this.dictionaryId = dictionaryId;
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

    public Integer getSortno() {
        return sortno;
    }

    public void setSortno(Integer sortno) {
        this.sortno = sortno;
    }
}