package com.Zotero.Zotero.Controller;


import com.Zotero.Zotero.APICalls;
import com.Zotero.Zotero.JSONObjects.Collection;
import com.Zotero.Zotero.JSONObjects.Item;
import com.Zotero.Zotero.SQL.CollectionSQL;
import com.Zotero.Zotero.SQL.ItemSQL;
import com.Zotero.Zotero.SQLActions;
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
	public String syncCollection(RestTemplate restTemplate,
								 @RequestParam(name="collectionId", required=false, defaultValue="") String collectionId,
								 @RequestParam(name="groupsOrUsers", required=false, defaultValue="") String groupsOrUsers,
								 @RequestParam(name="apiKey", required=false, defaultValue="") String apiKey,
								 @RequestParam(name="id", required=false, defaultValue="") String id) {


		APICalls apiCalls = new APICalls();
		SQLActions sqlActions = new SQLActions();


		Collection collection = apiCalls.CallCollection(restTemplate,id,collectionId,apiKey,groupsOrUsers);
		CollectionSQL collectionSQL = new CollectionSQL(collection);

		LinkedList<Item> itemList = apiCalls.CallAllItems(restTemplate,id,apiKey,groupsOrUsers);
		LinkedList<ItemSQL>itemSQLList = new LinkedList<>();




		return "syncCollection";

	}

}
