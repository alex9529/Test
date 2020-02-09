package com.Zotero.Zotero.Controller;


import com.Zotero.Zotero.APICalls;
import com.Zotero.Zotero.JSONObjects.Collection;
import com.Zotero.Zotero.JSONObjects.Item;
import com.Zotero.Zotero.SQL.*;
import com.Zotero.Zotero.SQLActions;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;

@Controller
public class SyncLibraryController {


	private ItemSQL itemSQL;
	private LinkedList<ItemSQL> itemSQLList = new LinkedList<ItemSQL>();
	private LinkedList<CollectionSQL> collectionSQList = new LinkedList<CollectionSQL>();
	private CollectionSQL collectionSQL;
	private LinkedList<ItemCollectionSQL> itemCollectionSQLList;
	private ItemTypeFieldsSQL itemTypeFieldsSQL;
	private UserSQL userSQL;
	private LinkedList<ItemAuthorSQL> itemAuthorSQLList;
	private LibrarySQL librarySQL;




	@GetMapping("/syncLibrary")
	public String syncLibrary(RestTemplate restTemplate,
							  @RequestParam(name="groupsOrUsers", required=false, defaultValue="") String groupsOrUsers,
							  @RequestParam(name="apiKey", required=false, defaultValue="") String apiKey,
							  @RequestParam(name="id", required=false, defaultValue="") String id,
							  ItemRepository itemRepo, CollectionRepository collectionRepo, ItemCollectionRepository itemCollectionRepo,
							  ItemTypeFieldsRepository itemTypeFieldsRepo, UserRepository userRepo, ItemAuthorRepository itemAuthorRepo, LibraryRepository libraryRepo
	) {


		APICalls apiCalls = new APICalls();
		SQLActions sqlActions = new SQLActions();

		LinkedList<ItemSQL> itemSQLList = new LinkedList<>();

		//all items from the library are called and transformed into SQL-ready objects
		LinkedList<Item> itemList = apiCalls.CallAllItems(restTemplate,id,apiKey,groupsOrUsers);
		for (int k = 0; k<itemList.size(); k++){
			itemSQLList.add(new ItemSQL(itemList.get(k)));
		}

		//each item is being saved in the database
		for (int k = 0; k<itemSQLList.size(); k++) {
			sqlActions.saveItem(itemRepo, collectionRepo, itemCollectionRepo,itemTypeFieldsRepo,itemAuthorRepo,libraryRepo,
					itemSQLList.get(k), collectionSQL, itemCollectionSQLList, itemTypeFieldsSQL, librarySQL, itemAuthorSQLList);
		}

		sqlActions.saveUser(userSQL, userRepo);

		return "syncLibrary";

	}

}
