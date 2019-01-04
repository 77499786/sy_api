package com.forest.home.model;

import javax.persistence.*;

@Table(name = "home_dictionary")
public class HomeDictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 字典编码
     */
    @Column(name = "dictionary_code")
    private String dictionaryCode;

    /**
     * 字典名称
     */
    @Column(name = "dictionary_name")
    private String dictionaryName;

    /**
     * 字典分类
     */
    @Column(name = "dictionary_type")
    private String dictionaryType;

    /**
     * 删除标志
     */
    private Integer isdeleted;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取字典编码
     *
     * @return dictionary_code - 字典编码
     */
    public String getDictionaryCode() {
        return dictionaryCode;
    }

    /**
     * 设置字典编码
     *
     * @param dictionaryCode 字典编码
     */
    public void setDictionaryCode(String dictionaryCode) {
        this.dictionaryCode = dictionaryCode;
    }

    /**
     * 获取字典名称
     *
     * @return dictionary_name - 字典名称
     */
    public String getDictionaryName() {
        return dictionaryName;
    }

    /**
     * 设置字典名称
     *
     * @param dictionaryName 字典名称
     */
    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    /**
     * 获取字典分类
     *
     * @return dictionary_type - 字典分类
     */
    public String getDictionaryType() {
        return dictionaryType;
    }

    /**
     * 设置字典分类
     *
     * @param dictionaryType 字典分类
     */
    public void setDictionaryType(String dictionaryType) {
        this.dictionaryType = dictionaryType;
    }

    /**
     * 获取删除标志
     *
     * @return isdeleted - 删除标志
     */
    public Integer getIsdeleted() {
        return isdeleted;
    }

    /**
     * 设置删除标志
     *
     * @param isdeleted 删除标志
     */
    public void setIsdeleted(Integer isdeleted) {
        this.isdeleted = isdeleted;
    }
}