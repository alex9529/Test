package com.Zotero.Zotero.Repositories;

import org.springframework.data.repository.CrudRepository;



/**
 * Die Klasse ist für die
 * * @author Alexander Nikolov
 *
 *
 */

public interface ItemTypeFieldsRepository extends CrudRepository<ItemTypeFieldsSQL, String> {

    ItemCollectionSQL findByKey(String key);
    void removeByKey(String key);

}


