package com.Zotero.Zotero.SQL;

import org.springframework.data.repository.CrudRepository;



/**
 * Die Klasse ist für die
 * * @author Alexander Nikolov
 *
 *
 */

public interface ItemTypeFieldsRepository extends CrudRepository<ItemTypeFieldsSQL, String> {

    ItemCollectionSQL findByItemType(String itemType);

}


