package com.Zotero.Zotero.SQL;

import com.Zotero.Zotero.JSONObjects.Item;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity (name="item_author")
public class ItemAuthorSQL {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemAuthorId;
    private String itemKey;
    private String creatorType;
    private String firstName;
    private String lastName;
    private String name;

    public ItemAuthorSQL() {
    }

    public ItemAuthorSQL(Item item, int i)  {

            this.itemKey = item.getData().getKey();
            this.creatorType = item.getData().getCreators().get(i).getCreatorType();
            this.firstName = item.getData().getCreators().get(i).getFirstName();
            this.lastName = item.getData().getCreators().get(i).getLastName();
            this.name = item.getData().getCreators().get(i).getName();

    }
}
