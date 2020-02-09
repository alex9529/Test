package com.Zotero.Zotero;

import com.Zotero.Zotero.JSONObjects.Collection;
import com.Zotero.Zotero.SQL.*;
import java.util.LinkedList;

public class SQLActions {

        public void saveItem(ItemRepository itemRepo, CollectionRepository collectionRepo, ItemCollectionRepository itemCollectionRepo,
                             ItemTypeFieldsRepository itemTypeFieldsRepo, ItemAuthorRepository itemAuthorRepo, LibraryRepository libraryRepo,
                             ItemSQL itemSQL, LinkedList<CollectionSQL> collectionSQLList, LinkedList<ItemCollectionSQL> itemCollectionSQLList, ItemTypeFieldsSQL itemTypeFieldsSQL,
                             LibrarySQL librarySQL, LinkedList<ItemAuthorSQL> itemAuthorSQLList){

           //Save the item
            itemRepo.save(itemSQL);

            //Save the itemCollection relationships
            for (int i = 0; i<itemCollectionSQLList.size(); i++) {
                itemCollectionRepo.save(itemCollectionSQLList.get(i));
            }

            //Save all the collection data from the previous loop
            for (int i = 0; i<collectionSQLList.size(); i++) {
                collectionRepo.save(collectionSQLList.get(i));
            }

            //Save the itemAuthor relationship
            for (int i = 0; i<itemAuthorSQLList.size(); i++) {
                itemAuthorRepo.save(itemAuthorSQLList.get(i));
            }


            //Save the itemTypeFields relationship
            itemTypeFieldsRepo.save(itemTypeFieldsSQL);

            //Save the library data
            libraryRepo.save(librarySQL);
        }

        public void saveUser (UserSQL userSQL, UserRepository userRepo){
            userRepo.save(userSQL);
        }

}
