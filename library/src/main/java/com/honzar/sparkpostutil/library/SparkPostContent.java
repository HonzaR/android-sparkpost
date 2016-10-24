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

    public SparkPostContent(SparkPostSender from, String subject, String text, String html, ArrayList<SparkPostFile> files) {
        this.from = from;
        this.subject = subject;
        this.text = text;
        this.html = html;
        this.inline_images = files;

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

}
