package com.Zotero.Zotero.JSONObjects;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Collection {

    private String key;
    private int version;
    private Library library;
    private Links links;
    private Meta meta;
    private Data data;

    public String getKey() {
        return key;
    }

    public String getCollectionKey() {
        return key;
    }

    public int getVersion() {
        return version;
    }

    public Library getLibrary() {
        return library;
    }

    public Links getLinks() {
        return links;
    }

    public Meta getMeta() {
        return meta;
    }

    public Data getData() {
        return data;
    }
}
