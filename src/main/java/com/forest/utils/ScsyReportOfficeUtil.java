package com.forest.utils;

import com.google.common.base.Strings;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScsyReportOfficeUtil extends ScsyReportUtil{

	private static String HEADER_OF_RESULTS_FIRST = "检测项目";
	private static int LIMIT_ROWS_COUNT = 12;
	private static String BLANKTEXT = "—以下空白—";
	public static String PATH_OF_TEMPLATE;
	private static XWPFDocument remain_Template;  // 残留报告模板

	public static String getOfficeTemplatePath(){
		return String.format("%s%sword/",getSystemRootPath(), getTemplatePath());
	}
	/**
	 * 更新残留检验报告模板
	 */
	public static XWPFDocument updateTemplate(){
		String systempath = getSystemRootPath();
		try {
			InputStream input = new FileInputStream(systempath + PATH_OF_TEMPLATE);
			remain_Template = new XWPFDocument(input);
			input.close();
			return remain_Template;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
/*	public static String editRemainReport(String filename, Map<String, String> infos,
			List<List<String>> results) {

			XWPFDocument template = updateTemplate();
//			// 删除旧数据
//			File f = new File(filename);
//			if(f.exists()){
//				f.delete();
//			}
			if(template == null) {
				return "";
			}
			// 只能替换表格中的内容
			Iterator<XWPFTable> itTable = template.getTablesIterator();
			while (itTable.hasNext()) {
				XWPFTable table = (XWPFTable) itTable.next();
				int start = table.getNumberOfRows();
				// 找到结果数据起始行
				for (int i = 0; i < start; i++) {
					XWPFTableRow row = table.getRow(i);
					if (HEADER_OF_RESULTS_FIRST.equals(row.getCell(0).getText())) {
						start = i + 1;
						break;
					}
				}
				if (start < table.getNumberOfRows()) {
					int numblank = LIMIT_ROWS_COUNT - results.size();
					if(results.size() <=1) {
						numblank--;
					}					
					// 追加空白行
					int index = start + 1;
					replaceInPara(table.getRow(index++).getTableCells().get(0).getParagraphs().get(0), BLANKTEXT);
					int r = numblank;
					XWPFTableRow rw = table.getRow(index);
					while (r > 0) {
						table.addRow(rw, index);
						r--;
					}
					
					// 追加检验结果
					if(results.size() == 1){
						editResultRow(table.getRow(start), results.get(0));
					} else if (results.size() > 1){
						rw = table.getRow(start);
						r = 0;
						editResultRow(table.getRow(start), results.get(r++));
						while (r < results.size()) {
							table.addRow(rw, start + r);
							editResultRow(table.getRow(start + r), results.get(r));
							r++;
						}
						table.addRow(table.getRow(start), start + r);
						table.removeRow(start);
					}
				}

				// 输出数据
				int rcount = table.getNumberOfRows();
				for (int i = 0; i < rcount; i++) {
					if (i + 1 == start) {
						i += results.size();
						continue;
					}
					XWPFTableRow row = table.getRow(i);
					List<XWPFTableCell> cells = row.getTableCells();
					// 检验基本信息
					for (XWPFTableCell cell : cells) {
						List<XWPFParagraph> paras = cell.getParagraphs();
						for (XWPFParagraph para : paras) {
							replaceInPara(para, infos);
						}
					}
				}
			}
			return exportReportFile(template, filename);
	}*/

	/**
	 * 根据模板编辑输出word结果文档
	 * 
	 * @param filename word文档名称
	 * @param infos 检验委托信息
	 * @param results 检验结果数据
	 */
	public static String exportRemainReport(String filename, Map<String, String> infos,
			List<List<String>> results) {
		 	Map<String, Object> objInfors = new HashMap<String, Object>();
		 	for(Map.Entry<String, String>  m:  infos.entrySet()){
		 		objInfors.put(m.getKey(), m.getValue());
		 	}
			try {
				String path =  String.format("%s%s", getOfficeTemplatePath(), "remain.docx");
				XWPFDocument template  = new XWPFDocument(POIXMLDocument.openPackage(path));
				if(template == null) {
					return "";
				}
				// 只能替换表格中的内容
				Iterator<XWPFTable> itTable = template.getTablesIterator();
				while (itTable.hasNext()) {
					XWPFTable table = (XWPFTable) itTable.next();
					replaceKeyValues(table, objInfors);
					int start = getResultStartLine(table);
					// 检验结果
					if(start > 0){
						int numblank = LIMIT_ROWS_COUNT - results.size();
						if(results.size() <=1) {
							numblank--;
						}
						// 追加空白行
						int index = start + 1;
						replaceInPara(table.getRow(index++).getTableCells().get(0).getParagraphs().get(0), BLANKTEXT);
						int r = numblank;
						XWPFTableRow rw = table.getRow(index);
						while (r > 0) {
							table.addRow(rw, index);
							r--;
						}
						
						// 追加检验结果
						if(results.size() == 1){
							editResultRow(table.getRow(start), results.get(0));
						} else if (results.size() > 1){
							rw = table.getRow(start);
							r = 0;
							editResultRow(table.getRow(start), results.get(r++));
							while (r < results.size()) {
								table.addRow(rw, start + r);
								editResultRow(table.getRow(start + r), results.get(r));
								r++;
							}
							table.addRow(table.getRow(start), start + r);
							table.removeRow(start);
						}
					}
				}
				return exportReportFile(template, filename);
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
	}

	/**
	 * 遍历Table单元格替换内容
	 * @param table
	 * @param objInfors
	 */
	private static void replaceKeyValues(XWPFTable table, Map<String, Object> objInfors){
		for (XWPFTableRow r :table.getRows()) {
			for (XWPFTableCell c: r.getTableCells()) {
				c.getParagraphs().forEach( p -> {
					replaceInPara(p, objInfors);
				});
			}
		}
	}
	/**
	 * 找到需要循环追加数据的结果区域开始行行号
	 * @param table 表格
	 * @return 结果数据填充区域起始行号，没有结果区域时返回-1
	 */
	private static Integer getResultStartLine(XWPFTable table){
		int start = 0;
		for (XWPFTableRow r :table.getRows()) {
			if(HEADER_OF_RESULTS_FIRST.equals(r.getCell(0).getText())){
				return ++start;
			}
			start++;
		}
		return -1;
	}
	/**
	 * 输出Word文档
	 * @param filename
	 */
	private static String exportReportFile(XWPFDocument template, String filename){
		try {
			String filepath = String.format("%s%s.docx", getRealReportPath(), filename);
			OutputStream os = new FileOutputStream(filepath);
			// 把doc输出到输出流中
			template.write(os);
			os.close();
			FileConvert.word2pdf(filepath).replace(getSystemRootPath(), "");
			return String.format("%s%s%s", getReportPath(), filename,FileConvert.PDF_SUFFIX);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	/**
	 * 编辑表格行数据（检验结果）
	 * @param row
	 * @param result
	 */
	private static void editResultRow(XWPFTableRow row, List<String> result) {
		int col = 0;
		for (XWPFTableCell cell : row.getTableCells()) {
			List<XWPFParagraph> paras = cell.getParagraphs();
			for (XWPFParagraph para : paras) {
				replaceInPara(para, result.get(col++));
			}
		}
	}
	/**
	 * 解析等待替换的 标志
	 * @param text
	 * @return
	 */
	private static List<String> getMarks(String text) {
		List<String> lst = new ArrayList<String>();
		String re = "\\{\\{([^\\}]+)\\}\\}";
		Pattern p = Pattern.compile(re);
		Matcher m = p.matcher(text);
		while(m.find()){
			lst.add(String.format("%s",m.group(1)));
		}
		return lst;
	}

	/**
	 * 格式化 需要替换的内容，用于内容替换
	 * @param name
	 * @return
	 */
	private static String getFormatMark(String name) {
		return String.format("{{%s}}", name);
	}

	/**
	 * 用指定值替换指定的段落内容
	 * @param para
	 * @param val
	 */
	private static void replaceInPara(XWPFParagraph para, String val) {
		List<XWPFRun> runs = para.getRuns();
		if (!Strings.isNullOrEmpty(val)) {
			int i = runs.size() - 1;
			while (i >= 0) {
				para.removeRun(i--);
			}
			XWPFRun run = para.createRun();
			run.setText(val);
		}
	}

	/**
	 * 替换段落里面的变量
	 * 
	 * @param para
	 *            要替换的段落
	 * @param params
	 *            参数
	 */
	private static void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
		List<XWPFRun> runs = para.getRuns();

		String text = para.getParagraphText();

		if (!Strings.isNullOrEmpty(text)) {
			List<String> keys = getMarks(text);
			for (String key : keys) {
				if (params.containsKey(key)) {
					String val = text.replace(getFormatMark(key), params.get(key).toString());
					runs = para.getRuns();
					int fontsize = runs.get(0).getFontSize();
					int i = runs.size() - 1;
					while (i >= 0) {
						para.removeRun(i--);
					}
					XWPFRun run = para.createRun();
					run.setFontSize(fontsize);
					run.setText(val);
				}
			}
		}
	}

	public static void main(String[] args) {
	}

}
