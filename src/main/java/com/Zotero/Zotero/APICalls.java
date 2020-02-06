package com.Zotero.Zotero;

import com.Zotero.Zotero.JSONObjects.Collection;
import com.Zotero.Zotero.JSONObjects.Item;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * CallItem erstellt zwei separate Items: einmal mit dem Zusatzparameter include=bib und einmal ohne. Diese werden später vor dem Abspeichern in der DB zusammengetan.
 * CallAllItems erstellt eine Liste aller Items, die sich in der Bibliothek vom user/ in der öffentlichen Bibliothek befinden.
 * GetAllItemIds liest lediglich die IDs der Items in der Library ab, um mit diesen einzelne Items aufrufen zu können.
 */

public class APICalls {


    public Item CallItem(RestTemplate restTemplate, String libraryId, String itemId, String apiKey, String groupOrUser) {


        String address = "https://api.zotero.org/{groupOrUser}/{id}/items/{itemId}?key={apiKey}";
        Map<String, String> map = new HashMap<String, String>();
        map.put("groupOrUser", groupOrUser);
        map.put("id", libraryId);
        map.put("itemId", itemId);
        map.put("apiKey", apiKey);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            address = address.replace("{" + entry.getKey() + "}", entry.getValue());
        }



        String bibAddress = "https://api.zotero.org/{groupOrUser}/{id}/items/{itemId}?include=bib&key={apiKey}";
        map = new HashMap<String, String>();
        map.put("groupOrUser", groupOrUser);
        map.put("id", libraryId);
        map.put("itemId", itemId);
        map.put("apiKey", apiKey);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            bibAddress = bibAddress.replace("{" + entry.getKey() + "}", entry.getValue());
        }



        Item item = restTemplate.getForObject(
                address, Item.class);
        Item itemBib = restTemplate.getForObject(
                bibAddress, Item.class);

        item.setBib(itemBib.getBib());
        return (item);
    }



    public LinkedList<Item> CallAllItems(RestTemplate restTemplate, String libraryId, String apiKey, String groupOrUser) {


        String address = "https://api.zotero.org/{groupOrUser}/{id}/items/?key={apiKey}";
        Map<String, String> map = new HashMap<String, String>();
        map.put("groupOrUser", groupOrUser);
        map.put("id", libraryId);
        map.put("apiKey", apiKey);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            address = address.replace("{" + entry.getKey() + "}", entry.getValue());
        }


        Item[] items =  (restTemplate.getForObject(
                address, Item[].class));
        LinkedList<Item> itemList = new LinkedList<Item>(Arrays.asList(items));

        for (int k = 0; k<itemList.size(); k++){
            itemList.set(k,CallItem(restTemplate,libraryId,items[k].getKey(),apiKey,groupOrUser));

        }

        return itemList;
    }

    public LinkedList<Collection> CallAllCollections(RestTemplate restTemplate, String libraryId, String apiKey, String groupOrUser) {

        String address = "https://api.zotero.org/{groupOrUser}/{id}/collections/?key={apiKey}";
        Map<String, String> map = new HashMap<String, String>();
        map.put("groupOrUser", groupOrUser);
        map.put("id", libraryId);
        map.put("apiKey", apiKey);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            address = address.replace("{" + entry.getKey() + "}", entry.getValue());
        }


        Collection[] collections =  (restTemplate.getForObject(
                address, Collection[].class));
        LinkedList<Collection> collectionList = new LinkedList<Collection>(Arrays.asList(collections));

        for (int k = 0; k<collectionList.size(); k++){
            collectionList.set(k,CallCollection(restTemplate,libraryId,collections[k].getKey(),apiKey,groupOrUser));

        }

        return collectionList;
    }

    public LinkedList<String> GetAllItemIds(RestTemplate restTemplate, String libraryId, String apiKey, String groupOrUser) {


        String address = "https://api.zotero.org/{groupOrUser}/{id}/items/?key={apiKey}";
        Map<String, String> map = new HashMap<String, String>();
        map.put("groupOrUser", groupOrUser);
        map.put("id", libraryId);
        map.put("apiKey", apiKey);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            address = address.replace("{" + entry.getKey() + "}", entry.getValue());
        }


        Item[] items =  (restTemplate.getForObject(
                address, Item[].class));
        LinkedList<Item> itemList = new LinkedList<Item>(Arrays.asList(items));
        LinkedList<String> idList = new LinkedList<String>();

        for (int k = 0; k<itemList.size(); k++){
            idList.add(k,itemList.get(k).getKey());

        }

        return idList;
    }

    public Collection CallCollection(RestTemplate restTemplate, String libraryId, String collectionId, String apiKey, String groupOrUser) {


        String address = "https://api.zotero.org/{groupOrUser}/{id}/collections/{collectionId}?key={apiKey}";
        Map<String, String> map = new HashMap<String, String>();
        map.put("groupOrUser", groupOrUser);
        map.put("id", libraryId);
        map.put("collectionId", collectionId);
        map.put("apiKey", apiKey);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            address = address.replace("{" + entry.getKey() + "}", entry.getValue());
        }

        Collection collection = restTemplate.getForObject(
                address, Collection.class);
        return (collection);
    }

    public LinkedList<String> GetAllCollectionIds(RestTemplate restTemplate, String libraryId, String apiKey, String groupOrUser) {


        String address = "https://api.zotero.org/{groupOrUser}/{id}/collections/?key={apiKey}";
        Map<String, String> map = new HashMap<String, String>();
        map.put("groupOrUser", groupOrUser);
        map.put("id", libraryId);
        map.put("apiKey", apiKey);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            address = address.replace("{" + entry.getKey() + "}", entry.getValue());
        }


        Collection[] collections =  (restTemplate.getForObject(
                address, Collection[].class));
        LinkedList<Collection> collectionList = new LinkedList<Collection>(Arrays.asList(collections));
        LinkedList<String> idList = new LinkedList<>();

        for (int k = 0; k<collectionList.size(); k++){
            idList.add(k,collectionList.get(k).getCollectionKey());

        }

        return idList;
    }


}
