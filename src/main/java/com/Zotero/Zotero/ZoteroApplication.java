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
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;

@SpringBootApplication
public class ZoteroApplication {

	private ItemSQL itemSQL;
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


			//Item Call
			//-------------------------------------
			String user = "6098055";
			String itemId = "DWU7JMWB";
			String apiKey = "xbPF51FSc8hD7MhYKkBsgtpj";
			String groupsOrUsers = "users";

			APICalls apiCalls = new APICalls();

			Item item = apiCalls.CallItem(restTemplate,user,itemId,apiKey,groupsOrUsers).get(0);
			Item itemBib = apiCalls.CallItem(restTemplate,user,itemId,apiKey,groupsOrUsers).get(1);
			itemSQL = new ItemSQL(item, itemBib);
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
			itemRepo.save(itemSQL);
			collectionRepo.save(collectionSQL);

			for (int i = 0; i<itemCollectionSQLList.size(); i++) {
				itemCollectionRepo.save(itemCollectionSQLList.get(i));
			}

			for (int i = 0; i<itemAuthorSQLList.size(); i++) {
				itemAuthorRepo.save(itemAuthorSQLList.get(i));
			}

			itemTypeFieldsRepo.save(itemTypeFieldsSQL);
			userRepo.save(userSQL);
			libraryRepo.save(librarySQL);

			log.info("");
		};
	}

}
