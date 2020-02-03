package com.Zotero.Zotero.SQL;

import org.springframework.data.repository.CrudRepository;

public interface ItemTypeFieldsRepository extends CrudRepository<ItemCollectionSQL, String> {

    ItemCollectionSQL findByCollectionKey(String collectionKey);
    ItemCollectionSQL findByItemKey(String itemKey);
}


