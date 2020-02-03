package com.Zotero.Zotero.API;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.LinkedList;

/** Die Klasse übernimmt alle möglichen JSON Attribute aus dem "data"-Teil des JSON-Objekts aus der GET Abfrage. Die Attribute widerspiegeln exakt die JSON-Struktur.
 * Nicht vorhandene Attribute werden mittels @JsonIgnoreProperties ignoriert, um einen runtime-Fehler zu vermeiden. Das Atrribut creators ist ein eigenes JSON Objekt.
 * Darum wurde dafür eine extra Klasse angelegt.
 *
 * @author Alexander Nikolov
 *
 */

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
    private String artworkMedium;
    private String artworkSize;
    private String audioRecordingFormat;
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
    private int seriesNumber;
    private int edition;
    private String publisher;
    private int numPages;
    private String bookTitle;
    private String proceedingsTitle;
    private String conferenceName;
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

    public String getArtworkMedium() {
        return artworkMedium;
    }

    public String getArtworkSize() {
        return artworkSize;
    }

    public String getAudioRecordingFormat() {
        return audioRecordingFormat;
    }

    public int getNumberOfVolumes() {
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

    public int getSeriesNumber() {
        return seriesNumber;
    }

    public int getEdition() {
        return edition;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getNumPages() {
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

    public String getReferences() {
        return references;
    }

    public String getLegalStatus() {
        return legalStatus;
    }

    public String getProgramTitle() {
        return programTitle;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public String getAudioRedordingFormat() {
        return audioRedordingFormat;
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
