package com.Zotero.Zotero.Repositories;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface ItemCollectionRepository extends CrudRepository<ItemCollectionSQL, String> {

    Iterable<ItemCollectionSQL> getAllByCollectionKey(String collectionKey);
    Iterable<ItemCollectionSQL> getAllByItemKey(String itemKez);
    ItemCollectionSQL findByItemKey(String itemKey);
    void removeByItemKey(String itemKey);
    void removeByItemKeyAndCollectionKey(String itemKey, String collectionKey);

}


