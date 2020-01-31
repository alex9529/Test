package com.Zotero.Zotero.SQL;

import com.Zotero.Zotero.API.Item;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity (name="Item_sql")
public class ItemSQL {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String key;

    protected ItemSQL() {}

    public ItemSQL(String s) {
        this.key=s;

    }

    public Item JSONtoSQL(Item item){
        this.key=item.getKey();
        return item;
    }

}
