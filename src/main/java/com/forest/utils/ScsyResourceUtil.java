package com.forest.utils;

import com.google.common.base.Strings;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目参数工具类
 * 
 */
public class ScsyResourceUtil {
	
	/**
	 * 残留检验委托的当前最大编号【缓存】
	 */
	public static Map<String, String> currentNoOfRemains = new HashMap<String, String>();

	/**
	 * 残留检验委托的当前最大编号【缓存】
	 */
	public static String currentNoOfRemain = new String();

	/**
	 * 兽药检验委托的当前最大编号【缓存】
	 */
	public static Map<String, String> currentNoOfDrugs = new HashMap<String, String>();  

	/**
	 * 当前年度保存的键
	 */
	public static String KEY_OF_CURRENTYEAR = "ywnd";

	/**
	 * 当前年度
	 */
	private static String currentYear;

	/**
	 * 是否只显示当前年度的数据 标志设置的键
	 */
	public static String KEY_OF_ONLY_SHOW_CURRENT_YEAR = "show_cur_year";  
	/**
	 * 是否只显示当前年度的数据
	 */
	public static boolean show_Current_year = true;

	/**
	 * 缓存各种类型的编号规则【缓存】
	 */
	public static Map<String, String> allTypeNumberFormats = new HashMap<String,String>();

	/**
	 * 数据字典库【缓存】
	 */
	private static Map<String, String> DICITIONARYS = new HashMap<String, String>();

	/**
	 * 检验部门【缓存】
	 */
	private static Map<String, String> ORGANIZATION = new HashMap<String, String>();

	/**
	 * 检验目的【缓存】
	 */
	private static Map<String, String> CHECK_TARGETS = new HashMap<String, String>();

	public static String pushForamts(String key, String value){
		return ScsyResourceUtil.allTypeNumberFormats.put(key,value);
	}

	public static String getCurrentYear() {
		return currentYear;
	}

	public static void setCurrentYear(String currentYear) {
		ScsyResourceUtil.currentYear = currentYear;
	}

	/**
	 * 追加编号规则最大值(兽药）
	 * @param key	标号类型id
	 * @param value	当前最大值
	 * @return
	 */
	public static String pushDrugsNo(String key, String value){
		return ScsyResourceUtil.currentNoOfDrugs.put(key,value);
	}

	/**
	 * 追加编号规则最大值(残留）
	 * @param key	标号类型id
	 * @param value	当前最大值
	 * @return
	 */
	public static String pushReaminNo(String key, String value){
		return ScsyResourceUtil.currentNoOfRemains.put(key,value);
	}

	public static String pushReaminNo(String value){
		return ScsyResourceUtil.currentNoOfRemain = value;
	}
	/**
	 * 根据残留检验项目自动生成新的检验编号
	 * @param typeid	检验项目
	 * @return 检验编号
	 */
	public static String getNewNumberofRemain(String typeid){
		String numFormat = "SC"; //allTypeNumberFormats.get(typeid);
		String currentNoOfRemain  = ScsyResourceUtil.currentNoOfRemain;  //currentNoOfRemains.get(typeid);
		String prifx = numFormat.concat(StringUtils.right(currentYear, 2));
		if(!Strings.isNullOrEmpty(currentNoOfRemain) && currentNoOfRemain.startsWith(prifx)){
			Integer number = Integer.valueOf(currentNoOfRemain.substring(prifx.length())); //Integer.valueOf(StringUtils.right(currentNoOfRemain ,4));
			number++;
			currentNoOfRemain = prifx.concat(StringUtils.leftPad(String.valueOf(number), 4, '0'));
			//StringUtils.removeEnd(currentNoOfRemain, StringUtils.right(currentNoOfRemain, 4)).concat(StringUtils.leftPad(String.valueOf(number), 4, '0'));
//			currentNoOfRemains.put(typeid, currentNoOfRemain);
			ScsyResourceUtil.pushReaminNo(currentNoOfRemain);
		} else {
			currentNoOfRemain = prifx.concat("0001");
			ScsyResourceUtil.pushReaminNo(currentNoOfRemain);
//			currentNoOfRemains.put(typeid, currentNoOfRemain);
//					String.format(numFormat, StringUtils.right(currentYear, 2),  "0001");
		}
		return currentNoOfRemain;
	}

	/**
	 * 根据兽药检验目的自动生成新的内部检验编号
	 * @param typeid	检验目的
	 * @return 检验编号
	 */
	public static String getNewNumberofDrugs(String typeid){
		String numFormat = allTypeNumberFormats.get(typeid);
		String currentNoOfDrugs  = ScsyResourceUtil.currentNoOfDrugs.get(typeid);
		String prifx = numFormat.concat(StringUtils.right(currentYear, 2));
		if(!Strings.isNullOrEmpty(currentNoOfDrugs) && currentNoOfDrugs.startsWith(prifx)){
			Integer number =Integer.valueOf(StringUtils.right(currentNoOfDrugs ,4));
			number++;
			currentNoOfDrugs = prifx.concat(StringUtils.leftPad(String.valueOf(number), 4, '0'));
			ScsyResourceUtil.currentNoOfDrugs.put(typeid, currentNoOfDrugs);
		} else {
			currentNoOfDrugs = prifx.concat("0001");
			ScsyResourceUtil.currentNoOfDrugs.put(typeid, currentNoOfDrugs);
		}
		return currentNoOfDrugs;
	}


	public static void setDicitionarys(Map<String, String> dicitionarys) {
		ScsyResourceUtil.DICITIONARYS = dicitionarys;
	}
	public static String getDicitionary(String key){
		if(Strings.isNullOrEmpty(key) || !ScsyResourceUtil.DICITIONARYS.containsKey(key)){
			return "";
		}
		return  ScsyResourceUtil.DICITIONARYS.get(key);
	}
	public static String pushDicitionary(String key, String value){
		return ScsyResourceUtil.DICITIONARYS.put(key,value);
	}

	public static String getOrganization(String key)  {
		if(Strings.isNullOrEmpty(key) || !ScsyResourceUtil.ORGANIZATION.containsKey(key)){
			return "";
		}
		return  ScsyResourceUtil.ORGANIZATION.get(key);
	}

	public static void setOrganization(Map<String, String> organization) {
		ScsyResourceUtil.ORGANIZATION = organization;
	}

	public static String getCheckTargets(String key) {
		if(Strings.isNullOrEmpty(key) || !ScsyResourceUtil.CHECK_TARGETS.containsKey(key)){
			return "";
		}
		return  ScsyResourceUtil.CHECK_TARGETS.get(key);
	}

	public static void setCheckTargets(Map<String, String> checkTargets) {
		ScsyResourceUtil.CHECK_TARGETS = checkTargets;
	}
}
