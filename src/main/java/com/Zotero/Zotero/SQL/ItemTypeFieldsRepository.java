package com.Zotero.Zotero.SQL;

import org.springframework.data.repository.CrudRepository;



/**
 * Die Klasse ist für die 
 * * @author Alexander Nikolov
 *
 */

public interface ItemTypeFieldsRepository extends CrudRepository<ItemCollectionSQL, String> {

    ItemCollectionSQL findByCollectionKey(String collectionKey);
    ItemCollectionSQL findByItemKey(String itemKey);
}


