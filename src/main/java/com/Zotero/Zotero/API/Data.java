package com.Zotero.Zotero.API;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.LinkedList;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {


    private String key;
    private int version;
    private String name;
    private String parentCollection;
    private String parentItem;
    private String itemType;
    private String title;
    private LinkedList<Creators> creators;
    private String abstractNote;
    private String publicationTitle;
    private int volume;
    private int issue;
    private String pages;
    private String date;
    private String series;
    private String seriesTitle;
    private String seriesText;
    private String journalAbbreviation;
    private String language;
    private String DOI;
    private String ISSN;
    private String shortTitle;
    private String url;
    private String accessDate;
    private String archive;
    private String archiveLocation;
    private String libraryCatalog;
    private String callNumber;
    private String rights;
    private String extra;
    private LinkedList <String> collections;
    private Relation relations;
    private String dateAdded;
    private String dateModified;

    public String getKey() {
        return key;
    }

    public int getVersion() {
        return version;
    }

    public String getParentCollection() {
        return parentCollection;
    }

    public LinkedList<Creators> getCreators() {
        return creators;
    }

    public String getAbstractNote() {
        return abstractNote;
    }

    public String getPublicationTitle() {
        return publicationTitle;
    }

    public int getVolume() {
        return volume;
    }

    public int getIssue() {
        return issue;
    }

    public String getName() {
        return name;
    }

    public String getPages() {
        return pages;
    }

    public String getDate()  {
        return date;
    }

    public String getParentItem() {
        return parentItem;
    }

    public String getSeries() {
        return series;
    }

    public String getSeriesTitle() {
        return seriesTitle;
    }

    public String getSeriesText() {
        return seriesText;
    }

    public String getJournalAbbreviation() {
        return journalAbbreviation;
    }

    public String getLanguage() {
        return language;
    }

    public String getDOI() {
        return DOI;
    }

    public String getISSN() {
        return ISSN;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public String getUrl() {
        return url;
    }

    public String getAccessDate() {
        return accessDate;
    }

    public String getArchive() {
        return archive;
    }

    public String getArchiveLocation() {
        return archiveLocation;
    }

    public String getLibraryCatalog() {
        return libraryCatalog;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public String getRights() {
        return rights;
    }

    public String getExtra() {
        return extra;
    }



    public LinkedList<String> getCollections() {
        if (collections==null){
            return new LinkedList<String>();
        }
        return collections;
    }

    public Relation getRelations() {
        return relations;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public String getDateModified() {
        return dateModified;
    }


    public String getItemType() {
        return this.itemType;
    }

    public String getTitle() {
        return this.title;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
