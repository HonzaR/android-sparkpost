package com.honzar.sparkpostutil.library;

/**
 * Created by Honza Rychnovský on 24.10.2016.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */
public class SparkPostFile {

    private String type;
    private String name;
    private String data;

    public SparkPostFile(String type, String name, String data) {
        this.type = type;
        this.name = name;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
