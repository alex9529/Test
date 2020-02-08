package com.Zotero.Zotero.Controller;



import com.Zotero.Zotero.APICalls;
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


		APICalls apiCalls = new APICalls();
		LinkedList<String> collectionIds = apiCalls.GetAllCollectionIds(restTemplate, id, apiKey, groupsOrUsers);
		model.addAttribute(collectionIds);

		return "library";




	}

}
