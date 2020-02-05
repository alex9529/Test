package com.Zotero.Zotero.SQL;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<UserSQL, Integer> {

    UserSQL findByUserId(Integer userId);


}
