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
public class GreetingController {


	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@GetMapping("/greeting")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="Undefined") String name, Model model,RestTemplate restTemplate) {


		APICalls apiCalls = new APICalls();
		String libraryId = "6098055";
		String apiKey = "NNb41PLF2hKJBKbo3tCtEJuO";
		String groupOrUser = "users";
		LinkedList<ItemSQL>itemSQLList = new LinkedList<>();

		//Get all Items from the Library
		//-------------------------------------
		LinkedList<Item> items = apiCalls.CallAllItems(restTemplate, libraryId, apiKey,groupOrUser);
		for (int k = 0; k<items.size(); k++){
			itemSQLList.add(new ItemSQL(items.get(k)));
		}
		//-------------------------------------





		model.addAttribute("id", name);
		model.addAttribute("items", items);

		return "greeting";
	}

}
