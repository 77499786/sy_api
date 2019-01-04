package com.forest.home.model;

public class HomeIOInfor {
    private String ymd;
    private String lx;
    private String money;
    private String remark;

    public HomeIOInfor(String ymd, String lx, String money, String remark) {
        this.ymd = ymd;
        this.lx = lx;
        this.money = money;
        this.remark = remark;
    }

    public HomeIOInfor() {
    }

    public String getYmd() {
        return ymd;
    }

    public void setYmd(String ymd) {
        this.ymd = ymd;
    }

    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "HomeIOInfor{" +
                "ymd='" + ymd + '\'' +
                ", lx='" + lx + '\'' +
                ", money='" + money + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
