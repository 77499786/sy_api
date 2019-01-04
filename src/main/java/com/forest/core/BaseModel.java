package com.forest.core;

public class BaseModel {
    /**
     * 使用标志
     */
    public static enum INUSE {
        TRUE("1"), FALSE("0"), STOP("9");
        private final String value;

        /**
         * 构造器默认也只能是private, 从而保证构造函数只能在内部使用
         */
        private INUSE(String value) {
            this.value = value;
        }

        public Integer getValue() {
            return Integer.valueOf(value);
        }
    }

    /**
     * 同意分包
     */
    public static enum TYFB {
        TRUE("同意",1), FALSE("不同意",0);
        private final Integer value;
        private final String name;

        /**
         * 构造器默认也只能是private, 从而保证构造函数只能在内部使用
         */
        private TYFB(String name , Integer value) {
            this.name = name;
            this.value = value;
        }

        public Integer getValue() {
            return Integer.valueOf(value);
        }
        public String getName() {
            return name;
        }
        public static  String getName(Integer key) {
            for (TYFB c : TYFB.values()) {
                if (c.getValue() == key) {
                    return c.name;
                }
            }
            return "";
        }
    }


    /**
     * 是否加急
     */
    public static enum SFJJ {
        TRUE("加急",1), FALSE("普通",0);
        private final Integer value;
        private final String name;

        /**
         * 构造器默认也只能是private, 从而保证构造函数只能在内部使用
         */
        private SFJJ(String name , Integer value) {
            this.name = name;
            this.value = value;
        }

        public Integer getValue() {
            return Integer.valueOf(value);
        }
        public String getName() {
            return name;
        }
        public static String getName(Integer key) {
            for (SFJJ c : SFJJ.values()) {
                if (c.getValue() == key) {
                    return c.name;
                }
            }
            return "";
        }
    }

    /**
     * 企业用户角色id
     */
    public static String ENTERPRISE_ROLE_ID = "c7de04e5420811e8bab7bcee7b9a8235";

    /**
     * 企业用户所属单位ID
     */
    public static String ENTERPRISE_UNIT_ID = "a0984973b62711e8a8c2bcee7b9a8235";
;}
