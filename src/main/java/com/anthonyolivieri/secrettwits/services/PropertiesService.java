package com.anthonyolivieri.secrettwits.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertiesService {

    /**
     * Properties prop
     */
    private final Properties prop;

    /**
     * ClassLoader loader
     */
    private final ClassLoader loader;

    /**
     * InputStream input
     */
    private final InputStream input;

    private final OutputStream output;

    private final String basePath;

    private final String filePath;

    public PropertiesService() throws FileNotFoundException {
        this.basePath = new File("").getAbsolutePath();
        this.filePath = basePath.concat("/src/main/resources/application.properties");
        this.prop = new Properties();
        this.loader = Thread.currentThread().getContextClassLoader();
        this.input = loader.getResourceAsStream("application.properties");
        this.output = new FileOutputStream(filePath);
        try {
            this.prop.load(input);
            
        } catch (IOException ex) {
            Logger.getLogger(PropertiesService.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            prop.setProperty(propertyName, propertyValue);
            prop.store(output, null);
        } catch (IOException ex) {
            Logger.getLogger(PropertiesService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
