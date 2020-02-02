package com.Zotero.Zotero.API;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.LinkedList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LibraryTopLevel {

    LinkedList<Collection> Collections;

}
