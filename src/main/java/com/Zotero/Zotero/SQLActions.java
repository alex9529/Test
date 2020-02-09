package com.Zotero.Zotero;

import com.Zotero.Zotero.SQL.*;
import org.apache.catalina.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.PersistenceContext;
import java.util.LinkedList;


public class SQLActions {


        public void saveItem(ItemRepository itemRepo, CollectionRepository collectionRepo, ItemCollectionRepository itemCollectionRepo,
                             ItemTypeFieldsRepository itemTypeFieldsRepo, ItemAuthorRepository itemAuthorRepo, LibraryRepository libraryRepo,
                             ItemSQL itemSQL, CollectionSQL collectionSQL, LinkedList<ItemCollectionSQL> itemCollectionSQLList, ItemTypeFieldsSQL itemTypeFieldsSQL,
                             LibrarySQL librarySQL, LinkedList<ItemAuthorSQL> itemAuthorSQLList){

            itemRepo.save(itemSQL);
            collectionRepo.save(collectionSQL);

            for (int i = 0; i<itemCollectionSQLList.size(); i++) {
                itemCollectionRepo.save(itemCollectionSQLList.get(i));
            }

            for (int i = 0; i<itemAuthorSQLList.size(); i++) {
                itemAuthorRepo.save(itemAuthorSQLList.get(i));
            }

            itemTypeFieldsRepo.save(itemTypeFieldsSQL);
            libraryRepo.save(librarySQL);


        }

        public void saveUser (UserSQL userSQL, UserRepository userRepo){
            userRepo.save(userSQL);
        }

}
