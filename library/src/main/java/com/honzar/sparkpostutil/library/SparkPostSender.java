package com.honzar.sparkpostutil.library;

/**
 * Created by Honza Rychnovský on 24.10.2016.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */
public class SparkPostSender {
    private String name;
    private String email;
    public SparkPostSender(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
