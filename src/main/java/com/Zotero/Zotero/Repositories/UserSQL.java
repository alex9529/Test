package com.Zotero.Zotero.Repositories;

import com.Zotero.Zotero.JSONObjects.Item;

import javax.persistence.Entity;
import javax.persistence.Id;


/**
 *
 */
@Entity (name="users")
public class UserSQL {


    @Id
    private int userId;
    private String username;
    private String userLink;

    public UserSQL() {
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getUserLink() {
        return userLink;
    }


    public UserSQL(Item item)  {

        if (item.getLibrary().getType().equals("group")){
            this.userId = item.getMeta().getCreatedByUser().getId();
            this.username = item.getMeta().getCreatedByUser().getUsername();
            this.userLink = item.getMeta().getCreatedByUser().getLinks().getAlternate().getHref();
        }
        else {
            this.userId = item.getLibrary().getId();
            this.username = item.getLibrary().getName();
            this.userLink = item.getLibrary().getLinks().getAlternate().getHref();
        }



    }
}
