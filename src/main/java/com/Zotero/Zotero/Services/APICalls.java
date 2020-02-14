package com.Zotero.Zotero.Services;

import com.Zotero.Zotero.JSONObjects.Collection;
import com.Zotero.Zotero.JSONObjects.Item;
import com.Zotero.Zotero.Repositories.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * CallItem erstellt zwei separate Items: einmal mit dem Zusatzparameter include=bib und einmal ohne. Diese werden später vor dem Abspeichern in der DB zusammengetan.
 * CallAllItems erstellt eine Liste aller Items, die sich in der Bibliothek vom user/ in der öffentlichen Bibliothek befinden.
 * GetAllItemIds liest lediglich die IDs der Items in der Library ab, um mit diesen einzelne Items aufrufen zu können.
 */


public class APICalls {

    Item[] items;
    Item[] itemsBib;


    public APICalls() {
    }

    public int GetNumberOfItems(String address) throws IOException {

        URL obj = new URL(address);
        URLConnection conn = obj.openConnection();
        int totalItems = Integer.parseInt(conn.getHeaderField("Total-Results"));
        return totalItems;
    }

    public LinkedList<String> GetAllItemIds(RestTemplate restTemplate, String libraryId, String apiKey, String groupOrUser) {


        String address = "https://api.zotero.org/{groupOrUser}/{id}/items/?limit=100&key={apiKey}";
        Map<String, String> map = new HashMap<String, String>();
        map.put("groupOrUser", groupOrUser);
        map.put("id", libraryId);
        map.put("apiKey", apiKey);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            address = address.replace("{" + entry.getKey() + "}", entry.getValue());
        }


        Item[] items = (restTemplate.getForObject(
                address, Item[].class));
        LinkedList<Item> itemList = new LinkedList<Item>(Arrays.asList(items));
        LinkedList<String> idList = new LinkedList<String>();

        for (int k = 0; k < itemList.size(); k++) {
            idList.add(k, itemList.get(k).getKey());

        }

