package com.Zotero.Zotero.Controllers;


import com.Zotero.Zotero.Services.APICalls;
import com.Zotero.Zotero.JSONObjects.Item;
import com.Zotero.Zotero.Repositories.*;
import com.Zotero.Zotero.Services.SQLActions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

@Controller
public class SyncCollectionController {


    private APICalls apiCalls = new APICalls();
    private SQLActions sqlActions = new SQLActions();


    private ItemTypeFieldsSQL itemTypeFieldsSQL;
    private UserSQL userSQL;
    private LibrarySQL librarySQL;
    private CollectionSQL collectionSQL;


    private final ItemRepository itemRepo;
    private final CollectionRepository collectionRepo;
    private final ItemCollectionRepository itemCollectionRepo;
    private final ItemTypeFieldsRepository itemTypeFieldsRepo;
    private final UserRepository userRepo;
    private final ItemAuthorRepository itemAuthorRepo;
    private final LibraryRepository libraryRepo;


    public SyncCollectionController(ItemRepository itemRepo, CollectionRepository collectionRepo, ItemCollectionRepository itemCollectionRepo,
                                    ItemTypeFieldsRepository itemTypeFieldsRepo, UserRepository userRepo, ItemAuthorRepository itemAuthorRepo, LibraryRepository libraryRepo) {

        this.itemRepo = itemRepo;
        this.collectionRepo = collectionRepo;
        this.itemCollectionRepo = itemCollectionRepo;
        this.itemTypeFieldsRepo = itemTypeFieldsRepo;
        this.userRepo = userRepo;
        this.itemAuthorRepo = itemAuthorRepo;
        this.libraryRepo = libraryRepo;
    }


    LinkedList<String> failedItems = new LinkedList<>();

    @GetMapping("/syncCollection")
    public String syncLibrary(RestTemplate restTemplate,
                              @RequestParam(name = "groupsOrUsers", required = false, defaultValue = "") String groupsOrUsers,
                              @RequestParam(name = "apiKey", required = false, defaultValue = "") String apiKey,
                              @RequestParam(name = "id", required = false, defaultValue = "") String id,
                              @RequestParam(name = "collectionKey", required = false, defaultValue = "") String collectionKey, Model model

    ) throws IOException {


        LinkedList<Item> itemList = new LinkedList<>();
        LinkedList<ItemSQL> itemSQLList = new LinkedList<ItemSQL>();
        LinkedList<CollectionSQL> collectionSQLList = new LinkedList<CollectionSQL>();
        LinkedList<ItemCollectionSQL> itemCollectionSQLList = new LinkedList<>();
        LinkedList<ItemAuthorSQL> itemAuthorSQLList = new LinkedList<>();
        int deletedItems = 0;

        //All items from the collection are called and transformed into SQL-ready objects
        itemList = new LinkedList<>(apiCalls.CallAllItemsFromCollection(restTemplate, id, apiKey, collectionKey, groupsOrUsers));
        for (int k = 0; k < itemList.size(); k++) {
            itemSQLList.add(new ItemSQL(itemList.get(k)));
        }

        //Get the collection and transform it into SQL-ready object
        CollectionSQL collectionSQL = new CollectionSQL(apiCalls.CallCollection(restTemplate, id, collectionKey, apiKey, groupsOrUsers));
        collectionSQLList.add(collectionSQL);

        //Get all the Collection - Item relationships
        //Loop through all items in the library
        for (int i = 0; i < itemList.size(); i++) {
            //Loop through all collections that contain item i
            for (int c = 0; c < itemList.get(i).getData().getCollections().size(); c++) {
                itemCollectionSQLList.add(new ItemCollectionSQL(itemList.get(i), c));
            }
        }

        //Get all the Author - Item relationships
        //Loop through all items in the library
        for (int i = 0; i < itemList.size(); i++) {
            //Loop through all authors of an item
            for (int a = 0; a < itemList.get(i).getData().getCreators().size(); a++) {
                itemAuthorSQLList.add(new ItemAuthorSQL(itemList.get(i), a));
            }
        }


        //Get all the ItemTypeFields for an item
        //Loop through all items in the library
        for (int i = 0; i < itemList.size(); i++) {
            itemTypeFieldsSQL = new ItemTypeFieldsSQL(itemList.get(i));
        }

        //Save user and library data
        if (itemList.size() > 0) {
            userSQL = new UserSQL(itemList.get(0));
            librarySQL = new LibrarySQL(itemList.get(0));
        }


        //each item is being saved in the database including all the relevant SQL tables: collection, itemCollection, itemTypeFields, itemAuthor, library
        failedItems.clear();
        for (int k = 0; k < itemSQLList.size(); k++) {

            if (itemList.get(k).getData().getCollections().size()!=0) {
                failedItems.add(sqlActions.saveItem(itemRepo, collectionRepo, itemCollectionRepo, itemTypeFieldsRepo, itemAuthorRepo, libraryRepo,
                        itemSQLList.get(k), collectionSQLList, itemCollectionSQLList, itemTypeFieldsSQL, librarySQL, itemAuthorSQLList));
                if (failedItems.getLast().equals("")) {
                    failedItems.removeLast();
                }
            }


        }


        if (itemList.size() > 0) {
            sqlActions.saveUser(userSQL, userRepo);
        }

        String collectionName = apiCalls.GetCollectionName(restTemplate, id, apiKey, groupsOrUsers, collectionKey);


        //-----------------------------------------------
        //Delete Items from SQL if they are no longer present in Zotero
        //Get all item-collection relationships in the DB which are part of the selected collection
        ArrayList<ItemCollectionSQL> repositoryItemCollection = (ArrayList<ItemCollectionSQL>) sqlActions.GetItemCollectionList(itemCollectionRepo, collectionKey);

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
                sqlActions.removeItem(itemRepo, collectionRepo, itemCollectionRepo, itemTypeFieldsRepo, itemAuthorRepo, libraryRepo,
                        repositoryItemCollection.get(i).getItemKey(), collectionSQLList, itemCollectionSQLList, itemTypeFieldsSQL, librarySQL, itemAuthorSQLList);
                deletedItems++;
            }
        }
        //-----------------------------------------------


        //Get the number of item chunks of the size between 1 and 100
        String url = apiCalls.AssembleCollectionURL(id, apiKey, collectionKey, groupsOrUsers);



        int numberOfItems = collectionSQL.getNumItems();
                //apiCalls.GetNumberOfItems(url);


        //Number successfully synced items
        int successfulItems = numberOfItems - failedItems.size();

        model.addAttribute("numberItems", numberOfItems);
        model.addAttribute("deletedItems", deletedItems);
        model.addAttribute("successfulItems", (successfulItems));
        model.addAttribute("collectionName", collectionName);
        model.addAttribute("collectionKey", collectionKey);
        model.addAttribute("id", id);


        return "syncCollection";

    }

}
