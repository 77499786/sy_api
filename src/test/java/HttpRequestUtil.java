import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.io.*;
import java.net.*;
import java.security.KeyException;
import java.security.PublicKey;
import java.util.*;

public class HttpRequestUtil {
    static boolean proxySet = false;
    static String proxyHost = "127.0.0.1";
    static int proxyPort = 8087;
    /**
     * 编码
     * @param source
     * @return
     */
    public static String urlEncode(String source,String encode) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source,encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "0";
        }
        return result;
    }
    public static String urlEncodeGBK(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source,"GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "0";
        }
        return result;
    }
    /**
     * 发起http请求获取返回结果
     * @param req_url 请求地址
     * @return
     */
    public static String httpRequest(String req_url) {
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(req_url);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

            httpUrlConn.setDoOutput(false);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.connect();

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return buffer.toString();
    }

    /**
     * 发送http请求取得返回的输入流
     * @param requestUrl 请求地址
     * @return InputStream
     */
    public static InputStream httpRequestIO(String requestUrl) {
        InputStream inputStream = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoInput(true);
            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.connect();
            // 获得返回的输入流
            inputStream = httpUrlConn.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;
    }


    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param isproxy
     *               是否使用代理模式
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param,boolean isproxy) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = null;
            if(isproxy){//使用代理模式
                @SuppressWarnings("static-access")
                Proxy proxy = new Proxy(Proxy.Type.DIRECT.HTTP, new InetSocketAddress(proxyHost, proxyPort));
                conn = (HttpURLConnection) realUrl.openConnection(proxy);
            }else{
                conn = (HttpURLConnection) realUrl.openConnection();
            }
            // 打开和URL之间的连接

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");    // POST方法

            // 设置通用的请求属性

            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("contentType", "GBK");
            conn.connect();

            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            out.write(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"GBK"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPost(String url, Object params ,boolean isproxy) {
//        OutputStreamWriter out = null;
        String result = "";
        try {
            URL realUrl =new URL(url);
            HttpURLConnection httpURLConnection= (HttpURLConnection) realUrl.openConnection();

            httpURLConnection.setConnectTimeout(2000);//设置连接超时时间，单位ms
            httpURLConnection.setReadTimeout(2000);//设置读取超时时间，单位ms

            //设置是否向httpURLConnection输出，因为post请求参数要放在http正文内，所以要设置为true
            httpURLConnection.setDoOutput(true);
            //设置是否从httpURLConnection读入，默认是false
            httpURLConnection.setDoInput(true);
            //POST请求不能用缓存，设置为false
            httpURLConnection.setUseCaches(false);

            //传送的内容是可序列化的
            //如果不设置此项，传送序列化对象时，当WEB服务默认的不是这种类型时，会抛出java.io.EOFException错误
            httpURLConnection.setRequestProperty("Content-type","application/x-java-serialized-object");
            //设置请求方法是POST
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("contentType", "GBK");
            //连接服务器
            httpURLConnection.connect();

            //getOutputStream会隐含调用connect()，所以不用写上述的httpURLConnection.connect()也行。
            //得到httpURLConnection的输出流
            OutputStream os= httpURLConnection.getOutputStream();
            //构建输出流对象，以实现输出序列化的对象
            ObjectOutputStream objOut=new ObjectOutputStream(os);
            //向对象输出流写出数据，这些数据将存到内存缓冲区中
//            objOut.writeObject(params);
            objOut.write(params.toString().getBytes("UTF-8"));
            //刷新对象输出流，将字节全部写入输出流中
            objOut.flush();
            //关闭流对象
            objOut.close();
            os.close();

            //将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端，并获取访问状态
            if(HttpURLConnection.HTTP_OK==httpURLConnection.getResponseCode()){
//                //得到httpURLConnection的输入流，这里面包含服务器返回来的java对象
//                InputStream in=httpURLConnection.getInputStream();
                BufferedReader in =  new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"GBK"));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
//                //构建对象输入流，使用readObject()方法取出输入流中的java对象
//                ObjectInputStream inObj=new ObjectInputStream(in);
//                JSONObject ret = (JSONObject) inObj.readObject();
//                //取出对象里面的数据
//                result=ret.toString();
                //关闭创建的流
                in.close();
//                inObj.close();
            }else{
                System.out.println("发送 POST 连接失败！");
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        queryIds();
        return;
    }

    private static List<String> listdata(){
        List<String> idList = new ArrayList<String>();
        idList = Arrays.asList("成都科宏达科技有限公司","成都科锐动物药业有限公司","成都纳比生物科技有限公司","成都市腾飞动物药业有限公司","成都通威三新药业有限公司","广东万士达动物药业有限公司","广东粤海生物科技有限公司","广西普大动物保健品有限公司","广西三晶兽药有限公司","广西正通瑞和生物制药有限公司","广州白云华南生物科技有限公司","广州白云牧之林动物药厂","广州白云山宝神动物保健品有限公司","广州市畜牧科学研究所动物保健药实验厂","广州市汇鑫动物药业有限公司","广州威生医药科技有限公司","广州珠江化工集团有限公司广州溶剂厂","哈尔滨中农华大兽药有限公司","哈药集团有限公司兽药厂","杭州富阳市动物保健药品厂","合肥强力动物药品有限责任公司","河北普德动物药业有限公司","河北仁得动物药业有限公司","河北瑞高动物药业有限公司","河北润普兽药有限公司","河北象大合众生物科技有限公司","河北迅清生物科技有限公司","河南安信生物科技股份有限公司","河南大德广动物药业有限公司","河南富朗赛尔动物药业有限公司","河南官渡兽药制造有限公司","河南济福生物科技有限公司","河南孟成生物药业股份有限公司","河南明磊动物药业有限公司","河南普旺生物工程有限公司","河南祺祥生物科技有限公司","河南神农药业有限公司","河南省奥邦生物科技有限公司","河南省康莱特生物科技有限公司","河南盛福隆动物药业有限公司","河南盛利来天府动物药业有限公司","河南伟龙兽药有限公司","河南豫神劲牛动物保健品有限公司","菏泽鸿升药业有限公司","黑龙江搏凯兽药有限公司","黑龙江省富兰特动物药业有限公司","黑龙江省富裕县富隆兽药有限公司","湖北启达药业有限公司","湖南德美生物工程有限公司","湖南科达生物科技有限责任公司","湖南美可达生物资源股份有限公司","湖南省亚牧动物药业有限公司","湖南顺信生物科技有限公司","湖南泰丰动物药业有限公司","湖州天丝动物保健有限公司","吉林省塞诺动物药业有限公司","济南德慧兽药有限公司","济南联达畜牧科技有限公司","济南鲁发兽药有限公司","济南深蓝动物保健品有限公司","济南新星动物药业有限公司","济南正源动物保健品有限公司","江苏华东贝尔生物药业有限公司","江苏九阳生物制药有限公司","江苏威泰龙生物科技有限公司","江苏长青兽药有限公司","江西成必信生物科技有限公司","江西赣州百灵动物药业有限公司","江西高胜动物保健品有限公司","江西人为峰药业有限公司","江西三同生物科技有限公司","江西天正动物药业有限公司","江西鑫瑞动物药业有限公司","江西英特科胜动保科技有限公司","江西友道药业有限公司","揭西县金瑞科兽药有限公司","京山瑞生制药有限公司","荆门亚卫江峰药业有限公司","昆山博青生物科技有限公司","兰溪市蚕药厂","辽宁宏泰药业有限公司","临沂市乐邦兽药饲料科技发展有限公司","凌源中天凯迪药业有限公司","罗定市天威动物药业有限公司","罗定市友诺动物药业有限公司","洛阳惠中兽药有限公司","洛阳瑞莱动物保健品有限公司","漯河汇德生物科技有限公司","南京金土地生物技术有限公司","南京科杰生物技术有限公司","内蒙古北奇药械有限公司","青岛北海兽药有限公司","青岛康地恩动物药业有限公司","青岛齐奥生物科技有限公司","青岛天元普康生物技术有限公司","日照康祥生物药业有限公司","山东奥迪尔生物集团有限公司","山东百和药业有限公司","山东邦和康欣生物科技有限公司","山东博泰生物制药有限公司","山东大禹动物药业有限公司","山东华尔康兽药有限公司","山东华宏生物工程有限公司","山东华森生物科技有限公司","山东济宁永立生物科技有限公司","山东济兴制药有限公司","山东佳蕴德生物科技有限公司","山东金太阳制药有限公司","山东金洋生物药业有限公司","山东鲁冠生物科技有限责任公司","山东鲁诺动物药业有限公司","山东启宏生物科技有限公司","山东瑞环制药有限公司","山东泰丰生物制品有限公司","山东闻达药业有限公司","山东翔禾药业有限公司","山东易和天生物科技有限公司","山东郓城硕宇动物药业有限公司","山东正牧生物药业有限公司","山东中抗药业有限公司","山西奥信动物药业有限公司","山西美默克生物技术有限公司","陕西格瑞特生物药业有限公司","陕西杨凌科大动物药品有限责任公司","商丘光华生物科技有限公司","上海贝灵生物科技有限公司","上海洪菲生物技术有限公司","上海天田科技有限公司","深泽县三洁化工有限公司","深州万嘉生物科技有限公司","沈阳维隆动物药业有限公司","沈阳伟嘉牧业技术有限公司","石家庄博林动物药品厂","石家庄和牧佳动物药业有限公司","石家庄市力维兽药厂","石家庄市一诺动物药业有限公司","石家庄市宇龙动物药业有限公司","石家庄天牧动物药业有限公司","世纪中升（天津）生物技术有限公司","四川伴农动保生物技术有限公司","四川好益特动物保健药品有限公司","四川禾迪动物药业有限公司","四川金瑞克动物药业有限公司","四川康四海动物药业有限公司","四川省隆昌佳利动物药业有限公司","四川省万鑫动物药业有限公司","四川省雄丰动物药业有限公司","四川喜亚动物药业有限公司","泰安市泰山神药业有限公司","泰安市祝阳蚕药厂","泰安威思特生物工程有限公司","唐山怡安生物工程有限公司","天津佰力喜动物药业有限公司","天津福临科技有限公司","天津康莱森生物科技集团有限公司","天津生机集团股份有限公司","天津市新星兽药厂","天泽恩源（天津）制药有限公司","潍坊格润药业有限公司","潍坊金海龙生化科技有限公司","潍坊普力美动物药业有限公司","潍坊市生生兽药有限公司","潍坊万博动物药业有限公司","潍坊永晟药业有限公司","潍坊中和动物药业有限公司","潍坊中牧利德药业有限公司","吴赣药业（苏州）有限公司","武汉九州神农药业有限责任公司","武汉科前生物股份有限公司","武汉施瑞福生物技术有限公司","西安汉堡生物技术发展有限公司","西安康乐动物药业有限公司","西安乐道生物科技有限公司","西藏自治区兽医生物药品制造厂","新疆西农动物药品有限责任公司","新乡市柏杨牧业有限公司柏兰制药分公司","徐州龙生药业有限公司","徐州龙生药业有限公司","许昌中盛高科制药有限公司","烟台汉尔德兽药有限公司","烟台金海药业有限公司","扬州优邦生物药品有限公司","宜兴市苏亚达生物技术有限公司","禹州华邈动物药业有限公司","张家口牧安生物科技有限公司","长春圣泰动物药业有限公司","长沙施比龙动物药业有限公司","浙江大飞龙动物保健品股份有限公司","浙江黄岩兽药厂","正大预混料（广汉）有限公司","郑州安博林生物科技有限公司","郑州百瑞动物药业有限公司","郑州大牧原动物药业有限公司","郑州华德动物药业有限公司","郑州华诺生物科技有限公司","郑州惠灵禽药有限公司","郑州市汉方畜牧科技有限公司","郑州威兰动物药业有限公司","郑州中德生物技术有限公司","重庆嘉瑞动物生化药业有限公司","重庆市吉亨动物药业有限责任公司","重庆正通药业有限公司","重庆综艺制药有限公司");
        return idList;
    }
    private static  void querybyname(String name){
        // 读取数据范围的数据id数组
        String url = "http://sysjk.ivdc.org.cn:8081/cx/query_syscqysj/";
        JSONObject cond = new JSONObject();
        cond.put("itemname","qymc");
        cond.put("itemfieldname","qymc");
        cond.put("itemval",name);
        cond.put("itemtype","String");
        cond.put("condType","val");
        JSONArray condList=new JSONArray();
        condList.add(cond);
//        System.out.println(condList.toString());
        String condListUTF="";
        try {
            condListUTF = URLEncoder.encode(condList.toString(), "UTF-8"); // "[{\"itemname\":\"qymc\",\"itemfieldname\":\"qymc\",\"itemval\":\"中大安特\",\"itemtype\":\"String\",\"condType\":\"val\"}]", "UTF-8");
        } catch (Exception e){
            e.printStackTrace();
        }
        String para = "start=0&limit=20&condList="+condListUTF+"&isGjcx=0";
//        System.out.println(para);
        String sr=HttpRequestUtil.sendPost(url +"querysyscqyinfo.do",para,false);
//        System.out.println(sr);

        JSONArray dataList = JSONObject.parseObject(sr).getJSONArray("rows");
        Map<String, String>  idmapp = new HashMap<String, String>();
        List<String> idList = new ArrayList<String>();
        for(int i= 0; i<dataList.size();i++){
            JSONObject object = dataList.getJSONObject(i);
            String id = object.getString("itemid");
            idmapp.put(id,object.getString("qymc"));
            idList.add(id);
        }
//        System.out.println(name + ":"+ idList.toString());

        try {
            String itemidList = URLEncoder.encode(idList.toString(), "UTF-8"); // "[{\"itemname\":\"qymc\",\"itemfieldname\":\"qymc\",\"itemval\":\"中大安特\",\"itemtype\":\"String\",\"condType\":\"val\"}]", "UTF-8");
            String param = "itemidList=[\""+idList.get(0)+ "\"]"+itemidList;
            String datas =HttpRequestUtil.sendPost(url +"querysyscqydetail.do",param,false);
            if(datas.length() <= 0){
                return;
            }
            JSONArray objArr = JSONObject.parseObject(datas).getJSONArray("rows");
            String id = objArr.getJSONObject(0).getString("itemid");
            String mc = objArr.getJSONObject(0).getString("qymc");
            String dz = objArr.getJSONObject(0).getString("scdz");
//            System.out.println(datas);
            System.out.println(id+";"+mc+":"+ dz);
        } catch (Exception e){
            e.printStackTrace();
        }


    }

    private static  void queryIds(){
        // 读取数据范围的数据id数组
        String url = "http://sysjk.ivdc.org.cn:8081/cx/query_syscqysj/";

        String para = "start=0&limit=2500&isGjcx=0";
        String sr=HttpRequestUtil.sendPost(url +"querysyscqyinfo.do",para,false);

        JSONArray dataList = JSONObject.parseObject(sr).getJSONArray("rows");
        Map<String, String>  idmapp = new HashMap<String, String>();
        List<String> idList = new ArrayList<String>();
        for(int i= 0; i<dataList.size();i++){
            JSONObject object = dataList.getJSONObject(i);
            String id = object.getString("itemid");
            idmapp.put(id,object.getString("qymc"));
            idList.add(id);
        }
        for(String id : idList){
            idmapp.put(id, querybyid(id));
        }

        System.out.println("数据读取完成");
        // 输出到文件
        FileWriter fw = null;
        String path = "D:\\Temp\\list.txt";
        File f = new File(path);
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            fw = new FileWriter(f);
            BufferedWriter out = new BufferedWriter(fw);
            for(Map.Entry<String, String> e: idmapp.entrySet()) {
                if(e.getValue().length()> 0){
                    out.write(e.getKey()+";"+e.getValue()+"\r\n");
                }
            }
            for(Map.Entry<String, String> e: idmapp.entrySet()) {
                if(e.getValue().length()<= 0){
                    out.write(e.getKey()+";"+e.getValue()+"\r\n");
                }
            }
            out.close();
            System.out.println("===========写入文本成功========");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static  String querybyid(String id){
        // 读取数据范围的数据id数组
        String url = "http://sysjk.ivdc.org.cn:8081/cx/query_syscqysj/";

        try {
            String param = "itemidList=[\""+id+ "\"]";
            String datas =HttpRequestUtil.sendPost(url +"querysyscqydetail.do",param,false);
            if(datas.length() <= 0){
                return "";
            }
            JSONArray objArr = JSONObject.parseObject(datas).getJSONArray("rows");
            String mc = objArr.getJSONObject(0).getString("qymc");
            String dz = objArr.getJSONObject(0).getString("scdz");
           return datas ;
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    private static void testAllData(){
        // 读取数据范围的数据id数组
        String url = "http://sysjk.ivdc.org.cn:8081/cx/query_syscqysj/";
        String para = "start=1&limit=2500&isGjcx=0";

        String sr=HttpRequestUtil.sendPost(url +"querysyscqyinfo.do",para,false);
//        System.out.println(sr);
        JSONArray dataList = JSONObject.parseObject(sr).getJSONArray("rows");
        Map<String, String>  mapData = new HashMap<String, String>();
        List<String> idList = new ArrayList<String>();
        for(int i= 0; i<dataList.size();i++){
            JSONObject object = dataList.getJSONObject(i);
            String id = object.getString("itemid");
            mapData.put(id,object.toString());
            idList.add(id);
        }
        // 输出到文件
        FileWriter fw = null;
        String path = "D:\\Temp\\list.txt";
        File f = new File(path);
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            fw = new FileWriter(f);
            BufferedWriter out = new BufferedWriter(fw);
            for(Map.Entry<String, String> e: mapData.entrySet()) {
                out.write(e.getKey()+";"+e.getValue()+"\r\n");
            }
            out.close();
            System.out.println("===========写入文本成功========");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static  void testsysj(){
        // 读取数据范围的数据id数组
        String url = "http://sysjk.ivdc.org.cn:8081/cx/query_syscqysj/";
        String para = "start=0&limit=2500&isGjcx=0";

        String sr=HttpRequestUtil.sendPost(url +"querysyscqyinfo.do",para,false);
//        System.out.println(sr);
        JSONArray dataList = JSONObject.parseObject(sr).getJSONArray("rows");
        Map<String, String>  idmapp = new HashMap<String, String>();
        List<String> idList = new ArrayList<String>();
        for(int i= 0; i<dataList.size();i++){
            JSONObject object = dataList.getJSONObject(i);
            String id = object.getString("itemid");
            idmapp.put(id,object.getString("qymc"));
            idList.add(id);
        }

//        idList = listdata();
        // 读取完整信息
        int start = 0;
        int step= 1;
        while(start < idList.size()){
            int endindex = start + step ;
            if(idList.size() < start + step ){
                endindex = idList.size();
            }
            String param = "itemidList="+idList.subList(start, endindex).toString();
            String datas =HttpRequestUtil.sendPost(url +"querysyscqydetail.do",param,false);
            if(datas.length() <= 0){
                start+= step;
                continue;
            }
            JSONArray objArr = JSONObject.parseObject(datas).getJSONArray("rows");
            for(int i= 0; i<objArr.size();i++){
                String id = objArr.getJSONObject(i).getString("itemid");
                String mc = objArr.getJSONObject(i).getString("qymc");
                String dz = objArr.getJSONObject(i).getString("scdz");
                if(idmapp.get(id).contains(mc) && !idmapp.get(id).contains(dz)){
                    idmapp.put(id, idmapp.get(id).concat(";").concat(dz));
                } else if(!idmapp.get(id).contains(mc)){
                    idmapp.put(id, idmapp.get(id).concat("OR").concat(mc).concat(";").concat(dz));
                }
            }
            start+= step;
            if(start%400 == 0){
                System.out.println(start *100/ idList.size());
            }
        }
        System.out.println("数据读取完成");
        // 输出到文件
        FileWriter fw = null;
        String path = "D:\\Temp\\list.txt";
        File f = new File(path);
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            fw = new FileWriter(f);
            BufferedWriter out = new BufferedWriter(fw);
            for(Map.Entry<String, String> e: idmapp.entrySet()) {
                if(e.getValue().length()> 0){
                    out.write(e.getKey()+";"+e.getValue()+"\r\n");
                }
            }
            for(Map.Entry<String, String> e: idmapp.entrySet()) {
                if(e.getValue().length()<= 0){
                    out.write(e.getKey()+";"+e.getValue()+"\r\n");
                }
            }
            out.close();
            System.out.println("===========写入文本成功========");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
