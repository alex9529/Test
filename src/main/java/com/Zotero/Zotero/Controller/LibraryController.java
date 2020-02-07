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
public class LibraryController {


	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@GetMapping("/library")
	public String library(@RequestParam(name="id", required=false, defaultValue="") String id,
						  @RequestParam(name="apiKey", required=false, defaultValue="") String apiKey,
						  @RequestParam(name="group", required=false, defaultValue="off") String groupOrUser,
						  Model model, RestTemplate restTemplate) {


		APICalls apiCalls = new APICalls();

		LinkedList<ItemSQL>itemSQLList = new LinkedList<>();

		model.addAttribute("id", id);
		model.addAttribute("apiKey", apiKey);

		if (groupOrUser.equals("on")){
			model.addAttribute("groupOrUser", "groups");
		}
		else {
			model.addAttribute("groupOrUser", "users");
		}
		return "library";
	}

}
