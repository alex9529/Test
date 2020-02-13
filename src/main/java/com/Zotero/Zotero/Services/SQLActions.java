package com.Zotero.Zotero.Services;

import com.Zotero.Zotero.JSONObjects.Item;
import com.Zotero.Zotero.Repositories.*;

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


    public void RemoveItem(ItemRepository itemRepo, ItemCollectionRepository itemCollectionRepo,
                           ItemTypeFieldsRepository itemTypeFieldsRepo, ItemAuthorRepository itemAuthorRepo,
                           String itemKey, LinkedList<CollectionSQL> collectionSQLList) {


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

        return itemCollectionRepo.getAllByCollectionKey(collectionKey);
    }


    public Iterable<ItemSQL> GetAllItemsFromLibrary(ItemRepository itemRepo, int libraryId) {

        return  itemRepo.findAllByLibraryId(libraryId);
    }


    public void saveUser(UserSQL userSQL, UserRepository userRepo) {
        userRepo.save(userSQL);
    }

    public int CheckForRemovedItemsInCollection(ItemRepository itemRepo, CollectionRepository collectionRepo, ItemCollectionRepository itemCollectionRepo,
                                                ItemTypeFieldsRepository itemTypeFieldsRepo, ItemAuthorRepository itemAuthorRepo,
                                                String collectionKey, LinkedList<CollectionSQL> collectionSQLList, LinkedList<Item> itemList) {

        int deletedItems = 0;

        //Get all item-collection relationships in the DB which are part of the selected collection
        ArrayList<ItemCollectionSQL> repositoryItemCollection = (ArrayList<ItemCollectionSQL>) GetItemCollectionList(itemCollectionRepo, collectionKey);

        //Loop through all the items in the collection
        for (int i = 0; i < repositoryItemCollection.size(); i++) {

            //Search for a match in the updated item list coming from the API
            boolean match = false;
            int k = 0;
            while (!match && k < itemList.size()) {
                if (repositoryItemCollection.get(i).getItemKey().equals(itemList.get(k).getKey())) {
                    match = true;
                }
                k++;
            }
            //If there is no match (i.e. the item in the DB is no longer available on Zotero), remove it
            if (!match) {
                RemoveItem(itemRepo, itemCollectionRepo, itemTypeFieldsRepo, itemAuthorRepo,
                        repositoryItemCollection.get(i).getItemKey(), collectionSQLList);
                deletedItems++;
            }
        }

        //update the collection table (the number of items attribute will be updated this way)

        collectionRepo.save(collectionSQLList.get(0));



        return deletedItems;
    }

    public int CheckForRemovedItemsInLibrary(ItemRepository itemRepo, ItemCollectionRepository itemCollectionRepo,
                                             ItemTypeFieldsRepository itemTypeFieldsRepo, ItemAuthorRepository itemAuthorRepo,
                                             LinkedList<CollectionSQL> collectionSQLList, LinkedList<Item> itemList, LibrarySQL librarySQL) {

        int deletedItems = 0;

        //Get all items in the DB which are part of the selected library
        ArrayList<ItemSQL> repositoryItems = (ArrayList<ItemSQL>) GetAllItemsFromLibrary(itemRepo, librarySQL.getLibraryId());

        //Loop through all the items
        for (int i = 0; i < repositoryItems.size(); i++) {

            //Search for a match in the updated item list coming from the API
            boolean match = false;
            int k = 0;
            while (!match && k < itemList.size()) {
                if (repositoryItems.get(i).getKey().equals(itemList.get(k).getKey())) {
                    match = true;
                }
                k++;
            }
            //If there is no match (i.e. the item in the DB is no longer available on Zotero), remove it
            if (!match) {
                RemoveItem(itemRepo, itemCollectionRepo, itemTypeFieldsRepo, itemAuthorRepo,
                        repositoryItems.get(i).getKey(), collectionSQLList);
                deletedItems++;
            }
        }
        return deletedItems;
    }

}


