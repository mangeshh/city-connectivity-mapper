package com.mc.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Top 5000 records will be stored.
 * @author mange
 *
 */
public class LRUCache extends LinkedHashMap<String, String> {

	private static final long serialVersionUID = 1L;
	private int size;

    public LRUCache(int size) {
        this.size = size;
    }

    public void put(String origin, String destination, String result) {
    	put(origin+"#"+destination, result);
    }
    
    public String get(String origin, String destination) {
    	String cacheData = get(origin+"#"+destination);
    	if(cacheData == null) {
    		cacheData = get(destination+"#"+origin);
    	}
    	if(cacheData!= null) {
        	System.out.println("cache:" + origin + "#" + destination);
    	}
    	return cacheData;
    }
    
    @Override
    protected boolean removeEldestEntry(Map.Entry<String, String> stale) {
        return size() > size;
    }
}