package com.Zotero.Zotero.SQL;
import org.springframework.data.repository.CrudRepository;


public interface ItemAuthorRepository extends CrudRepository<ItemAuthorSQL, Integer> {

    ItemAuthorSQL findByItemAuthorId(Integer itemAuthorId);
}
