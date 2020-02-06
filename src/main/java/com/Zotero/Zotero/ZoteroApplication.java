package com.Zotero.Zotero;


import com.Zotero.Zotero.JSONObjects.Collection;
import com.Zotero.Zotero.JSONObjects.Item;
import com.Zotero.Zotero.SQL.*;
import net.bytebuddy.dynamic.scaffold.MethodGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


import java.util.LinkedList;

@SpringBootApplication
public class ZoteroApplication {

	private ItemSQL itemSQL;
	private LinkedList<ItemSQL> itemSQLList = new LinkedList<ItemSQL>();
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
			String libraryId = "2407208";
			String apiKey = "";
			String groupsOrUsers = "groups";


			//Get all Items from the Library
			//-------------------------------------
			LinkedList<Item> items = apiCalls.CallAllItems(restTemplate, libraryId, apiKey,groupsOrUsers);
			for (int k = 0; k<items.size(); k++){
				itemSQLList.add(new ItemSQL(items.get(k)));
			}
			//-------------------------------------


			//Get all Item Ids in the library
			//-------------------------------------
			LinkedList<String> idList = apiCalls.GetAllItemIds(restTemplate,libraryId,apiKey,groupsOrUsers);
			//-------------------------------------


			//Get a specific item
			//-------------------------------------
			String itemId = idList.get(1);
			Item item = apiCalls.CallItem(restTemplate,libraryId,itemId,apiKey,groupsOrUsers);
			itemSQL = new ItemSQL(item);
			//-------------------------------------




			Collection collection = restTemplate.getForObject(
					"https://api.zotero.org/users/6098055/collections/YPQC2LG5?key=NNb41PLF2hKJBKbo3tCtEJuO", Collection.class);


			collectionSQL = new CollectionSQL(collection);
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


	@Bean
	public CommandLineRunner SendToDB(ItemRepository itemRepo, CollectionRepository collectionRepo, ItemCollectionRepository itemCollectionRepo,
									  ItemTypeFieldsRepository itemTypeFieldsRepo, UserRepository userRepo, ItemAuthorRepository itemAuthorRepo, LibraryRepository libraryRepo) {
		return (args) -> {
			SQLActions sqlActions = new SQLActions();


			itemRepo.save(itemSQL);

			for (int k = 0; k<itemSQLList.size(); k++){
				itemRepo.save(itemSQLList.get(k));
			}


			collectionRepo.save(collectionSQL);

			for (int i = 0; i<itemCollectionSQLList.size(); i++) {
				itemCollectionRepo.save(itemCollectionSQLList.get(i));
			}

			for (int i = 0; i<itemAuthorSQLList.size(); i++) {
				itemAuthorRepo.save(itemAuthorSQLList.get(i));
			}

			itemTypeFieldsRepo.save(itemTypeFieldsSQL);
			libraryRepo.save(librarySQL);


			sqlActions.saveUser(userSQL, userRepo);

			log.info("");
		};
	}

}
