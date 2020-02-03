package com.Zotero.Zotero.SQL;

import org.springframework.data.repository.CrudRepository;

public interface ItemCollectionRepository extends CrudRepository<ItemCollectionSQL, String> {

    ItemCollectionSQL findByCollectionKey(String collectionKey);
}

