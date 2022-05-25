package com.rgicl.savvion.motor.uw;

/*
 * PropertyReader.java
 *
 * Copyright - Savvion Technologies Ltd
 *
 * Modification Log
 * ----------------
 * Ver          Date           Modified By         Description
 * 0.0a         10-June-2007    Rajeevan EP            Initial Version
 * 0.1          10-Aug-2007     Rajeevan EP            Added logger
 */
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.Set;

/**
 * The PropertyReader contains methods which are used for 
 * get the keys and properties
 * 
 * @author Rajeevan EP, Savvion Technologies Ltd
 * @since RLIC R1 1.0
 */

public class PropertyReader {

    private static PropertyResourceBundle resrcBundle;
    private static PropertyReader propertyReader = null;
    
    static 
    { 
        try {
        	resrcBundle = new PropertyResourceBundle(PropertyReader.class.getClassLoader().getResourceAsStream("rgi_config_en.properties"));            
            }
        catch (Exception ex)
            {        	
            }
    }
    
    private PropertyReader(){
    	
    }
    
    public static PropertyReader getInstance(){
    	if(propertyReader == null){
    		propertyReader = new PropertyReader();
    	}
    	return propertyReader;  	
    }

    /**
     * @param selectedlanguage
     * @param key
     * @return
     */
    public String getProperty(Locale selectedlanguage, String key) throws Exception {
        String propertyValue = "";
        try {
        	propertyValue = (String) resrcBundle.getObject(key);
        }
        catch (Exception e) {
			e.printStackTrace();
			throw e;
        }
        return propertyValue;
    }

    /**
     * This method retrieves the value based on a key passed.
     * 
     * @author Rajeevan, Savvion
     * @param key
     * @return
     */
    public String getPropertyByKey(String key)throws Exception {
        String propertyValue = "";
        try {
        	propertyValue = (String) resrcBundle.getObject(key);
        }
        catch (Exception e) {
			e.printStackTrace();
			throw e;
        }
        return propertyValue;
    }

    /**
     * This method retrieves the values of keys passed in hashmap and update the HashMap.
     * 
     * @author Rajeevan, Savvion
     * @param map
     * @return
     */
    public HashMap getPropertyByMap(HashMap map) throws Exception{
        String key = "";
        String value = "";
        Set keyset = map.keySet();
        Iterator itr = keyset.iterator();
        while (itr.hasNext()) {
            key = (String) itr.next();
            value = getPropertyByKey(key);
            map.put(key, value);
        }
        return map;
    }
}
