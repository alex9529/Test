package com.Zotero.Zotero;


import com.Zotero.Zotero.API.Item;
import com.Zotero.Zotero.SQL.ItemRepository;
import com.Zotero.Zotero.SQL.ItemSQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ZoteroApplication {


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

			log.info(item.toString());
		};
	}


	@Bean
	public CommandLineRunner demo(ItemRepository repository) {
		return (args) -> {
			ItemSQL item = new ItemSQL("asd1");
			repository.save(item);
			log.info("");
		};
	}
}
