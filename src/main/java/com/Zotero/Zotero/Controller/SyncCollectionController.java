package com.Zotero.Zotero.Controller;


import com.Zotero.Zotero.APICalls;
import com.Zotero.Zotero.JSONObjects.Collection;
import com.Zotero.Zotero.SQL.ItemSQL;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;

@Controller
public class SyncCollectionController {


	@GetMapping("/syncCollection")
	public String syncLibrary(RestTemplate restTemplate, @RequestParam(name="collectionId", required=false, defaultValue="") String collectionId) {


		APICalls apiCalls = new APICalls();
		Collection collection = apiCalls.CallCollection(restTemplate,"2407208",collectionId,"","groups");

		//LinkedList<Item> itemList = apiCalls.CallAllItems();

		LinkedList<ItemSQL>itemSQLList = new LinkedList<>();

		return "syncCollection";

	}

}
