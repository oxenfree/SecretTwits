package com.anthonyolivieri.secrettwits.services;

import com.anthonyolivieri.secrettwits.App;
import com.anthonyolivieri.secrettwits.api.OpenSecretsAPI;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.json.JSONObject;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TwitterService {

    private final Twitter twitter;
    private final List<Status> repliedTo;
    private final String basePath;
    private final String newPath;
    private final OpenSecretsAPI api;
    private final APIService apiServe;

    /**
     *
     * @param twitter
     * @throws TwitterException
     * @throws FileNotFoundException
     */
    public TwitterService(Twitter twitter) throws TwitterException, FileNotFoundException {
        this.basePath = new File("").getAbsolutePath();
        this.newPath = basePath.concat("/src/main/resources/");
        this.twitter = twitter;
        this.repliedTo = new ArrayList<>();
        this.api = new OpenSecretsAPI();
        this.apiServe = new APIService();
    }

    private String stripName(Status status) {
        String text = status.getText();
        int startIndex = text.indexOf("@");
        int endIndex = text.indexOf(" ");
        String stripped = text.substring(startIndex, endIndex + 1);
        
        return text.replaceAll(stripped, "");
    }

    private String buildText(String candidate) {
        StringBuffer textToImage = new StringBuffer("Top Contributions to: ").append(candidate).append("--\n");
        try {
            String candId = apiServe.getCandidateIdFromName(candidate);
            JSONObject contrib = api.getTopContributionsToCandidate(candId);
            Map<String, String> jsonMap = apiServe.getSortedMapByRawJSON(contrib);
            jsonMap.forEach((k, v) -> textToImage
                    .append("Contributor: ")
                    .append(k).append("\nAmount: $")
                    .append(v)
                    .append("\n-----\n"));
        } catch (NullPointerException ex) {
            textToImage.append("\n Sorry, couldn't find information for that candidate."
                    + "\n Is the name spelled correctly?");
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        textToImage.append("\n-----\n\n-----\n\n-----\n");

        return textToImage.toString();
    }

    private File generateFileFromText(String imageText) {
        UUID uuid = UUID.randomUUID();
        String fileName = "image" + uuid.toString();
        File newFile = new File(newPath + "img/" + fileName + ".jpeg");

        // Font 
        Font font = new Font("Helvetica Neue", Font.PLAIN, 12);
        FontRenderContext frc = new FontRenderContext(null, true, true);

        String[] lines = imageText.split("\n");

        //get the height and width of the text
        Rectangle2D bounds = font.getStringBounds(imageText, frc);
        int w = 400;
        int h = (int) bounds.getHeight() * lines.length;

        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, w, h);
        g.setColor(Color.BLACK);
        g.setFont(font);
        int lineHeight = g.getFontMetrics().getHeight();
        for (int lineCount = 0; lineCount < lines.length; lineCount++) { //lines from above
            g.drawString(lines[lineCount], (float) bounds.getX() + 5, (float) -bounds.getY() + lineHeight * lineCount);
        }
        g.dispose();

        try {
            //creating the file
            ImageIO.write(image, "jpeg", newFile);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

        return newFile;
    }

    /**
     *
     * @param status
     */
    public void replyWithImage(Status status) {
        String candidate = stripName(status);
        File attachable = generateFileFromText(buildText(candidate));

        try {
            status.getInReplyToUserId();
            System.out.println(status.getText());
            StatusUpdate reply = new StatusUpdate(
                    "@" + status.getUser().getScreenName()
            );
            reply.inReplyToStatusId(status.getId());
            reply.setMedia(attachable);
            Status update = twitter.updateStatus(reply);
            repliedTo.add(status);
            System.out.println(update.getText());

        } catch (TwitterException ex) {
            Logger.getLogger(TwitterService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
