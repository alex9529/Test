package com.Zotero.Zotero.Services;

import com.Zotero.Zotero.Repositories.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedList;


public class SQLActions {

    String failedItems = new String();

    public String saveItem(ItemRepository itemRepo, CollectionRepository collectionRepo, ItemCollectionRepository itemCollectionRepo,
                           ItemTypeFieldsRepository itemTypeFieldsRepo, ItemAuthorRepository itemAuthorRepo, LibraryRepository libraryRepo,
                           ItemSQL itemSQL, LinkedList<CollectionSQL> collectionSQLList, LinkedList<ItemCollectionSQL> itemCollectionSQLList, ItemTypeFieldsSQL itemTypeFieldsSQL,
                           LibrarySQL librarySQL, LinkedList<ItemAuthorSQL> itemAuthorSQLList) {


        try {
            //Save the item. If an error occurs, put it into an array of failed item synchronizations
            itemRepo.save(itemSQL);

            //Save the itemCollection relationships
            for (int i = 0; i < itemCollectionSQLList.size(); i++) {
                itemCollectionRepo.save(itemCollectionSQLList.get(i));
            }

            //Save all the collection data
            for (int i = 0; i < collectionSQLList.size(); i++) {
                collectionRepo.save(collectionSQLList.get(i));
            }

            //Save the itemAuthor relationship
            for (int i = 0; i < itemAuthorSQLList.size(); i++) {
                itemAuthorRepo.save(itemAuthorSQLList.get(i));
            }

            //Save the itemTypeFields relationship
            itemTypeFieldsRepo.save(itemTypeFieldsSQL);

            //Save the library data
            libraryRepo.save(librarySQL);
        } catch (Exception e) {
            failedItems = itemSQL.getKey();
        }

        return failedItems;
    }


    public void removeItem(ItemRepository itemRepo, CollectionRepository collectionRepo, ItemCollectionRepository itemCollectionRepo,
                           ItemTypeFieldsRepository itemTypeFieldsRepo, ItemAuthorRepository itemAuthorRepo, LibraryRepository libraryRepo,
                           String itemKey, LinkedList<CollectionSQL> collectionSQLList, LinkedList<ItemCollectionSQL> itemCollectionSQLList, ItemTypeFieldsSQL itemTypeFieldsSQL,
                           LibrarySQL librarySQL, LinkedList<ItemAuthorSQL> itemAuthorSQLList) {


        //Get a list of all the item-collection relationships from the table item_collection. If the item exists in only one collection, it can be safely deleted,
        //including all the affected tables in the DB (item_type_fields, item_author, item_collection).
        //If it is contained in more than one collection, it must be not removed entirely from the DB but just the item-collection relationship.
        ArrayList<ItemCollectionSQL> numberOfCollections = (ArrayList<ItemCollectionSQL>) itemCollectionRepo.getAllByItemKey(itemKey);
        if (numberOfCollections.size() == 1) {
            itemRepo.removeByKey(itemKey);
            itemCollectionRepo.removeByItemKey(itemKey);
            itemAuthorRepo.removeByItemKey(itemKey);
            itemTypeFieldsRepo.removeByKey(itemKey);
        } else {
            itemCollectionRepo.removeByItemKeyAndCollectionKey(itemKey, collectionSQLList.get(0).getCollectionKey());
        }
    }

    public Iterable<ItemCollectionSQL> GetItemCollectionList(ItemCollectionRepository itemCollectionRepo, String collectionKey) {

        Iterable<ItemCollectionSQL> listSQLItems = itemCollectionRepo.getAllByCollectionKey(collectionKey);
        return listSQLItems;
    }


    public void saveUser(UserSQL userSQL, UserRepository userRepo) {
        userRepo.save(userSQL);
    }

}
