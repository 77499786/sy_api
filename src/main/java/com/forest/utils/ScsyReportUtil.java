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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

@Component
public class ScsyReportUtil {

	public static String PDF_SUFFIX =".pdf";
	/** Word和PDF打印目录 */
	private static String PATH_OF_REPORT;
	@Value("${scsy.reportpath}")
	public void setPathOfReport(String pathOfReport) {
		ScsyReportUtil.PATH_OF_REPORT = pathOfReport;
	}
	/** PDF归档目录 */
	private static String PATH_OF_REPORT_ARCHIVE = "temp/report/archive";
	@Value("${scsy.reportArchive}")
	public void setIsdebug(String pathOfReportArchive) {
		ScsyReportUtil.PATH_OF_REPORT_ARCHIVE = pathOfReportArchive;
	}

	private static String systemPath_of_template;
	private static boolean isdebug;
	@Value("${scsy.isdebug}")
	public void setIsdebug(Boolean flag) {
		ScsyReportUtil.isdebug = flag;
	}

	protected static String templatePath;
	@Value("${scsy.template}")
	public void setTemplatePath(String templatePath) {
		ScsyReportUtil.templatePath = templatePath;
	}

	public static String getTemplatePath() {
		return templatePath.endsWith("/") ? templatePath : templatePath.concat("/");
	}

	public static String SY_SUBDICTIONARY = "shouyao/";

	public static String CL_SUBDICTIONARY = "canliu/";

	public static void setIsdebug(boolean isdebug) {
		ScsyReportUtil.isdebug = isdebug;
	}

	/**
	 *
	 * @return
	 */
	public static String getSystemRootPath(){
		if(isdebug){
			systemPath_of_template = "C:/java/work/scsy/";
		} else {
			if(Strings.isNullOrEmpty(systemPath_of_template)){
				systemPath_of_template = JarToolUtil.getJarDir();
/*				System.out.println(JarToolUtil.getJarPath());
				System.out.println(JarToolUtil.getJarDir());
				System.out.println(JarToolUtil.getJarName());
				System.out.println(systemPath_of_template);*/
//				 HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//				 systemPath_of_template =request.getSession().getServletContext().getRealPath("/") ;
			}
		}
		return systemPath_of_template;
	}
	/**
	 * 获取word、PDF报告目录（绝对路径）
	 * @return
	 */
	public static String getRealReportPath() {
		return String.format("%s%s", getSystemRootPath(), getReportPath());
	}

	/**
	 * 获取word、PDF报告目录
	 * @return
	 */
	public static String getReportPath() {
		return PATH_OF_REPORT.endsWith("/") ? PATH_OF_REPORT : PATH_OF_REPORT.concat("/");
	}

	/**
	 * 获取归档PDF报告目录（获取绝对路径）
	 * @return
	 */
	public static String getRealArchiveReportPath() {
		return String.format("%s%s", getSystemRootPath(), getArchiveReportPath());
	}

