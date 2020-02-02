package com.Zotero.Zotero.SQL;

import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<ItemSQL, String> {

    ItemSQL findByKey(String key);
}

