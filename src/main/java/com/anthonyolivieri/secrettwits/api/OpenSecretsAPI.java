package com.anthonyolivieri.secrettwits.api;

import com.anthonyolivieri.secrettwits.Example;
import com.anthonyolivieri.secrettwits.services.PropertiesService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class OpenSecretsAPI {

    private final String apiKey;
    private final StringBuilder rootURL;
    private final List<String> candIndCycles;
    private final List<String> memPFDYears;
    private final List<String> congressNumbers;
    private final PropertiesService propService;
    private final String userAgent;

    public OpenSecretsAPI() throws FileNotFoundException {
        this.propService = new PropertiesService();
        this.apiKey = propService.getProperty("APIKey");
        this.userAgent = propService.getProperty("UserAgent");
        this.candIndCycles = Arrays.asList(propService.getProperty("CandIndCycles").split(","));
        this.memPFDYears = Arrays.asList(propService.getProperty("MemPFDYears").split(","));
        this.congressNumbers = Arrays.asList(propService.getProperty("CongressNumbers").split(","));
        this.rootURL = new StringBuilder(propService.getProperty("RootURL"));
        this.rootURL.append("&").append("apikey=").append(apiKey);
    }

    /**
     * @param candidateId
     *
     * @return JSONObject
     */
    public JSONObject getCandidateSummary(String candidateId) {

        return getCandidateSummary(candidateId, null);
    }

    /**
     * @param candidateId
     * @param cycle
     *
     * @return JSONObject
     */
    public JSONObject getCandidateSummary(String candidateId, String cycle) {
        addCidAndCycle(candidateId, cycle);

        return makeCall("candSummary");
    }

    /**
     * @param memberId
     *
     * @return JSONObject
     */
    public JSONObject getFinancialDisclosure(String memberId) {

        return getFinancialDisclosure(memberId, null);
    }

    /**
     * @param memberId
     * @param year
     *
     * @return JSONObject
     */
    public JSONObject getFinancialDisclosure(String memberId, String year) {
        rootURL.append("&cid=").append(memberId);

        if (null != year && this.memPFDYears.contains(year)) {
            rootURL.append("&year=").append(year);
        }

        return makeCall("memPFDprofile");
    }

    /**
     * This id can be either the OpenSecrets id for a specific legislator, a two-character state abbreviation (NJ, CA,
     * etc) or a four-character district id. See OpenSecrets API documentation for more info.
     *
     * @param id
     *
     * @return JSONObject
     */
    public JSONObject getLegislators(String id) {
        rootURL.append("&id=").append(id);

        return makeCall("getLegislators");
    }

    /**
     * @param name
     *
     * @return JSONObject
     */
    public JSONObject getOrganizationIdsByName(String name) {
        rootURL.append("&org=").append(name);

        return makeCall("getOrgs");
    }

    /**
     * @param id
     *
     * @return JSONObject
     */
    public JSONObject getOrganizationSummaryById(String id) {
        rootURL.append("&id=").append(id);

        return makeCall("orgSummary");
    }

    /**
     * @param candidateId
     *
     * @return JSONObject
     */
    public JSONObject getTopContributionsToCandidate(String candidateId) {

        return getTopContributionsToCandidate(candidateId, null);
    }

    /**
     * @param candidateId
     * @param cycle
     *
     * @return JSONObject
     */
    public JSONObject getTopContributionsToCandidate(String candidateId, String cycle) {
        addCidAndCycle(candidateId, cycle);

        return makeCall("candContrib");
    }

    /**
     * @param candidateId
     *
     * @return JSONObject
     */
    public JSONObject getTopIndustriesForCandidate(String candidateId) {

        return getTopIndustriesForCandidate(candidateId, null);
    }

    /**
     * @param candidateId
     * @param cycle
     *
     * @return JSONObject
     */
    public JSONObject getTopIndustriesForCandidate(String candidateId, String cycle) {
        addCidAndCycle(candidateId, cycle);

        return makeCall("candIndustry");
    }

    /**
     * @param candidateId
     *
     * @return JSONObject
     */
    public JSONObject getTopSectorsForCandidate(String candidateId) {

        return getTopSectorsForCandidate(candidateId, null);
    }

    /**
     * @param candidateId
     * @param cycle
     *
     * @return JSONObject
     */
    public JSONObject getTopSectorsForCandidate(String candidateId, String cycle) {
        addCidAndCycle(candidateId, cycle);

        return makeCall("candSector");
    }

    /**
     * @param candidateId
     * @param industryId
     *
     * @return JSONObject
     */
    public JSONObject getTotalsForCandidateByIndustry(String candidateId, String industryId) {

        return getTotalsForCandidateByIndustry(candidateId, industryId, null);
    }

    /**
     * @param candidateId
     * @param industryId
     * @param cycle
     *
     * @return JSONObject
     */
    public JSONObject getTotalsForCandidateByIndustry(String candidateId, String industryId, String cycle) {
        rootURL.append("&ind=").append(industryId);
        addCidAndCycle(candidateId, cycle);

        return makeCall("CandIndByInd");
    }

    /**
     * @param committeeId
     * @param industryId
     *
     * @return JSONObject
     */
    public JSONObject getTotalsForCommitteeByIndustry(String committeeId, String industryId) {

        return getTotalsForCommitteeByIndustry(committeeId, industryId, null);
    }

    /**
     * @param committeeId
     * @param industryId
     * @param congressNo
     *
     * @return JSONObject
     */
    public JSONObject getTotalsForCommitteeByIndustry(String committeeId, String industryId, String congressNo) {
        rootURL.append("&cmte=").append(committeeId);
        rootURL.append("&indus=").append(industryId);

        if (null != congressNo && congressNumbers.contains(congressNo)) {
            rootURL.append("&congno=").append(congressNo);
        }

        return makeCall("congCmteIndus");
    }

    private void addCidAndCycle(String candidateId, String cycle) {
        rootURL.append("&cid=").append(candidateId);

        if (null != cycle && candIndCycles.contains(cycle)) {
            rootURL.append("&cycle=").append(cycle);
        }
    }

    private JSONObject makeCall(String method) {
        JSONObject json = new JSONObject();
        rootURL.append("&method=").append(method);

        try {
            URL url = new URL(rootURL.toString());
            URLConnection uc = url.openConnection();
            uc.addRequestProperty("User-Agent", userAgent);
            json = new JSONObject(IOUtils.toString(uc.getInputStream(), "UTF-8"));
        } catch (MalformedURLException ex) {
            Logger.getLogger(Example.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Example.class.getName()).log(Level.SEVERE, null, ex);
        }

        return json;
    }
}
