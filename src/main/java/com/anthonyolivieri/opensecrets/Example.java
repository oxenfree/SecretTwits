package com.anthonyolivieri.opensecrets;

import com.anthonyolivieri.opensecrets.API.OpenSecretsAPI;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class Example {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        String candidateId = "N00007360"; //Nancy Pelosi(D CA-12th)
        String cycle = "2014"; //Check application.properties for "cycle" and "year" explanations
        String year = "2013";
        String stateId = "NJ";
        String name = "Goog"; //Partial names or full names of organizations
        String orgId = "D000000085"; //Org Id's can be found in the results of getOrganizationIdsByName
        String industryId = "K02";
        String committeeId = "HARM"; //Committee ID's can be found at https://www.opensecrets.org/resources/create/api_doc.php
        String congressNo = "113"; //Check application.properties for "Congress Number" explanation
        List<JSONObject> jsonList = new ArrayList<>();
        
        OpenSecretsAPI api = new OpenSecretsAPI();
        
        /**
         * Candidate summary can be called without a cycle.
         * If no cycle is supplied, return is for latest cycle.
         */
        JSONObject candSum = api.getCandidateSummary(candidateId);
        jsonList.add(candSum);
        
        /**
         * Or with a cycle (2014 from above).
         */
        JSONObject candSumWithCycle = api.getCandidateSummary(candidateId, cycle);
        jsonList.add(candSumWithCycle);
        
        /**
         * Financial disclosure can be called without a year.
         * If no year is supplied, return is for latest year on record (2014 as of now).
         */
        JSONObject financialDisclosure = api.getFinancialDisclosure(candidateId);
        jsonList.add(financialDisclosure);
        
        /**
         * Or with a year (2013 from above).
         */
        JSONObject financialDisclosureWithYear = api.getFinancialDisclosure(candidateId, year);
        jsonList.add(financialDisclosureWithYear);
        
        /**
         * Find your Congress Rep's Twitter handle and fax line!
         * Legislators are returned according to their two-letter state Id, 
         * 4-character district Id (OH03), or OpenSecrets CID. 
         * This particular call returns the legislators for NJ (stateId from above).
         */
        JSONObject legislators = api.getLegislators(stateId);
        jsonList.add(legislators);
        
        /**
         * getOrganizationIdsByName returns a JSONObject with several possible organizations that match
         * a name or partial name. For example, calling this with "Goldman" returns many organizations
         * with Goldman in the name, not just Goldman Sachs. You'll have to parse to find the correct id.
         */
        JSONObject orgs = api.getOrganizationIdsByName(name);
        jsonList.add(orgs);
        
        /**
         * The ID's from above can be plugged in below to get contribution information for a particular
         * organization. This is Goldman Sachs (orgId "D000000085" from above).
         */
        JSONObject orgSummary = api.getOrganizationSummaryById(orgId);
        jsonList.add(orgSummary);
        
        /**
         * Top contributions can be called without a cycle.
         * If no cycle is supplied, return is for latest on record (2016 as of now).
         */
        JSONObject contributions = api.getTopContributionsToCandidate(candidateId);
        jsonList.add(contributions);
        
        /**
         * Or with a cycle (2014 from above)
         */
        JSONObject contributionsOfCycle = api.getTopContributionsToCandidate(candidateId, cycle);
        jsonList.add(contributionsOfCycle);
        
        /**
         * Get top industries that contributed to a particular candidate without a cycle.
         * Latest results (2016) are returned.
         */
        JSONObject topIndustries = api.getTopIndustriesForCandidate(candidateId);
        jsonList.add(topIndustries);
        
        /**
         * Or with a cycle.
         */
        JSONObject topIndustriesOfCycle = api.getTopIndustriesForCandidate(candidateId, cycle);
        jsonList.add(topIndustriesOfCycle);
        
        /**
         * Top industrial sectors. Called without a cycle. Latest data returned.
         */
        JSONObject topSectors = api.getTopSectorsForCandidate(candidateId);
        jsonList.add(topSectors);
        
        /**
         * Or with a cycle (2014).
         */
        JSONObject topSectorsOfCycle = api.getTopSectorsForCandidate(candidateId, cycle);
        jsonList.add(topSectorsOfCycle);
        
        /**
         * Totals by industry without cycle. Latest returned. 2016
         * This particular call finds contributions from the industry: "lobbyist" (K02 from above)
         */
        JSONObject totalsByIndustry = api.getTotalsForCandidateByIndustry(candidateId, industryId);
        jsonList.add(totalsByIndustry);
        
        /**
         * Or with a cycle (2014)
         */
        JSONObject totalsIndustryOfCycle = api.getTotalsForCandidateByIndustry(candidateId, industryId, cycle);
        jsonList.add(totalsIndustryOfCycle);
        
        /**
         * Contributions to a particular Congressional Committee by Industry can be called
         * without a "Congress Number". If congressNumber isn't supplied then the latest data
         * on record (114th Congress) is returned. Committee ID's and Industry ID's can be found at:
         * https://www.opensecrets.org/resources/create/api_doc.php
         */
        JSONObject committeeByIndustry = api.getTotalsForCommitteeByIndustry(committeeId, industryId);
        jsonList.add(committeeByIndustry);
        
        /**
         * Or with a particular Congress number. (113th from above)
         */
        JSONObject committeeByIndustryCongNo = api.getTotalsForCommitteeByIndustry(committeeId, industryId, congressNo);
        jsonList.add(committeeByIndustryCongNo);
        
        /**
         * Here comes a wealth of information!
         */
        for (JSONObject json: jsonList) {
            System.out.println(json.toString(4)); // 4 indents the resulting string to make it readable.
        }
    }
}
