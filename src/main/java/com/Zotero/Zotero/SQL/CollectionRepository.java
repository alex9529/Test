package com.Zotero.Zotero.SQL;

import org.springframework.data.repository.CrudRepository;

public interface CollectionRepository extends CrudRepository<CollectionSQL, String> {

    CollectionSQL findByCollectionKey(String collectionKey);

}

