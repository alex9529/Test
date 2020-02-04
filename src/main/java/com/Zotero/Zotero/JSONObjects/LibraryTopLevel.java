package com.Zotero.Zotero.JSONObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.LinkedList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LibraryTopLevel {

    LinkedList<Collection> Collections;

}
