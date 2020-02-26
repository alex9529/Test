package com.Zotero.Zotero.Controllers;

import com.Zotero.Zotero.JSONObjects.Collection;
import com.Zotero.Zotero.Services.APICalls;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;

@Controller
public class LibraryController {

	private APICalls apiCalls = new APICalls();


	@GetMapping("/library")
	public String library(@RequestParam(name="id", required=false, defaultValue="") String id,
						  @RequestParam(name="apiKey", required=false, defaultValue="") String apiKey,
						  @RequestParam(name="group", required=false, defaultValue="off") String group, Model model, RestTemplate restTemplate) {



		model.addAttribute("id", id);
		model.addAttribute("apiKey", apiKey);


		String groupsOrUsers;

		if (group.equals("on")){
			groupsOrUsers = "groups";
		}
		else {
			groupsOrUsers = "users";
		}
		model.addAttribute("groupsOrUsers", groupsOrUsers);




		//Get all Collection names from Library
		LinkedList<Collection> collections = apiCalls.CallAllCollections(restTemplate, id, apiKey, groupsOrUsers);
		String[] collectionNames = new String[collections.size()];
		for (int c = 0; c < collections.size(); c++) {
			collectionNames[c]=(collections.get(c).getData().getName()+" - " +collections.get(c).getCollectionKey());
		}
		model.addAttribute("collections", collectionNames);

		return "library";




	}

}
