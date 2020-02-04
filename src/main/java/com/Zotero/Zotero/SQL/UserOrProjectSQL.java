package com.Zotero.Zotero.SQL;

import com.Zotero.Zotero.JSONObjects.Item;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity (name="user_or_project")
public class UserOrProjectSQL {


    @Id
    private int key;
    private int userOrProjectId;
    private String type;
    private String name;
    private String libraryLink;

    public UserOrProjectSQL() {
    }

    public UserOrProjectSQL(Item item)  {

        this.userOrProjectId = item.getLibrary().getId();
        this.type = item.getLibrary().getType();
        this.name = item.getLibrary().getName();
        this.libraryLink = item.getLibrary().getLinks().getAlternate().getHref();


    }
}
