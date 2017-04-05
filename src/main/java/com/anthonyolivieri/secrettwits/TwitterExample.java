package com.anthonyolivieri.secrettwits;

import com.anthonyolivieri.secrettwits.services.PropertiesService;
import java.io.FileNotFoundException;
import java.io.IOException;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TwitterExample {

    

    public static void main(String... args) throws TwitterException, InterruptedException, FileNotFoundException, IOException {
        Twitter twitter = TwitterFactory.getSingleton();
        
        System.out.println(twitter.getScreenName());
        
        
    }

}
