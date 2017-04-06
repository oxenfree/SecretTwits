package com.anthonyolivieri.secrettwits.services;

import com.anthonyolivieri.secrettwits.App;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.json.JSONArray;
import org.json.JSONObject;

public class APIService {

    private final String basePath;
    private final String newPath;
    private final String delimeter = "::";
    private final Map<String, String> candidateMap;
    private final Map<String, String> jsonMap;

    public APIService() {
        this.basePath = new File("").getAbsolutePath();
        this.newPath = basePath.concat("/src/main/resources/");
        this.candidateMap = new HashMap<>();
        this.jsonMap = new TreeMap<>();
    }

    /**
     *
     * @param name
     *
     * @return String
     */
    public String getCandidateIdFromName(String name) throws NullPointerException {
        try (Stream<String> lines = Files.lines(Paths.get(newPath + "candidates.txt"))) {
            lines.filter(line -> line.contains(delimeter)).forEach(
                    line -> candidateMap.putIfAbsent(line.split(delimeter)[0], line.split(delimeter)[1])
            );
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        Object print = null == getKeyFromValue(candidateMap, name)
                ? null
                : getKeyFromValue(candidateMap, name);

        return print.toString();
    }

    /**
     *
     * @param contributions
     *
     * @return Map
     */
    public Map<String, String> getSortedMapByRawJSON(JSONObject contributions) {
        JSONArray innerArray = contributions
                .getJSONObject("response")
                .getJSONObject("contributors")
                .getJSONArray("contributor");

        innerArray.forEach((entry) -> {
            JSONObject obj = (JSONObject) entry;
            JSONObject actual = obj.getJSONObject("@attributes");
            jsonMap.put(actual.getString("org_name"), actual.getString("total"));
        });

        return jsonMap;
    }

    private Object getKeyFromValue(Map hm, String value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }

        return null;
    }
}
