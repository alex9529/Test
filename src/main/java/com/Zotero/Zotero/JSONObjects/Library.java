package com.Zotero.Zotero.JSONObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Library {

    private String type;
    private int id;
    private String name;
    private Links links;

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Links getLinks() {
        return links;
    }
}
