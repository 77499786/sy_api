package com.forest.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.forest.cl.model.ClFlowdefine;
import com.forest.sy.model.SyFlowdefine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 兽药流程管理用工具类
 * 
 */
public class ScsyFlowUtil {
	public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"; 
	/**
	 * 根据流程记录数据读取流程处理详情
	 * 
	 * @param jybh
	 *            检验编号
	 * @param flowdata
	 *            流程记录数据
	 * @return
	 */
	public static List<FlowNodeData> readFlowData(String jybh, String flowdata) {
		List<FlowNodeData> logs =  JSONObject.parseArray(flowdata, FlowNodeData.class);
		return logs;
	}

	/**
	 * 生成流程处理记录的保存用字符串
	 * 
	 * @param logs
	 * @return
	 */
	public static String flowLogs(List<FlowNodeData> logs) {
		return JSON.toJSONString(logs);
	}

	/**
	 * 生成流程处理记录的保存用字符串（残留检验流程）
	 * 
	 * @param defines 残留检验流程定义数据
	 * @return
	 */
	public static String flowDefines(List<ClFlowdefine> defines) {
		List<FlowNodeData> logs = new ArrayList<FlowNodeData>();

		int index =1;
		for (ClFlowdefine flowDefine : defines) {
			FlowNodeData log = new FlowNodeData();
			log.setNo(index++);
			log.setNode(flowDefine.getIndexno());
			log.setName(flowDefine.getNodename());
			log.setNextnd(flowDefine.getNextnode());
			logs.add(log);
		}

		return flowLogs(logs);
	}

	/**
	 * 生成流程处理记录的保存用字符串（兽药检验流程）
	 *
	 * @param defines 兽药检验流程定义数据
	 * @return
	 */
	public static String syFlowDefines(List<SyFlowdefine> defines) {
		List<FlowNodeData> logs = new ArrayList<FlowNodeData>();

		int index =1;
		for (SyFlowdefine flowDefine : defines) {
			FlowNodeData log = new FlowNodeData();
			log.setNo(index++);
			log.setNode(flowDefine.getIndexno());
			log.setName(flowDefine.getNodename());
			log.setNextnd(flowDefine.getNextnode());
			logs.add(log);
		}

		return flowLogs(logs);
	}

/*
生成流程处理记录的保存用字符串（检验流程）
	public static String flowDefineString(List<Object> defines) {
		List<FlowNodeData> logs = new ArrayList<FlowNodeData>();

		int startnode = FlowDefine.START_INDEX;
		int indexno = 1;
		List<Integer> nodeLst = new ArrayList<Integer>();
		while(startnode!= FlowDefine.END_INDEX){
			FlowNodeData nodedata = editNodeData(indexno++, startnode, defines);
			if(nodedata!= null){
				logs.add(nodedata);
				startnode = nodedata.getNextnd();
				nodeLst.add(nodedata.getNode());				
			} else {
				startnode = FlowDefine.END_INDEX;
			}
		}
		// 分支数据追加 (登记）
		Iterator<Object> it = defines.iterator();
		while (it.hasNext()) {
			Object nd = it.next();
			if(!nodeLst.contains(nd.getIndexno())){
				FlowNodeData log = new FlowNodeData();
				log.setNo(indexno++);
				log.setNode(nd.getIndexno());
				log.setName(nd.getNodename());
				log.setNextnd(nd.getNextnode());
				logs.add(log);
			}
		}
		return flowLogs(logs);
	}

	private static FlowNodeData editNodeData(int index, Integer node,List<Object> defines){
		Iterator<Object> it = defines.iterator();
		while (it.hasNext()) {
			Object nd = it.next();
			if(nd.getIndexno() == node){
				FlowNodeData log = new FlowNodeData();
				log.setNo(index);
				log.setNode(nd.getIndexno());
				log.setName(nd.getNodename());
				log.setNextnd(nd.getNextnode());
				return log;
			}
		}
		return null;
	}
*/

	public static void main(String[] args){
		FlowNodeData e = new FlowNodeData();
		Date d = DateUtils.addDays(DateUtils.getDate(), -1);
		e.setDoTime(DateUtils.formatTime(d));
		e.setDone("hello");
		String json = JSON.toJSONString(e);
		System.out.println(json);
		
		FlowNodeData n = JSON.parseObject(json, FlowNodeData.class);
		System.out.println(n.getDoTime());
		
		List<FlowNodeData> lst = new ArrayList<FlowNodeData>();
		lst.add(e);
		json = JSONObject.toJSONString(lst);
		System.out.println(json);
		
		List<FlowNodeData> ls = JSONObject.parseArray(json, FlowNodeData.class);
		System.out.println(ls.get(0).getDoTime());
	}
}