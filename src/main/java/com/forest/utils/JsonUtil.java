package com.forest.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonUtil {
    public static Map<String, Object>  string2Map(String jsonstr) {
        JSONObject json = JSON.parseObject(jsonstr);
        Map<String, Object> map = new HashMap<String, Object>();
        if(json != null) {
            Iterator it = json.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                Object value = json.get(key);
                map.put(key, value);
            }
        }
        return map;
    }
}
