package com.honzar.sparkpostutil.library;

/**
 * Created by Honza Rychnovský on 24.10.2016.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */
public interface EmailListener {
    void onSuccess();
    void onError(String errorMessage);
}
