package com.forest.crm.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "crm_goods")
public class CrmGoods {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 商品名称
     */
    private String spmc;

    /**
     * 商品编码
     */
    private String spbm;

    /**
     * 备注
     */
    private String remark;

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
     * 删除标志
     */
    private Integer isdeleted;

    private Integer cbj;

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
     * 获取商品名称
     *
     * @return spmc - 商品名称
     */
    public String getSpmc() {
        return spmc;
    }

    /**
     * 设置商品名称
     *
     * @param spmc 商品名称
     */
    public void setSpmc(String spmc) {
        this.spmc = spmc;
    }

    /**
     * 获取商品编码
     *
     * @return spbm - 商品编码
     */
    public String getSpbm() {
        return spbm;
    }

    /**
     * 设置商品编码
     *
     * @param spbm 商品编码
     */
    public void setSpbm(String spbm) {
        this.spbm = spbm;
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
     * 获取删除标志
     *
     * @return isdeleted - 删除标志
     */
    public Integer getIsdeleted() {
        return isdeleted;
    }

    /**
     * 设置删除标志
     *
     * @param isdeleted 删除标志
     */
    public void setIsdeleted(Integer isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Integer getCbj() {
        return cbj;
    }

    public void setCbj(Integer cbj) {
        this.cbj = cbj;
    }
}