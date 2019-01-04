package com.forest.utils;

import com.google.common.base.Strings;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

@Component
public class ScsyReportJasperUtil extends ScsyReportUtil{
	private static String getJasperTemplatePath(){
		return String.format("%s%s",getSystemRootPath(), getTemplatePath());
	}
	/**
	 * 生成残留报告pdf文件，返回文件全路径名称
	 * @param filename 文件简称
	 * @param infos	委托信息
	 * @param results		检验结果信息
	 * @return	文件全路径名称
	 */
	public static String exportRemainReportPdf(String filename, Map<String, Object> infos,List<?> results) {
		 	Map<String, Object> infor = new HashMap<String, Object>();
		 	Map<String, Object> title = new HashMap<String, Object>();
		 	for(Map.Entry<String, Object>  m:  infos.entrySet()){
		 		infor.put(m.getKey(), m.getValue());
		 		title.put(m.getKey(), m.getValue());
		 	}
		 	String  markicon_path = String.format("%s%s",getJasperTemplatePath(),"mark.jpg");
			infor.put("图片资源位置", markicon_path );
			title.put("图片资源位置", markicon_path );
		 	String filepath = String.format("%s%s.pdf", getRealReportPath(), filename);
			try {
				// 生成首页报表
				String sourceFileName_first = String.format("%s%s",getJasperTemplatePath(),"remain-page.jasper");
				String sourceFileName_second = String.format("%s%s",getJasperTemplatePath(), "remain-detail.jasper");
				JasperPrint jasperPrintFirst = JasperFillManager.fillReport(sourceFileName_first ,
						infor, new JRBeanCollectionDataSource(results));
				JasperPrint jasperPrintDetail = JasperFillManager.fillReport(sourceFileName_second ,
						title,new JRBeanCollectionDataSource(results));
				// 报表分组合并输出一个pdf文件
				List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
				jasperPrintList.add(jasperPrintFirst);
				jasperPrintList.add(jasperPrintDetail);
				
				JRPdfExporter exporter = new JRPdfExporter();
				exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(filepath));
				SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
				configuration.setCreatingBatchModeBookmarks(true);
				exporter.setConfiguration(configuration);
				exporter.exportReport();
				return filepath.replace(getSystemRootPath(), "");
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
	}

	/**
	 * 生成兽药检验报告（假兽药）pdf文件，返回文件全路径名称
	 * @param filename 文件简称
	 * @param infos	委托信息
	 * @param results		检验结果信息
	 * @return	文件全路径名称
	 */
	@SuppressWarnings("rawtypes")
	public static String exportReportPdf(String filename, Map<String, Object> infos,List<?> results) {
		 	Map<String, Object> infor = new HashMap<String, Object>();
		 	Map<String, Object> title = new HashMap<String, Object>();
		 	for(Map.Entry<String, Object>  m:  infos.entrySet()){
		 		infor.put(m.getKey(), m.getValue());
		 		title.put(m.getKey(), m.getValue());
		 	}
		 	String  markicon_path = String.format("%s%s", getJasperTemplatePath(), "mark.jpg");
			infor.put("图片资源位置", markicon_path );
			title.put("图片资源位置", markicon_path );
		 	String filepath = String.format("%s%s.pdf", getRealReportPath(), filename);
		 	
		 	List<Map> beanCollection_1 = new ArrayList<Map>();
		 	beanCollection_1.add(infor);
		 	JRBeanCollectionDataSource beanCollectionDataSource_1 = new JRBeanCollectionDataSource(beanCollection_1);
		 	List<Map> beanCollection_2 = new ArrayList<Map>();
		 	beanCollection_2.add(title);
		 	JRBeanCollectionDataSource beanCollectionDataSource_2= new JRBeanCollectionDataSource(beanCollection_2);
		 	// 根据检验编号识别报告类别
//		 	String jybh = infos.get("检验编号");		 	
		 	String report_page_1 =String.format("%s%s",getJasperTemplatePath(), "jybg-main.jasper");
		 	String report_page_2 = String.format("%s%s",getJasperTemplatePath(), "jybg-base.jasper");
		 	String report_page_3 = String.format("%s%s",getJasperTemplatePath(), "jybg-detail.jasper");
		 	if("是".equals(infos.get("是否假兽药"))){
		 		report_page_1 = String.format("%s%s",getJasperTemplatePath(), "jy-jsy-main.jasper");
		 		report_page_2 = String.format("%s%s",getJasperTemplatePath(), "jy-jsy-detail.jasper");
		 	}
		 		
			try {
				// 生成首页报表
				JasperPrint jasperPrintFirst = JasperFillManager.fillReport(report_page_1 ,  new HashMap<String, Object>(), beanCollectionDataSource_1);
				JasperPrint jasperPrintDetail = JasperFillManager.fillReport(report_page_2 ,new HashMap<String, Object>(),beanCollectionDataSource_2);
				// 报表分组合并输出一个pdf文件
				List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
				jasperPrintList.add(jasperPrintFirst);
				jasperPrintList.add(jasperPrintDetail);
				// 检验报告打印第三页(假兽药不需要)
				if("否".equals(infos.get("是否假兽药")) ){
					JasperPrint jasperPrintThree = JasperFillManager.fillReport(report_page_3 ,  new HashMap<String, Object>(), new JRBeanCollectionDataSource(results));
					jasperPrintList.add(jasperPrintThree);
				}
				
				JRPdfExporter exporter = new JRPdfExporter();
				exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(filepath));
				SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
				configuration.setCreatingBatchModeBookmarks(true);
				exporter.setConfiguration(configuration);
				exporter.exportReport();
				return filepath.replace(getSystemRootPath(), "");
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
	}

