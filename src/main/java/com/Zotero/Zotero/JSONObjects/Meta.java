package com.Zotero.Zotero.JSONObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Meta {
    private CreatedByUser createdByUser;
    private String creatorSummary;
    private String parsedDate;
    private int numChildren;
    private int numCollections;
    private int numItems;

    public int getNumCollections() {
        return numCollections;
    }

    public int getNumItems() {
        return numItems;
    }

    public CreatedByUser getCreatedByUser() {
        return createdByUser;
    }
    public String getCreatorSummary() {
        return creatorSummary;
    }

    public String getParsedDate() {
        return parsedDate;
    }

    public int getNumChildren() {
        return numChildren;
    }
}
