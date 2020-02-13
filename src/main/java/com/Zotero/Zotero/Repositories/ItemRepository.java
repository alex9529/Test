package com.Zotero.Zotero.Repositories;

import com.Zotero.Zotero.JSONObjects.Item;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.LinkedList;

@Transactional
public interface ItemRepository extends CrudRepository<ItemSQL, String> {

    ItemSQL findByKey(String key);
    void removeByKey (String key);

}