	/**
	 * 获取归档PDF报告目录
	 * @return
	 */
	public static String getArchiveReportPath() {
		return PATH_OF_REPORT_ARCHIVE.endsWith("/") ? PATH_OF_REPORT_ARCHIVE : PATH_OF_REPORT_ARCHIVE.concat("/");
	}
//	/**
//	 * 更新残留检验报告模板
//	 */
//	public static XWPFDocument updateTemplate(){
//		getSystemRootPath();
//		try {
//			InputStream input = new FileInputStream(systemPath_of_template + PATH_OF_TEMPLATE);
//			remain_Template = new XWPFDocument(input);
//			input.close();
//			return remain_Template;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	public static String editRemainReport(String filename, Map<String, String> infos,
//			List<List<String>> results) {
//
//			XWPFDocument template = updateTemplate();
////			// 删除旧数据
////			File f = new File(filename);
////			if(f.exists()){
////				f.delete();
////			}
//			if(template == null) {
//				return "";
//			}
//			// 只能替换表格中的内容
//			Iterator<XWPFTable> itTable = template.getTablesIterator();
//			while (itTable.hasNext()) {
//				XWPFTable table = (XWPFTable) itTable.next();
//				int start = table.getNumberOfRows();
//				// 找到结果数据起始行
//				for (int i = 0; i < start; i++) {
//					XWPFTableRow row = table.getRow(i);
//					if (HEADER_OF_RESULTS_FIRST.equals(row.getCell(0).getText())) {
//						start = i + 1;
//						break;
//					}
//				}
//				if (start < table.getNumberOfRows()) {
//					int numblank = LIMIT_ROWS_COUNT - results.size();
//					if(results.size() <=1) {
//						numblank--;
//					}
//					// 追加空白行
//					int index = start + 1;
//					replaceInPara(table.getRow(index++).getTableCells().get(0).getParagraphs().get(0), BLANKTEXT);
//					int r = numblank;
//					XWPFTableRow rw = table.getRow(index);
//					while (r > 0) {
//						table.addRow(rw, index);
//						r--;
//					}
//
//					// 追加检验结果
//					if(results.size() == 1){
//						editResultRow(table.getRow(start), results.get(0));
//					} else if (results.size() > 1){
//						rw = table.getRow(start);
//						r = 0;
//						editResultRow(table.getRow(start), results.get(r++));
//						while (r < results.size()) {
//							table.addRow(rw, start + r);
//							editResultRow(table.getRow(start + r), results.get(r));
//							r++;
//						}
//						table.addRow(table.getRow(start), start + r);
//						table.removeRow(start);
//					}
//				}
//
//				// 输出数据
//				int rcount = table.getNumberOfRows();
//				for (int i = 0; i < rcount; i++) {
//					if (i + 1 == start) {
//						i += results.size();
//						continue;
//					}
//					XWPFTableRow row = table.getRow(i);
//					List<XWPFTableCell> cells = row.getTableCells();
//					// 检验基本信息
//					for (XWPFTableCell cell : cells) {
//						List<XWPFParagraph> paras = cell.getParagraphs();
//						for (XWPFParagraph para : paras) {
//							replaceInPara(para, infos);
//						}
//					}
//				}
//			}
//			return exportWordFile(template, filename);
//	}
//
//	/**
//	 * 根据模板编辑输出word结果文档
//	 *
//	 * @param filename word文档名称
//	 * @param infos 检验委托信息
//	 * @param results 检验结果数据
//	 */
//	public static String exportRemainReport(String filename, Map<String, String> infos,
//			List<List<String>> results) {
//		 	Map<String, Object> objInfors = new HashMap<String, Object>();
//		 	for(Map.Entry<String, String>  m:  infos.entrySet()){
//		 		objInfors.put(m.getKey(), m.getValue());
//		 	}
//			try {
//				String path =  String.format("%s%s", getSystemRootPath(), "export/template/remain.docx");
//				XWPFDocument template  = new XWPFDocument(POIXMLDocument.openPackage(path));
//				if(template == null) {
//					return "";
//				}
//				// 只能替换表格中的内容
//				Iterator<XWPFTable> itTable = template.getTablesIterator();
//				while (itTable.hasNext()) {
//					XWPFTable table = (XWPFTable) itTable.next();
//					int start = table.getNumberOfRows()-1;
//					// 找到结果数据起始行
//					while (start >= 0){
//						if(HEADER_OF_RESULTS_FIRST.equals(table.getRow(start).getCell(0).getText())){
//							start++;
//							break;
//						}
//						start--;
//					}
//					// 检验结果
//					if(start > 0){
//						int numblank = LIMIT_ROWS_COUNT - results.size();
//						if(results.size() <=1) {
//							numblank--;
//						}
//						// 追加空白行
//						int index = start + 1;
//						replaceInPara(table.getRow(index++).getTableCells().get(0).getParagraphs().get(0), BLANKTEXT);
//						int r = numblank;
//						XWPFTableRow rw = table.getRow(index);
//						while (r > 0) {
//							table.addRow(rw, index);
//							r--;
//						}
//
//						// 追加检验结果
//						if(results.size() == 1){
//							editResultRow(table.getRow(start), results.get(0));
//						} else if (results.size() > 1){
//							rw = table.getRow(start);
//							r = 0;
//							editResultRow(table.getRow(start), results.get(r++));
//							while (r < results.size()) {
//								table.addRow(rw, start + r);
//								editResultRow(table.getRow(start + r), results.get(r));
//								r++;
//							}
//							table.addRow(table.getRow(start), start + r);
//							table.removeRow(start);
//						}
//					}
//				}
//				return exportWordFile(template, filename);
//			} catch (Exception e) {
//				e.printStackTrace();
//				return "";
//			}
//	}
//
//	/**
//	 * 输出Word文档
//	 * @param filename
//	 */
//	private static String  exportWordFile(XWPFDocument template, String filename){
//		try {
//			String filepath = String.format("%s%s.docx", getRealReportPath(), filename);
//			OutputStream os = new FileOutputStream(filepath);
//			// 把doc输出到输出流中
//			template.write(os);
//			os.close();
//			return file2pdf(filepath);
////			System.out.println("docx文件输出成功。");
//		} catch (IOException e) {
////			System.out.println("docx文件输出失败。");
//			e.printStackTrace();
//			return "";
//		}
//	}
//	/**
//	 * 编辑表格行数据（检验结果）
//	 * @param row
//	 * @param result
//	 */
//	private static void editResultRow(XWPFTableRow row, List<String> result) {
//		int col = 0;
//		for (XWPFTableCell cell : row.getTableCells()) {
//			List<XWPFParagraph> paras = cell.getParagraphs();
//			for (XWPFParagraph para : paras) {
//				replaceInPara(para, result.get(col++));
//			}
//		}
//	}
//	/**
//	 * 解析等待替换的 标志
//	 * @param text
//	 * @return
//	 */
//	private static List<String> getMarks(String text) {
//		List<String> lst = new ArrayList<String>();
//		int index = -1;
//		int start = -1;
//		while (index < text.length()) {
//			if (text.indexOf("}", index) >= 0 && start < 0) {
//				index = text.indexOf("{", index);
//				start = index + 1;
//			} else if (text.indexOf("}", index) >= 0) {
//				lst.add(text.substring(start, text.indexOf("}", index)));
//				start = -1;
//				index = text.indexOf("}", index) + 1;
//			} else {
//				index = text.length();
//			}
//		}
//		return lst;
//	}
//
//	/**
//	 * 格式化 需要替换的内容，用于内容替换
//	 * @param name
//	 * @return
//	 */
//	private static String getFormatMark(String name) {
//		return String.format("{%s}", name);
//	}
//
//	/**
//	 * 用指定值替换指定的段落内容
//	 * @param para
//	 * @param val
//	 */
//	private static void replaceInPara(XWPFParagraph para, String val) {
//		List<XWPFRun> runs = para.getRuns();
//		if (Strings.isNullOrEmpty(val)) {
//			int i = runs.size() - 1;
//			while (i >= 0) {
//				para.removeRun(i--);
//			}
//			XWPFRun run = para.createRun();
//			run.setText(val);
//		}
//	}
//
//	/**
//	 * 替换段落里面的变量
//	 *
//	 * @param para
//	 *            要替换的段落
//	 * @param params
//	 *            参数
//	 */
//	private static void replaceInPara(XWPFParagraph para, Map<String, String> params) {
//		List<XWPFRun> runs = para.getRuns();
//
//		String text = para.getParagraphText();
//
//		if (!Strings.isNullOrEmpty(text)) {
//			List<String> keys = getMarks(text);
//			for (String key : keys) {
//				if (params.containsKey(key)) {
//					String val = text.replace(getFormatMark(key), params.get(key));
//					runs = para.getRuns();
//					int fontsize = runs.get(0).getFontSize();
//					int i = runs.size() - 1;
//					while (i >= 0) {
//						para.removeRun(i--);
//					}
//					XWPFRun run = para.createRun();
//					run.setFontSize(fontsize);
//					run.setText(val);
//				}
//			}
//		}
//	}
//
//	/**
//	 * Word文档转化PDF文件
//	 *
//	 * @param flname
//	 */
//	public static String file2pdf(String flname) {
////		File sourceFile = new File(flname);
////		File targetFile =  new File(flname.replaceAll(".docx", PDF_SUFFIX));
////		try {
////			_documentConverter.convert(sourceFile).to(targetFile).execute();
////			return  targetFile.getName();
////		} catch (OfficeException e) {
////			e.printStackTrace();
////			return "";
////		}
//		return "";
//	}

