package com.Zotero.Zotero.Controllers;


import com.Zotero.Zotero.JSONObjects.Library;
import com.Zotero.Zotero.Services.APICalls;
import com.Zotero.Zotero.JSONObjects.Item;
import com.Zotero.Zotero.Repositories.*;
import com.Zotero.Zotero.Services.SQLActions;
import com.Zotero.Zotero.Services.SQLEntities;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
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


        LinkedList<Item> itemList;
        LinkedList<ItemSQL> itemSQLList;
        LinkedList<CollectionSQL> collectionSQLList;
        LinkedList<ItemCollectionSQL> itemCollectionSQLList;
        LinkedList<ItemAuthorSQL> itemAuthorSQLList;


        //Generate the itemList and all the necessary SQL-ready entities
        SQLEntities sqlEntities = apiCalls.PrepareItemsForDB(restTemplate,groupsOrUsers,apiKey,id,collectionKey);

        itemList = sqlEntities.getItemList();
        itemSQLList = sqlEntities.getItemSQLList();
        collectionSQLList = sqlEntities.getCollectionSQLList();
        itemCollectionSQLList = sqlEntities.getItemCollectionSQLList();
        itemAuthorSQLList = sqlEntities.getItemAuthorSQLList();
        itemTypeFieldsSQL = sqlEntities.getItemTypeFieldsSQL();
        userSQL = sqlEntities.getUserSQL();
        librarySQL = sqlEntities.getLibrarySQL();



        //Delete Items from DB if they are no longer available on Zotero
        int deletedItems = sqlActions.CheckForRemovedItemsInCollection(itemRepo, collectionRepo, itemCollectionRepo, itemTypeFieldsRepo, itemAuthorRepo,
                collectionKey, collectionSQLList, itemList);


        //Each item in itemSQLList is being saved in the database including all the relevant SQL tables: collection, itemCollection, itemTypeFields, itemAuthor, library
        //Note: to avoid saving "invisible items", only the ones that have the collection key in the JSON attribute "  "collections" : [...]  " are going to be sent to the DB
        failedItems.clear();
        for (int k = 0; k < itemSQLList.size(); k++) {

                failedItems.add(sqlActions.saveItem(itemRepo, collectionRepo, itemCollectionRepo, itemTypeFieldsRepo, itemAuthorRepo, libraryRepo,
                        itemSQLList.get(k), collectionSQLList, itemCollectionSQLList, itemTypeFieldsSQL, librarySQL, itemAuthorSQLList));
                if (failedItems.getLast().equals("")) {
                    failedItems.removeLast();
                }
        }

        if (itemList.size() > 0) {
            sqlActions.saveUser(userSQL, userRepo);
        }


        //Retrieve collection name for the syncCollection view
        String collectionName = apiCalls.GetCollectionName(restTemplate, id, apiKey, groupsOrUsers, collectionKey);

        //Retrieve number of items for the syncCollection view
        int numberOfItems = itemList.size();


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
