package com.forest.core;


/**
 * 项目常量
 */
public final class ProjectConstant {
    public static final String BASE_PACKAGE = "com.forest";//项目基础包名称，根据自己公司的项目修改

//    public static final String MODEL_PACKAGE = BASE_PACKAGE + ".model";//Model所在包
    public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".**.dao";//Mapper所在包
    public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service";//Service所在包
//    public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";//ServiceImpl所在包
//    public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".web";//Controller所在包

    public static final String MAPPER_INTERFACE_REFERENCE = BASE_PACKAGE + ".core.Mapper";//Mapper插件基础接口的完全限定名


    /**
     * 使用标志
     */
    public enum USE_STATUS {
        INUSE("启用", "1"), STOPPED("停用", "9"), NOTUSE("未启用", "0");
        // 成员变量
        private String name;
        private String index;
        // 构造方法
        USE_STATUS(String name, String index) {
            this.name = name;
            this.index = index;
        }
        //覆盖方法
        @Override
        public String toString() {
            return this.index+"_"+this.name;
        }
        // 普通方法
        public static String getName(String index) {
            for (USE_STATUS c : USE_STATUS.values()) {
                if (c.getIndex().equals(index)) {
                    return c.name;
                }
            }
            return null;
        }
        public String getIndex() {
            return index;
        }
    }
}