	/**
	 * 合并PDF
	 *
	 */
	public static String mergePdfFiles(List<String> filenames, String subdir) {
//		long start = System.currentTimeMillis();
		if(filenames.size() == 1){
			return  filenames.get(0); //.substring(getSystemRootPath().length());
		}
		String outpath = String.format("%s%s%s%s", getRealReportPath(), subdir,
				UUID.randomUUID().toString(), PDF_SUFFIX);
		PDFMergerUtility merger = new PDFMergerUtility();
		merger.setDestinationFileName(outpath);

		for(String filename : filenames){
			String flname = filename.startsWith(systemPath_of_template) ? filename : String.format("%s%s",systemPath_of_template, filename);
			try {
				merger.addSource(new File(flname));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		try {
			merger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
			return outpath.substring(getSystemRootPath().length());
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
//		System.out.println("合并PDF耗时:"+ (System.currentTimeMillis()- start) +"毫秒");
	}

	public static String path2Url(String filepath) {
		return filepath.replaceAll(getSystemRootPath(), "");
	}
//	/**
//	 * 合并PDF文件
//	 * @param files 原始pdf文件名称
//	 * @return 合并后的pdf文件名称
//	 */
//	public static String mergePdfFiles(List<String> files) {
//		if(files.size() <= 0){
//			return "";
//		}
//		if(files.size()  == 1){
//			return files.get(0);
//		}
//        String newfile = String.format("%s%s%s", getRealReportPath(),
//				UUID.randomUUID().toString(), PDF_SUFFIX);
//        Document document = null;
//        try {
////            document = new Document(new PdfReader(String.format("%s%s", getSystemRootPath() ,files.get(0))).getPageSize(1));
////            PdfCopy copy = new PdfCopy(document, new FileOutputStream(newfile));
////            document.open();
////            for (int i = 0; i < files.size(); i++) {
////                PdfReader reader = new PdfReader(String.format("%s%s", getSystemRootPath() ,files.get(i)));
////                int n = reader.getNumberOfPages();
////                for (int j = 1; j <= n; j++) {
////                    document.newPage();
////                    PdfImportedPage page = copy.getImportedPage(reader, j);
////                    copy.addPage(page);
////                }
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        } finally {
////            document.close();
//        }
//        return newfile.replace(getSystemRootPath(), "");
//    }
//	/**
//	 * 生成残留报告pdf文件，返回文件全路径名称
//	 * @param filename 文件简称
//	 * @param infos	委托信息
//	 * @param results		检验结果信息
//	 * @return	文件全路径名称
//	 */
//	public static String exportRemainReportPdf(String filename, Map<String, String> infos,List<?> results) {
//		 	Map<String, Object> infor = new HashMap<String, Object>();
//		 	Map<String, Object> title = new HashMap<String, Object>();
//		 	for(Map.Entry<String, String>  m:  infos.entrySet()){
//		 		infor.put(m.getKey(), m.getValue());
//		 		title.put(m.getKey(), m.getValue());
//		 	}
//		 	String  markicon_path = String.format("%s%s%s", getSystemRootPath(), ScsyReportUtil.templatePath,
//					"mark.jpg");
//			infor.put("图片资源位置", markicon_path );
//			title.put("图片资源位置", markicon_path );
//		 	String filepath = String.format("%s%s.pdf", getRealReportPath(), filename);
//			try {
//				// 生成首页报表
//				String sourceFileName_first = String.format("%s%s%s",getSystemRootPath(),
//						ScsyReportUtil.templatePath,"remain-page.jasper");
//				String sourceFileName_second = String.format("%s%s%s",getSystemRootPath(),
//						ScsyReportUtil.templatePath, "remain-detail.jasper");
//				JasperPrint jasperPrintFirst = JasperFillManager.fillReport(sourceFileName_first ,
//						infor, new JRBeanCollectionDataSource(results));
//				JasperPrint jasperPrintDetail = JasperFillManager.fillReport(sourceFileName_second ,
//						title,new JRBeanCollectionDataSource(results));
//				// 报表分组合并输出一个pdf文件
//				List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
//				jasperPrintList.add(jasperPrintFirst);
//				jasperPrintList.add(jasperPrintDetail);
//
//				JRPdfExporter exporter = new JRPdfExporter();
//				exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
//				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(filepath));
//				SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
//				configuration.setCreatingBatchModeBookmarks(true);
//				exporter.setConfiguration(configuration);
//				exporter.exportReport();
//				return filepath.replace(getSystemRootPath(), "");
//			} catch (Exception e) {
//				e.printStackTrace();
//				return "";
//			}
//	}
//
//	/**
//	 * 生成兽药检验报告pdf文件，返回文件全路径名称
//	 * @param filename 文件简称
//	 * @param infos	委托信息
//	 * @param results		检验结果信息
//	 * @return	文件全路径名称
//	 */
//	@SuppressWarnings("rawtypes")
//	public static String exportReportPdf(String filename, Map<String, String> infos,List<?> results) {
//		 	Map<String, Object> infor = new HashMap<String, Object>();
//		 	Map<String, Object> title = new HashMap<String, Object>();
//		 	for(Map.Entry<String, String>  m:  infos.entrySet()){
//		 		infor.put(m.getKey(), m.getValue());
//		 		title.put(m.getKey(), m.getValue());
//		 	}
//		 	String  markicon_path = String.format("%s%s", getSystemRootPath(), "export/template/mark.jpg");
//			infor.put("图片资源位置", markicon_path );
//			title.put("图片资源位置", markicon_path );
//		 	String filepath = String.format("%s%s.pdf", getRealReportPath(), filename);
//
//		 	List<Map> beanCollection_1 = new ArrayList<Map>();
//		 	beanCollection_1.add(infor);
//		 	JRBeanCollectionDataSource beanCollectionDataSource_1 = new JRBeanCollectionDataSource(beanCollection_1);
//		 	List<Map> beanCollection_2 = new ArrayList<Map>();
//		 	beanCollection_2.add(title);
//		 	JRBeanCollectionDataSource beanCollectionDataSource_2= new JRBeanCollectionDataSource(beanCollection_2);
//		 	// 根据检验编号识别报告类别
////		 	String jybh = infos.get("检验编号");
//		 	String report_page_1 =String.format("%s%s",getSystemRootPath(), "export/template/jybg-main.jasper");
//		 	String report_page_2 = String.format("%s%s",getSystemRootPath(), "export/template/jybg-base.jasper");
//		 	String report_page_3 = String.format("%s%s",getSystemRootPath(), "export/template/jybg-detail.jasper");
//		 	if("是".equals(infos.get("是否假兽药"))){
//		 		report_page_1 = String.format("%s%s",getSystemRootPath(), "export/template/jy-jsy-main.jasper");
//		 		report_page_2 = String.format("%s%s",getSystemRootPath(), "export/template/jy-jsy-detail.jasper");
//		 	}
//
//			try {
//				// 生成首页报表
//				JasperPrint jasperPrintFirst = JasperFillManager.fillReport(report_page_1 ,  new HashMap<String, Object>(), beanCollectionDataSource_1);
//				JasperPrint jasperPrintDetail = JasperFillManager.fillReport(report_page_2 ,new HashMap<String, Object>(),beanCollectionDataSource_2);
//				// 报表分组合并输出一个pdf文件
//				List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
//				jasperPrintList.add(jasperPrintFirst);
//				jasperPrintList.add(jasperPrintDetail);
//				// 检验报告打印第三页(假兽药不需要)
//				if("否".equals(infos.get("是否假兽药")) ){
//					JasperPrint jasperPrintThree = JasperFillManager.fillReport(report_page_3 ,  new HashMap<String, Object>(), new JRBeanCollectionDataSource(results));
//					jasperPrintList.add(jasperPrintThree);
//				}
//
//				JRPdfExporter exporter = new JRPdfExporter();
//				exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
//				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(filepath));
//				SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
//				configuration.setCreatingBatchModeBookmarks(true);
//				exporter.setConfiguration(configuration);
//				exporter.exportReport();
//				return filepath.replace(getSystemRootPath(), "");
//			} catch (Exception e) {
//				e.printStackTrace();
//				return "";
//			}
//	}
//
//	/**
//	 * 生成合同pdf文件，返回文件全路径名称
//	 * @param filename 文件简称
//	 * @param infos	委托信息
//	 * @return	文件全路径名称(相对路径)
//	 */
//	public static String exportContractReportPdf(String filename, Map<String, String> infos) {
//		 	Map<String, Object> infor = new HashMap<String, Object>();
//		 	for(Map.Entry<String, String>  m:  infos.entrySet()){
//		 		infor.put(m.getKey(), m.getValue());
//		 	}
//		 	String filepath = String.format("%sapply/%s_ht.pdf", getRealReportPath(), filename);
//			String sourceFileName_first = String.format("%s%s",getSystemRootPath(), "export/template/wtjyht.jasper");
//			return createPdf(filepath, sourceFileName_first, infor);
//	}
//
//	/**
//	 * 生成申请批次清单文件，返回文件全路径名称
//	 * @param filename 文件简称
//	 * @param datas	综合数据
//	 * @return	文件全路径名称(相对路径)
//	 */
//	public static String exportApplyListPdf(String filename, Map<String, Object> datas) {
//		 	String filepath = String.format("%sapply/%s_list.pdf", getRealReportPath(), filename);
//			String sourceFileName = String.format("%s%s",getSystemRootPath(), "export/template/ypqd.jasper");
//			return createPdf(filepath, sourceFileName, datas);
//	}
//
//	/**
//	 * 生成抽检批次清单文件，返回文件全路径名称
//	 * @param filename 文件简称
//	 * @param datas	综合数据
//	 * @return	文件全路径名称(相对路径)
//	 */
//	public static String createChoujianPiciPdf(String filename, Map<String, Object> datas) {
//		 	String filepath = String.format("%sapply/%s_list.pdf", getRealReportPath(), filename);
//			String sourceFileName = String.format("%s%s",getSystemRootPath(), "export/template/cjpc.jasper");
//			return createPdf(filepath, sourceFileName, datas);
//	}
//
//	/**
//	 * 生成抽检单文件(产品确认通知书)，返回文件全路径名称
//	 * @param filename 文件简称
//	 * @param datas	综合数据
//	 * @return	文件全路径名称(相对路径)
//	 */
//	public static String createChoujiandanPdf(String filename, Map<String, Object> datas) {
//		 	String filepath = String.format("%sapply/%s.pdf", getRealReportPath(), filename);
//			String sourceFileName = String.format("%s%s",getSystemRootPath(), "export/template/cpqrtzs.jasper");
//			return createPdf(filepath, sourceFileName, datas);
//	}
//
//	/**
//	 * 生成pdf文件
//	 * @param pdfFile 待生成的文件
//	 * @param sourceFileName 报表模板文件
//	 * @param datas	报表数据
//	 * @return 文件全路径名称(相对路径)
//	 */
//	private static String createPdf(String pdfFile, String sourceFileName, Map<String, Object> datas){
//		try {
//			// 生成首页报表
//		 	@SuppressWarnings("rawtypes")
//			List<Map> beanCollection = new ArrayList<Map>();
//		 	beanCollection.add(datas);
//		 	JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(beanCollection);
//
//			JasperPrint jasperPrintFirst = JasperFillManager.fillReport(sourceFileName ,  new HashMap<String, Object>(), beanCollectionDataSource);
//			// 报表分组合并输出一个pdf文件
//			List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
//			jasperPrintList.add(jasperPrintFirst);
//
//			JRPdfExporter exporter = new JRPdfExporter();
//			exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
//			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfFile));
//			SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
//			configuration.setCreatingBatchModeBookmarks(true);
//			exporter.setConfiguration(configuration);
//			exporter.exportReport();
//			return pdfFile.replace(getSystemRootPath(), "");
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "";
//		}
//	}
//	public static void main(String[] args) {
//		// Auto-generated method stub
//		Map<String, String> infors = new HashMap<String, String>();
//		infors.put("报告日期", DateUtils.formatTime());
//		infors.put("样品编号", "ABC123");
//		infors.put("送样单编号", "S0001");
//		infors.put("样品名称", "猪肉大腿肉");
//		infors.put("检验编号", "B160007");
//		infors.put("样品量", "500克");
//		infors.put("包封情况", "完好");
//		infors.put("保存情况", "不错");
//		infors.put("运输情况", "良好");
//		infors.put("检验依据", "2015标准");
//		// infors.put("取样方式",e.getQyfs());
//		// infors.put("抽样场所",e.getCjcs());
//		infors.put("抽样日期", DateUtils.formatTime());
//		infors.put("生产单位", "药厂");
//		infors.put("送样单位", "药监局");
//		infors.put("收检日期", DateUtils.formatTime());
//		infors.put("检验项目", "残留");
//		List<List<String>> results = new ArrayList<List<String>>();
//		String flname = String.format("/report/test1.docx", getRealReportPath());
////		List<String> filenamelist = new ArrayList<String>();
//
//		long start = System.currentTimeMillis();
//////		ScsyReportUtil.setIsdebug(true);
//////		ScsyReportUtil.editRemainReport(flname, infors, results);
//////		System.out.println("生成报表文件1耗时：" + (System.currentTimeMillis() - start) + "毫秒。");
//////		start = System.currentTimeMillis();
//////		ScsyReportUtil.file2pdf(flname);
//////		System.out.println("生成报告pdf耗时：" + (System.currentTimeMillis() - start) + "毫秒。");
//////		filenamelist.add(flname);
//////		start = System.currentTimeMillis();
////
//////		flname = String.format("%s/test2.docx", getRealReportPath());
//////		results.add(Arrays.asList("A", "1", "2", "3", "4"));
//////		ScsyReportUtil.editRemainReport(flname, infors, results);
//////		System.out.println("生成报表文件2耗时：" + (System.currentTimeMillis() - start) + "毫秒。");
//////		start = System.currentTimeMillis();
//////		ScsyReportUtil.file2pdf(flname);
//////		System.out.println("生成报告pdf耗时：" + (System.currentTimeMillis() - start) + "毫秒。");
//////		filenamelist.add(flname);
//////		start = System.currentTimeMillis();
////
////		flname = String.format("%s/test3.docx", getRealReportPath());
////		results.add(Arrays.asList("B", "2", "3", "4", "5"));
//////		results.add(Arrays.asList("c", "3", "4", "1", "2"));
//////		results.add(Arrays.asList("D", "2", "3", "4", "5"));
//////		results.add(Arrays.asList("E", "2", "3", "4", "5"));
//////		results.add(Arrays.asList("BF", "2", "3", "4", "5"));
////		ScsyReportUtil.exportRemainReport(flname, infors, results);
////		System.out.println("生成报表文件耗时：" + (System.currentTimeMillis() - start) + "毫秒。");
////		start = System.currentTimeMillis();
////		ScsyReportUtil.file2pdf(flname);
////		System.out.println("生成PDF耗时：" + (System.currentTimeMillis() - start) + "毫秒。");
////		filenamelist.add(flname);
//////		start = System.currentTimeMillis();
//////		ScsyReportUtil.mergePdf(filenamelist);
//		exportRemainReport(flname, infors, results);
//		System.out.println("合并PDF耗时：" + (System.currentTimeMillis() - start) + "毫秒。");
//	}

}
