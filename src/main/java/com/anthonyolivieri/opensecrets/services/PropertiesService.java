package com.anthonyolivieri.opensecrets.services;

import java.io.IOException;
import java.io.InputStream;
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

    public PropertiesService() {
        this.prop = new Properties();
        this.loader = Thread.currentThread().getContextClassLoader();
        this.input = loader.getResourceAsStream("application.properties");
    }
    
    /**
     *
     * @param propertyName
     * @return String
     */
    public String getProperty(String propertyName)  {
        try {
            prop.load(input);
        } catch (IOException ex) {
            Logger.getLogger(PropertiesService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return prop.getProperty(propertyName);
    }
}
