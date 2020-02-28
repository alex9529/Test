package com.Zotero.Zotero.JSONObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** Die Klasse übernimmt alle JSON Attribute aus der GET HTTP Abfrage. Die Attribute widerspiegeln exakt die JSON-Struktur.
 * Nicht vorhandene Attribute werden mittels @JsonIgnoreProperties ignoriert, um einen runtime-Fehler zu vermeiden. Attribute wie Data und Meta sind ebenfalls JSON Objekte, aus welchem Grund
 * extra Klassen dafür angelegt wurden.
 *
 * @author Alexander Nikolov
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {

    private String key;
    private int version;
    private Library library;
    private Links links;
    private Meta meta;
    private Data data;
    private String bib;

    public String getBib() {
        return bib;
    }

    public void setBib(String bib) {
        this.bib = bib;
    }

    public int getVersion() {
        return version;
    }

    public Library getLibrary() {
        return library;
    }

    public Links getLinks() {
        return links;
    }

    public Meta getMeta() {
        return meta;
    }

    public Item() {

    }

    public String getKey() {
        return this.key;
    }

    public Data getData() {
        return this.data;
    }



    public void setKey(String key) {
        this.key = key;
    }


}
