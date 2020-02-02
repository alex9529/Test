package com.Zotero.Zotero;


import com.Zotero.Zotero.API.Collection;
import com.Zotero.Zotero.API.Item;
import com.Zotero.Zotero.SQL.CollectionRepository;
import com.Zotero.Zotero.SQL.CollectionSQL;
import com.Zotero.Zotero.SQL.ItemRepository;
import com.Zotero.Zotero.SQL.ItemSQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ZoteroApplication {

	private ItemSQL itemSQL;
	private CollectionSQL collectionSQL;
	private static final Logger log = LoggerFactory.getLogger(ZoteroApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ZoteroApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			Item item = restTemplate.getForObject(
					"https://api.zotero.org/users/6098055/items/56DQEYU6?key=NNb41PLF2hKJBKbo3tCtEJuO", Item.class);

			Collection collection = restTemplate.getForObject(
					"https://api.zotero.org/users/6098055/collections/YPQC2LG5?key=NNb41PLF2hKJBKbo3tCtEJuO", Collection.class);

			itemSQL = new ItemSQL(item);
			collectionSQL = new CollectionSQL(collection);

			log.info(item.toString());
		};
	}


	@Bean
	public CommandLineRunner demo(ItemRepository itemRepo, CollectionRepository collectionRepo ) {
		return (args) -> {
			itemRepo.save(itemSQL);
			collectionRepo.save(collectionSQL);
			log.info("");
		};
	}

}
