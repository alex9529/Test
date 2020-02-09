package com.Zotero.Zotero;


import com.Zotero.Zotero.JSONObjects.Collection;
import com.Zotero.Zotero.JSONObjects.Item;
import com.Zotero.Zotero.SQL.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;


import java.util.LinkedList;

@SpringBootApplication
public class ZoteroApplication {

	private ItemSQL itemSQL;
	private LinkedList<ItemSQL> itemSQLList = new LinkedList<ItemSQL>();
	private LinkedList<CollectionSQL> collectionSQList = new LinkedList<CollectionSQL>();
	private CollectionSQL collectionSQL;
	private LinkedList<ItemCollectionSQL> itemCollectionSQLList;
	private ItemTypeFieldsSQL itemTypeFieldsSQL;
	private UserSQL userSQL;
	private LinkedList<ItemAuthorSQL> itemAuthorSQLList;
	private LibrarySQL librarySQL;

	private static final Logger log = LoggerFactory.getLogger(ZoteroApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ZoteroApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}


	@Bean
	public CommandLineRunner GetSQLObjects(RestTemplate restTemplate) throws Exception {
		return args -> {


			APICalls apiCalls = new APICalls();
			String libraryId = "6098055";
			String apiKey = "NNb41PLF2hKJBKbo3tCtEJuO";
			String groupOrUser = "users";


			//Get all Items from the Library
			//-------------------------------------
			LinkedList<Item> items = apiCalls.CallAllItems(restTemplate, libraryId, apiKey,groupOrUser);
			for (int k = 0; k<items.size(); k++){
				itemSQLList.add(new ItemSQL(items.get(k)));
			}
			//-------------------------------------


			//Get all Item Ids in the library
			//-------------------------------------
			LinkedList<String> idList = apiCalls.GetAllItemIds(restTemplate,libraryId,apiKey,groupOrUser);
			//-------------------------------------


			//Get a specific item
			//-------------------------------------
			String itemId = idList.get(1);
			Item item = apiCalls.CallItem(restTemplate,libraryId,itemId,apiKey,groupOrUser);
			itemSQL = new ItemSQL(item);
			//-------------------------------------


			//Get all Collections Ids in the library
			//-------------------------------------
			LinkedList<String> collectionIds = apiCalls.GetAllCollectionIds(restTemplate,libraryId,apiKey,groupOrUser);
			//-------------------------------------


			//Get all Collecitons in the library
			//-------------------------------------
			LinkedList<Collection> collections = apiCalls.CallAllCollections(restTemplate, libraryId, apiKey,groupOrUser);
			for (int k = 0; k<collections.size(); k++){
				collectionSQList.add(new CollectionSQL(collections.get(k)));
			}
			//-------------------------------------


			//Get a specific collection
			//-------------------------------------
			String collectionId = collectionIds.get(0);
			Collection collection = apiCalls.CallCollection(restTemplate,libraryId,collectionId,apiKey,groupOrUser);
			collectionSQL = new CollectionSQL(collection);
			//-------------------------------------



			itemCollectionSQLList = new LinkedList<ItemCollectionSQL>();
			itemAuthorSQLList = new LinkedList<ItemAuthorSQL>();

			//Loop through all collections that contain item
			for (int i = 0; i<item.getData().getCollections().size(); i++){
				itemCollectionSQLList.addFirst(new ItemCollectionSQL(item, i));
			}

			//Loop through all authors of an item
				for (int i = 0; i<item.getData().getCreators().size(); i++){
				itemAuthorSQLList.addFirst(new ItemAuthorSQL(item, i));
			}

			itemTypeFieldsSQL = new ItemTypeFieldsSQL(item);
			userSQL = new UserSQL(item);
			librarySQL = new LibrarySQL(item);



			log.info(item.toString());
		};
	}





	
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
