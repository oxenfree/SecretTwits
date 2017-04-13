package com.anthonyolivieri.secrettwits.services;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class PreferencesService {

    private Preferences pref;

    private final String rootUrl = "http://www.opensecrets.org/api/?output=json";
    private final List<String> memPFDYears;
    private final List<String> congressNumbers;
    private final List<String> candIndCycles;
    private final String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.110 Safari/537.36";
    private final String apiKey = "your-api-key";
    private String lastId;

    public PreferencesService() {
        pref = Preferences.systemRoot().node(this.getClass().getName());
        memPFDYears = new ArrayList<>();
        memPFDYears.add("2013");
        memPFDYears.add("2014");
        congressNumbers = new ArrayList<>();
        congressNumbers.add("112");
        congressNumbers.add("113");
        congressNumbers.add("114");
        candIndCycles = new ArrayList<>();
        candIndCycles.add("2012");
        candIndCycles.add("2014");
        candIndCycles.add("2016");
        lastId = pref.get("LastId", "850137187320565760");
    }

    public void setLastId(String lastId) {
        pref.put("LastId", lastId);
    }
    
    public Preferences getPref() {
        return pref;
    }

    public String getRootUrl() {
        return rootUrl;
    }

    public List<String> getMemPFDYears() {
        return memPFDYears;
    }

    public List<String> getCongressNumbers() {
        return congressNumbers;
    }

    public List<String> getCandIndCycles() {
        return candIndCycles;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getLastId() {
        return pref.get("LastId", "850137187320565760");
    }
}
