package com.forest.utils;

import com.forest.home.model.HomeIOInfor;
import com.google.common.base.CaseFormat;
import com.google.common.base.Function;
import com.google.common.collect.*;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OfficeUtil {

//    @Value("${spring.profiles.reportpath}")
    private static String PATH_OF_REPORT = "/report";
//    @Value("${spring.profiles.archivepath}")
    private static String PATH_OF_REPORT_ARCHIVE = "/archive";
//    @Value("${spring.profiles.filefolder}")
    private static String systemPath_of_template;

    /**
     * 设置系统文件管理根目录
     * @param path 系统文件管理根目录
     */
    public static void setSystemRootPath(String path){
        systemPath_of_template = path;
    }
    /**
     * @return 系统文件根目录（绝对位置）
     */
    public static String getSystemRootPath(){
        return systemPath_of_template;
    }
    /**
     * 获取目录的绝对路径
     * @param relativePath 文件目录相对位置
     * @return
     */
    public static String getRealReportPath(String relativePath) {
        return String.format("%s%s", getSystemRootPath(), relativePath);
    }

    /**
     * 获取目录的相对路径(错误的目录地址返回null)
     * @return
     */
    public static String getRelativePath(String absolutePath) {
        if(absolutePath.contains(systemPath_of_template)){
            return absolutePath.substring(systemPath_of_template.length());
        }
        return null;
    }

    /**
     * 输出Word文档
     * @param filename
     */
    private static String exportWordFile(XWPFDocument template, String filename){
        try {
            String filepath = getRealReportPath(filename);
            OutputStream os = new FileOutputStream(filepath);
            // 把doc输出到输出流中
            template.write(os);
            os.close();
            return filepath;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * 编辑表格行数据(逐格替换）
     * @param row 表格行
     * @param rowdatas 行数据
     */
    private static void editWordTableRow(XWPFTableRow row, List<String> rowdatas) {
        int col = 0;
        for (XWPFTableCell cell : row.getTableCells()) {
            List<XWPFParagraph> paras = cell.getParagraphs();
            for (XWPFParagraph para : paras) {
//                replaceInPara(para, rowdatas.get(col++));
            }
        }
    }

    /**
     * 正则匹配字符串
     * @param str
     * @return
     */
    private static Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\{\\{(.+?)\\}\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }
    /**
     * 解析等待替换的标志
     * @param text
     * @return
     */
    private static List<String> getMarks(String text) {
        List<String> lst = new ArrayList<String>();
//        String pattern = "\\{\\{(.*?)\\}\\}";
//        // 创建 Pattern 对象
//        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = matcher(text);
        while(m.find()){
            lst.add(m.group().replace("\\{\\{","").replace("\\}\\}",""));
        }
        return lst;
    }

    /**
     * 格式化 需要替换的内容，用于内容替换
     * @param name 把列名添加前后标志“{*}”
     * @return
     */
    private static String getFormatMark(String name) {
        return String.format("{{%s}}", name);
    }

    /**
     * 按段落进行替换
     * @param map   输出数据map
     * @param paragraph 段落对象
     */
    private static void replaceInParagraph(Map<String, String> map, XWPFParagraph paragraph){
        map.forEach((k, v) -> {
            paragraph.getRuns().forEach(xwpfRun -> {
                if(k.equals(xwpfRun.getText(xwpfRun.getTextPosition()))){
                    xwpfRun.setText(v,0);
                }
            });
        });
    }

    /**
     * 根据原始模板替换数据，生成新文件
     * @param srcPath   模板文件（绝对路径）
     * @param destPath  生成文件（绝对路径）
     * @param map   替换用数据map
     * @return XWPFDocument对象，操作失败时返回Null
     */
    public static XWPFDocument searchWordReplace(String srcPath, String destPath,Map<String, String> map) {
        try {
            XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(srcPath));
            /**
             * 替换段落中的指定文字
             */
            Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
            while (itPara.hasNext()) {
                replaceInParagraph(map,(XWPFParagraph) itPara.next());
            }

            /**
             * 替换表格中的指定文字
             */
            Iterator<XWPFTable> itTable = document.getTablesIterator();
            while (itTable.hasNext()) {
                XWPFTable table = (XWPFTable) itTable.next();
                int count = table.getNumberOfRows();
                for (int i = 0; i < count; i++) {
                    XWPFTableRow row = table.getRow(i);
                    List<XWPFTableCell> cells = row.getTableCells();
                    for (XWPFTableCell cell : cells) {
                        cell.getParagraphs().forEach(p->{
                            replaceInParagraph(map,p);
                        });
                    }
                }
            }
            return document;
//            FileOutputStream outStream = null;
//            outStream = new FileOutputStream(destPath);
//            document.write(outStream);
//            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 公共的到处excel方法
     * @param list 数据集合
     * @param map 属性名称对照关系
     * @return 返回导出的Workbook，导出错误时返回null
     */
    public static XSSFWorkbook excelOutput(List list, String sheetname,BiMap<String, String> map){
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();//创建Excel工作簿对象
            XSSFSheet sheet = workbook.createSheet();//在工作簿中创建工作表对象
            workbook.setSheetName(0, sheetname);//设置工作表的名称

            XSSFRow row = null;//行
            XSSFCell cell = null;//列

            //创建表头，使用迭代器进行迭代
            int _x=0;
            row = sheet.createRow(0);//创建第1行
            for(Map.Entry<String, String> entry: map.entrySet()){
                cell=row.createCell(_x);//新增一列
                cell.setCellType(CellType.STRING);//设置单元格的数据格式为字符串
                XSSFCellStyle cellType = workbook.createCellStyle();
                cellType.setAlignment(HorizontalAlignment.CENTER);
                XSSFFont font = workbook.createFont();
                font.setBold(true);
                cellType.setFont(font);
                cell.setCellStyle(cellType);
                cell.setCellValue(entry.getValue()); //向单元格中写入数据
                _x++;
            }

            // 写入文件内容
            int _i=1;//写入文件的行的内容的开始
            for (Object rowObje: list) {
                row = sheet.createRow(_i);//创建第二行到最后一行
                Class<?> clz = rowObje.getClass();//list集合的所有对象

                int _x1=0;//写入文件内容的列的开始
                for(Map.Entry<String, String> entry: map.entrySet()){
                    Field field = clz.getDeclaredField(entry.getKey());//通过反射，通过键获取类的所有的名称和数据类型
                    String fieldType = field.getGenericType().toString();//通过反射获取键的数据类型
                    String methodName = "get";
                    if (("class java.lang.Boolean").equals(fieldType)) {//布尔类型的是特殊的
                        methodName = "is";
                    }

                    //通过反射，得到对象的get方法和数据格式
                    Method m = (Method) rowObje.getClass().getMethod(
                            methodName.concat(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, entry.getKey())));
                    Object value = m.invoke(rowObje);//反射调用对象里面的方法

                    cell = row.createCell(_x1);//新增1列
                    cell.setCellType(getCellType(field));
//                    cell.setCellType(CellType.STRING);//给单元格设置数据格式，为字符串
                    cell.setCellValue(value.toString());
                    _x1++;
                }
                _i++;
            }
            return workbook;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据对象属性类型判断Excel单元格属性
     * @param field 对象属性
     * @return 单元格属性
     */
    private static CellType getCellType(Field field){
        String fieldType = field.getGenericType().toString();
        CellType retType = CellType.STRING;
        switch (fieldType){
            case "class java.lang.Integer":
                retType = CellType.NUMERIC;
            case "class java.util.Date":
                retType = CellType.STRING;
            case "class java.lang.Boolean":
                retType = CellType.BOOLEAN;
            default:
                retType = CellType.STRING;
        }
        return retType;
    }
    /**
     * @param file 上传的文件
     * @return 检查失败信息，检查成功时返回null
     */
    public static String checkXlsxFile(MultipartFile file){
        //判断文件是否存在
        if(null == file){
            return "文件不存在！";
        }
        //获得文件名
        String fileName = file.getOriginalFilename();
        //判断文件是否是excel文件
        if(!fileName.toLowerCase().endsWith("xlsx")){
            return fileName + "不是excel文件";
        }
        return null;
    }

    /**
     * @param cell 单元格
     * @return  单元格的内容，如果读取异常返回“非法字符”或者“未知类型”
     */
    private static String getCellValue(XSSFCell cell){
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if(cell.getCellTypeEnum().equals(CellType.NUMERIC)){
            cell.setCellType(CellType.STRING);
        }
        //判断数据的类型
        switch (cell.getCellTypeEnum()){
            case NUMERIC: //数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case BLANK: //空值
                cellValue = "";
                break;
            case ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    /**
     * @param file 上传的文件
     * @return excel解析后的全部数据对象,读取失败的单元格转换为“非法字符”或者“未知类型”
     * @throws IOException
     */
    public static Map<String, Multimap<Integer,String>> readExcelData(MultipartFile file) throws IOException {
        Map<String, Multimap<Integer,String>> mapresult = new HashMap<String, Multimap<Integer,String>>();
        //获取excel文件的io流
        InputStream is = file.getInputStream();
        @SuppressWarnings("resource")
        XSSFWorkbook workbook = new XSSFWorkbook(is);
        if(workbook!=null){
            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){
                //获得当前sheet工作表
                XSSFSheet sheet = workbook.getSheetAt(sheetNum);
                if(sheet == null){
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum  = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
//                List<Multimap<String,String>> mapsheet = new ArrayList<>();
                Multimap<Integer,String> multimap = ArrayListMultimap.create();
                //循环所有行（第一行作为标题行）
                for(int rowNum = firstRowNum;rowNum <= lastRowNum;rowNum++){
                    //获得当前行
                    XSSFRow row = sheet.getRow(rowNum);
                    if(row == null || row.getFirstCellNum() < 0){
                        continue;
                    }
                    //获得当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                    //获得当前行的列数
                    int lastCellNum = row.getPhysicalNumberOfCells();
                    //循环当前行
                    for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){
                        XSSFCell cell = row.getCell(cellNum);
                        multimap.put(rowNum,getCellValue(cell) );
                    }
                }
                mapresult.put(sheet.getSheetName(), multimap);
            }
        }
        return mapresult;
    }

    /**
     * 读取指定sheet的数据转换成对应的class对象
     * @param file  上传的文件
     * @param sheetname sheet名称
     * @param map   对象属性与列名称的对应关系
     * @param clz   转换对象class
     * @return  List<T>
     * @throws IOException
     * @throws Exception
     */
    public static List readExcelData(MultipartFile file, String sheetname, BiMap<String, String> map, Class clz) throws IOException,Exception {
        List list = new ArrayList();

        //获取excel文件的io流
        InputStream is = file.getInputStream();
        @SuppressWarnings("resource")
        XSSFWorkbook workbook = new XSSFWorkbook(is);
        if(workbook == null) {
            return list;
        }
        //获得当前sheet工作表
        XSSFSheet sheet = workbook.getSheet(sheetname);
        if(sheet == null){
            return list;
        }
        //获得当前sheet的开始行
        int firstRowNum  = sheet.getFirstRowNum();
        //获得当前sheet的结束行
        int lastRowNum = sheet.getLastRowNum();

        Multimap<String,String> multimap = ArrayListMultimap.create();
        //循环所有行（第一行作为标题行）
        for(int rowNum = firstRowNum;rowNum <= lastRowNum;rowNum++){
            //获得当前行
            XSSFRow row = sheet.getRow(rowNum);
            if(row == null){
                continue;
            }
            //获得当前行的开始列
            int firstCellNum = row.getFirstCellNum();
            //获得当前行的列数
            int lastCellNum = row.getPhysicalNumberOfCells();
//            multimap = ArrayListMultimap.create();
            //循环当前行
            for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){
                XSSFCell cell = row.getCell(cellNum);
                multimap.put(String.valueOf(rowNum),getCellValue(cell) );
            }
        }
        // 整理数据转换为list
        BiMap<String, Integer> map_index =  HashBiMap.create();
        List<String> headers = Lists.transform(Arrays.asList(multimap.get("0").toArray())
                ,new Function<Object, String>(){
            @Nullable
            @Override
            public String apply(@Nullable Object val) {
                return String.valueOf(val);
            }
        });
        for(String key  : map.inverse().keySet()){
            map_index.put(key, headers.indexOf(key));
        }
        // 读取数据
        int _row = 1;
        while(_row < multimap.size()){
            List<String> data = Lists.transform(Arrays.asList(multimap.get(String.valueOf(_row)).toArray())
                    ,new Function<Object, String>(){
                        @Nullable
                        @Override
                        public String apply(@Nullable Object val) {
                            return String.valueOf(val);
                        }
                    });
            if(data.size() == 0){
                _row++;
                continue;
            }
            Object dataobj = clz.newInstance();
            for(String key : map_index.keySet()){
                String methodName = "set".concat(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, map.inverse().get(key)));

                //通过反射，得到对象的get方法和数据格式
                Method m = (Method)clz.getMethod(methodName,String.class);
                m.invoke(dataobj, data.get(map_index.get(key)));//反射调用对象里面的方法
            }
            list.add(dataobj);
            _row++;
        }
        return list;
    }

    /**
     * Map数据转换为Bean对象数组
     * @param datamap   Map数据
     * @param map   数据列与属性对照关系
     * @param clz   Bean对象Class
     * @return  List
     * @throws Exception 转换报异常
     */
    public static List map2Bean(Multimap<Integer,String> datamap, BiMap<String, String> map, Class clz) throws Exception {
        List list = new ArrayList();

        // 读取标题行
        BiMap<String, Integer> map_index =  HashBiMap.create();
        List<String> headers = Lists.transform(Arrays.asList(datamap.get(0).toArray())
                ,new Function<Object, String>(){
                    @Nullable
                    @Override
                    public String apply(@Nullable Object val) {
                        return String.valueOf(val);
                    }
                });
        for(String key  : map.inverse().keySet()){
            map_index.put(key, headers.indexOf(key));
        }

        // 读取数据
        int _row = 1;
        while(_row < datamap.size()){
            List<String> data = Lists.transform(Arrays.asList(datamap.get(_row).toArray())
                    ,new Function<Object, String>(){
                        @Nullable
                        @Override
                        public String apply(@Nullable Object val) {
                            return String.valueOf(val);
                        }
                    });
            if(data.size() == 0){
                _row++;
                continue;
            }
            Object dataobj = clz.newInstance();
            for(String key : map_index.keySet()){
                String methodName = "set".concat(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, map.inverse().get(key)));

                //通过反射，得到对象的get方法和数据格式
                Field field  = clz.getField(map.inverse().get(key));
                Method m = null;
                switch (field.getGenericType().toString()){
                    case "class java.lang.Integer":
                        m = (Method)clz.getMethod(methodName,Integer.class);
                        m.invoke(dataobj, Integer.valueOf(data.get(map_index.get(key))));//反射调用对象里面的方法
                    case "class java.lang.Long":
                        m = (Method)clz.getMethod(methodName,Long.class);
                        m.invoke(dataobj, Long.valueOf(data.get(map_index.get(key))));//反射调用对象里面的方法
                    case "class java.lang.Float":
                        m = (Method)clz.getMethod(methodName,Float.class);
                        m.invoke(dataobj, Float.valueOf(data.get(map_index.get(key))));//反射调用对象里面的方法
                    case "class java.lang.Short":
                        m = (Method)clz.getMethod(methodName,Short.class);
                        m.invoke(dataobj, Short.valueOf(data.get(map_index.get(key))));//反射调用对象里面的方法
                    case "class java.lang.Double":
                        m = (Method)clz.getMethod(methodName,Double.class);
                        m.invoke(dataobj, Double.valueOf(data.get(map_index.get(key))));//反射调用对象里面的方法
                    case "class java.lang.Character":
                        m = (Method)clz.getMethod(methodName,Character.class);
                        m.invoke(dataobj, Character.codePointAt(data.get(map_index.get(key)),0));//反射调用对象里面的方法
                    case "class java.lang.Boolean":
                        m = (Method)clz.getMethod(methodName,Boolean.class);
                        m.invoke(dataobj, Boolean.valueOf(data.get(map_index.get(key))));//反射调用对象里面的方法
                    case "class java.util.Date":
                        m = (Method)clz.getMethod(methodName,Date.class);
                        m.invoke(dataobj, DateUtils.valueOf(data.get(map_index.get(key))));//反射调用对象里面的方法
                    default:
                        m = (Method)clz.getMethod(methodName,String.class);
                        m.invoke(dataobj, data.get(map_index.get(key)));//反射调用对象里面的方法
                }
            }
            list.add(dataobj);
            _row++;
        }
        return list;
    }

    public static  void main(String[] args) throws  Exception{
        // excel测试
        BiMap<String, String> map = HashBiMap.create();
        map.put("ymd", "消费日期");
        map.put("lx", "消费类型");
        map.put("money", "消费金额");
        map.put("remark", "说明");
        List<HomeIOInfor> list = Lists.newArrayList(
                new HomeIOInfor("2018-07-01", "饮食娱乐","30", "午餐"),
                new HomeIOInfor("2018-07-02", "饮食娱乐","20", "午餐"),
                new HomeIOInfor("2018-07-03", "饮食娱乐","16", "午餐"),
                new HomeIOInfor("2018-07-04", "饮食娱乐","78", "午餐"),
                new HomeIOInfor("2018-07-05", "饮食娱乐","34", "午餐"),
                new HomeIOInfor("2018-07-05", "佳通费","98", "充值"));
//        String str = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, "ymd");
//        System.out.println(str);
        XSSFWorkbook document =  excelOutput(list, "消费记录", map);
        String destPath = "D:/temp/sample.xlsx";
        if(document != null){
            FileOutputStream outStream = null;
            outStream = new FileOutputStream(destPath);
            document.write(outStream);
            outStream.close();
        }
//        MultipartFile f = getMulFileByPath(destPath);
//        List<HomeIOInfor> lista  = readExcelData(null, "消费记录",map, HomeIOInfor.class);
//        for(HomeIOInfor e : lista){
//            System.out.println(e.toString());
//        }
    }

}
