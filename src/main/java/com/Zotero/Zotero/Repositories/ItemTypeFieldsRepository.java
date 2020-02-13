package com.Zotero.Zotero.Repositories;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;


@Transactional
public interface ItemTypeFieldsRepository extends CrudRepository<ItemTypeFieldsSQL, String> {

    ItemCollectionSQL findByKey(String key);
    void removeByKey(String key);

}


