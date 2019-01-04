package com.forest.utils;

import com.forest.home.model.HomeIOInfor;
import com.forest.project.model.FrameResource;
import com.google.common.base.Joiner;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfficeUtilTest {

    @Test
    public void searchWordReplace() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("${title}", "POI操作示例");
        map.put("{{name}}", "灰姑娘");
//        map.put("{{detail}}", "一个凄美的故事。");
        map.put("{{col1}}", "第一列");
        map.put("{{col2}}", "第2列");
        map.put("${content1}", "内容第一行第一列");
        map.put("${content2}", "内容第一行第2列");
        String srcPath = "D:/temp/aaa.docx";
        String destPath = "D:/temp/3.docx";
        XWPFDocument document = OfficeUtil.searchWordReplace(srcPath, destPath, map);
        if(document != null){
            FileOutputStream outStream = null;
            outStream = new FileOutputStream(destPath);
            document.write(outStream);
            outStream.close();
        }
    }

    @Test
    public void excelOutput() throws Exception {
        BiMap<String, String> map = HashBiMap.create();
        map.put("ymd", "消费日期");
        map.put("lx", "消费类型");
        map.put("money", "消费金额");
        map.put("remark", "说明");
        List<HomeIOInfor> list = Lists.newArrayList(
                new HomeIOInfor("2018-07-01", "饮食娱乐", "30", "午餐"),
                new HomeIOInfor("2018-07-02", "饮食娱乐", "20", "午餐"),
                new HomeIOInfor("2018-07-03", "饮食娱乐", "16", "午餐"),
                new HomeIOInfor("2018-07-04", "饮食娱乐", "78", "午餐"),
                new HomeIOInfor("2018-07-05", "饮食娱乐", "34", "午餐"),
                new HomeIOInfor("2018-07-05", "佳通费", "98", "充值"));
//        String str = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, "ymd");
//        System.out.println(str);
        XSSFWorkbook document = OfficeUtil.excelOutput(list, "消费记录", map);
        String destPath = "D:/temp/sample.xlsx";
        if (document != null) {
            FileOutputStream outStream = null;
            outStream = new FileOutputStream(destPath);
            document.write(outStream);
            outStream.close();
        }
    }

    @Test
    public void readExcelData() throws Exception {
        String destPath = "D:/temp/0521.xlsx";

        File file = new File(destPath);
        FileInputStream input = new FileInputStream(file);
        MultipartFile f = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));

        Map<String, Multimap<Integer,String>> r = OfficeUtil.readExcelData(f);
        r.forEach((k,v) ->{
            System.out.println(k+":");
            for(Integer k1 : v.keySet()){
                System.out.println(k1+"->" + Joiner.on(" | ").join(v.get(k1)));
            }
        });
    }

    @Test
    public void readExcelData1() throws Exception {
        String destPath = "D:/temp/sample.xlsx";
        BiMap<String, String> map = HashBiMap.create();
        map.put("ymd", "消费日期");
        map.put("lx", "消费类型");
        map.put("money", "消费金额");
        map.put("remark", "说明");

        File file = new File(destPath);
        FileInputStream input = new FileInputStream(file);
        MultipartFile f = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));

//        MultipartFile f = MockMultipartFile(destPath);
        List<HomeIOInfor> lista = OfficeUtil.readExcelData(f, "消费记录", map, HomeIOInfor.class);
        for (HomeIOInfor e : lista) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void testreflect() throws Exception {
        for(Field field : FrameResource.class.getDeclaredFields()){
            System.out.println(field.getName().concat(":").concat(field.getGenericType().toString()));
        }

    }
}