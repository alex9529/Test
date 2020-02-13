package com.Zotero.Zotero.Repositories;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface LibraryRepository extends CrudRepository<LibrarySQL, Integer> {

    LibrarySQL findByLibraryId(Integer libraryId);
}
