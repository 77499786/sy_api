import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.view.JasperViewer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportTest {
    public static void main(String[] args) {
        testMain4();
    }

    private static void testMain1(){
        String sourceFileName =
                "C://Users/Administrator/JaspersoftWorkspace/MyReports/remain-page.jasper";
        ArrayList<DataBean> dataList = new ArrayList<DataBean>();
        dataList.add(new DataBean("B17002","样品名车"));
        dataList.add(new DataBean("B17005","样品名城"));
        String printFileName = null;
        JRBeanCollectionDataSource beanColDataSource =
                new JRBeanCollectionDataSource(dataList);

        Map parameters = new HashMap();
        try {
            JasperPrint jasperPrint =JasperFillManager.fillReport(sourceFileName, parameters,beanColDataSource);
            File o = new File("d:/test.pdf");
            JasperExportManager.exportReportToPdfFile(jasperPrint,o.getAbsolutePath());
        }  catch (JRException e) {
            e.printStackTrace();
        }
    }

    private static  void testMain2(){
        String sourceFileName =
                "C://Users/Administrator/JaspersoftWorkspace/MyReports/remain-page.jasper";
        ArrayList<Map> dataList = new ArrayList<Map>();
         String printFileName = null;

        Map parameters = new HashMap();
        parameters.put("检品编号","B180002");
        parameters.put("检验编号","B18X002");
        parameters.put("检品名称","猪肉");
        parameters.put("样品编号","B1810002");
        parameters.put("送样单位","成都市药监局");
        parameters.put("报告日期","2018-03-08");
        parameters.put("样品量","200g");
        parameters.put("送样单编号","180308-001");
        parameters.put("包封情况","lianghao");
        parameters.put("保存情况","冷藏");
        parameters.put("生产单位","成都市");
        parameters.put("运输情况","货运");
        parameters.put("送样单位","乐山市");
        parameters.put("抽样日期","2018-03-18");
        parameters.put("检验项目","全检");
        parameters.put("收检日期","2018-03-20");
        parameters.put("检验依据","检验依据检验依据检验依据检验依据检验依据");
        parameters.put("报告日期","2018-04-01");

        List<Map> results= new ArrayList<Map>();
        for(int i=0; i<= 5; i++ ){
            Map result= new HashMap();
            result.put("检测项目", "检测项目"+i);
            result.put("检测限", "检测限"+i);
            result.put("最高残留量", "最高残留量"+i);
            result.put("检测数据", "检测数据"+i);
            result.put("检测结果", "检测结果"+i);
            results.add(result);
        }
//        parameters.put("检测结果数据",results);
        Map bean = new HashMap();
        bean.put("检品编号","B180001");
        bean.put("检品名称","猪肉");
        bean.put("样品编号","B1810002");
        bean.put("送样单位","成都市药监局35");
        bean.put("报告日期","2018-03-08");
        bean.put("检测结果数据",results);
        dataList.add(bean);
        JRBeanCollectionDataSource beanColDataSource =
                new JRBeanCollectionDataSource(dataList);

        try {
            printFileName = JasperFillManager.fillReportToFile(
                    sourceFileName,
                    parameters,
                    beanColDataSource);
//            String mainFileName= "C://Users/Administrator/JaspersoftWorkspace/MyReports/remain-main.jasper";;
//            String printMainFile = JasperFillManager.fillReportToFile(
//                    mainFileName,
//                    parameters,
//                    beanColDataSource);
            if(printFileName != null) {
                JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObjectFromFile(printFileName);
                if (jasperPrint.getPages().size() > 0) {
//			JasperExportManager.exportReportToPdfFile(jasperPrint,"d:\\test.pdf");
                    JasperViewer.viewReport(jasperPrint, true);
                } else {
                    System.out.println("没有数据！");
                }
            }
        }  catch (JRException e) {
            e.printStackTrace();
        }
    }

    private static  void testMain3(){
        String sourceFileName =
                "C://Users/Administrator/JaspersoftWorkspace/MyReports/Blank_A4.jasper";
        ArrayList<DataBean> dataList = new ArrayList<DataBean>();
        dataList.add(new DataBean("B17002","样品名车"));
        dataList.add(new DataBean("B17005","样品名城"));
        String printFileName = null;
        JRBeanCollectionDataSource beanColDataSource =
                new JRBeanCollectionDataSource(dataList);

        Map parameters = new HashMap();
        try {
            printFileName = JasperFillManager.fillReportToFile(
                    sourceFileName,
                    parameters,
                    beanColDataSource);

            if(printFileName != null) {
                JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObjectFromFile(printFileName);
                if (jasperPrint.getPages().size() > 0) {
			JasperExportManager.exportReportToPdfFile(jasperPrint,"C://Users/Administrator/JaspersoftWorkspace/MyReports/text.pdf");
//                    JasperViewer.viewReport(jasperPrint, true);
                } else {
                    System.out.println("没有数据！");
                }
            }
        }  catch (JRException e) {
            e.printStackTrace();
        }
    }

    private static  void testMain4(){
        String sourceFileName =
                "C://java/work/scsy/export/template/remain-detail.jasper";
        ArrayList<Map> dataList = new ArrayList<Map>();

        Map parameters = new HashMap();
        parameters.put("检品编号","B180002");
        parameters.put("检验编号","B18X002");
        parameters.put("检品名称","猪肉");
        parameters.put("样品编号","B1810002");
        parameters.put("送样单位","成都市药监局");
        parameters.put("报告日期","2018-03-08");
        parameters.put("样品量","200g");
        parameters.put("送样单编号","180308-001");
        parameters.put("包封情况","lianghao");
        parameters.put("保存情况","冷藏");
        parameters.put("生产单位","成都市");
        parameters.put("运输情况","货运");
        parameters.put("送样单位","乐山市");
        parameters.put("抽样日期","2018-03-18");
        parameters.put("检验项目","全检");
        parameters.put("收检日期","2018-03-20");
        parameters.put("检验依据","检验依据检验依据检验依据检验依据检验依据");
        parameters.put("报告日期","2018-04-01");

        List<Map<String, Object>> results= new ArrayList<Map<String, Object>>();
        for(int i=0; i<= 5; i++ ){
            Map result= new HashMap();
            result.put("检测项目", "检测项目"+i);
            result.put("检测限", "检测限"+i);
            result.put("最高残留量", "最高残留量"+i);
            result.put("检测数据", "检测数据"+i);
            result.put("检测结果", "检测结果"+i);
            results.add(result);
        }
//        parameters.put("检测结果数据",results);
        Map<String, Object> bean = new HashMap<String, Object>();
//        bean.put("检品编号","B180001");
//        bean.put("检品名称","猪肉");
//        bean.put("样品编号","B1810002");
//        bean.put("送样单位","成都市药监局35");
//        bean.put("报告日期","2018-03-08");
        bean.put("检测结果数据",results);
        dataList.add(bean);
        JRBeanCollectionDataSource beanColDataSource =
                new JRBeanCollectionDataSource(dataList);

        Map parametermain = new HashMap();
        parametermain.put("检品编号","B180001");
        parametermain.put("检品名称","猪肉");
        parametermain.put("样品编号","B1810002");
        parametermain.put("送样单位","成都市药监局35");
        parametermain.put("报告日期","2018-03-08");
        JRBeanCollectionDataSource mainds =
                new JRBeanCollectionDataSource(dataList);
        try {
            String mainFileName ="C://java/work/scsy/export/template/remain-page.jasper";
            JasperPrint jasperPrintdetail = JasperFillManager.fillReport(mainFileName, parametermain,mainds);
//            JasperViewer.viewReport(jasperPrintdetail, false);
            JasperPrint jasperPrint = JasperFillManager.fillReport(sourceFileName,parameters,beanColDataSource);
            JasperViewer.viewReport(jasperPrint, false);
            List<JasperPrint> list = new ArrayList<JasperPrint>();
            list.add(jasperPrintdetail);
            list.add(jasperPrint);

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(SimpleExporterInput.getInstance(list));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("d:\\\\test.pdf"));
//            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
//            configuration.setCreatingBatchModeBookmarks(true);
//            exporter.setConfiguration(configuration);
            exporter.exportReport();
        }  catch (JRException e) {
            e.printStackTrace();
        }
    }

    private static  void testypqd(){
        String sourceFileName =
                "C://Users/Administrator/JaspersoftWorkspace/MyReports/ypqd.jasper";
        ArrayList<Map> dataList = new ArrayList<Map>();

        Map parameters = new HashMap();
        parameters.put("sqph","B180001");
        parameters.put("scdw","生产单位");

        List<Map> results= new ArrayList<Map>();
        for(int i=0; i<= 5; i++ ){
            Map result= new HashMap();
            result.put("ypbh", "样品编号"+i);
            result.put("ypmc", "样品名称"+i);
            result.put("gg", "规格"+i);
            result.put("jyyj", "检验依据"+i);
            results.add(result);
        }
        Map bean = new HashMap();
        bean.put("sqph","B180001");
        bean.put("scdw","生产单位");
        bean.put("listdata",results);
        dataList.add(bean);
        JRBeanCollectionDataSource beanColDataSource =
                new JRBeanCollectionDataSource(dataList);

        JRBeanCollectionDataSource mainds =
                new JRBeanCollectionDataSource(dataList);
        try {
            String mainFileName ="C://Users/Administrator/JaspersoftWorkspace/MyReports/ypqd.jasper";
            JasperPrint jasperPrint = JasperFillManager.fillReport(sourceFileName,parameters,beanColDataSource);
            JasperViewer.viewReport(jasperPrint, true);
        }  catch (JRException e) {
            e.printStackTrace();
        }
    }

    private static  void testsjpc(){
        String sourceFileName =
                "C://Users/Administrator/JaspersoftWorkspace/MyReports/cjpc.jasper";
        ArrayList<Map> dataList = new ArrayList<Map>();

        Map parameters = new HashMap();

        List<Map> results= new ArrayList<Map>();
        for(int i=0; i<= 5; i++ ){
            Map result= new HashMap();
            result.put("ypbh", "B17000"+i);
            result.put("ypmc", "样品名称"+i);
            result.put("spmc", "商品名称"+i);
            result.put("gg", "规格"+i);
            result.put("scdw", "生产单位"+i);
            result.put("sydw", "被抽单位"+i);
            result.put("jyyj", "检验依据"+i);
            result.put("remark", "备注"+i);
            result.put("pzwh", "批准文号"+i);
            result.put("jybm", "部门"+i);
            results.add(result);
        }
        Map bean = new HashMap();
        bean.put("抽检批号","B170001");
        bean.put("抽检样品清单",results);
        dataList.add(bean);
        JRBeanCollectionDataSource beanColDataSource =
                new JRBeanCollectionDataSource(dataList);

        JRBeanCollectionDataSource mainds =
                new JRBeanCollectionDataSource(dataList);
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(sourceFileName,parameters,beanColDataSource);
            JasperViewer.viewReport(jasperPrint, true);
        }  catch (JRException e) {
            e.printStackTrace();
        }
    }

}

