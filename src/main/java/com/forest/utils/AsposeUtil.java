package com.forest.utils;

import com.aspose.words.*;
import com.aspose.words.net.System.Data.DataColumnCollection;
import com.aspose.words.net.System.Data.DataRow;
import com.aspose.words.net.System.Data.DataTable;
import com.google.common.base.Strings;

import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 利用Aspose组件生成报表
 */
public class AsposeUtil {

    private static String REMAIN_TEMPLATE_NAME = "残留检验报告.docx";
    private static String SY_CONTRACT_TEMPLATE_NAME = "兽药检验合同.docx";
    private static String SY_INSPECTION_TEMPLATE_NAME = "产品确认通知书.docx";
    private static String SY_REPORT_TEMPLATE_NAME = "兽药检验报告.docx";
    private static String SY_INSPECTION_REPORT_TEMPLATE_NAME = "兽药检验报告（抽检）.docx";
    private static String SY_FAKE_TEMPLATE_NAME = "兽药检验报告（假兽药）.docx";
    private static String SY_APPLY_TEMPLATE_NAME = "抽检清单.docx";

    public static String getOfficeTemplatePath(){
        return String.format("%s%sword/",ScsyReportUtil.getSystemRootPath(), ScsyReportUtil.getTemplatePath());
    }

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
     * 填充模板字段生成目标文件
     *
     * @param templateFile 模板文件
     * @param targetFile   目标文件
     * @param objInfors    单一数据Map对象
     * @param results      循环填充的原始数据DataTable
     * @param saveFormat   保存的格式
     * @return 保存后的文件名称（相对路径）
     */
    public static String replaceText(String templateFile,
                                     String targetFile,
                                     Map<String, Object> objInfors,
                                     DataTable results,
                                     int saveFormat) {
        try {
            getLicense();
            Document doc = new Document(templateFile);//读取模板

            List<String> keylist = new ArrayList<String>();
            List<Object> vallist = new ArrayList<Object>();
            objInfors.forEach((key, val) -> {
                keylist.add(key);
                vallist.add(val);
            });
            String[] keyarr = new String[keylist.size()];
            keylist.toArray(keyarr);
            doc.getMailMerge().execute(keyarr, vallist.toArray());

            FindReplaceOptions options = new FindReplaceOptions();
            options.setMatchCase(true);
            options.setFindWholeWordsOnly(true);
            //使用文本方式替换单一文本
            for (String name : keylist) {
                String keystr = String.format("{{%s}}", name);
                String val = objInfors.get(name) == null ? "":objInfors.get(name).toString();
                doc.getRange().replace(keystr, val, options);
            }
            if( results != null) {
                // 表格填充
                doc.getMailMerge().executeWithRegions(results);
            }

            if(results!= null){
                doBeforeSave(doc, templateFile, results);
            }
//            doc.save(targetFile.replace(".pdf", ".docx"), SaveFormat.DOCX);
            doc.save(targetFile, saveFormat);
            return targetFile;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 生成残留检验报告
     *
     * @param filename  文件名称
     * @param mapDatas 单一数据
     * @return 保存后的文件名称（相对路径）
     */
    public static String createPdfReport(String filename, Map<String, Object> mapDatas) {
        String templateFile = getOfficeTemplatePath().concat(REMAIN_TEMPLATE_NAME);
        String relativePath = ScsyReportUtil.CL_SUBDICTIONARY.concat(filename).concat(ScsyReportUtil.PDF_SUFFIX);
        String targetFile = ScsyReportUtil.getRealReportPath().concat(relativePath);
        DataTable dt = convert2DataTable((List<Map<String, Object>>)mapDatas.get("检验结果"), "检验结果");
//        DataTable dt = convert2DataTable(results, "检验结果");
        String file = replaceText(templateFile, targetFile, mapDatas, dt, SaveFormat.PDF);
        String relativeFile = ScsyReportUtil.getReportPath().concat(relativePath);
        if (!targetFile.equals(file)) {
            System.out.println("文件生成失败：".concat(relativeFile));
        }
        return relativeFile;
    }

    /**
     * 生成兽药检验委托检验合同
     * @param filename
     * @param dataMap
     * @return
     */
    public static String exportContractPdf(String filename, Map<String, Object> dataMap) {
        String templateFile = getOfficeTemplatePath().concat(SY_CONTRACT_TEMPLATE_NAME);
        String fileSufix = "_ht.pdf";
        String subDictionary = "hetong/";
        String targetFile = ScsyReportUtil.getRealReportPath().concat(subDictionary)
                .concat(filename).concat(fileSufix);
        String file = replaceText(templateFile, targetFile, dataMap, null, SaveFormat.PDF);
        String relativeFile = ScsyReportUtil.getReportPath().concat(subDictionary).concat(filename).concat(fileSufix);
        if (!targetFile.equals(file)) {
            System.out.println("文件生成失败：".concat(relativeFile));
        }
        return relativeFile;
    }

    /**
     * 生成兽药抽检单
     * @param filename
     * @param dataMap
     * @return
     */
    public static String createChoujiandanPdf(String filename, Map<String, Object> dataMap) {
        String templateFile = getOfficeTemplatePath().concat(SY_INSPECTION_TEMPLATE_NAME);
        String fileSufix = ".pdf";
        String subDictionary = "choujian/";
        String targetFile = ScsyReportUtil.getRealReportPath().concat(subDictionary)
                .concat(filename).concat(fileSufix);
        String file = replaceText(templateFile, targetFile, dataMap, null, SaveFormat.PDF);
        String relativeFile = ScsyReportUtil.getReportPath().concat(subDictionary).concat(filename).concat(fileSufix);
        if (!targetFile.equals(file)) {
            System.out.println("文件生成失败：".concat(relativeFile));
        }
        return relativeFile;
    }

    /**
     * 生成检验申请清单
     * @param filename
     * @param dataMap
     * @param lst
     * @return
     */
    public static String createApplyListPdf(String filename, Map<String, Object> dataMap,
                                            List<Map<String, Object>> lst) {
        String templateFile = getOfficeTemplatePath().concat(SY_APPLY_TEMPLATE_NAME);
        String fileSufix = ".pdf";
        String subDictionary = "apply/";
        String targetFile = ScsyReportUtil.getRealReportPath().concat(subDictionary)
                .concat(filename).concat(fileSufix);
        DataTable dt = convert2DataTable(lst, "样品信息");
        String file = replaceText(templateFile, targetFile, dataMap, dt, SaveFormat.PDF);
        String relativeFile = ScsyReportUtil.getReportPath().concat(subDictionary).concat(filename).concat(fileSufix);
        if (!targetFile.equals(file)) {
            System.out.println("文件生成失败：".concat(relativeFile));
        }
        return relativeFile;
    }

    /**
     * 生成兽药检报告
     * @param filename
     * @param mapDatas
     * @return
     */
    public static String createSyPdfReport(String filename, Map<String, Object> mapDatas) {
        String jpbh = (String) mapDatas.get("检品编号");
        String template = SY_REPORT_TEMPLATE_NAME;
        if(jpbh.startsWith("C") || jpbh.startsWith("W")){
            template = "是".equals(mapDatas.get("假兽药")) ? SY_FAKE_TEMPLATE_NAME : SY_INSPECTION_REPORT_TEMPLATE_NAME;
        } else {
            if("是".equals(mapDatas.get("假兽药"))) {
                template = SY_FAKE_TEMPLATE_NAME;
            } else {
                template = SY_REPORT_TEMPLATE_NAME;
            }
        }
//        String template = "是".equals(mapDatas.get("假兽药")) ? SY_FAKE_TEMPLATE_NAME : SY_INSPECTION_REPORT_TEMPLATE_NAME;
        String templateFile = getOfficeTemplatePath().concat(template);
//        String fileSufix = ".pdf";
//        String subDictionary = ScsyReportUtil.SY_SUBDICTIONARY;
        String relativePath = ScsyReportUtil.SY_SUBDICTIONARY.concat(filename).concat(ScsyReportUtil.PDF_SUFFIX);
        String targetFile = ScsyReportUtil.getRealReportPath().concat(relativePath);
        DataTable dt = convert2DataTable((List<Map<String, Object>>)mapDatas.get("检验结果"), "检验结果");
        String file = replaceText(templateFile, targetFile, mapDatas, dt, SaveFormat.PDF);
        String relativeFile = ScsyReportUtil.getReportPath().concat(relativePath);
        if (!targetFile.equals(file)) {
            System.out.println("文件生成失败：".concat(relativeFile));
        }
        return relativeFile;
    }


    /**
     * 将List<Map>数据转换成为DataTable</Map>
     *
     * @param listMap
     * @param tableName DataTable名称 对应模板的《TableStart:TableName》
     * @return
     */
    private static DataTable convert2DataTable(List<Map<String, Object>> listMap, String tableName) {
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

    /**
     * 保存文件前最后做优化调整
     * @param doc
     */
    private static void doBeforeSave(Document doc, String templateFile,DataTable rows){
        if(templateFile.contains(REMAIN_TEMPLATE_NAME)) {
            // 残留检验报告 表格扩展满页面
            Table table = (Table) doc.getChild(NodeType.TABLE, 1, true);
            int index = rows.getRows().getCount() + 12;
            while (index < 25) {
                Node nw = table.getRows().get(index).deepClone(true);
                table.getRows().insert(++index, nw);
            }
        }
        if(templateFile.contains(SY_INSPECTION_REPORT_TEMPLATE_NAME)) {
            // 抽检报告 表格扩展满页面
            Table table = (Table) doc.getChild(NodeType.TABLE, 5, true);
            int index = 1 + rows.getRows().getCount();
            Row lastRow = (Row)table.getRows().get(index).deepClone(true);
            try {
                lastRow.getFirstCell().getParagraphs().get(0).getRuns().get(0).setText("");
                lastRow.getFirstCell().getCellFormat().getBorders().getTop().setLineStyle(LineStyle.NONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            while (index < 30) {
                Node nw = lastRow.deepClone(true);
                table.getRows().insert(++index, nw);
            }
        }
        if(templateFile.contains(SY_REPORT_TEMPLATE_NAME)) {
            // 兽药检验报告 表格扩展满页面
            Table table = (Table) doc.getChild(NodeType.TABLE, 5, true);
            int index = 2 + rows.getRows().getCount();
            Row lastRow = (Row)table.getRows().get(index).deepClone(true);
            try {
                lastRow.getFirstCell().getParagraphs().get(0).getRuns().get(0).setText("");
                lastRow.getFirstCell().getCellFormat().getBorders().getTop().setLineStyle(LineStyle.NONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            while(index <30) {
                Row nw = (Row)lastRow.deepClone(true);
                nw = (Row)table.insertBefore(nw, table.getRows().get(index + 1));
                index++;
            }
        }
    }
    /**
     * 新建Cell
     * @param value
     * @param doc
     * @return
     */
    private static Cell createCell(String value, Document doc) {
        Cell c1 = new Cell(doc);
        Paragraph p = new Paragraph(doc);
        p.appendChild(new Run(doc, value));
        c1.appendChild(p);
        return c1;
    }

    /**
     * 新建row
     * @param columnCount   列数
     * @param columnValues  列值
     * @param doc
     * @return
     */
    private static Row createRow(int columnCount, List<String> columnValues, Document doc) {
        Row r2 = new Row(doc);
        for (int i = 0; i < columnCount; i++) {
            if (columnValues.size() > i) {
                Cell cell = createCell(columnValues.get(i), doc);
                r2.getCells().add(cell);
            } else {
                Cell cell = createCell("", doc);
                r2.getCells().add(cell);
            }
        }
        return r2;
    }

    /**
     * 合并单元格
     * @param startCell
     * @param endCell
     */
    public static void mergeCells(Cell startCell, Cell endCell) {
        Table parentTable = startCell.getParentRow().getParentTable();

        Point startCellPos = new Point(startCell.getParentRow().indexOf(startCell),
                parentTable.indexOf(startCell.getParentRow()));
        Point endCellPos = new Point(endCell.getParentRow().indexOf(endCell),
                parentTable.indexOf(endCell.getParentRow()));
        Rectangle mergeRange = new Rectangle(Math.min(startCellPos.x, endCellPos.x),
                Math.min(startCellPos.y, endCellPos.y),
                Math.abs(endCellPos.x - startCellPos.x) + 1,
                Math.abs(endCellPos.y - startCellPos.y) + 1);

        for (Row row : parentTable.getRows()) {
            for (Cell cell : row.getCells()) {
                Point currentPos = new Point(row.indexOf(cell), parentTable.indexOf(row));

                if (mergeRange.contains(currentPos)) {
                    if (currentPos.x == mergeRange.x)
                        cell.getCellFormat().setHorizontalMerge(CellMerge.FIRST);
                    else
                        cell.getCellFormat().setHorizontalMerge(CellMerge.PREVIOUS);

                    if (currentPos.y == mergeRange.y)
                        cell.getCellFormat().setVerticalMerge(CellMerge.FIRST);
                    else
                        cell.getCellFormat().setVerticalMerge(CellMerge.PREVIOUS);
                }
            }
        }
    }
}
