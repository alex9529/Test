package com.Zotero.Zotero.Repositories;

import org.springframework.data.repository.CrudRepository;

public interface ItemCollectionRepository extends CrudRepository<ItemCollectionSQL, String> {

    ItemCollectionSQL findByCollectionKey(String collectionKey);
    ItemCollectionSQL findByItemKey(String itemKey);
}


