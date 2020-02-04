package com.Zotero.Zotero.SQL;
import org.apache.catalina.User;
import org.springframework.data.repository.CrudRepository;


public interface UserOrProjectRepository extends CrudRepository<UserOrProjectSQL, Integer> {

    UserOrProjectSQL findByUserProjectId(Integer userProjectId);


}
