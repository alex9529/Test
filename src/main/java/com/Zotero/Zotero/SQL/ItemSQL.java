package com.Zotero.Zotero.SQL;

import com.Zotero.Zotero.API.Item;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Entity (name="Item_sql")
public class ItemSQL {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String key;
    private int version;
    private int groupOrUserId;
    private String href;
    private int creatorUserId;
    private String creatorSummary;
    private int parsedDate;
    private String parentItem;
    private String itemType;
    private String collectionKey;
    private LocalDateTime accessDate;
    private String archive;
    private String archiveLocation;


    public int getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public int getVersion() {
        return version;
    }

    public int getGroupOrUserId() {
        return groupOrUserId;
    }

    public String getHref() {
        return href;
    }

    public int getCreatorUserId() {
        return creatorUserId;
    }

    public String getCreatorSummary() {
        return creatorSummary;
    }

    public int getParsedDate() {
        return parsedDate;
    }

    public String getParentItem() {
        return parentItem;
    }

    public String getItemType() {
        return itemType;
    }

    public String getCollectionKey() {
        return collectionKey;
    }

    public LocalDateTime getAccessDate() {
        return accessDate;
    }

    public String getArchive() {
        return archive;
    }

    public String getArchiveLocation() {
        return archiveLocation;
    }

    protected ItemSQL() {}

    public ItemSQL(Item item) {
        try {
            this.key = item.getKey();
            this.version = item.getVersion();
            this.groupOrUserId = item.getLibrary().getId();
            this.href = item.getLinks().getAlternate().getHref();
            this.creatorUserId = item.getMeta().get;
            this.creatorSummary = sad;
            this.parsedDate = sad;
            this.parentItem = sad;
            this.itemType = sad;
            this.collectionKey = sad;
            this.accessDate = sad;
            this.archive = sad;
            this.archiveLocation = sad;
        } finally {

        }




    }

    public Item JSONtoSQL(Item item){
        this.key=item.getKey();
        return item;
    }

}
