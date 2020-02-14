package com.Zotero.Zotero.Services;

import com.Zotero.Zotero.JSONObjects.Item;
import com.Zotero.Zotero.Repositories.*;

import java.util.LinkedList;

public class SQLEntities {

    LinkedList<Item> itemList = new LinkedList<>();
    LinkedList<ItemSQL> itemSQLList = new LinkedList<>();
    LinkedList<CollectionSQL> collectionSQLList = new LinkedList<>();
    LinkedList<ItemCollectionSQL> itemCollectionSQLList = new LinkedList<>();
    LinkedList<ItemAuthorSQL> itemAuthorSQLList = new LinkedList<>();
    ItemTypeFieldsSQL itemTypeFieldsSQL = new ItemTypeFieldsSQL();
    UserSQL userSQL = new UserSQL();
    LibrarySQL librarySQL = new LibrarySQL();


    public SQLEntities(LinkedList<Item> itemList, LinkedList<ItemSQL> itemSQLList, LinkedList<CollectionSQL> collectionSQLList,
                       LinkedList<ItemCollectionSQL> itemCollectionSQLList, LinkedList<ItemAuthorSQL> itemAuthorSQLList,
                       ItemTypeFieldsSQL itemTypeFieldsSQL, UserSQL userSQL, LibrarySQL librarySQL) {
        this.itemList = itemList;
        this.itemSQLList = itemSQLList;
        this.collectionSQLList = collectionSQLList;
        this.itemCollectionSQLList = itemCollectionSQLList;
        this.itemAuthorSQLList = itemAuthorSQLList;
        this.itemTypeFieldsSQL = itemTypeFieldsSQL;
        this.userSQL = userSQL;
        this.librarySQL = librarySQL;
    }

    public LinkedList<Item> getItemList() {
        return itemList;
    }

    public LinkedList<ItemSQL> getItemSQLList() {
        return itemSQLList;
    }

    public LinkedList<CollectionSQL> getCollectionSQLList() {
        return collectionSQLList;
    }

    public LinkedList<ItemCollectionSQL> getItemCollectionSQLList() {
        return itemCollectionSQLList;
    }

    public LinkedList<ItemAuthorSQL> getItemAuthorSQLList() {
        return itemAuthorSQLList;
    }

    public ItemTypeFieldsSQL getItemTypeFieldsSQL() {
        return itemTypeFieldsSQL;
    }

    public UserSQL getUserSQL() {
        return userSQL;
    }

    public LibrarySQL getLibrarySQL() {
        return librarySQL;
    }
}
