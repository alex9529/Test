package com.Zotero.Zotero.Repositories;

import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<ItemSQL, String> {

    ItemSQL findByKey(String key);
}

