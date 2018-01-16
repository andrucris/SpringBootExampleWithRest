package com.sample.rest;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.cache.CacheManager;
import com.sample.model.impl.CachedObject;
import com.sample.utils.CalculatorUtils;

/**
 * 
 * @author andrucris
 *
 *         calculator rest web service that caches the results of its
 *         computations, with endpoints
 * 
 *         /add/{a}/{b}/{c}
 * 
 *         /substract/{a}/{b}/{c}
 * 
 *         /multiply/{a}/{b}/{c}
 * 
 *         /divide/{a}/{b}
 * 
 *         the result is returned from cache if the same operation is called
 *         more then one time
 * 
 * 
 */
@RestController
public class CalculatorWebService {

	CacheManager cacheManager = CacheManager.getInstance();

	/**
	 * 
	 * method calculate addition for three parameters sent in url
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return response containting the result of the operation
	 */
	@RequestMapping(path = "/add/{a}/{b}/{c}")
	public String add(@PathVariable("a") String a, @PathVariable("b") String b, @PathVariable("c") String c) {
		System.out.println("Enter here");
		List<Double> doubleList = CalculatorUtils.checkDouble(new String[] { a, b, c });

		if (doubleList != null) {

			return getResponse(cacheManager, "add", doubleList);
		} else {
			return "One or more parameters are not valid!)";
		}
	}

	/**
	 * method calculate substraction for three parameters sent in url
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return response containting the result of the operation
	 */
	@RequestMapping(path = "/substract/{a}/{b}/{c}")
	public String substract(@PathVariable("a") String a, @PathVariable("b") String b, @PathVariable("c") String c) {

		List<Double> doubleList = CalculatorUtils.checkDouble(new String[] { a, b, c });

		if (doubleList != null) {

			return getResponse(cacheManager, "substract", doubleList);
		} else {
			return "One or more parameters are not valid!)";
		}
	}

	/**
	 * method calculate multiplication for three parameters sent in url
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return response containting the result of the operation
	 */
	@RequestMapping(path = "/multiply/{a}/{b}/{c}")
	public String multiply(@PathVariable("a") String a, @PathVariable("b") String b, @PathVariable("c") String c) {

		List<Double> doubleList = CalculatorUtils.checkDouble(new String[] { a, b, c });

		if (doubleList != null) {

			return getResponse(cacheManager, "multiply", doubleList);
		} else {
			return "One or more parameters are not valid!)";
		}
	}

	/**
	 * method calculate division for three parameters sent in url
	 * 
	 * @param a
	 * @param b
	 * @return response containting the result of the operation
	 */
	@RequestMapping(path = "/divide/{a}/{b}")
	public String divide(@PathVariable("a") String a, @PathVariable("b") String b) {

		if (!"0".equals(b)) {
			List<Double> doubleList = CalculatorUtils.checkDouble(new String[] { a, b });

			if (doubleList != null) {

				return getResponse(cacheManager, "divide", doubleList);
			} else {
				return "One or more parameters are not valid!)";
			}

		} else {
			return " The second parameter should not be 0 !";
		}
	}

	/**
	 * method build the response by concatenating the parameters with operation
	 * and creating a cached Object that then is inserted into the cache if the
	 * cache does not contain it if the object exists already in the map the
	 * operation is not computed again and the result is taken from the cache
	 * 
	 * @param cacheManager
	 * @param operation
	 * @param parameters
	 * @return response containting the result of the operation
	 */
	public String getResponse(CacheManager cacheManager, String operation, List<Double> parameters) {
		StringBuilder result = new StringBuilder();
		double numericResult = 0;
		int counter = 0;

		CachedObject obj = CalculatorUtils.buildCachedObjectFromParametersAndOperation(operation, parameters);
		// set the identifier the hashCode for Strings
		String cachedResult = cacheManager.getObject(obj);

		if (cachedResult != null) {

			result.append(cachedResult);

		} else {

			for (Double element : parameters) {
				if ("add".equals(operation)) {
					numericResult += element;
				} else if ("multiply".equals(operation)) {
					if (counter == 0) {
						numericResult = element;
					} else {
						numericResult *= element;
					}
				} else if ("substract".equals(operation)) {
					if (counter == 0) {
						numericResult = element;
					} else {
						numericResult = numericResult - element;
					}
				} else if ("divide".equals(operation)) {
					if (counter == 0) {
						numericResult = element;
					} else {
						numericResult = numericResult / element;
					}
				}
				counter++;
			}

			result.append(String.valueOf(numericResult));
			cacheManager.putCache(obj, result.toString());

		}

		return result.toString();

	}

}