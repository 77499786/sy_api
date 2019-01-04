package com.forest.utils;

import com.aspose.words.License;
import com.aspose.words.SaveFormat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileConvert {
    public static String PDF_SUFFIX =".pdf";

    public static boolean getLicense() throws Exception {
        boolean result = false;
        try {

            InputStream is = com.aspose.words.Document.class
                    .getResourceAsStream("/com.aspose.words.lic_2999.xml");
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }

    /**
     * Word文档转化PDF文件
     * @param flname
     */
    public static String word2pdf(String flname) {
        try {
            if (!getLicense()) {
                // 验证License 若不验证则转化出的pdf文档有水印
                throw new Exception("com.aspose.words lic ERROR!");
            }
            File targetFile =  new File(flname.replaceAll(".docx", PDF_SUFFIX));
            System.out.println(flname + " -> " + targetFile.getAbsolutePath());
            long old = System.currentTimeMillis();
            FileOutputStream os = new FileOutputStream(targetFile);
            com.aspose.words.Document doc = new com.aspose.words.Document(flname); // word文档
            System.out.println(((System.currentTimeMillis() - old) / 1000.0) + "秒");
            // 支持RTF HTML,OpenDocument, PDF,EPUB, XPS转换
            doc.save(os, SaveFormat.PDF);
            long now = System.currentTimeMillis();
            System.out.println("convert OK! " + ((now - old) / 1000.0) + "秒");
            return targetFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
