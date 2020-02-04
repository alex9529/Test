package com.Zotero.Zotero.SQL;
import org.springframework.data.repository.CrudRepository;


public interface UserOrProjectRepository extends CrudRepository<UserOrProjectSQL, Integer> {


    UserOrProjectSQL findByKey(Integer key);

}
