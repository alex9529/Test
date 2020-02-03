package com.Zotero.Zotero.SQL;

import com.Zotero.Zotero.API.Collection;
import javax.persistence.*;
import java.io.Serializable;


@Entity (name="collection")
public class CollectionSQL  {


    @Id
    private String collectionKey;
    private int groupOrUserId;
    private int version;
    private String linksSelfHref;
    private int numItems;
    private int numCollections;
    private String name;
    private String parentCollectionKey;

    public String getCollectionKey() {
        return collectionKey;
    }

    public String getLinksSelfHref() {
        return linksSelfHref;
    }

    public int getNumItems() {
        return numItems;
    }

    public int getNumCollections() {
        return numCollections;
    }

    public String getName() {
        return name;
    }

    public String getParentCollectionKey() {
        return parentCollectionKey;
    }


    public int getVersion() {
        return version;
    }

    public int getGroupOrUserId() {
        return groupOrUserId;
    }

    protected CollectionSQL() {}

    public CollectionSQL(Collection collection) {

            this.collectionKey = collection.getCollectionKey();
            this.version = collection.getVersion();
            this.groupOrUserId = collection.getLibrary().getId();
            this.linksSelfHref = collection.getLinks().getSelf().getHref();
            this.numItems = collection.getMeta().getNumItems();
            this.numCollections = collection.getMeta().getNumCollections();
            this.name = collection.getData().getName();
            this.parentCollectionKey = collection.getData().getParentCollection();
    }
}
