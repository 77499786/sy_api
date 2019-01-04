package com.forest.sy.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.base.Strings;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sy_weituo")
public class SyWeituo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 检验年度
     */
    private String jynd;

    /**
     * 样品编号
     */
    private String ypbh;

    /**
     * 申请批号
     */
    @Transient
    private String sqph;

    /**
     * 检品编号
     */
    private String jybh;

    /**
     * 商品名称
     */
    private String ypmc;

    /**
     * 商品名称
     */
    private String spmc;

    /**
     * 检验项目
     */
    private String jyxm;

    /**
     * 检验项目名称
     */
    @Transient
    private String jyxmmc;

    /**
     * 检验依据
     */
    private String jyyj;

    /**
     * 检验目的
     */
    private String jymd;

    /**
     * 检验目的名称
     */
    @Transient
    private String jymdmc;
    /**
     * 检验部门
     */
    private String jybm;
    /**
     * 检验部门名称
     */
    @Transient
    private String jybmmc;
    /**
     * 药品类型
     */
    private String yplx;
    /**
     * 药品类型名称
     */
    @Transient
    private String yplxmc;
    /**
     * 剂型
     */
    private String jx;
    /**
     * 剂型名称
     */
    @Transient
    private String jxmc;
    /**
     * 包装
     */
    private String bz;
    /**
     * 包装名称
     */
    @Transient
    private String bzmc;

    /**
     * 规格
     */
    private String gg;

    /**
     * 批准文号
     */
    private String pzwh;

    /**
     * 批号
     */
    private String ph;

    /**
     * 有效期
     */
    @JSONField(format="yyyy-MM-dd")
    private Date yxq;

    /**
     * 代表量
     */
    private String dbl;

    /**
     * 检品数量
     */
    private String jpsl;

    /**
     * 抽检日期
     */
    @JSONField(format="yyyy-MM-dd")
    private Date cjrq;

    /**
     * 生产单位名称
     */
    private String scdw;

    /**
     * 生产单位地址
     */
    private String scdwdz;

    /**
     * 生产单位邮编
     */
    private String scdwyb;

    /**
     * 生产单位联系人
     */
    private String scdwlxr;

    /**
     * 生产单位电话
     */
    private String scdwdh;

    /**
     * 是否加急
     */
    private Integer sfjj;

    /**
     * 同意分包
     */
    private Integer tyfb;

    /**
     * 取样方式
     */
    private String qyfs;
    /**
     * 取样方式名称
     */
    @Transient
    private String qyfsmc;

    /**
     * 抽检类型
     */
    private String cjlx;
    /**
     * 抽检类型名称
     */
    @Transient
    private String cjlxmc;

    /**
     * 抽检依据
     */
    private String cjyj;
    /**
     * 抽检依据名称
     */
    @Transient
    private String cjyjmc;

    /**
     * 抽检环节
     */
    private String cjhj;
    /**
     * 抽检环节名称
     */
    @Transient
    private String cjhjmc;

    private String cjdw;

    private String sydw;

    /**
     * 委托单位
     */
    private String wtdw;

    /**
     * 委托单位地址
     */
    private String wtdwdz;

    /**
     * 委托单位邮编
     */
    private String wtdwyb;

    /**
     * 委托单位电话
     */
    private String wtdwdh;

    /**
     * 委托单位传真
     */
    private String wtdwcz;

    /**
     * 委托单位联系人
     */
    private String wtdwlxr;

    /**
     * 委托人
     */
    private String wtr;

    /**
     * 委托日期
     */
    @JSONField(format="yyyy-MM-dd")
    private Date wtrq;

    /**
     * 备注
     */
    private String remark;

    /**
     * 收检日期
     */
    @JSONField(format="yyyy-MM-dd")
    private Date sjrq;

    /**
     * 申请类型。1首次申请，2换发申请
     */
    private Integer sqlx;
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
     * 数据来源1:导入,0:录入
     */
    private Integer sjly;
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
     * 获取检验年度
     *
     * @return jynd - 检验年度
     */
    public String getJynd() {
        return jynd;
    }

    /**
     * 设置检验年度
     *
     * @param jynd 检验年度
     */
    public void setJynd(String jynd) {
        this.jynd = jynd;
    }

    /**
     * 获取样品编号
     *
     * @return ypbh - 样品编号
     */
    public String getYpbh() {
        return ypbh;
    }

    /**
     * 设置样品编号
     *
     * @param ypbh 样品编号
     */
    public void setYpbh(String ypbh) {
        this.ypbh = ypbh;
    }

    /**
     * 获取检品编号
     *
     * @return jybh - 检品编号
     */
    public String getJybh() {
        return jybh;
    }

    /**
     * 设置检品编号
     *
     * @param jybh 检品编号
     */
    public void setJybh(String jybh) {
        this.jybh = jybh;
    }

    /**
     * 获取商品名称
     *
     * @return ypmc - 商品名称
     */
    public String getYpmc() {
        return ypmc;
    }

    /**
     * 设置商品名称
     *
     * @param ypmc 商品名称
     */
    public void setYpmc(String ypmc) {
        this.ypmc = ypmc;
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
     * 获取检验项目
     *
     * @return jyxm - 检验项目
     */
    public String getJyxm() {
        return jyxm;
    }

    /**
     * 设置检验项目
     *
     * @param jyxm 检验项目
     */
    public void setJyxm(String jyxm) {
        this.jyxm = jyxm;
    }

    /**
     * 获取检验依据
     *
     * @return jyyj - 检验依据
     */
    public String getJyyj() {
        return jyyj;
    }

    /**
     * 设置检验依据
     *
     * @param jyyj 检验依据
     */
    public void setJyyj(String jyyj) {
        this.jyyj = jyyj;
    }

    /**
     * 获取检验目的
     *
     * @return jymd - 检验目的
     */
    public String getJymd() {
        return jymd;
    }

    /**
     * 设置检验目的
     *
     * @param jymd 检验目的
     */
    public void setJymd(String jymd) {
        this.jymd = jymd;
    }

    /**
     * 获取检验部门
     *
     * @return jybm - 检验部门
     */
    public String getJybm() {
        return jybm;
    }

    /**
     * 设置检验部门
     *
     * @param jybm 检验部门
     */
    public void setJybm(String jybm) {
        this.jybm = jybm;
    }

    /**
     * 获取药品类型
     *
     * @return yplx - 药品类型
     */
    public String getYplx() {
        return yplx;
    }

    /**
     * 设置药品类型
     *
     * @param yplx 药品类型
     */
    public void setYplx(String yplx) {
        this.yplx = yplx;
    }

    /**
     * 获取剂型
     *
     * @return jx - 剂型
     */
    public String getJx() {
        return jx;
    }

    /**
     * 设置剂型
     *
     * @param jx 剂型
     */
    public void setJx(String jx) {
        this.jx = jx;
    }

    /**
     * 获取包装
     *
     * @return bz - 包装
     */
    public String getBz() {
        return bz;
    }

    /**
     * 设置包装
     *
     * @param bz 包装
     */
    public void setBz(String bz) {
        this.bz = bz;
    }

    /**
     * 获取规格
     *
     * @return gg - 规格
     */
    public String getGg() {
        return gg;
    }

    /**
     * 设置规格
     *
     * @param gg 规格
     */
    public void setGg(String gg) {
        this.gg = gg;
    }

    /**
     * 获取批准文号
     *
     * @return pzwh - 批准文号
     */
    public String getPzwh() {
        return pzwh;
    }

    /**
     * 设置批准文号
     *
     * @param pzwh 批准文号
     */
    public void setPzwh(String pzwh) {
        this.pzwh = pzwh;
    }

    /**
     * 获取批号
     *
     * @return ph - 批号
     */
    public String getPh() {
        return ph;
    }

    /**
     * 设置批号
     *
     * @param ph 批号
     */
    public void setPh(String ph) {
        this.ph = ph;
    }

    /**
     * 获取有效期
     *
     * @return yxq - 有效期
     */
    public Date getYxq() {
        return yxq;
    }

    /**
     * 设置有效期
     *
     * @param yxq 有效期
     */
    public void setYxq(Date yxq) {
        this.yxq = yxq;
    }

    /**
     * 获取代表量
     *
     * @return dbl - 代表量
     */
    public String getDbl() {
        return dbl;
    }

    /**
     * 设置代表量
     *
     * @param dbl 代表量
     */
    public void setDbl(String dbl) {
        this.dbl = dbl;
    }

    /**
     * 获取检品数量
     *
     * @return jpsl - 检品数量
     */
    public String getJpsl() {
        return jpsl;
    }

    /**
     * 设置检品数量
     *
     * @param jpsl 检品数量
     */
    public void setJpsl(String jpsl) {
        this.jpsl = jpsl;
    }

    /**
     * 获取抽检日期
     *
     * @return cjrq - 抽检日期
     */
    public Date getCjrq() {
        return cjrq;
    }

    /**
     * 设置抽检日期
     *
     * @param cjrq 抽检日期
     */
    public void setCjrq(Date cjrq) {
        this.cjrq = cjrq;
    }

    /**
     * 获取生产单位名称
     *
     * @return scdw - 生产单位名称
     */
    public String getScdw() {
        return scdw;
    }

    /**
     * 设置生产单位名称
     *
     * @param scdw 生产单位名称
     */
    public void setScdw(String scdw) {
        this.scdw = scdw;
    }

    /**
     * 获取生产单位地址
     *
     * @return scdwdz - 生产单位地址
     */
    public String getScdwdz() {
        return scdwdz;
    }

    /**
     * 设置生产单位地址
     *
     * @param scdwdz 生产单位地址
     */
    public void setScdwdz(String scdwdz) {
        this.scdwdz = scdwdz;
    }

    /**
     * 获取生产单位邮编
     *
     * @return scdwyb - 生产单位邮编
     */
    public String getScdwyb() {
        return scdwyb;
    }

    /**
     * 设置生产单位邮编
     *
     * @param scdwyb 生产单位邮编
     */
    public void setScdwyb(String scdwyb) {
        this.scdwyb = scdwyb;
    }

    /**
     * 获取生产单位联系人
     *
     * @return scdwlxr - 生产单位联系人
     */
    public String getScdwlxr() {
        return scdwlxr;
    }

    /**
     * 设置生产单位联系人
     *
     * @param scdwlxr 生产单位联系人
     */
    public void setScdwlxr(String scdwlxr) {
        this.scdwlxr = scdwlxr;
    }

    /**
     * 获取生产单位电话
     *
     * @return scdwdh - 生产单位电话
     */
    public String getScdwdh() {
        return scdwdh;
    }

    /**
     * 设置生产单位电话
     *
     * @param scdwdh 生产单位电话
     */
    public void setScdwdh(String scdwdh) {
        this.scdwdh = scdwdh;
    }

    /**
     * 获取是否加急
     *
     * @return sfjj - 是否加急
     */
    public Integer getSfjj() {
        return sfjj;
    }

    /**
     * 设置是否加急
     *
     * @param sfjj 是否加急
     */
    public void setSfjj(Integer sfjj) {
        this.sfjj = sfjj;
    }

    /**
     * 获取同意分包
     *
     * @return tyfb - 同意分包
     */
    public Integer getTyfb() {
        return tyfb;
    }

    /**
     * 设置同意分包
     *
     * @param tyfb 同意分包
     */
    public void setTyfb(Integer tyfb) {
        this.tyfb = tyfb;
    }

    /**
     * 获取取样方式
     *
     * @return qyfs - 取样方式
     */
    public String getQyfs() {
        return qyfs;
    }

    /**
     * 设置取样方式
     *
     * @param qyfs 取样方式
     */
    public void setQyfs(String qyfs) {
        this.qyfs = qyfs;
    }

    /**
     * 获取抽检类型
     *
     * @return cjlx - 抽检类型
     */
    public String getCjlx() {
        return cjlx;
    }

    /**
     * 设置抽检类型
     *
     * @param cjlx 抽检类型
     */
    public void setCjlx(String cjlx) {
        this.cjlx = cjlx;
    }

    /**
     * 获取抽检依据
     *
     * @return cjyj - 抽检依据
     */
    public String getCjyj() {
        return cjyj;
    }

    /**
     * 设置抽检依据
     *
     * @param cjyj 抽检依据
     */
    public void setCjyj(String cjyj) {
        this.cjyj = cjyj;
    }

    /**
     * 获取抽检环节
     *
     * @return cjhj - 抽检环节
     */
    public String getCjhj() {
        return cjhj;
    }

    /**
     * 设置抽检环节
     *
     * @param cjhj 抽检环节
     */
    public void setCjhj(String cjhj) {
        this.cjhj = cjhj;
    }

    /**
     * @return cjdw
     */
    public String getCjdw() {
        return cjdw;
    }

    /**
     * @param cjdw
     */
    public void setCjdw(String cjdw) {
        this.cjdw = cjdw;
    }

    /**
     * @return sydw
     */
    public String getSydw() {
        return sydw;
    }

    /**
     * @param sydw
     */
    public void setSydw(String sydw) {
        this.sydw = sydw;
    }

    /**
     * 获取委托单位
     *
     * @return wtdw - 委托单位
     */
    public String getWtdw() {
        return wtdw;
    }

    /**
     * 设置委托单位
     *
     * @param wtdw 委托单位
     */
    public void setWtdw(String wtdw) {
        this.wtdw = wtdw;
    }

    /**
     * 获取委托单位地址
     *
     * @return wtdwdz - 委托单位地址
     */
    public String getWtdwdz() {
        return wtdwdz;
    }

    /**
     * 设置委托单位地址
     *
     * @param wtdwdz 委托单位地址
     */
    public void setWtdwdz(String wtdwdz) {
        this.wtdwdz = wtdwdz;
    }

    /**
     * 获取委托单位邮编
     *
     * @return wtdwyb - 委托单位邮编
     */
    public String getWtdwyb() {
        return wtdwyb;
    }

    /**
     * 设置委托单位邮编
     *
     * @param wtdwyb 委托单位邮编
     */
    public void setWtdwyb(String wtdwyb) {
        this.wtdwyb = wtdwyb;
    }

    /**
     * 获取委托单位电话
     *
     * @return wtdwdh - 委托单位电话
     */
    public String getWtdwdh() {
        return wtdwdh;
    }

    /**
     * 设置委托单位电话
     *
     * @param wtdwdh 委托单位电话
     */
    public void setWtdwdh(String wtdwdh) {
        this.wtdwdh = wtdwdh;
    }

    /**
     * 获取委托单位传真
     *
     * @return wtdwcz - 委托单位传真
     */
    public String getWtdwcz() {
        return wtdwcz;
    }

    /**
     * 设置委托单位传真
     *
     * @param wtdwcz 委托单位传真
     */
    public void setWtdwcz(String wtdwcz) {
        this.wtdwcz = wtdwcz;
    }

    /**
     * 获取委托单位联系人
     *
     * @return wtdwlxr - 委托单位联系人
     */
    public String getWtdwlxr() {
        return wtdwlxr;
    }

    /**
     * 设置委托单位联系人
     *
     * @param wtdwlxr 委托单位联系人
     */
    public void setWtdwlxr(String wtdwlxr) {
        this.wtdwlxr = wtdwlxr;
    }

    /**
     * 获取委托人
     *
     * @return wtr - 委托人
     */
    public String getWtr() {
        return wtr;
    }

    /**
     * 设置委托人
     *
     * @param wtr 委托人
     */
    public void setWtr(String wtr) {
        this.wtr = wtr;
    }

    /**
     * 获取委托日期
     *
     * @return wtrq - 委托日期
     */
    public Date getWtrq() {
        return wtrq;
    }

    /**
     * 设置委托日期
     *
     * @param wtrq 委托日期
     */
    public void setWtrq(Date wtrq) {
        this.wtrq = wtrq;
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
     * 获取收检日期
     *
     * @return sjrq - 收检日期
     */
    public Date getSjrq() {
        return sjrq;
    }

    /**
     * 设置收检日期
     *
     * @param sjrq 收检日期
     */
    public void setSjrq(Date sjrq) {
        this.sjrq = sjrq;
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

    public String getJyxmmc() {
        return jyxmmc;
    }

    public void setJyxmmc(String jyxmmc) {
        this.jyxmmc = jyxmmc;
    }

    public String getJymdmc() {
        return jymdmc;
    }

    public void setJymdmc(String jymdmc) {
        this.jymdmc = jymdmc;
    }

    public String getJybmmc() {
        return jybmmc;
    }

    public void setJybmmc(String jybmmc) {
        this.jybmmc = jybmmc;
    }

    public String getYplxmc() {
        return yplxmc;
    }

    public void setYplxmc(String yplxmc) {
        this.yplxmc = yplxmc;
    }

    public String getJxmc() {
        return jxmc;
    }

    public void setJxmc(String jxmc) {
        this.jxmc = jxmc;
    }

    public String getBzmc() {
        return bzmc;
    }

    public void setBzmc(String bzmc) {
        this.bzmc = bzmc;
    }

    public String getCjlxmc() {
        return cjlxmc;
    }

    public void setCjlxmc(String cjlxmc) {
        this.cjlxmc = cjlxmc;
    }

    public String getCjyjmc() {
        return cjyjmc;
    }

    public void setCjyjmc(String cjyjmc) {
        this.cjyjmc = cjyjmc;
    }

    public String getCjhjmc() {
        return cjhjmc;
    }

    public void setCjhjmc(String cjhjmc) {
        this.cjhjmc = cjhjmc;
    }

    public String getQyfsmc() {
        return qyfsmc;
    }

    public void setQyfsmc(String qyfsmc) {
        this.qyfsmc = qyfsmc;
    }

    public String getSqph() {
        return sqph;
    }

    public void setSqph(String sqph) {
        this.sqph = sqph;
    }

    public Integer getSqlx() {
        return sqlx;
    }

    public void setSqlx(Integer sqlx) {
        this.sqlx = sqlx;
    }

    public String getSqlxmc() {
        return (sqlx != null && sqlx == 2) ? "换发申请" : "首次申请";
    }

    public Integer getSjly() {
        return sjly;
    }

    public void setSjly(Integer sjly) {
        this.sjly = sjly;
    }
}