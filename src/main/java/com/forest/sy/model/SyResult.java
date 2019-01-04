package com.forest.sy.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sy_result")
public class SyResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 检验编号
     */
    private String jybh;

    /**
     * 序号
     */
    private Integer sortno;

    /**
     * 检验总项
     */
    private String jyzx;

    /**
     * 检验子项
     */
    private String jyxx;

    /**
     * 标准规定
     */
    private String bzgd;

    /**
     * 检验结果1
     */
    private String jyjg1;

    /**
     * 检验结果2
     */
    private String jyjg2;

    /**
     * 检验结果3
     */
    private String jyjg3;

    /**
     * 项目结论
     */
    private String xmjl;

    /**
     * 创建人名称
     */
    private String creator;

    /**
     * 创建日期
     */
    @JSONField(format="yyyy-MM-dd HH:mm")
    private Date createtime;

    /**
     * 更新人名称
     */
    private String modifer;

    /**
     * 更新日期
     */
    @Column(name = "modifyTime")
    @JSONField(format="yyyy-MM-dd HH:mm")
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
     * 获取检验总项
     *
     * @return jyzx - 检验总项
     */
    public String getJyzx() {
        return jyzx;
    }

    /**
     * 设置检验总项
     *
     * @param jyzx 检验总项
     */
    public void setJyzx(String jyzx) {
        this.jyzx = jyzx;
    }

    /**
     * 获取检验子项
     *
     * @return jyxx - 检验子项
     */
    public String getJyxx() {
        return jyxx;
    }

    /**
     * 设置检验子项
     *
     * @param jyxx 检验子项
     */
    public void setJyxx(String jyxx) {
        this.jyxx = jyxx;
    }

    /**
     * 获取标准规定
     *
     * @return bzgd - 标准规定
     */
    public String getBzgd() {
        return bzgd;
    }

    /**
     * 设置标准规定
     *
     * @param bzgd 标准规定
     */
    public void setBzgd(String bzgd) {
        this.bzgd = bzgd;
    }

    /**
     * 获取检验结果1
     *
     * @return jyjg1 - 检验结果1
     */
    public String getJyjg1() {
        return jyjg1;
    }

    /**
     * 设置检验结果1
     *
     * @param jyjg1 检验结果1
     */
    public void setJyjg1(String jyjg1) {
        this.jyjg1 = jyjg1;
    }

    /**
     * 获取检验结果2
     *
     * @return jyjg2 - 检验结果2
     */
    public String getJyjg2() {
        return jyjg2;
    }

    /**
     * 设置检验结果2
     *
     * @param jyjg2 检验结果2
     */
    public void setJyjg2(String jyjg2) {
        this.jyjg2 = jyjg2;
    }

    /**
     * 获取检验结果3
     *
     * @return jyjg3 - 检验结果3
     */
    public String getJyjg3() {
        return jyjg3;
    }

    /**
     * 设置检验结果3
     *
     * @param jyjg3 检验结果3
     */
    public void setJyjg3(String jyjg3) {
        this.jyjg3 = jyjg3;
    }

    /**
     * 获取项目结论
     *
     * @return xmjl - 项目结论
     */
    public String getXmjl() {
        return xmjl;
    }

    /**
     * 设置项目结论
     *
     * @param xmjl 项目结论
     */
    public void setXmjl(String xmjl) {
        this.xmjl = xmjl;
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