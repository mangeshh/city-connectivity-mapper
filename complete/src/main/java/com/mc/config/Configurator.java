package com.mc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.mc.cache.CityConnectivityMap;
import com.mc.cache.DisjointSet;
import com.mc.cache.LRUCache;
import com.mc.loader.CacheLoader;

@Configuration
public class Configurator {
	
	@Value("${cache.input.file}")
	String path;
	
	@Bean("cityConnectivityMap")
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public CityConnectivityMap getCityConnectivityMap() {
		return new CityConnectivityMap();
	}
	
	@Bean("disjointSet")
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public DisjointSet getDisjointSet() {
		return new DisjointSet();
	}

	@Bean("cacheLoader")
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public CacheLoader getCacheLoader() {
		return new CacheLoader(path);
	}

	@Bean("lruCache")
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public LRUCache getLRUCache() {
		return new LRUCache(5000);
	}
}
