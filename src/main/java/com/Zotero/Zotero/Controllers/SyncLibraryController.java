package com.Zotero.Zotero.Controllers;


import com.Zotero.Zotero.Services.APICalls;
import com.Zotero.Zotero.JSONObjects.Collection;
import com.Zotero.Zotero.JSONObjects.Item;
import com.Zotero.Zotero.Repositories.*;
import com.Zotero.Zotero.Services.SQLActions;
import com.Zotero.Zotero.Services.SQLEntities;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.LinkedList;

@Controller
public class SyncLibraryController {


    private APICalls apiCalls = new APICalls();
    private SQLActions sqlActions = new SQLActions();

    private ItemTypeFieldsSQL itemTypeFieldsSQL;



    private final ItemRepository itemRepo;
    private final CollectionRepository collectionRepo;
    private final ItemCollectionRepository itemCollectionRepo;
    private final ItemTypeFieldsRepository itemTypeFieldsRepo;
    private final UserRepository userRepo;
    private final ItemAuthorRepository itemAuthorRepo;
    private final LibraryRepository libraryRepo;


    public SyncLibraryController(ItemRepository itemRepo, CollectionRepository collectionRepo, ItemCollectionRepository itemCollectionRepo,
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

    @GetMapping("/syncLibrary")
    public String syncLibrary(RestTemplate restTemplate,
                              @RequestParam(name = "groupsOrUsers", required = false, defaultValue = "") String groupsOrUsers,
                              @RequestParam(name = "apiKey", required = false, defaultValue = "") String apiKey,
                              @RequestParam(name = "id", required = false, defaultValue = "") String id, Model model

    ) throws IOException {


        UserSQL userSQL = new UserSQL();
        LibrarySQL librarySQL = new LibrarySQL();

        LinkedList<Item> itemList = new LinkedList<>();
        LinkedList<ItemSQL> itemSQLList = new LinkedList<>();
        LinkedList<CollectionSQL> collectionSQLList = new LinkedList<>();
        LinkedList<ItemCollectionSQL> itemCollectionSQLList = new LinkedList<>();
        LinkedList<ItemAuthorSQL> itemAuthorSQLList = new LinkedList<>();
        int deletedItems = 0;
        int deletedCollections = 0;




        //Get all Collections from Library
        LinkedList<Collection> collections = apiCalls.CallAllCollections(restTemplate, id, apiKey, groupsOrUsers);


        //Loop through all collections
        for (int c = 0; c<collections.size(); c++){
            //Generate the itemList and all the necessary SQL-ready entities
            SQLEntities sqlEntities = apiCalls.PrepareItemsForDB(restTemplate,groupsOrUsers,apiKey,id,collections.get(c).getCollectionKey());

            itemList = sqlEntities.getItemList();
            itemSQLList = sqlEntities.getItemSQLList();
            collectionSQLList = sqlEntities.getCollectionSQLList();
            itemCollectionSQLList = sqlEntities.getItemCollectionSQLList();
            itemAuthorSQLList = sqlEntities.getItemAuthorSQLList();
            itemTypeFieldsSQL = sqlEntities.getItemTypeFieldsSQL();

            if (librarySQL.getLibraryName()==null){
                userSQL = sqlEntities.getUserSQL();
                librarySQL = sqlEntities.getLibrarySQL();
            }




            //Each item in itemSQLList is being saved in the database including all the relevant SQL tables: collection, itemCollection, itemTypeFields, itemAuthor, library
            failedItems.clear();
            for (int k = 0; k < itemSQLList.size(); k++) {
                failedItems.add(sqlActions.saveItem(itemRepo, collectionRepo, itemCollectionRepo, itemTypeFieldsRepo, itemAuthorRepo, libraryRepo,
                        itemSQLList.get(k), collectionSQLList, itemCollectionSQLList, itemTypeFieldsSQL, librarySQL, itemAuthorSQLList, userRepo, userSQL));
                if (failedItems.getLast().equals("")) {
                    failedItems.removeLast();
                }
            }
        }


        //Delete Collections from DB if they are no longer available on Zotero
        deletedCollections = sqlActions.CheckForRemovedCollectionsInLibrary(itemRepo, collectionRepo, itemCollectionRepo, itemTypeFieldsRepo, itemAuthorRepo,
                collectionSQLList, librarySQL);


        //Delete Items from DB if they are no longer available in library on Zotero
        deletedItems = sqlActions.CheckForRemovedItemsInLibrary(itemRepo, itemCollectionRepo, itemTypeFieldsRepo, itemAuthorRepo,
                collectionSQLList, itemList, librarySQL);



        //Save the user and library data
        if (itemList.size() > 0) {
            sqlActions.saveUser(userSQL, userRepo);
            libraryRepo.save(librarySQL);
        }


        /**

        //All items from the library are called and transformed into SQL-ready objects
        itemList = new LinkedList<>(apiCalls.CallAllItems(restTemplate, id, apiKey, groupsOrUsers));

        //The items are transformed into SQL-ready objects
        itemSQLList = new LinkedList<>();
        for (int k = 0; k < itemList.size(); k++) {
            itemSQLList.add(new ItemSQL(itemList.get(k)));
        }

         */


        //Each item in itemSQLList is being saved in the database including all the relevant SQL tables: collection, itemCollection, itemTypeFields, itemAuthor, library
        failedItems.clear();
        for (int k = 0; k < itemSQLList.size(); k++) {
            failedItems.add(sqlActions.saveItem(itemRepo, collectionRepo, itemCollectionRepo, itemTypeFieldsRepo, itemAuthorRepo, libraryRepo,
                    itemSQLList.get(k), collectionSQLList, itemCollectionSQLList, itemTypeFieldsSQL, librarySQL, itemAuthorSQLList, userRepo, userSQL));
            if (failedItems.getLast().equals("")) {
                failedItems.removeLast();
            }
        }



        //Get the library name
        String libraryName = (new LinkedList<Collection>(apiCalls.CallAllCollections(restTemplate, id, apiKey, groupsOrUsers))).get(0).getLibrary().getName();




        //Get the number of item chunks of the size between 1 and 100
        String url = apiCalls.AssembleURL(id, apiKey, groupsOrUsers);
        int numberOfItems = itemList.size();


        int successfulItems = numberOfItems - failedItems.size();

        model.addAttribute("numberItems", numberOfItems);
        model.addAttribute("deletedItems", deletedItems);
        model.addAttribute("deletedCollections", deletedCollections);
        model.addAttribute("successfulItems", (successfulItems));
        model.addAttribute("id", id);
        model.addAttribute("libraryName", libraryName);

        return "syncLibrary";

    }

}
