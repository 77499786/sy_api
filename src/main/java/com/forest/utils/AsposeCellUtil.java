package com.forest.utils;

import com.aspose.cells.License;
import com.aspose.cells.Workbook;
import com.aspose.cells.WorkbookDesigner;
import com.aspose.words.net.System.Data.DataColumnCollection;
import com.aspose.words.net.System.Data.DataRow;
import com.aspose.words.net.System.Data.DataTable;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 利用Aspose组件生成报表
 */
public class AsposeCellUtil {

    public static boolean getLicense() throws Exception {
        boolean result = false;
        try {

            InputStream is = License.class
                    .getResourceAsStream("/license_2999.xml");
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
     * 填充模板字段生成目标文件
     *
     * @param templateFile 模板文件
     * @param targetFile   目标文件
     * @param objInfors    单一数据Map对象
     * @param saveFormat   保存的格式
     * @return 保存后的文件名称（相对路径）
     */
    public static String replaceText(String templateFile,
                                     String targetFile,
                                     Map<String, Object> objInfors,
                                     int saveFormat) {
        try {
            getLicense();
            WorkbookDesigner designer = new WorkbookDesigner();
            designer.setWorkbook(new Workbook(templateFile));
            objInfors.forEach((k,v) ->{
                if(v.getClass().equals(Map.class)){
                    designer.setDataSource(k, new HashMapDataTable((Map<String, Object>) v));
                } else if (HashMapDataTable.class.equals(v.getClass())) {
                    designer.setDataSource(k, (HashMapDataTable)v);
                } else {
                    designer.setDataSource(k,v);
                }
            });
            designer.process();
//            if (!Strings.isNullOrEmpty(sheetName))
//            {
//                designer.getWorkbook().getWorksheets().get(0).setName(sheetName);
//            }
            designer.getWorkbook().save(targetFile, saveFormat);
            return targetFile;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static DataTable convert2DataTable(List<Map<String, Object>> listMap, String tableName) {
        List<String> names = new ArrayList<String>();
        //= Arrays.asList("检测项目","检测限","最高残留量","检测数据","检测结果");
        if (listMap.size() > 0) {
            listMap.get(0).keySet().forEach(k -> {
                names.add(k);
            });
        } else {
            return null;
        }

        DataTable dt = new DataTable();
        dt.setTableName(tableName);
        DataColumnCollection dc = dt.getColumns();
        names.forEach(name -> {
            dc.add(name);
        });
        listMap.forEach(r -> {
            DataRow dr = dt.newRow();
            names.forEach(name -> {
                dr.set(name, r.get(name));
            });
            dt.getRows().add(dr);
        });
        return dt;
    }

}
