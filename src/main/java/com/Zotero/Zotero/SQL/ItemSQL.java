package com.Zotero.Zotero.SQL;

import com.Zotero.Zotero.JSONObjects.Item;

import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * Die Klasse widerspiegelt die Datenstruktur für die Entität "item" in der PostgreSQL DB. Das Attribut creatorUserId kommt nur bei Items vor, die Bestandteil einer geteilten Bibliothek sind,
 * um auf den Item-Erstellter hinzudeuten.
 * Die Annotation @Entity gibt den Namen der Entität an Spring Boot weiter.
 * @Id kennzeichnet das Attribut "key" als den Primärschlüssel.
 *
 * @author Alexander Nikolov
 *
 */


@Entity (name="item")
public class ItemSQL {


    @Id
    private String key;
    private int version;
    private int libraryId;
    private String itemLink;
    private int createdBy;
    private String creatorSummary;
    private String parsedDate;
    private String parentItem;
    private String itemType;
    private String accessDate;
    private String archive;
    private String archiveLocation;
    private  String bib;


    public String getKey() {
        return key;
    }

    public int getVersion() {
        return version;
    }

    public int getGroupOrUserId() {
        return libraryId;
    }

    public String getItemLink() {
        return itemLink;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public String getCreatorSummary() {
        return creatorSummary;
    }

    public String getParsedDate() {
        return parsedDate;
    }

    public String getParentItem() {
        return parentItem;
    }

    public String getItemType() {
        return itemType;
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

    protected ItemSQL() {}

    public ItemSQL(Item item, Item bibItem) {

            this.key = item.getKey();
            this.version = item.getVersion();
            this.libraryId = item.getLibrary().getId();
            this.itemLink = item.getLinks().getAlternate().getHref();

            if (item.getLibrary().getType().equals("group")){
                this.createdBy = item.getMeta().getCreatedByUser().getId();
            }
            else {
                this.createdBy = this.libraryId;
            }

            this.creatorSummary = item.getMeta().getCreatorSummary();
            this.parsedDate = item.getMeta().getParsedDate();
            this.parentItem = item.getData().getParentItem();
            this.itemType = item.getData().getItemType();
            this.accessDate = item.getData().getAccessDate();
            this.archive = item.getData().getArchive();
            this.archiveLocation = item.getData().getArchiveLocation();
            this.bib = bibItem.getBib();
    }



}
