package com.honzar.sparkpostutil.library;

import java.util.ArrayList;

/**
 * Created by Honza Rychnovský on 24.10.2016.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */
public class SparkPostEmailMessage {
    private ArrayList<SparkPostRecipient> recipients;
    private SparkPostContent content;

    public SparkPostEmailMessage(ArrayList<SparkPostRecipient> recipients, SparkPostContent content) {
        this.recipients = recipients;
        this.content = content;
    }
}
