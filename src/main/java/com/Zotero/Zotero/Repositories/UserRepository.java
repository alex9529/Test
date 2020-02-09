package com.Zotero.Zotero.Repositories;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<UserSQL, Integer> {

    UserSQL findByUserId(Integer userId);


}
