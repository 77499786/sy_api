package com.forest.sy.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sy_flowdefine")
public class SyFlowdefine {
    /** 开始节点编号	 */
    public static int START_INDEX = 1;
    /** 归档节点编号	 */
    public static int GUIDANG_INDEX = 10;
    /** 已归档节点编号 */
    public static int STOP_INDEX = 99;

    public static String KEYS_INDEXNO ="indexno";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 序号
     */
    private Integer indexno;

    /**
     * 简称
     */
    private String abbr;

    /**
     * 流程名称
     */
    private String nodename;

    /**
     * 下一节点
     */
    private Integer nextnode;

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
     * 获取序号
     *
     * @return indexno - 序号
     */
    public Integer getIndexno() {
        return indexno;
    }

    /**
     * 设置序号
     *
     * @param indexno 序号
     */
    public void setIndexno(Integer indexno) {
        this.indexno = indexno;
    }

    /**
     * 获取简称
     *
     * @return abbr - 简称
     */
    public String getAbbr() {
        return abbr;
    }

    /**
     * 设置简称
     *
     * @param abbr 简称
     */
    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    /**
     * 获取流程名称
     *
     * @return nodename - 流程名称
     */
    public String getNodename() {
        return nodename;
    }

    /**
     * 设置流程名称
     *
     * @param nodename 流程名称
     */
    public void setNodename(String nodename) {
        this.nodename = nodename;
    }

    /**
     * 获取下一节点
     *
     * @return nextnode - 下一节点
     */
    public Integer getNextnode() {
        return nextnode;
    }

    /**
     * 设置下一节点
     *
     * @param nextnode 下一节点
     */
    public void setNextnode(Integer nextnode) {
        this.nextnode = nextnode;
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