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
public class SparkPostResultWrapper {
    private ArrayList<SparkPostError> errors;
    private SparkPostResult results;

    public SparkPostResultWrapper(ArrayList<SparkPostError> errors, SparkPostResult results) {
        this.errors = errors;
        this.results = results;
    }

    public ArrayList<SparkPostError> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<SparkPostError> errors) {
        this.errors = errors;
    }

    public SparkPostResult getResults() {
        return results;
    }

    public void setResults(SparkPostResult results) {
        this.results = results;
    }

    public static SparkPostResultWrapper fromJson(String json) {
        return new Gson().fromJson(json, SparkPostResultWrapper.class);
    }
}
