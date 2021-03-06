package com.honzar.sparkpostutil.library;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by noelchew on 11/23/15.
 *
 * Updated by Honza Rychnovský on 24.10.2016.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */
public class SparkPostEmailJsonRequest {
    public static final String API_BASE_URL = "https://api.sparkpost.com/api/v1/";
    public static final String EMAIL_API_PATH = "transmissions?num_rcpt_errors=3";

    private ArrayList<SparkPostRecipient> recipients;
    private SparkPostContent content;

    public SparkPostEmailJsonRequest(String subject, String message, ArrayList<SparkPostRecipient> recipients, SparkPostSender sender, String html, ArrayList<SparkPostFile> files, String replyTo) {
        this.recipients = recipients;
        this.content = new SparkPostContent(sender, subject, message, html, files, formatReplyToEmailAddress(replyTo));
    }

    public SparkPostEmailJsonRequest(String subject, String message, String recipientEmail, String senderEmail, String senderName) {
        SparkPostRecipient recipient = new SparkPostRecipient(recipientEmail);
        this.recipients = new ArrayList<>();
        this.recipients.add(recipient);

        SparkPostSender sender = new SparkPostSender("feedback@sparkpostbox.com", senderName);
        this.content = new SparkPostContent(sender, subject, message, null, null, null);
    }

    public String toString() {
        return new Gson().toJson(this);
    }

    private String formatReplyToEmailAddress(String addr)
    {
        if (addr != null && addr.length() > 0) {

            if (!addr.startsWith("<"))
                addr = "<" + addr;

            if (!addr.endsWith(">"))
                addr = addr + ">";
        }
        return addr;
    }

}
