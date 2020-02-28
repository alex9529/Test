package com.Zotero.Zotero.Services;

import com.Zotero.Zotero.JSONObjects.Collection;
import com.Zotero.Zotero.JSONObjects.Item;
import com.Zotero.Zotero.Repositories.*;

import java.util.ArrayList;
import java.util.LinkedList;


public class SQLActions {



    public String saveItem(ItemRepository itemRepo, CollectionRepository collectionRepo, ItemCollectionRepository itemCollectionRepo,
                           ItemTypeFieldsRepository itemTypeFieldsRepo, ItemAuthorRepository itemAuthorRepo,
                           ItemSQL itemSQL, CollectionSQL collectionSQLList, LinkedList<ItemCollectionSQL> itemCollectionSQLList, ItemTypeFieldsSQL itemTypeFieldsSQL,
                           LinkedList<ItemAuthorSQL> itemAuthorSQLList) {

        String failedItems = new String();
        try {

            //Save the item. If an error occurs, put it into an array of failed item synchronizations
            itemRepo.save(itemSQL);

            //Save the itemCollection relationships
            for (int i = 0; i < itemCollectionSQLList.size(); i++) {
                itemCollectionRepo.save(itemCollectionSQLList.get(i));
            }

            //Save all the collection data
            collectionRepo.save(collectionSQLList);


            //Save the itemAuthor relationship
            for (int i = 0; i < itemAuthorSQLList.size(); i++) {
                itemAuthorRepo.save(itemAuthorSQLList.get(i));
            }

            //Save the itemTypeFields relationship
            itemTypeFieldsRepo.save(itemTypeFieldsSQL);


        } catch (Exception e) {
            failedItems = itemSQL.getKey();
        }

        return failedItems;
    }


    public String saveItemWithNoCollection(ItemRepository itemRepo,
                           ItemTypeFieldsRepository itemTypeFieldsRepo, ItemAuthorRepository itemAuthorRepo,
                           ItemSQL itemSQL, ItemTypeFieldsSQL itemTypeFieldsSQL,
                           LinkedList<ItemAuthorSQL> itemAuthorSQLList) {
        String failedItems = new String();
        try {

            //Save the item. If an error occurs, put it into an array of failed item synchronizations
            itemRepo.save(itemSQL);


            //Save the itemAuthor relationship
            for (int i = 0; i < itemAuthorSQLList.size(); i++) {
                itemAuthorRepo.save(itemAuthorSQLList.get(i));
            }

            //Save the itemTypeFields relationship
            itemTypeFieldsRepo.save(itemTypeFieldsSQL);

        } catch (Exception e) {
            failedItems = itemSQL.getKey();
        }




        return failedItems;
    }


    public void RemoveItem(ItemRepository itemRepo, ItemCollectionRepository itemCollectionRepo,
                           ItemTypeFieldsRepository itemTypeFieldsRepo, ItemAuthorRepository itemAuthorRepo,
                           String itemKey, CollectionSQL collection) {


        //Get a list of all the item-collection relationships from the table item_collection. If the item exists in only one collection, it can be safely deleted,
        //including all the affected tables in the DB (item_type_fields, item_author, item_collection).
        //If it is contained in more than one collection, it must be not removed entirely from the DB but just the item-collection relationship.
            ArrayList<ItemCollectionSQL> numberOfCollections = (ArrayList<ItemCollectionSQL>) itemCollectionRepo.getAllByItemKey(itemKey);
        if (numberOfCollections.size() <2) {
            itemRepo.removeByKey(itemKey);
            itemCollectionRepo.removeByItemKey(itemKey);
            itemAuthorRepo.removeByItemKey(itemKey);
            itemTypeFieldsRepo.removeByKey(itemKey);
        } else {
            itemCollectionRepo.removeByItemKeyAndCollectionKey(itemKey, collection.getCollectionKey());
        }
    }


    private void RemoveCollectionlessItem(ItemRepository itemRepo, ItemTypeFieldsRepository itemTypeFieldsRepo, ItemAuthorRepository itemAuthorRepo, String itemKey) {
        //If the item exists in the library top level and nowhere else, it can be safely deleted,
        //including all the affected tables in the DB (item_type_fields, item_author, item_collection).
        itemRepo.removeByKey(itemKey);
        itemAuthorRepo.removeByItemKey(itemKey);
        itemTypeFieldsRepo.removeByKey(itemKey);
    }

