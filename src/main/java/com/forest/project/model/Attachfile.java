package com.forest.project.model;

public class Attachfile {
    private String name;
    private String filepath;
    private String realpath;

    public Attachfile(String name, String filepath){
        this.name= name;
        this.filepath = filepath;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getRealpath() {
        return realpath;
    }

    public void setRealpath(String realpath) {
        this.realpath = realpath;
    }
}
