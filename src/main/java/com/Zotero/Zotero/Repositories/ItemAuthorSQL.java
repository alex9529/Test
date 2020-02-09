package com.Zotero.Zotero.Repositories;

import com.Zotero.Zotero.JSONObjects.Item;

import javax.persistence.*;
import java.io.Serializable;


@Entity (name="item_author")
@IdClass(ItemAuthorSQL.class)
public class ItemAuthorSQL implements Serializable {


    @Id
    private String itemKey;
    @Id
    private int libraryId;
    private String creatorType;
    @Id
    private String firstName;
    @Id
    private String lastName;
    @Id
    private String name;

    public ItemAuthorSQL() {
    }

    public ItemAuthorSQL(Item item, int i)  {

            this.itemKey = item.getData().getKey();
            this.creatorType = item.getData().getCreators().get(i).getCreatorType();
            this.libraryId = item.getLibrary().getId();
            this.firstName = item.getData().getCreators().get(i).getFirstName();
            this.lastName = item.getData().getCreators().get(i).getLastName();
            this.name = item.getData().getCreators().get(i).getName();

            if (this.firstName == null){
                this.firstName="na";
            }
            if (this.lastName == null){
                this.lastName="na";
            }
            if (this.name == null){
                this.name="na";
            }
    }
}
