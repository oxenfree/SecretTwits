package com.anthonyolivieri.secrettwits;

import com.anthonyolivieri.secrettwits.services.PreferencesService;
import com.anthonyolivieri.secrettwits.services.TwitterService;
import java.io.IOException;
import java.util.List;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class App {

    public static void main(String[] args) throws IOException, TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        PreferencesService props = new PreferencesService();
        Long lastId = Long.parseLong(props.getLastId());
        Paging page = new Paging(lastId);
        List<Status> mentions = twitter.getMentionsTimeline(page);
        TwitterService twits = new TwitterService(twitter);
        System.out.println("Starting up.");

        for (Status status : mentions) {
            lastId = status.getId();
            twits.replyWithImage(status);
        }

        props.setLastId(lastId.toString());
        System.out.println(props.getLastId());
    }
}