	/**
	 * 生成合同pdf文件，返回文件全路径名称
	 * @param filename 文件简称
	 * @param infor	委托信息
	 * @return	文件全路径名称(相对路径)
	 */
	public static String exportContractPdf(String filename, Map<String, Object> infor) {
		 	String filepath = String.format("%sapply/%s_ht.pdf", getRealReportPath(), filename);
			String sourceFileName_first = String.format("%s%s",getJasperTemplatePath(),"wtjyht.jasper");
			return createPdf(filepath, sourceFileName_first, infor);
	}

	/**
	 * 生成抽检批次清单文件，返回文件全路径名称
	 * @param filename 文件简称
	 * @param datas	综合数据
	 * @return	文件全路径名称(相对路径)
	 */
	public static String createChoujianPiciPdf(String filename, Map<String, Object> datas) {
		 	String filepath = String.format("%sapply/%s_list.pdf", getRealReportPath(), filename);
			String sourceFileName = String.format("%s%s",getJasperTemplatePath(),"cjpc.jasper");
			return createPdf(filepath, sourceFileName, datas);
	}

	/**
	 * 生成送检批次清单文件，返回文件全路径名称
	 * @param filename 文件简称
	 * @param datas	综合数据
	 * @return	文件全路径名称(相对路径)
	 */
	public static String createYangpinQingdanPdf(String filename, Map<String, Object> datas) {
		String filepath = String.format("%sapply/%s_list.pdf", getRealReportPath(), filename);
		String sourceFileName = String.format("%s%s",getJasperTemplatePath(), "ypqd.jasper");
		return createPdf(filepath, sourceFileName, datas);
	}

	/**
	 * 生成抽检单文件(产品确认通知书)，返回文件全路径名称
	 * @param filename 文件简称
	 * @param datas	综合数据
	 * @return	文件全路径名称(相对路径)
	 */
	public static String createChoujiandanPdf(String filename, Map<String, Object> datas) {
		 	String filepath = String.format("%sapply/%s.pdf", getRealReportPath(), filename);
			String sourceFileName = String.format("%s%s",getJasperTemplatePath(),"cpqrtzs.jasper");
			return createPdf(filepath, sourceFileName, datas);
	}

