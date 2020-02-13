package com.Zotero.Zotero.Controllers;


import com.Zotero.Zotero.Services.APICalls;
import com.Zotero.Zotero.JSONObjects.Collection;
import com.Zotero.Zotero.JSONObjects.Item;
import com.Zotero.Zotero.Repositories.*;
import com.Zotero.Zotero.Services.SQLActions;
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
    private UserSQL userSQL;
    private LibrarySQL librarySQL;


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


    LinkedList<Item> itemList = new LinkedList<>();
    LinkedList<ItemSQL> itemSQLList = new LinkedList<ItemSQL>();
    LinkedList<CollectionSQL> collectionSQLList = new LinkedList<CollectionSQL>();
    LinkedList<ItemCollectionSQL> itemCollectionSQLList = new LinkedList<>();
    LinkedList<ItemAuthorSQL> itemAuthorSQLList = new LinkedList<>();


    LinkedList<String> failedItems = new LinkedList<>();

    @GetMapping("/syncLibrary")
    public String syncLibrary(RestTemplate restTemplate,
                              @RequestParam(name = "groupsOrUsers", required = false, defaultValue = "") String groupsOrUsers,
                              @RequestParam(name = "apiKey", required = false, defaultValue = "") String apiKey,
                              @RequestParam(name = "id", required = false, defaultValue = "") String id, Model model

    ) throws IOException {


        //All items from the library are called and transformed into SQL-ready objects
        itemList = new LinkedList<>(apiCalls.CallAllItems(restTemplate, id, apiKey, groupsOrUsers));
        for (int k = 0; k < itemList.size(); k++) {
            itemSQLList.add(new ItemSQL(itemList.get(k)));
        }

        //All collections from the library are called and transformed into SQL-ready objects
        LinkedList<Collection> collections = apiCalls.CallAllCollections(restTemplate, id, apiKey, groupsOrUsers);
        for (int k = 0; k < collections.size(); k++) {
            collectionSQLList.add(new CollectionSQL(collections.get(k)));
        }


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


        userSQL = new UserSQL(itemList.get(0));
        librarySQL = new LibrarySQL(itemList.get(0));


        //each item is being saved in the database including all the relevant SQL tables: collection, itemCollection, itemTypeFields, itemAuthor, library
        failedItems.clear();
        for (int k = 0; k < itemSQLList.size(); k++) {
            failedItems.add(sqlActions.saveItem(itemRepo, collectionRepo, itemCollectionRepo, itemTypeFieldsRepo, itemAuthorRepo, libraryRepo,
                    itemSQLList.get(k), collectionSQLList, itemCollectionSQLList, itemTypeFieldsSQL, librarySQL, itemAuthorSQLList));
            if (failedItems.getLast().equals("")) {
                failedItems.removeLast();
            }
        }

        sqlActions.saveUser(userSQL, userRepo);


        //Delete Collections from DB if they are no longer available on Zotero
        int deletedCollections = sqlActions.CheckForRemovedCollectionsInLibrary(itemRepo, collectionRepo, itemCollectionRepo, itemTypeFieldsRepo, itemAuthorRepo,
                collectionSQLList, itemList, librarySQL);




        //Delete Items from DB if they are no longer available on Zotero
        int deletedItems = sqlActions.CheckForRemovedItemsInLibrary(itemRepo, itemCollectionRepo, itemTypeFieldsRepo, itemAuthorRepo,
                collectionSQLList, librarySQL);


        String libraryName = (new LinkedList<Collection>(apiCalls.CallAllCollections(restTemplate, id, apiKey, groupsOrUsers))).get(0).getLibrary().getName();


        //Get the number of item chunks of the size between 1 and 100
        String url = apiCalls.AssembleURL(id, apiKey, groupsOrUsers);
        int numberOfItems = apiCalls.GetNumberOfItems(url);


        int successfulItems = numberOfItems - failedItems.size();

        model.addAttribute("numberItems", numberOfItems);
        model.addAttribute("deletedItems", deletedItems);
        model.addAttribute("successfulItems", (successfulItems));
        model.addAttribute("id", id);
        model.addAttribute("libraryName", libraryName);

        return "syncLibrary";

    }

}
