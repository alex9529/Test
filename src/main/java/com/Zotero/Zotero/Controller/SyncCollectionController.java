package com.Zotero.Zotero.Controller;


import com.Zotero.Zotero.APICalls;
import com.Zotero.Zotero.JSONObjects.Item;
import com.Zotero.Zotero.SQL.ItemSQL;
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


	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@GetMapping("/syncLibrary")
	public String syncLibrary(RestTemplate restTemplate) {


		APICalls apiCalls = new APICalls();
		//LinkedList<Item> itemList = apiCalls.CallAllItems();

		LinkedList<ItemSQL>itemSQLList = new LinkedList<>();

		return "syncLibrary";

	}

}
