
package com.mc.resource;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mc.cache.CityConnectivityMap;
import com.mc.cache.DisjointSet;
import com.mc.cache.LRUCache;
import com.mc.exception.CityConnectivityException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
@EnableCircuitBreaker
public class PathFinderResource {

	@Autowired
	public CityConnectivityMap citiConnectivityMap;

	@Autowired
	DisjointSet disjointSet;
	
	@Autowired
	public LRUCache lruCache;

	@RequestMapping(value = "/connectedV1", method = RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "isConnectedV1Fallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") })
	public String isConnectedV1(@RequestParam(name = "origin", required = true) String origin,
			@RequestParam(name = "destination", required = true) String destination) throws CityConnectivityException {
		String returnResult;

		if ((returnResult = lruCache.get(origin, destination)) != null) {
			return returnResult;
		}
		returnResult = citiConnectivityMap.isConnected(origin, destination);
		lruCache.put(origin, destination, returnResult);
		return returnResult;
	}

	@RequestMapping(value = "/connected", method = RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "isConnectedV2Fallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") })
	public String isConnectedV2(@RequestParam(name = "origin", required = true) String origin,
			@RequestParam(name = "destination", required = true) String destination) throws CityConnectivityException {
		String returnResult;
		if ((returnResult = lruCache.get(origin, destination)) != null) {
			return returnResult;
		}
		returnResult = citiConnectivityMap.isConnectedQuickCheck(origin, destination);
		lruCache.put(origin, destination, returnResult);
		return returnResult;
	}
	
	@RequestMapping(value = "/connectedV3", method = RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "isConnectedV2Fallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") })
	public String isConnectedByDisJointSet(@RequestParam(name = "origin", required = true) String origin,
			@RequestParam(name = "destination", required = true) String destination) throws CityConnectivityException {
		String returnResult;
		 
		if(disjointSet.findSet(origin) == disjointSet.findSet(destination)){
			returnResult = "yes";
		} else {
			returnResult = "no";
		}
 		return returnResult;
	}

	
	public String isConnectedV1Fallback(String origin, String destination) {
		System.out.println("fallbackMethod:isConnectedFallback");
		return "INTERNAL SERVER ERROR";
	}
	
	public String isConnectedV2Fallback(String origin, String destination) {
		System.out.println("fallbackMethod:isConnectedQuickCheckFallback");
		return "INTERNAL SERVER ERROR";
	}

	public String isConnectedV3Fallback(String origin, String destination) {
		System.out.println("fallbackMethod:isConnectedByDisJointSet");
		return "INTERNAL SERVER ERROR";
	}
	
	@RequestMapping("/test")
	public String check() {
		return "UP..";
	}

	@ExceptionHandler(CityConnectivityException.class)
	public void handleCityConnectivityIssues(CityConnectivityException exp, HttpServletResponse response)
			throws IOException {
		System.out.println("handleCityConnectivityIssues is being called!");
		response.sendError(HttpStatus.EXPECTATION_FAILED.value(), exp.getMessage());
	}

}
