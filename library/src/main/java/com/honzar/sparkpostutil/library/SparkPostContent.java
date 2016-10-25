package com.honzar.sparkpostutil.library;

import java.util.ArrayList;
/**
 * Created by noelchew on 11/23/15.
 *
 * Updated by Honza Rychnovský on 24.10.2016.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */
public class SparkPostContent {
    private SparkPostSender from;
    private String subject;
    private String text;
    private String html;
    private ArrayList<SparkPostFile> inline_images;
    private String reply_to;

    public SparkPostContent(SparkPostSender from, String subject, String text, String html, ArrayList<SparkPostFile> files, String reply_to) {
        this.from = from;
        this.subject = subject;
        this.text = text;
        this.html = html;
        this.inline_images = files;
        this.reply_to = reply_to;

    }

    public SparkPostSender getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }

    public ArrayList<SparkPostFile> getInline_images() {
        return inline_images;
    }

    public String getHtml() {
        return html;
    }

    public String getReply_to() {
        return reply_to;
    }
}
