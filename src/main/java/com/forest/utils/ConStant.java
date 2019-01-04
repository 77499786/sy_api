package com.forest.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * 常量类
 */
@Component
public class ConStant {
	/**OpenOffice安装根目录*/
	public String OFFICE_HOME;

	@Value("${scsy.officeHome}")
	public void setOFFICE_HOME(String OFFICE_HOME) {
		this.OFFICE_HOME = OFFICE_HOME;
	}

	//	static{
//		OFFICE_HOME = ResourceUtil.getSessionattachmenttitle("office_home");
//	}
	/**文件上传保存根目录*/
	public static final String UPLOAD_BASE_DIR = "upload";
	/**文件转换工具根目录*/
	public static final String SWFTOOLS_BASE_DIR = "swftools";
	/**PDF-SWF*/
	public static final String SWFTOOLS_PDF2SWF_PATH ="pdf2swf.exe";
	/**GIF-SWF*/
	public static final String SWFTOOLS_GIF2SWF_PATH ="gif2swf.exe";
	/**PNG-SWF*/
	public static final String SWFTOOLS_PNG2SWF_PATH ="png2swf.exe";
	/**JPEG-SWF*/
	public static final String SWFTOOLS_JPEG2SWF_PATH ="jpeg2swf.exe";
	/**WAV-SWF*/
	public static final String SWFTOOLS_WAV2SWF_PATH = "wav2swf.exe";
	/**PDF合并*/
	public static final String SWFTOOLS_PDFCOMBINE_PATH ="swfcombine.exe";
	/**SWF文件后缀*/
	public static final String SWF_STUFFIX = "swf";
	public static String SWFTOOLS_HOME="";

	public static final String MAX_TIMESTAMP = "2099-12-31 23:59:59";
	public static final String MIN_TIMESTAMP = "2010-01-01 00:00:00";
	public static final String MAX_TIME = " 23:59:59";
	public static final String MIN_TIME = " 00:00:00";
	public static final String MAX_DATE = " 2099-12-31";
	public static final String MIN_DATE = " 2010-01-01";

	/** 报告生成类型  */
	public static String REPORT_TYPE="office";
	@Value("${scsy.reportType}")
	public void setReportType(String reportType) {
		REPORT_TYPE = reportType;
	}

	public static final String REPORT_TYPE_JASPER="jasper";
	public static final String REPORT_TYPE_OFFICE="office";
	/**
	 * 根据扩展名获取转换工具
	 * @param extend
	 * @return
	 */
	public static String getSWFToolsPath(String extend)	{
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		SWFTOOLS_HOME=request.getSession().getServletContext().getRealPath("/") + SWFTOOLS_BASE_DIR + "/";
		if(extend.equals("pdf"))
		{
			SWFTOOLS_HOME+=SWFTOOLS_PDF2SWF_PATH;
		}
		if(extend.equals("gif"))
		{
			SWFTOOLS_HOME+=SWFTOOLS_GIF2SWF_PATH;
		}
		if(extend.equals("png"))
		{
			SWFTOOLS_HOME+=SWFTOOLS_PNG2SWF_PATH;
		}
		if(extend.equals("jpeg"))
		{
			SWFTOOLS_HOME+=SWFTOOLS_JPEG2SWF_PATH;
		}
		if(extend.equals("wav"))
		{
			SWFTOOLS_HOME+=SWFTOOLS_WAV2SWF_PATH;
		}
		return SWFTOOLS_HOME;
	}

/*
	public static String getReportType() {
		return REPORT_TYPE;
	}

	@Value("${scsy.reportType}")
	public static void setReportType(String reportType) {
		REPORT_TYPE = reportType;
	}
*/
}