    public Iterable<ItemCollectionSQL> GetItemCollectionList(ItemCollectionRepository itemCollectionRepo, String collectionKey) {

        return itemCollectionRepo.getAllByCollectionKey(collectionKey);
    }


    public Iterable<ItemSQL> GetAllItemsFromLibrary(ItemRepository itemRepo, int libraryId) {

        return  itemRepo.findAllByLibraryId(libraryId);
    }


    public Iterable<CollectionSQL> GetAllCollections(CollectionRepository collectionRepo, int libraryId) {

        return  collectionRepo.findByLibraryId(libraryId);
    }


    public void saveUser(UserSQL userSQL, UserRepository userRepo) {
        userRepo.save(userSQL);
    }

    public int CheckForRemovedItemsInCollection(ItemRepository itemRepo, CollectionRepository collectionRepo, ItemCollectionRepository itemCollectionRepo,
                                                ItemTypeFieldsRepository itemTypeFieldsRepo, ItemAuthorRepository itemAuthorRepo,
                                                String collectionKey, CollectionSQL collectionSQL, LinkedList<Item> itemList) {

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
                        repositoryItemCollection.get(i).getItemKey(), collectionSQL);
                deletedItems++;
            }
        }

        //update the collection table (the number of items attribute will be updated this way)

        collectionRepo.save(collectionSQL);



        return deletedItems;
    }

    public int CheckForRemovedItemsInLibrary(ItemRepository itemRepo,
                                             ItemTypeFieldsRepository itemTypeFieldsRepo, ItemAuthorRepository itemAuthorRepo,
                                             LinkedList<Item> itemList, int libraryId) {

        int deletedItems = 0;

        //Get all items in the DB which are part of the selected library
        ArrayList<ItemSQL> repositoryItems = (ArrayList<ItemSQL>) GetAllItemsFromLibrary(itemRepo, libraryId);

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
                RemoveCollectionlessItem(itemRepo, itemTypeFieldsRepo, itemAuthorRepo,
                        repositoryItems.get(i).getKey());
                deletedItems++;
            }
        }
        return deletedItems;
    }



    public int[] CheckForRemovedCollectionsInLibrary(ItemRepository itemRepo, CollectionRepository collectionRepo, ItemCollectionRepository itemCollectionRepo,
                                             ItemTypeFieldsRepository itemTypeFieldsRepo, ItemAuthorRepository itemAuthorRepo,
                                             LinkedList<CollectionSQL> collectionSQLList, int libraryId) {
        int[] deletedCollectionsAndItems = new int[2];
        int deletedCollections = 0;
        int deletedItems = 0;

        //Get all Collections in the DB
        ArrayList<CollectionSQL> repositoryCollections = (ArrayList<CollectionSQL>) GetAllCollections(collectionRepo, libraryId);

        //Loop through all the collections
        for (int c = 0; c < repositoryCollections.size(); c++) {

            //Search for a match in the updated collections list coming from the API
            boolean match = false;
            int k = 0;
            while (!match && k < collectionSQLList.size()) {
                if (repositoryCollections.get(c).getCollectionKey().equals(collectionSQLList.get(k).getCollectionKey())) {
                    match = true;
                }
                k++;
            }
            //If there is no match (i.e. the collection in the DB is no longer available on Zotero), remove it
            if (!match) {

                //Get all item keys from the collection
                ArrayList<ItemCollectionSQL> repositoryItemCollection = (ArrayList<ItemCollectionSQL>) GetItemCollectionList(itemCollectionRepo,repositoryCollections.get(c).getCollectionKey());
                //Remove all the items in the collection first
                for (int i = 0; i<repositoryItemCollection.size(); i++){
                    RemoveItem(itemRepo, itemCollectionRepo, itemTypeFieldsRepo, itemAuthorRepo,
                            repositoryItemCollection.get(i).getItemKey(), repositoryCollections.get(c));
                    deletedItems++;
                }
                deletedCollections++;
                //Remove the collection itself
                RemoveCollection(collectionRepo, repositoryCollections.get(c));

            }
        }
        deletedCollectionsAndItems[0] = deletedCollections;
        deletedCollectionsAndItems[1] = deletedItems;
        return deletedCollectionsAndItems;
    }

    private void RemoveCollection(CollectionRepository collectionRepo, CollectionSQL collectionSQL) {
        collectionRepo.removeByCollectionKey(collectionSQL.getCollectionKey());
    }


}


