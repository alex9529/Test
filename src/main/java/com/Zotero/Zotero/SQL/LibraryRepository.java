package com.Zotero.Zotero.SQL;
import org.springframework.data.repository.CrudRepository;


public interface LibraryRepository extends CrudRepository<LibrarySQL, Integer> {

    LibrarySQL findByLibraryId(Integer libraryId);
}
