package com.Zotero.Zotero.Repositories;

import com.Zotero.Zotero.JSONObjects.Item;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;


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

    public String getKey() {
        return key;
    }

    public int getLibraryId() {
        return libraryId;
    }

    public String getItemType() {
        return itemType;
    }

    public String getTitle() {
        return title;
    }

    public String getAbstractNote() {
        return abstractNote;
    }

    public String getArtworkMedium() {
        return artworkMedium;
    }

    public String getArtworkSize() {
        return artworkSize;
    }

    public String getDate() {
        return date;
    }

    public String getLanguage() {
        return language;
    }

    public String getShortTitle() {
        return shortTitle;
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

    public String getUrl() {
        return url;
    }

    public String getAccessDate() {
        return accessDate;
    }

    public String getRights() {
        return rights;
    }

    public String getExtra() {
        return extra;
    }

    public String getAudioRecordingFormat() {
        return audioRecordingFormat;
    }

    public String getSeriesTitle() {
        return seriesTitle;
    }

    public String getVolume() {
        return volume;
    }

    public String getNumberOfVolumes() {
        return numberOfVolumes;
    }

    public String getPlace() {
        return place;
    }

    public String getLabel() {
        return label;
    }

    public String getRunningTime() {
        return runningTime;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public String getCode() {
        return code;
    }

    public String getCodeVolume() {
        return codeVolume;
    }

    public String getSection() {
        return section;
    }

    public String getCodePages() {
        return codePages;
    }

    public String getLegislativeBody() {
        return legislativeBody;
    }

    public String getSession() {
        return session;
    }

    public String getHistory() {
        return history;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public String getWebsiteType() {
        return websiteType;
    }

    public String getSeries() {
        return series;
    }

    public String getSeriesNumber() {
        return seriesNumber;
    }

    public String getEdition() {
        return edition;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getNumPages() {
        return numPages;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getProceedingsTitle() {
        return proceedingsTitle;
    }

    public String getConferenceName() {
        return conferenceName;
    }

    public String getDOI() {
        return DOI;
    }

    public String getCaseName() {
        return caseName;
    }

    public String getCourt() {
        return court;
    }

    public String getDateDecided() {
        return dateDecided;
    }

    public String getDocketNumber() {
        return docketNumber;
    }

    public String getReporter() {
        return reporter;
    }

    public String getReporterVolume() {
        return reporterVolume;
    }

    public String getFirstPage() {
        return firstPage;
    }

    public String getSubject() {
        return subject;
    }

    public String getEncyclopediaTitle() {
        return encyclopediaTitle;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public String getSystem() {
        return system;
    }

    public String getCompany() {
        return company;
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    public String getInterviewMedium() {
        return interviewMedium;
    }

    public String getPublicationTitle() {
        return publicationTitle;
    }

    public String getJournalAbbreviation() {
        return journalAbbreviation;
    }

    public String getManuscriptType() {
        return manuscriptType;
    }

    public String getCountry() {
        return country;
    }

    public String getAssignee() {
        return assignee;
    }

    public String getIssuingAuthority() {
        return issuingAuthority;
    }

    public String getPatentNumber() {
        return patentNumber;
    }

    public String getFilingDate() {
        return filingDate;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public String getPriorityNumbers() {
        return priorityNumbers;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getReference() {
        return reference;
    }

    public String getLegalStatus() {
        return legalStatus;
    }

    public String getProgramTitle() {
        return programTitle;
    }

    public String getEpisodeNumber() {
        return episodeNumber;
    }

    public String getNetwork() {
        return network;
    }

    public String getReportNumber() {
        return reportNumber;
    }

    public String getInstitution() {
        return institution;
    }

    public String getThesisType() {
        return thesisType;
    }

    public String getUniversity() {
        return university;
    }

    public String getWebsiteTitle() {
        return websiteTitle;
    }

    public String getISSN() {
        return ISSN;
    }

    public String getSeriesText() {
        return seriesText;
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
