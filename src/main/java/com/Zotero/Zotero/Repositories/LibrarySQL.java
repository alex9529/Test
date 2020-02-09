package com.Zotero.Zotero.Repositories;

import com.Zotero.Zotero.JSONObjects.Item;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity (name="library")
public class LibrarySQL {


    @Id
    private int libraryId;
    private String type;
    private String libraryName;
    private String libraryLink;

    public LibrarySQL() {
    }

    public LibrarySQL(Item item)  {

        this.libraryId = item.getLibrary().getId();
        this.type = item.getLibrary().getType();
        this.libraryName = item.getLibrary().getName();
        this.libraryLink = item.getLibrary().getLinks().getAlternate().getHref();


    }
}
