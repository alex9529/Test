package com.Zotero.Zotero.SQL;

import com.Zotero.Zotero.API.Item;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.LinkedList;


@Entity (name="item_type_fields")
@IdClass(ItemTypeFieldsSQL.class)
public class ItemTypeFieldsSQL implements Serializable {


    @Id
    private String itemType;
    private String title;
    private String abstractNote;
    private String artworkMedium;
    private String artworkSize;
    private String date;
    private String language;
    private String shortTitle;
    private String archive;
    private String archiveLocation;
    private String libraryCatalog;
    private String callNumber;
    private String url;
    private String accessDate;
    private String rights;
    private String extra;
    private String audioRecordingFormat;
    private String seriesTitle;
    private String volume;
    private int numberOfVolumes;
    private String place;
    private String label;
    private String runningTime;
    private String ISBN;
    private String billNumber;
    private String code;
    private String codeVolume;
    private String section;
    private String codePages;
    private String legislativeBody;
    private String session;
    private String history;
    private String blogTitle;
    private String websiteType;
    private String series;
    private int seriesNumber;
    private int edition;
    private String publisher;
    private int numPages;
    private String bookTitle;
    private String proceedingsTitle;
    private String conferenceName;
    private String DOI;
    private String caseName;
    private String court;
    private String dateDecided;
    private String docketNumber;
    private String reporter;
    private String reporterVolume;
    private String firstPage;
    private String subject;
    private String encyclopediaTitle;
    private String versionNumber;
    private String system;
    private String company;
    private String programmingLanguage;
    private String interviewMedium;
    private String publicationTitle;
    private String journalAbbreviation;
    private String manuscriptType;
    private String country;
    private String assignee;
    private String issuingAuthority;
    private String patentNumber;
    private String filingDate;
    private String applicationNumber;
    private String priorityNumbers;
    private String issueDate;
    private String references;
    private String legalStatus;
    private String programTitle;
    private int episodeNumber;
    private String audioRedordingFormat;
    private String network;
    private String reportNumber;
    private String institution;
    private String thesisType;
    private String university;
    private String websiteTitle;
    private String ISSN;
    private String seriesText;
    


    public ItemTypeFieldsSQL() {
    }

    public ItemTypeFieldsSQL(Item item)  {



    }
}
