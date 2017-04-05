package com.anthonyolivieri.secrettwits;

import com.anthonyolivieri.secrettwits.services.APIService;
import com.anthonyolivieri.secrettwits.api.OpenSecretsAPI;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

public class MoreExamples {

    public static void main(String[] args) throws IOException {

        String candidate = "Booker, Cory";
        OpenSecretsAPI api = new OpenSecretsAPI();
        APIService apiServe = new APIService();
        JSONObject contrib = null;
        Map<String, String> jsonMap = null;
        StringBuffer textToImage = new StringBuffer("Top Contributions to: ").append(candidate).append("--\n");
        try {
            String candId = apiServe.getCandidateIdFromName(candidate);
            contrib = api.getTopContributionsToCandidate(candId);
            jsonMap = apiServe.getSortedMapByRawJSON(contrib);
            jsonMap.forEach((k, v) -> textToImage
                    .append("Contributor: ")
                    .append(k).append("\nAmount: $")
                    .append(v)
                    .append("\n-----\n"));
        } catch (NullPointerException ex) {
            textToImage.append("\n Sorry, couldn't find information for that candidate."
                    + "\n Is the name spelled correctly?");
            Logger.getLogger(MoreExamples.class.getName()).log(Level.SEVERE, null, ex);
        }

        textToImage.append("\n-----\n\n-----\n\n-----\n");
        apiServe.generateFileFromText(textToImage.toString());
    }
}
