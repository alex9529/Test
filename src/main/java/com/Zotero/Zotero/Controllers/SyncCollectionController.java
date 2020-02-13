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


        //All items from the collection are called and transformed into SQL-ready objects
        itemList = new LinkedList<>(apiCalls.CallAllItemsFromCollection(restTemplate, id, apiKey, collectionKey, groupsOrUsers));



        //Security Measure to remove all invisible items, i.e. items which have been retrieved from the API but are not visible in the desktop and web apps
        // (their "collections :" attribute does not include the current collection)
        for (int i = 0; i<itemList.size(); i++){
            if (!itemList.get(i).getData().getCollections().contains(collectionKey)){
                itemList.remove(i);
                i--;
            }
        }


        for (int k = 0; k < itemList.size(); k++) {
            itemSQLList.add(new ItemSQL(itemList.get(k)));
        }

        //Get the collection and transform it into an SQL-ready object
        CollectionSQL collectionSQL = new CollectionSQL(apiCalls.CallCollection(restTemplate, id, collectionKey, apiKey, groupsOrUsers));
        collectionSQLList.add(collectionSQL);

        //Get all the Collection - Item relationships
        //Loop through all items in the library
        for (int i = 0; i < itemList.size(); i++) {
            itemCollectionSQLList.add(new ItemCollectionSQL(itemList.get(i), collectionKey));
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




        //Delete Items from DB if they are no longer available on Zotero
        int deletedItems = sqlActions.CheckForRemovedItemsInCollection(itemRepo, collectionRepo, itemCollectionRepo, itemTypeFieldsRepo, itemAuthorRepo,
                collectionKey, collectionSQLList, itemList);


        //each item is being saved in the database including all the relevant SQL tables: collection, itemCollection, itemTypeFields, itemAuthor, library
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
        int numberOfItems = collectionSQL.getNumItems();


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
