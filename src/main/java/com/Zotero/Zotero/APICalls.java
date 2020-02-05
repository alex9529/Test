package com.Zotero.Zotero;

import com.Zotero.Zotero.JSONObjects.Item;
import com.Zotero.Zotero.SQL.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class APICalls {


    public LinkedList<Item> CallItem(RestTemplate restTemplate, String user, String itemId, String apiKey, String groupOrUser) {


        String address = "https://api.zotero.org/{groupOrUser}/{id}/items/{itemId}?key={apiKey}";
        Map<String, String> map = new HashMap<String, String>();
        map.put("groupOrUser", groupOrUser);
        map.put("id", user);
        map.put("itemId", itemId);
        map.put("apiKey", apiKey);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            address = address.replace("{" + entry.getKey() + "}", entry.getValue());
        }



        String bibAddress = "https://api.zotero.org/{groupOrUser}/{id}/items/{itemId}?include=bib&key={apiKey}";
        map = new HashMap<String, String>();
        map.put("groupOrUser", groupOrUser);
        map.put("id", user);
        map.put("itemId", itemId);
        map.put("apiKey", apiKey);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            bibAddress = bibAddress.replace("{" + entry.getKey() + "}", entry.getValue());
        }



        Item item = restTemplate.getForObject(
                address, Item.class);
        Item itemBib = restTemplate.getForObject(
                bibAddress, Item.class);

        LinkedList<Item> items = new LinkedList<Item>();
        items.add(item);
        items.add(itemBib);

        return (items);
    }






}
