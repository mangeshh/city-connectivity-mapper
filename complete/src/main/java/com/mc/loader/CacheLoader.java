package com.mc.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.mc.cache.CityConnectivityMap;

@Component
public class CacheLoader {

	@Autowired
	CityConnectivityMap cityConnectivityMap;

	private String path;

	private BufferedReader buffer;

	public CacheLoader(String path) {
		super();
		this.path = path;
	}

	@PostConstruct
	public void init() throws Exception {
		System.out.println("started loading cache from " + path);
		loadCityMap();
	}
	
	/**
	 * validation of file is pending.. need to work on validation common framework.
	 * @throws Exception
	 */
	public void loadCityMap() throws Exception {
		File file = ResourceUtils.getFile(path);
		//File file = new File(path);
		FileReader reader = new FileReader(file);
		buffer = new BufferedReader(reader);
		String line = null;
		int counter = 0;
		while ((line = buffer.readLine()) != null) {
			try {
				if (line.trim().length() > 0) {
					if(line.indexOf(',') == -1) {
						System.out.println("inside file line, is corrupted : " + line );
						continue;
					}
					int indexOfDelimiter = line.indexOf(',');
					String origin = line.substring(0, indexOfDelimiter).trim();
					String destination = line.substring(indexOfDelimiter + 1, line.length()).trim();
					cityConnectivityMap.put(origin, destination);
					counter++;
				}
			} catch (Exception e) {
				System.out.println("ERROR while reading - " + line);
			}
		}
		System.out.println("done loading cache. size : [" + counter + "]");
	}

}
