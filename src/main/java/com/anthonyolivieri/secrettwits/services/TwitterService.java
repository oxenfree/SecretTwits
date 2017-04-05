package com.anthonyolivieri.secrettwits.services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TwitterService {

    private final Twitter twitter;
    private final List<Status> mentions;
    private final List<Status> repliedTo;

    public TwitterService() throws TwitterException {
        this.twitter = TwitterFactory.getSingleton();
        this.mentions = twitter.getMentionsTimeline();
        this.repliedTo = new ArrayList<>();
    }

    private boolean haveReplied(Status status) {
        return repliedTo.stream().anyMatch((reply) -> (reply.getId() == status.getId()));
    }

    public void replyTo(Status status) {
        try {
            status.getInReplyToUserId();
            System.out.println(status.getText());
            StatusUpdate reply = new StatusUpdate(
                    "Automated reply to @" + status.getUser().getScreenName()
            );
            reply.inReplyToStatusId(status.getId());

            Status update = twitter.updateStatus(reply);
            repliedTo.add(status);
            System.out.println(update.getText());

        } catch (TwitterException ex) {
            Logger.getLogger(TwitterService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
