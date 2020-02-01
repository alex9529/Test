package com.Zotero.Zotero.API;

public class Meta {
    private createdByUser createdByUser;
    private String creatorSummary;
    private String parsedDate;
    private int numChildren;


    public com.Zotero.Zotero.API.createdByUser getCreatedByUser() {
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
