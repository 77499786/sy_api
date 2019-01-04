package com.forest.utils;

import com.alibaba.fastjson.annotation.JSONField;

public class FlowNodeData {
	/** 检验编号 */
	private String jybh;
	/**序号*/
	private Integer no;
	/** 节点编号 */
	private Integer node;
	/**流程节点名称*/
	private String name;
	/**下一节点 */
	private Integer nextnd;
	/** 待办人 */
	private String nextuser;
	/** 处理人 */
	private String done;
	/** 处理时间 */
	private String doTime;
	/** 处理结果 */
	private String result;
	/**流程节点名称*/
	private String abbr;

	public String getJybh() {
		return jybh;
	}
	public void setJybh(String jybh) {
		this.jybh = jybh;
	}
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public Integer getNode() {
		return node;
	}
	public void setNode(Integer node) {
		this.node = node;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNextnd() {
		return nextnd;
	}
	public void setNextnd(Integer nextnd) {
		this.nextnd = nextnd;
	}
	public String getNextuser() {
		return nextuser;
	}
	public void setNextuser(String nextuser) {
		this.nextuser = nextuser;
	}
	public String getDone() {
		return done;
	}
	public void setDone(String done) {
		this.done = done;
	}
	public String getDoTime() {
		return doTime;
	}
	public void setDoTime(String doTime) {
		this.doTime = doTime;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getAbbr() {
		return abbr;
	}
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
}
