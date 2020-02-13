package com.Zotero.Zotero.Repositories;

import com.Zotero.Zotero.JSONObjects.Item;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;


@Entity (name="item_collection")
@IdClass(ItemCollectionSQL.class)
public class ItemCollectionSQL implements Serializable {


    @Id
    private String collectionKey;
    @Id
    private String  itemKey;

    public ItemCollectionSQL() {
    }


    public String getCollectionKey() {
        return collectionKey;
    }

    public String getItemKey() {
        return itemKey;
    }

    //Used when syncing items from a specific collection and only that one collection key has to be saved
    public ItemCollectionSQL(Item item, String collectionKey)  {

        this.collectionKey = collectionKey;
        this.itemKey = item.getKey();

    }

    //Used when syncing items from the entire library and all collection information has to be saved as well
    public ItemCollectionSQL(Item item, int i)  {

            LinkedList<String> collections = item.getData().getCollections();
            this.collectionKey = collections.get(i);
            this.itemKey = item.getKey();


    }





}
