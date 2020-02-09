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

    public ItemCollectionSQL(Item item, int i)  {

            LinkedList<String> collections = item.getData().getCollections();
            this.collectionKey = collections.get(i);
            this.itemKey = item.getKey();


    }
}
