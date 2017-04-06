package com.anthonyolivieri.secrettwits.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertiesService {

    private final Properties prop;

    private final ClassLoader loader;

    private final InputStream input;

    private final String basePath;

    private final String filePath;

    public PropertiesService() throws FileNotFoundException {
        this.basePath = new File("").getAbsolutePath();
        this.filePath = basePath.concat("/src/main/resources/application.properties");
        this.prop = new Properties();
        this.loader = Thread.currentThread().getContextClassLoader();
        this.input = loader.getResourceAsStream("application.properties");
        loadInput();
    }

    /**
     * @param propertyName
     *
     * @return String
     */
    public String getProperty(String propertyName) {
        return prop.getProperty(propertyName);
    }

    /**
     * @param propertyName
     * @param propertyValue
     */
    public void setProperty(String propertyName, String propertyValue) {
        prop.put(propertyName, propertyValue);
        storeOutput();
    }

    private void loadInput() {
        try {
            this.prop.load(input);
        } catch (IOException ex) {
            Logger.getLogger(PropertiesService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void storeOutput()  {
        try {
            this.prop.store(new FileOutputStream(filePath, false), null);
        } catch (IOException ex) {
            Logger.getLogger(PropertiesService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