        return idList;
    }

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

    public LinkedList<Item> CallAllItems(RestTemplate restTemplate, String libraryId, String apiKey, String groupOrUser) throws IOException {

        String address = AssembleURL(libraryId, apiKey, groupOrUser);
        LinkedList<Item> itemList = new LinkedList<Item>();
        LinkedList<Item> itemBibList = new LinkedList<Item>();
        float numberOfItems = GetNumberOfItems(address);
        float numberChunks = numberOfItems / 100;


        //If the collection contains more than 100 items, it needs to be split into chunks of 100 items each (by calling the URL with the additional parameter "start" and then added together
        //Two separate calls are made with and without the parameter include=bib and eventually they are merged
        if (numberChunks > 1) {
            for (int i = 0; i < numberChunks; i++) {
                String tempAddress = address + ("&start=" + i * 100);
                Item[] itemsChunk = (restTemplate.getForObject(
                        tempAddress, Item[].class));
                itemList.addAll(new LinkedList<>(Arrays.asList(itemsChunk)));


                tempAddress = address + ("&include=bib&start=" + i * 100);
                itemsChunk = (restTemplate.getForObject(
                        tempAddress, Item[].class));
                itemBibList.addAll(new LinkedList<>(Arrays.asList(itemsChunk)));
            }
        } else {
            items = (restTemplate.getForObject(
                    address, Item[].class));
            itemsBib = (restTemplate.getForObject(
                    address + "&include=bib", Item[].class));

            itemList = new LinkedList<>(Arrays.asList(items));
            itemBibList = new LinkedList<>(Arrays.asList(itemsBib));
        }


        //Merge itemList and itemBibList
        for (int k = 0; k < itemList.size(); k++) {
            itemList.get(k).setBib(itemBibList.get(k).getBib());

        }
        return itemList;
    }

    public String AssembleCollectionURL(String libraryId, String apiKey, String collectionKey, String groupOrUser) {
        String address = "https://api.zotero.org/{groupOrUser}/{id}/collections/{collectionKey}/items?limit=100&key={apiKey}";
        Map<String, String> map = new HashMap<String, String>();
        map.put("groupOrUser", groupOrUser);
        map.put("id", libraryId);
        map.put("collectionKey", collectionKey);
        map.put("apiKey", apiKey);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            address = address.replace("{" + entry.getKey() + "}", entry.getValue());
        }

        return address;
    }

    public String AssembleURL(String libraryId, String apiKey, String groupOrUser) {
        String address = "https://api.zotero.org/{groupOrUser}/{id}/items?limit=100&key={apiKey}";
        Map<String, String> map = new HashMap<String, String>();
        map.put("groupOrUser", groupOrUser);
        map.put("id", libraryId);
        map.put("apiKey", apiKey);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            address = address.replace("{" + entry.getKey() + "}", entry.getValue());
        }

        return address;
    }

    public LinkedList<Item> CallAllItemsFromCollection(RestTemplate restTemplate, String libraryId, String apiKey, String collectionKey, String groupOrUser) throws IOException {

        String address = AssembleCollectionURL(libraryId, apiKey, collectionKey, groupOrUser);
        LinkedList<Item> itemList = new LinkedList<Item>();
        LinkedList<Item> itemBibList = new LinkedList<Item>();
        float numberOfItems = GetNumberOfItems(address);
        float numberChunks = numberOfItems / 100;


        //If the collection contains more than 100 items, it needs to be split into chunks of 100 items each (by calling the URL with the additional parameter "start" and then added together
        //Two separate calls are made with and without the parameter include=bib and eventually they are merged
        if (numberChunks > 1) {
            for (int i = 0; i < numberChunks; i++) {
                String tempAddress = address + ("&start=" + i * 100);
                Item[] itemsChunk = (restTemplate.getForObject(
                        tempAddress, Item[].class));
                itemList.addAll(new LinkedList<>(Arrays.asList(itemsChunk)));


                tempAddress = address + ("&include=bib&start=" + i * 100);
                itemsChunk = (restTemplate.getForObject(
                        tempAddress, Item[].class));
                itemBibList.addAll(new LinkedList<>(Arrays.asList(itemsChunk)));
            }
        } else {
            items = (restTemplate.getForObject(
                    address, Item[].class));
            itemsBib = (restTemplate.getForObject(
                    address + "&include=bib", Item[].class));

            itemList = new LinkedList<>(Arrays.asList(items));
            itemBibList = new LinkedList<>(Arrays.asList(itemsBib));
        }


        //Merge itemList and itemBibList
        for (int k = 0; k < itemList.size(); k++) {
            itemList.get(k).setBib(itemBibList.get(k).getBib());

        }

        return itemList;
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


        Collection[] collections = (restTemplate.getForObject(
                address, Collection[].class));
        LinkedList<Collection> collectionList = new LinkedList<Collection>(Arrays.asList(collections));
        LinkedList<String> idList = new LinkedList<>();

        for (int k = 0; k < collectionList.size(); k++) {
            idList.add(k, collectionList.get(k).getCollectionKey());

        }

        return idList;
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


        Collection[] collections = (restTemplate.getForObject(
                address, Collection[].class));
        LinkedList<Collection> collectionList = new LinkedList<Collection>(Arrays.asList(collections));

        for (int k = 0; k < collectionList.size(); k++) {
            collectionList.set(k, CallCollection(restTemplate, libraryId, collections[k].getKey(), apiKey, groupOrUser));

        }

        return collectionList;
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

    public String GetCollectionName(RestTemplate restTemplate, String libraryId, String apiKey, String groupOrUser, String collectionKey) {

        LinkedList<Collection> collections = (new LinkedList<Collection>(CallAllCollections(restTemplate, libraryId, apiKey, groupOrUser)));
        String name = "";
        int k = 0;
        do {
            name = collections.get(k).getData().getName();
            k++;
        }
        while (!collections.get(k - 1).getData().getKey().equals(collectionKey));

        return name;
    }


    public SQLEntities PrepareItemsForDB(RestTemplate restTemplate, String groupsOrUsers, String apiKey, String id, String collectionKey) throws IOException {

        LinkedList<Item> itemList = new LinkedList<>();
        LinkedList<ItemSQL> itemSQLList = new LinkedList<ItemSQL>();
        LinkedList<CollectionSQL> collectionSQLList = new LinkedList<CollectionSQL>();
        LinkedList<ItemCollectionSQL> itemCollectionSQLList = new LinkedList<>();
        LinkedList<ItemAuthorSQL> itemAuthorSQLList = new LinkedList<>();
        ItemTypeFieldsSQL itemTypeFieldsSQL = new ItemTypeFieldsSQL();
        UserSQL userSQL = new UserSQL();
        LibrarySQL librarySQL = new LibrarySQL();

        SQLEntities sqlEntities;

        //All items from the collection are called
        itemList = new LinkedList<>(CallAllItemsFromCollection(restTemplate, id, apiKey, collectionKey, groupsOrUsers));

        //Security Measure to remove all invisible items, i.e. items which have been retrieved from the API but are not visible in the desktop and web apps
        // (their "collections :" attribute does not include the current collection)
        for (int i = 0; i<itemList.size(); i++){
            if (!itemList.get(i).getData().getCollections().contains(collectionKey)){
                itemList.remove(i);
                i--;
            }
        }
        //The items are transformed into SQL-ready objects
        for (int k = 0; k < itemList.size(); k++) {
            itemSQLList.add(new ItemSQL(itemList.get(k)));
        }



        //Get the collection and transform it into an SQL-ready object
        CollectionSQL collectionSQL = new CollectionSQL(CallCollection(restTemplate, id, collectionKey, apiKey, groupsOrUsers));
        collectionSQLList.add(collectionSQL);

        //Get all the Collection - Item relationships
        //Loop through all items in the library
        for (int i = 0; i < itemList.size(); i++) {
            itemCollectionSQLList.add(new ItemCollectionSQL(itemList.get(i), collectionKey));
        }



        //Get all the Author - Item relationships
        //Loop through all items in the library
        for (int i = 0; i < itemList.size(); i++) {
            //Loop through all authors of an item
            for (int a = 0; a < itemList.get(i).getData().getCreators().size(); a++) {
                itemAuthorSQLList.add(new ItemAuthorSQL(itemList.get(i), a));
            }
        }


        //Get all the ItemTypeFields for an item
        //Loop through all items in the library
        for (int i = 0; i < itemList.size(); i++) {
            itemTypeFieldsSQL = new ItemTypeFieldsSQL(itemList.get(i));
        }

        //Save user and library data
        if (itemList.size() > 0) {
            userSQL = new UserSQL(itemList.get(0));
            librarySQL = new LibrarySQL(itemList.get(0));
        }

        sqlEntities = new SQLEntities(itemList,itemSQLList,collectionSQLList,itemCollectionSQLList,itemAuthorSQLList,itemTypeFieldsSQL,userSQL,librarySQL);


        return  sqlEntities;

    }


}
