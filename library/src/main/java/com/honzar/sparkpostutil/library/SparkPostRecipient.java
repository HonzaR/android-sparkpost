package com.honzar.sparkpostutil.library;

/**
 * Created by noelchew on 11/23/15.
 *
 * Updated by Honza Rychnovský on 24.10.2016.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */
public class SparkPostRecipient {
    private String address;

    public SparkPostRecipient(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}