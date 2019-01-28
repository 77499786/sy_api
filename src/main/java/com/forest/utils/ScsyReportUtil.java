package com.forest.utils;

import com.google.common.base.Strings;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
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
	public static String DOC_SUFFIX =".docx";
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
			systemPath_of_template = "C:/java/work/sy_api/sy_api/";
		} else {
			if(Strings.isNullOrEmpty(systemPath_of_template)){
				systemPath_of_template = JarToolUtil.getJarDir();
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

}
