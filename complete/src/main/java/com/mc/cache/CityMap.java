package com.mc.cache;

public interface CityMap {
	
	public void put(String key, String value);

	public String isConnected(String origin, String dest);
	
	public String isConnectedQuickCheck(String origin, String dest);

}