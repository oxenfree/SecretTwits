package com.anthonyolivieri.opensecrets.services;

import com.anthonyolivieri.opensecrets.MoreExamples;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
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

    public void generateFileFromText(String text) throws IOException {
        UUID uuid = UUID.randomUUID();
        String fileName = "image" + uuid.toString();
        File newFile = new File(newPath + "img/" + fileName + ".jpeg");

        // Font 
        Font font = new Font("Helvetica Neue", Font.PLAIN, 12);
        FontRenderContext frc = new FontRenderContext(null, true, true);

        String[] lines = text.split("\n");

        //get the height and width of the text
        Rectangle2D bounds = font.getStringBounds(text, frc);
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
            Logger.getLogger(MoreExamples.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            Logger.getLogger(MoreExamples.class.getName()).log(Level.SEVERE, null, ex);
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
