package com.Zotero.Zotero.Repositories;

import org.springframework.data.repository.CrudRepository;

public interface CollectionRepository extends CrudRepository<CollectionSQL, String> {

    CollectionSQL findByCollectionKey(String collectionKey);

}

