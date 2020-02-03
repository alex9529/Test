package com.Zotero.Zotero.SQL;

import com.Zotero.Zotero.API.Collection;
import com.Zotero.Zotero.API.Item;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity (name="item")
public class ItemSQL {


    @Id
    private String key;
    private int version;
    private int groupOrUserId;
    private String href;
    private int creatorUserId;
    private String creatorSummary;
    private String parsedDate;
    private String parentItem;
    private String itemType;
    private String accessDate;
    private String archive;
    private String archiveLocation;


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

    public ItemSQL(Item item) {

            this.key = item.getKey();
            this.version = item.getVersion();
            this.groupOrUserId = item.getLibrary().getId();
            this.href = item.getLinks().getAlternate().getHref();

            if (item.getLibrary().getType()=="group"){
                this.creatorUserId = item.getMeta().getCreatedByUser().getId();
            }

            this.creatorSummary = item.getMeta().getCreatorSummary();
            this.parsedDate = item.getMeta().getParsedDate();
            this.parentItem = item.getData().getParentItem();
            this.itemType = item.getData().getItemType();
            this.accessDate = item.getData().getAccessDate();
            this.archive = item.getData().getArchive();
            this.archiveLocation = item.getData().getArchiveLocation();






    }



}
