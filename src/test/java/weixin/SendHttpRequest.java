package weixin;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSONObject;


public class SendHttpRequest {

    public static JSONObject sendGet(String _url) {
        try {
            //访问准备
            URL url = new URL(_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (200 == conn.getResponseCode()){
                //得到输入流
                InputStream is = conn.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while(-1 != (len = is.read(buffer))){
                    baos.write(buffer,0,len);
                    baos.flush();
                }
                System.out.println(baos);
                return  JSONObject.parseObject(baos.toString("utf-8"));
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}