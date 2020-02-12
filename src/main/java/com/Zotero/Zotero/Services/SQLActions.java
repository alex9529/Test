package com.Zotero.Zotero.Services;

import com.Zotero.Zotero.Repositories.*;
import java.util.LinkedList;

public class SQLActions {

        LinkedList<String> failedItems;

        public LinkedList<String> saveItem(ItemRepository itemRepo, CollectionRepository collectionRepo, ItemCollectionRepository itemCollectionRepo,
                             ItemTypeFieldsRepository itemTypeFieldsRepo, ItemAuthorRepository itemAuthorRepo, LibraryRepository libraryRepo,
                             ItemSQL itemSQL, LinkedList<CollectionSQL> collectionSQLList, LinkedList<ItemCollectionSQL> itemCollectionSQLList, ItemTypeFieldsSQL itemTypeFieldsSQL,
                             LibrarySQL librarySQL, LinkedList<ItemAuthorSQL> itemAuthorSQLList){


            //Save the item. If an error occurs, put it into an array of failed item synchronizations
            try{
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
            } catch (Exception e) {
               failedItems.add(itemSQL.getKey());
            }
            return failedItems;
        }

        public void saveUser (UserSQL userSQL, UserRepository userRepo){
            userRepo.save(userSQL);
        }

}
