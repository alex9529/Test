package com.Zotero.Zotero.Repositories;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface CollectionRepository extends CrudRepository<CollectionSQL, String> {

    CollectionSQL findByCollectionKey(String collectionKey);
    Iterable<CollectionSQL> findByLibraryId(int libraryId);
    void removeByCollectionKey(String collectionKey);

}

