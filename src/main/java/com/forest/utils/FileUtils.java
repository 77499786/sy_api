package com.forest.utils;

import ch.qos.logback.core.util.FileUtil;
import com.forest.project.model.TreeData;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件操作工具类
 * @author 张代浩
 *
 */
public class FileUtils {
	/**
	 * 获取文件扩展名
	 * 
	 * @param filename	文件名称
	 * @return 扩展名
	 */
	public static String getExtend(String filename) {
		return getExtend(filename, "");
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param filename	文件名称
	 * @return 扩展名
	 */
	public static String getExtend(String filename, String defExt) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');

			if ((i > 0) && (i < (filename.length() - 1))) {
				return (filename.substring(i+1)).toLowerCase();
			}
		}
		return defExt.toLowerCase();
	}

	/**
	 * 获取文件名称[不含后缀名]
	 * 
	 * @param
	 * @return String
	 */
	public static String getFilePrefix(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		return fileName.substring(0, splitIndex).replaceAll("\\s*", "");
	}
	
	/**
	 * 获取文件名称[不含后缀名]
	 * 不去掉文件目录的空格
	 * @param
	 * @return String
	 */
	public static String getFilePrefix2(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		return fileName.substring(0, splitIndex);
	}
	
	/**
	 * 文件复制
	 *方法摘要：这里一句话描述方法的用途
	 *@param
	 *@return void
	 */
	public static void copyFile(String inputFile,String outputFile) throws FileNotFoundException{
		File sFile = new File(inputFile);
		File tFile = new File(outputFile);
		FileInputStream fis = new FileInputStream(sFile);
		FileOutputStream fos = new FileOutputStream(tFile);
		int temp = 0;  
		byte[] buf = new byte[10240];
        try {  
        	while((temp = fis.read(buf))!=-1){   
        		fos.write(buf, 0, temp);   
            }   
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally{
            try {
            	fis.close();
            	fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        } 
	}
	
	/**
	 * 移动文件到目的文件夹
	 * @param srcFile
	 * @param destPath 目的文件夹
	 */
	public static void moveFile(String srcFile,String destPath) {
		File file = new File(srcFile);       
		File dir = new File(destPath);
		if(file.exists()){
			file.renameTo(new File(dir, file.getName()));
		}
	}

	/**
	 * 判断文件是否为图片<br>
	 * <br>
	 * 
	 * @param filename
	 *            文件名<br>
	 *            判断具体文件类型<br>
	 * @return 检查后的结果<br>
	 * @throws Exception
	 */
	public static boolean isPicture(String filename) {
//		// 文件名称为空的场合
//		if (oConvertUtils.isEmpty(filename)) {
//			// 返回不和合法
//			return false;
//		}
		// 获得文件后缀名
		//String tmpName = getExtend(filename);
		String tmpName = filename;
		// 声明图片后缀名数组
		String imgeArray[][] = { { "bmp", "0" }, { "dib", "1" },
				{ "gif", "2" }, { "jfif", "3" }, { "jpe", "4" },
				{ "jpeg", "5" }, { "jpg", "6" }, { "png", "7" },
				{ "tif", "8" }, { "tiff", "9" }, { "ico", "10" } };
		// 遍历名称数组
		for (int i = 0; i < imgeArray.length; i++) {
			// 判断单个类型文件的场合
			if (imgeArray[i][0].equals(tmpName.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断文件是否为DWG<br>
	 * <br>
	 * 
	 * @param filename
	 *            文件名<br>
	 *            判断具体文件类型<br>
	 * @return 检查后的结果<br>
	 * @throws Exception
	 */
	public static boolean isDwg(String filename) {
//		// 文件名称为空的场合
//		if (oConvertUtils.isEmpty(filename)) {
//			// 返回不和合法
//			return false;
//		}
		// 获得文件后缀名
		String tmpName = getExtend(filename);
		// 声明图片后缀名数组
        return tmpName.equals("dwg");
    }
	
	/**
	 * 删除指定的文件
	 * 
	 * @param strFileName
	 *            指定绝对路径的文件名
	 * @return 如果删除成功true否则false
	 */
	public static boolean delete(String strFileName) {
		File fileDelete = new File(strFileName);

		if (!fileDelete.exists() || !fileDelete.isFile()) {
//			LogUtil.info("错误: " + strFileName + "不存在!");
			return false;
		}

//		LogUtil.info("--------成功删除文件---------"+strFileName);
		return fileDelete.delete();
	}
	
	public static boolean exists(String strFileName) {
		File file = new File(strFileName);
		return file.exists();
	}
	
	/**
	 * 删除文件夹
	 * @param strFolderName
	 * @return
	 */
	public static boolean deleteFolder(String strFolderName) {
		if (!strFolderName.endsWith(File.separator)) {
			strFolderName = strFolderName + File.separator;
		}
		File fileDelete = new File(strFolderName);

		if (!fileDelete.exists() || !fileDelete.isDirectory()) {
//			LogUtil.info("错误: " + strFolderName + "不存在!");
			return false;
		}

		boolean flag = true;
		// 删除文件夹中的所有文件包括子目录
		File[] files = fileDelete.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = FileUtils.delete(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
			// 删除子目录
			else if (files[i].isDirectory()) {
				flag = FileUtils.deleteFolder(files[i]
						.getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
//			System.out.println("删除目录失败！");
			return false;
		}
		// 删除当前目录
		if (fileDelete.delete()) {
//			System.out.println("删除目录" + strFolderName + "成功！");
			return true;
		} else {
			return false;
		}
//		LogUtil.info("--------成功删除文件夹---------"+strFolderName);
//		return fileDelete.delete();
	}
	
	/**
	 * 获取文件夹对象，文件夹不存在时创建再返回
	 * @param strFolderName
	 * @return
	 */
	public static File getFolder(String strFolderName) {
		File folerNew = new File(strFolderName);

		if (!folerNew.exists() || !folerNew.isDirectory()) {
			folerNew.mkdir();
		}
		return folerNew;
	}
	/**
	 * 
	* @Title: encodingFileName 2015-11-26 huangzq add
	* @Description: 防止文件名中文乱码含有空格时%20 
	* @param @param fileName
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String encodingFileName(String fileName) {
        String returnFileName = "";
        try {
            returnFileName = URLEncoder.encode(fileName, "UTF-8");
            returnFileName = StringUtils.replace(returnFileName, "+", "%20");
            if (returnFileName.length() > 150) {
                returnFileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");
                returnFileName = StringUtils.replace(returnFileName, " ", "%20");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
//            LogUtil.info("Don't support this encoding ...");
        }
        return returnFileName;
    }

	/**
	 * 压缩
	 *
	 * @param zipFileName
	 *            压缩产生的zip包文件名--带路径,如果为null或空则默认按文件名生产压缩文件名
	 * @param relativePath
	 *            相对路径，默认为空
	 * @param directory
	 *            文件或目录的绝对路径
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String zip(String zipFileName, String relativePath, String directory) throws FileNotFoundException, IOException {
		String fileName = zipFileName;
		if (fileName == null || fileName.trim().equals("")) {
			File temp = new File(directory);
			if (temp.isDirectory()) {
				fileName = directory + ".zip";
			} else {
				if (directory.indexOf(".") > 0) {
					fileName = directory.substring(0, directory.lastIndexOf(".")) + ".zip";
				} else {
					fileName = directory + ".zip";
				}
			}
		}
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(fileName));
		try {
			zip(zos, relativePath, directory);
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (null != zos) {
				zos.close();
			}
		}
		return fileName;
	}

	/**
	 * 压缩
	 *
	 * @param zos
	 *            压缩输出流
	 * @param relativePath
	 *            相对路径
	 * @param absolutPath
	 *            文件或文件夹绝对路径
	 * @throws IOException
	 */
	private static void zip(ZipOutputStream zos, String relativePath, String absolutPath) throws IOException {
		File file = new File(absolutPath);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				File tempFile = files[i];
				if (tempFile.isDirectory()) {
					String newRelativePath = relativePath + tempFile.getName() + File.separator;
					createZipNode(zos, newRelativePath);
					zip(zos, newRelativePath, tempFile.getPath());
				} else {
					zipFile(zos, tempFile, relativePath);
				}
			}
		} else {
			zipFile(zos, file, relativePath);
		}
	}

	/**
	 * * 压缩文件
	 *
	 * @param zos
	 *            压缩输出流
	 * @param file
	 *            文件对象
	 * @param relativePath
	 *            相对路径
	 * @throws IOException
	 */
	private static void zipFile(ZipOutputStream zos, File file, String relativePath) throws IOException {
		ZipEntry entry = new ZipEntry(relativePath + file.getName());
		zos.putNextEntry(entry);
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			int BUFFERSIZE = 2 << 10;
			int length = 0;
			byte[] buffer = new byte[BUFFERSIZE];
			while ((length = is.read(buffer, 0, BUFFERSIZE)) >= 0) {
				zos.write(buffer, 0, length);
			}
			zos.flush();
			zos.closeEntry();
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (null != is) {
				is.close();
			}
		}
	}

	/**
	 * 创建目录
	 *
	 * @param zos
	 *            zip输出流
	 * @param relativePath
	 *            相对路径
	 * @throws IOException
	 */
	private static void createZipNode(ZipOutputStream zos, String relativePath) throws IOException {
		ZipEntry zipEntry = new ZipEntry(relativePath);
		zos.putNextEntry(zipEntry);
		zos.closeEntry();
	}

	/**
	 * 读取文件夹内文件及目录清单
	 * @param strFolderName
	 * @return
	 */
	public static List<TreeData> getFolderExtends(String strFolderName){
		if (!strFolderName.endsWith(File.separator)) {
			strFolderName = strFolderName + File.separator;
		}
		File folder = new File(strFolderName);
		String spearator = System.getProperty("file.separator");
		List<TreeData> treeDatas = new ArrayList<TreeData>();
		if (folder.isDirectory()){
			for ( File f: folder.listFiles()) {
				TreeData treeData = new TreeData();
				String p = f.getPath().replaceAll("\\\\", "/");
				treeData.setId(p);
				treeData.setName(f.getName());
//				treeData.setCode(f.getPath());
				if(f.isDirectory()){
					treeData.setLeaf(false);
					treeData.setChildren(FileUtils.getFolderExtends(f.getAbsolutePath()));
				} else {
					treeData.setLeaf(f.isFile());
				}
				treeDatas.add(treeData);
			}
		}
		return treeDatas;
	}

}
