package weixin;

import com.alibaba.fastjson.JSONObject;

public class WeixinToken {
    public static String APPID = "wxc59bdedbd07a7cff";
    public static String APPSECRET = "3cdd32273d6a1a848a5f1d9f7df8608b";
    public static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static String OAUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";
    public static String REDIRECT_URI = "http://forest.148.freefrp.cn";//自己的授权地址
    public static String SERVER_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";

    private static String access_token = "";
    private static String jsapi_ticket = "";
    public static long time = 0;
    private static int expires_in = 5400;

    static {
        Thread t = new Thread(new Runnable() {
            public void run() {
                do {
                    time++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (true);
            }
        });
        t.start();
    }

    public static String getToken() {
        if ("".equals(access_token) || access_token == null) {
            send();
        } else if (time > expires_in) {
            //当前token已经失效，从新获取信息
            send();
        }
        return access_token;
    }

    public static String getTicket() {
        if ("".equals(jsapi_ticket) || jsapi_ticket == null) {
            send();
        } else if (time > expires_in) {
            //当前token已经失效，从新获取信息
            send();
        }
        return jsapi_ticket;
    }

    private static void send() {
        String url = SERVER_TOKEN_URL + "&appid=" + APPID + "&secret=" + APPSECRET;
        JSONObject json = SendHttpRequest.sendGet(url);
        access_token = json.getString("access_token");
        String ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";
        jsapi_ticket = SendHttpRequest.sendGet(ticket_url).getString("ticket");
        time = System.currentTimeMillis();
    }
}