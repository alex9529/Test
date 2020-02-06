package com.Zotero.Zotero;

import com.Zotero.Zotero.SQL.*;
import org.apache.catalina.User;

public class SQLActions {

    private UserSQL userSQL;

        public void saveItem (ItemRepository itemRepo, CollectionRepository collectionRepo, ItemCollectionRepository itemCollectionRepo,
                              ItemTypeFieldsRepository itemTypeFieldsRepo, ItemAuthorRepository itemAuthorRepo, LibraryRepository libraryRepo){

        }

        public void saveUser (UserSQL userSQL, UserRepository userRepo){
            userRepo.save(userSQL);
        }

}