	/**
	 * 生成pdf文件
	 * @param pdfFile 待生成的文件
	 * @param sourceFileName 报表模板文件
	 * @param datas	报表数据
	 * @return 文件全路径名称(相对路径)
	 */
	private static String createPdf(String pdfFile, String sourceFileName, Map<String, Object> datas){
		try {
			// 生成首页报表
		 	@SuppressWarnings("rawtypes")
			List<Map> beanCollection = new ArrayList<Map>();
		 	beanCollection.add(datas);
		 	JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(beanCollection);

			JasperPrint jasperPrintFirst = JasperFillManager.fillReport(sourceFileName ,  new HashMap<String, Object>(), beanCollectionDataSource);
			// 报表分组合并输出一个pdf文件
			List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
			jasperPrintList.add(jasperPrintFirst);
			
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfFile));
			SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
			configuration.setCreatingBatchModeBookmarks(true);
			exporter.setConfiguration(configuration);
			exporter.exportReport();
			return pdfFile.replace(getSystemRootPath(), "");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) {
		// Auto-generated method stub
		Map<String, String> infors = new HashMap<String, String>();
		infors.put("报告日期", DateUtils.formatTime());
		infors.put("样品编号", "ABC123");
		infors.put("送样单编号", "S0001");
		infors.put("样品名称", "猪肉大腿肉");
		infors.put("检验编号", "B160007");
		infors.put("样品量", "500克");
		infors.put("包封情况", "完好");
		infors.put("保存情况", "不错");
		infors.put("运输情况", "良好");
		infors.put("检验依据", "2015标准");
		// infors.put("取样方式",e.getQyfs());
		// infors.put("抽样场所",e.getCjcs());
		infors.put("抽样日期", DateUtils.formatTime());
		infors.put("生产单位", "药厂");
		infors.put("送样单位", "药监局");
		infors.put("收检日期", DateUtils.formatTime());
		infors.put("检验项目", "残留");
		List<List<String>> results = new ArrayList<List<String>>();
		String flname = String.format("/report/test1.docx", getRealReportPath());
//		List<String> filenamelist = new ArrayList<String>();
		
		long start = System.currentTimeMillis();
////		ScsyReportUtil.setIsdebug(true);
////		ScsyReportUtil.editRemainReport(flname, infors, results);
////		System.out.println("生成报表文件1耗时：" + (System.currentTimeMillis() - start) + "毫秒。");
////		start = System.currentTimeMillis();
////		ScsyReportUtil.file2pdf(flname);
////		System.out.println("生成报告pdf耗时：" + (System.currentTimeMillis() - start) + "毫秒。");
////		filenamelist.add(flname);
////		start = System.currentTimeMillis();
//		
////		flname = String.format("%s/test2.docx", getRealReportPath());
////		results.add(Arrays.asList("A", "1", "2", "3", "4"));
////		ScsyReportUtil.editRemainReport(flname, infors, results);
////		System.out.println("生成报表文件2耗时：" + (System.currentTimeMillis() - start) + "毫秒。");
////		start = System.currentTimeMillis();
////		ScsyReportUtil.file2pdf(flname);
////		System.out.println("生成报告pdf耗时：" + (System.currentTimeMillis() - start) + "毫秒。");
////		filenamelist.add(flname);
////		start = System.currentTimeMillis();
//
//		flname = String.format("%s/test3.docx", getRealReportPath());
//		results.add(Arrays.asList("B", "2", "3", "4", "5"));
////		results.add(Arrays.asList("c", "3", "4", "1", "2"));
////		results.add(Arrays.asList("D", "2", "3", "4", "5"));
////		results.add(Arrays.asList("E", "2", "3", "4", "5"));
////		results.add(Arrays.asList("BF", "2", "3", "4", "5"));
//		ScsyReportUtil.exportRemainReport(flname, infors, results);
//		System.out.println("生成报表文件耗时：" + (System.currentTimeMillis() - start) + "毫秒。");
//		start = System.currentTimeMillis();
//		ScsyReportUtil.file2pdf(flname);
//		System.out.println("生成PDF耗时：" + (System.currentTimeMillis() - start) + "毫秒。");
//		filenamelist.add(flname);
////		start = System.currentTimeMillis();
////		ScsyReportUtil.mergePdf(filenamelist);
//		exportRemainReport(flname, infors, results);
		System.out.println("合并PDF耗时：" + (System.currentTimeMillis() - start) + "毫秒。");
	}

}
