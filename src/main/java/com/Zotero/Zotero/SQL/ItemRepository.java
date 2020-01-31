package com.Zotero.Zotero.SQL;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<ItemSQL, String> {

    ItemSQL findByKey(String key);
}

