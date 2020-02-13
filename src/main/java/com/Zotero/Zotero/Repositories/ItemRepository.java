package com.Zotero.Zotero.Repositories;

import com.Zotero.Zotero.JSONObjects.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.LinkedList;

public interface ItemRepository extends CrudRepository<ItemSQL, String> {

    ItemSQL findByKey(String key);
    void removeByKey (String key);

}

