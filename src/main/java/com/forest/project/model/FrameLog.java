package com.forest.project.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Table(name = "frame_log")
public class FrameLog {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * IP地址
     */
    @Column(name = "ipaddress")
    private String ipaddress;

    /**
     * 操作人员
     */
    private String operator;
    /**
     * 操作时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm")
    private Date operatetime;

    /**
     * 响应时间
     */
    private Long spendtime;
    /**
     * 请求Api
     */
    private String requesturl;

    private String remark;

    @Transient
    private String keyword;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOperatetime() {
        return operatetime;
    }

    public void setOperatetime(Date operatetime) {
        this.operatetime = operatetime;
    }

    public String getRequesturl() {
        return requesturl;
    }

    public void setRequesturl(String requesturl) {
        this.requesturl = requesturl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getSpendtime() {
        return spendtime;
    }

    public void setSpendtime(Long spendtime) {
        this.spendtime = spendtime;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}