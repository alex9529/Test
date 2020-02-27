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

    private LinkedList<ItemTypeFieldsSQL> itemTypeFieldsSQLList;


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
                              @RequestParam(name = "id", required = false, defaultValue = "") String id,
                              @RequestParam(name = "collectionlessItems", required = false, defaultValue = "off") String collectionlessItems, Model model

    ) throws IOException {


        UserSQL userSQL = new UserSQL();
        LibrarySQL librarySQL = new LibrarySQL();

        LinkedList<Item> itemList = new LinkedList<>();
        LinkedList<ItemSQL> itemSQLList = new LinkedList<>();
        LinkedList<CollectionSQL> collectionSQLList = new LinkedList<>();
        LinkedList<ItemCollectionSQL> itemCollectionSQLList = new LinkedList<>();
        LinkedList<ItemAuthorSQL> itemAuthorSQLList = new LinkedList<>();
        int[] deleteCollectionsAndItems = new int[2];
        int deletedItems = 0;
        int deletedCollections = 0;
        int numberOfItems = 0;


        //Get all Collections from Library
        LinkedList<Collection> collections = apiCalls.CallAllCollections(restTemplate, id, apiKey, groupsOrUsers);


        //Loop through all collections
        for (int c = 0; c < collections.size(); c++) {
            //Generate the itemList and all the necessary SQL-ready entities
            SQLEntities sqlEntities = apiCalls.PrepareItemsForDB(restTemplate, groupsOrUsers, apiKey, id, collections.get(c).getCollectionKey());

            itemList = sqlEntities.getItemList();
            itemSQLList = sqlEntities.getItemSQLList();
            collectionSQLList.add(sqlEntities.getCollectionSQL());
            itemCollectionSQLList = sqlEntities.getItemCollectionSQLList();
            itemAuthorSQLList = sqlEntities.getItemAuthorSQLList();
            itemTypeFieldsSQLList = sqlEntities.getItemTypeFieldsSQL();
            userSQL = sqlEntities.getUserSQL();
            librarySQL = sqlEntities.getLibrarySQL();



            //Each item in itemSQLList is being saved in the database including all the relevant SQL tables: collection, itemCollection, itemTypeFields, itemAuthor, library
            failedItems.clear();
            for (int k = 0; k < itemSQLList.size(); k++) {
                failedItems.add(sqlActions.saveItem(itemRepo, collectionRepo, itemCollectionRepo, itemTypeFieldsRepo, itemAuthorRepo,
                        itemSQLList.get(k), collectionSQLList.get(c), itemCollectionSQLList, itemTypeFieldsSQLList.get(k), itemAuthorSQLList));
                if (failedItems.getLast().equals("")) {
                    failedItems.removeLast();
                }
            }

            numberOfItems = numberOfItems + itemList.size();

            //Delete Items from DB if they are no longer available in Zotero collection
            deletedItems = deletedItems + sqlActions.CheckForRemovedItemsInCollection(itemRepo, collectionRepo, itemCollectionRepo, itemTypeFieldsRepo, itemAuthorRepo,
                    collectionSQLList.get(c).getCollectionKey(), collectionSQLList.get(c), itemList);
        }


        //Delete Collections from DB if they are no longer available on Zotero
        deleteCollectionsAndItems = sqlActions.CheckForRemovedCollectionsInLibrary(itemRepo, collectionRepo, itemCollectionRepo, itemTypeFieldsRepo, itemAuthorRepo,
                collectionSQLList, Integer.parseInt(id));
        deletedCollections += deleteCollectionsAndItems[0];
        deletedItems += deleteCollectionsAndItems[1];

        //Save the user and library data
        if (itemList.size() > 0) {
            sqlActions.saveUser(userSQL, userRepo);
            libraryRepo.save(librarySQL);
        }


        // -----------------------------collection-less items---------------------------------------------//


        //If collection-less items shall be considered, call ALL items from the Library
        //Note: this might take significantly longer
        if (collectionlessItems.equals("on")) {


            LinkedList<ItemTypeFieldsSQL> itemTypeFieldsSQLList = new LinkedList<>();
            itemAuthorSQLList = new LinkedList<>();

            //All items from the library are called and transformed into SQL-ready objects
            itemList = new LinkedList<>(apiCalls.CallAllItems(restTemplate, id, apiKey, groupsOrUsers));

            //The items amd the type fields are transformed into SQL-ready objects
            itemSQLList = new LinkedList<>();
            for (int k = 0; k < itemList.size(); k++) {
                itemSQLList.add(new ItemSQL(itemList.get(k)));
                itemTypeFieldsSQLList.add(new ItemTypeFieldsSQL(itemList.get(k)));

            }

            //Get all the Author - Item relationships
            //Loop through all items in the library
            for (int i = 0; i < itemList.size(); i++) {
                //Loop through all authors of an item
                for (int a = 0; a < itemList.get(i).getData().getCreators().size(); a++) {
                    itemAuthorSQLList.add(new ItemAuthorSQL(itemList.get(i), a));
                }

            }

            //Each item in itemSQLList is being saved in the database including all the relevant SQL tables: collection, itemCollection, itemTypeFields, itemAuthor, library
            for (int i = 0; i<itemList.size(); i++){
                failedItems.clear();
                for (int k = 0; k < itemSQLList.size(); k++) {
                    failedItems.add(sqlActions.saveItemWithNoCollection(itemRepo, itemTypeFieldsRepo, itemAuthorRepo,
                            itemSQLList.get(k), itemTypeFieldsSQLList.get(i), itemAuthorSQLList));
                    if (failedItems.getLast().equals("")) {
                        failedItems.removeLast();
                    }
                }
            }



            //Save the user and library data
            if (itemList.size() > 0) {
                sqlActions.saveUser(new UserSQL(itemList.getFirst()), userRepo);
                libraryRepo.save(new LibrarySQL(itemList.getFirst()));
            }

            numberOfItems = numberOfItems + itemList.size();

            //Delete Items from DB if they are no longer available in Zotero library
            deletedItems = deletedItems + sqlActions.CheckForRemovedItemsInLibrary(itemRepo, itemTypeFieldsRepo, itemAuthorRepo,
                    itemList, Integer.parseInt(id));


        }

        //Get the library name
        String libraryName = id;
        try {
            libraryName = (new LinkedList<Collection>(apiCalls.CallAllCollections(restTemplate, id, apiKey, groupsOrUsers))).get(0).getLibrary().getName();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Library name retrieval not possible because there are no collections in this library.");
        }


        //Get the number of item chunks of the size between 1 and 100
        String url = apiCalls.AssembleURL(id, apiKey, groupsOrUsers);


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
