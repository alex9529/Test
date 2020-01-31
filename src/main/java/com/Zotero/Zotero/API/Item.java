package com.Zotero.Zotero.API;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {

    private String key;
    private int version;
    private Library library;
    private Links links;
    private Meta meta;
    private Data data;


    public Item(String key) {
        this.key = key;
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

    public Item() {
    }

    public String getKey() {
        return this.key;
    }

    public Data getData() {
        return this.data;
    }



    public void setKey(String key) {
        this.key = key;
    }



    /*@Override
    public String toString() {
        return "Item{" +
                "key=" + key +
                ", data='" + data  +
                '}';
    }*/
}
