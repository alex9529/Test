package com.Zotero.Zotero.Repositories;

import com.Zotero.Zotero.JSONObjects.Item;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity (name="item_type_fields")
public class ItemTypeFieldsSQL  {


    @Id
    private String key;
    private int libraryId;
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
    private String numberOfVolumes;
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
    private String seriesNumber;
    private String edition;
    private String publisher;
    private String numPages;
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
    private String reference;
    private String legalStatus;
    private String programTitle;
    private String episodeNumber;
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

        this.key = item.getKey();
        this.libraryId = item.getLibrary().getId();
        this.itemType = item.getData().getItemType();
        this.title = item.getData().getTitle();
        this.abstractNote = item.getData().getAbstractNote();
        this.artworkMedium = item.getData().getArtworkMedium();
        this.artworkSize = item.getData().getArtworkSize();
        this.date = item.getData().getDate();
        this.language = item.getData().getLanguage();
        this.shortTitle = item.getData().getShortTitle();
        this.archive = item.getData().getArchive();
        this.archiveLocation = item.getData().getArchiveLocation();
        this.libraryCatalog = item.getData().getLibraryCatalog();
        this.callNumber = item.getData().getCallNumber();
        this.url = item.getData().getUrl();
        this.accessDate = item.getData().getAccessDate();
        this.rights = item.getData().getRights();
        this.extra = item.getData().getExtra();
        this.audioRecordingFormat = item.getData().getAudioRecordingFormat();
        this.seriesTitle = item.getData().getSeriesTitle();
        this.volume = item.getData().getVolume();
        this.numberOfVolumes = item.getData().getNumberOfVolumes();
        this.place = item.getData().getPlace();
        this.label = item.getData().getLabel();
        this.runningTime = item.getData().getRunningTime();
        this.ISBN = item.getData().getISBN();
        this.billNumber = item.getData().getBillNumber();
        this.code = item.getData().getCode();
        this.codeVolume = item.getData().getCodeVolume();
        this.section = item.getData().getSection();
        this.codePages = item.getData().getCodePages();
        this.legislativeBody = item.getData().getLegislativeBody();
        this.session = item.getData().getSession();
        this.history = item.getData().getHistory();
        this.blogTitle = item.getData().getBlogTitle();
        this.websiteType = item.getData().getWebsiteType();
        this.series = item.getData().getSeries();
        this.seriesNumber = item.getData().getSeriesNumber();
        this.edition = item.getData().getEdition();
        this.publisher = item.getData().getPublisher();
        this.numPages = item.getData().getNumPages();
        this.bookTitle = item.getData().getBookTitle();
        this.proceedingsTitle = item.getData().getProceedingsTitle();
        this.conferenceName = item.getData().getConferenceName();
        this.DOI = item.getData().getDOI();
        this.caseName = item.getData().getCaseName();
        this.court = item.getData().getCourt();
        this.dateDecided = item.getData().getDateDecided();
        this.docketNumber = item.getData().getDocketNumber();
        this.reporter = item.getData().getReporter();
        this.reporterVolume = item.getData().getReporterVolume();
        this.firstPage = item.getData().getFirstPage();
        this.subject = item.getData().getSubject();
        this.encyclopediaTitle = item.getData().getEncyclopediaTitle();
        this.versionNumber = item.getData().getVersionNumber();
        this.system = item.getData().getSystem();
        this.company = item.getData().getCompany();
        this.programmingLanguage = item.getData().getProgrammingLanguage();
        this.interviewMedium = item.getData().getInterviewMedium();
        this.publicationTitle = item.getData().getPublicationTitle();
        this.journalAbbreviation = item.getData().getJournalAbbreviation();
        this.manuscriptType = item.getData().getManuscriptType();
        this.country = item.getData().getCountry();
        this.assignee = item.getData().getAssignee();
        this.issuingAuthority = item.getData().getIssuingAuthority();
        this.patentNumber = item.getData().getPatentNumber();
        this.filingDate = item.getData().getFilingDate();
        this.applicationNumber = item.getData().getApplicationNumber();
        this.priorityNumbers = item.getData().getPriorityNumbers();
        this.issueDate = item.getData().getIssueDate();
        this.reference = item.getData().getReferences();
        this.legalStatus = item.getData().getLegalStatus();
        this.programTitle = item.getData().getProgramTitle();
        this.episodeNumber = item.getData().getEpisodeNumber();
        this.network = item.getData().getNetwork();
        this.reportNumber = item.getData().getReportNumber();
        this.institution = item.getData().getInstitution();
        this.thesisType = item.getData().getThesisType();
        this.university = item.getData().getUniversity();
        this.websiteTitle = item.getData().getWebsiteTitle();
        this.ISSN = item.getData().getISSN();
        this.seriesText = item.getData().getSeriesText();





    }
}
