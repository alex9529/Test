package com.Zotero.Zotero.SQL;

import com.Zotero.Zotero.API.Collection;
import com.Zotero.Zotero.API.Item;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.LinkedList;


@Entity (name="item_collection")
public class ItemCollectionSQL {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String collectionKey;
    private String  key;

    public ItemCollectionSQL(Item item, int i) {

            LinkedList<String> collections = item.getData().getCollections();
            this.collectionKey = collections.get(i);
            this.key = item.getKey();


    }
}
