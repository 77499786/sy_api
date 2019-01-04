package com.forest.utils;

import java.util.ArrayList;
import java.util.List;  
import java.util.Map;  
  
import com.aspose.words.IMailMergeDataSource;
import com.aspose.words.ref.Ref;

/** 
 * 实现对HashMap的支持 
 */  
public class MapMailMergeDataSource implements IMailMergeDataSource {  
  
    private List<Map<String, String>> dataList;
      
    private int index;  
      
    //word模板中的«TableStart:tableName»«TableEnd:tableName»对应  
    private String tableName = null;  
      
    /** 
     * @param dataList 数据集 
     * @param tableName 与模板中的Name对应 
     */  
    public MapMailMergeDataSource(List<Map<String, String>> dataList, String tableName) {
        this.dataList = dataList;  
        this.tableName = tableName;  
        index = -1;  
    }  
      
    /** 
     * @param data 单个数据集 
     * @param tableName 与模板中的Name对应 
     */  
    public MapMailMergeDataSource(Map<String, String> data, String tableName) {
        if(this.dataList == null) {  
            this.dataList = new ArrayList<Map<String,String>>();
            this.dataList.add(data);  
        }  
        this.tableName = tableName;  
        index = -1;  
    }  
      
    /** 
     * 获取结果集总数 
     * @return 
     */  
    private int getCount() {  
        return this.dataList.size();  
    }  
      
    @Override  
    public IMailMergeDataSource getChildDataSource(String arg0)  
            throws Exception {  
        return null;  
    }  
  
    @Override  
    public String getTableName() throws Exception {  
        return this.tableName;  
    }

    /**
     * 实现接口
     * 获取当前index指向数据行的数据
     * 将数据存入Ref<Object>中即可
     * @return ***返回false则不绑定数据***
     */
    @Override
    public boolean getValue(String s, Ref<Object> ref) throws Exception {
        if(index < 0 || index >= this.getCount()) {
            return false;
        }

        if(ref != null) {
            System.out.println(s + ":" + this.dataList.get(index).get(s).toString());
            ref = new Ref<Object>(this.dataList.get(index).get(s));
            return true;
        } else {
            return false;
        }
    }

    /**
     * 实现接口
     * 判断是否还有下一条记录
     */
    @Override
    public boolean moveNext() throws Exception {
        index += 1;
        if(index >= this.getCount())
        {
            return false;
        }
        return true;
    }
}  