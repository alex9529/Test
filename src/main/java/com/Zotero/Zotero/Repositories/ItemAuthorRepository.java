package com.Zotero.Zotero.Repositories;
import org.springframework.data.repository.CrudRepository;


public interface ItemAuthorRepository extends CrudRepository<ItemAuthorSQL, String> {

    ItemAuthorSQL findByItemKey(String itemKey);
    void removeByItemKey(String itemKey);


}
