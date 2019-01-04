package com.forest.cl.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "cl_result")
public class ClResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

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
     * 检验编号
     */
    private String jybh;

    /**
     * 序号
     */
    private Integer sortno;

    /**
     * 检测项目
     */
    @Column(name = "detect_item")
    private String detectItem;

    @Transient
    private String detectItemName;

    /**
     * 检测限
     */
    @Column(name = "detect_limit")
    private String detectLimit;

    /**
     * 最高残留量
     */
    @Column(name = "top_residue")
    private String topResidue;

    /**
     * 检测数据
     */
    @Column(name = "detect_data")
    private String detectData;

    /**
     * 检测结果
     */
    @Column(name = "detect_result")
    private String detectResult;

    /**
     * 使用标志
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
     * 获取检验编号
     *
     * @return jybh - 检验编号
     */
    public String getJybh() {
        return jybh;
    }

    /**
     * 设置检验编号
     *
     * @param jybh 检验编号
     */
    public void setJybh(String jybh) {
        this.jybh = jybh;
    }

    /**
     * 获取序号
     *
     * @return sortno - 序号
     */
    public Integer getSortno() {
        return sortno;
    }

    /**
     * 设置序号
     *
     * @param sortno 序号
     */
    public void setSortno(Integer sortno) {
        this.sortno = sortno;
    }

    /**
     * 获取检测项目
     *
     * @return detect_item - 检测项目
     */
    public String getDetectItem() {
        return detectItem;
    }

    /**
     * 设置检测项目
     *
     * @param detectItem 检测项目
     */
    public void setDetectItem(String detectItem) {
        this.detectItem = detectItem;
    }

    /**
     * 获取检测限
     *
     * @return detect_limit - 检测限
     */
    public String getDetectLimit() {
        return detectLimit;
    }

    /**
     * 设置检测限
     *
     * @param detectLimit 检测限
     */
    public void setDetectLimit(String detectLimit) {
        this.detectLimit = detectLimit;
    }

    /**
     * 获取最高残留量
     *
     * @return top_residue - 最高残留量
     */
    public String getTopResidue() {
        return topResidue;
    }

    /**
     * 设置最高残留量
     *
     * @param topResidue 最高残留量
     */
    public void setTopResidue(String topResidue) {
        this.topResidue = topResidue;
    }

    /**
     * 获取检测数据
     *
     * @return detect_data - 检测数据
     */
    public String getDetectData() {
        return detectData;
    }

    /**
     * 设置检测数据
     *
     * @param detectData 检测数据
     */
    public void setDetectData(String detectData) {
        this.detectData = detectData;
    }

    /**
     * 获取检测结果
     *
     * @return detect_result - 检测结果
     */
    public String getDetectResult() {
        return detectResult;
    }

    /**
     * 设置检测结果
     *
     * @param detectResult 检测结果
     */
    public void setDetectResult(String detectResult) {
        this.detectResult = detectResult;
    }

    /**
     * 获取使用标志
     *
     * @return inuse - 使用标志
     */
    public Integer getInuse() {
        return inuse;
    }

    /**
     * 设置使用标志
     *
     * @param inuse 使用标志
     */
    public void setInuse(Integer inuse) {
        this.inuse = inuse;
    }

    public String getDetectItemName() {
        return detectItemName;
    }

    public void setDetectItemName(String detectItemName) {
        this.detectItemName = detectItemName;
    }
}