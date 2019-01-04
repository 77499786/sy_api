package com.forest.cl.model;

import java.util.ArrayList;
import java.util.List;

public class ClResultsPage implements java.io.Serializable {
    /** 残留检验结果*/
    private List<ClResult> clResultList = new ArrayList<ClResult>();

    private ClWeituo clWeituo = new ClWeituo();

    private ClFlowdata clFlowdata=new ClFlowdata();

    public List<ClResult> getClResultList() {
        return clResultList;
    }

    public void setClResultList(List<ClResult> clResultList) {
        this.clResultList = clResultList;
    }

    public ClWeituo getClWeituo() {
        return clWeituo;
    }

    public void setClWeituo(ClWeituo clWeituo) {
        this.clWeituo = clWeituo;
    }

    public ClFlowdata getClFlowdata() {
        return clFlowdata;
    }

    public void setClFlowdata(ClFlowdata clFlowdata) {
        this.clFlowdata = clFlowdata;
    }
}