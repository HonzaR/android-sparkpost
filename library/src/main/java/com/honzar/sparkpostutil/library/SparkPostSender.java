package com.honzar.sparkpostutil.library;

/**
 * Created by noelchew on 11/23/15.
 *
 * Updated by Honza Rychnovský on 24.10.2016.
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

    @Override
    public String toString() {
        return "<" + email + ">";
    }
}
