package com.sample.utils;

import java.util.ArrayList;
import java.util.List;

import com.sample.model.impl.CachedObject;

/**
 * Util class with util methods used in project
 * 
 * @author andrucris
 *
 */
public class CalculatorUtils {

	/**
	 * build cached objects from parameters and operation name
	 * 
	 * @param operation
	 * @param parameters
	 * @return
	 */
	public static CachedObject buildCachedObjectFromParametersAndOperation(String operation, List<Double> parameters) {
		StringBuilder result = new StringBuilder();
		result.append(operation);
		for (Double element : parameters) {
			result.append(element);
		}
		return new CachedObject(result.toString().hashCode(), result.toString());
	}

	public static List<Double> checkDouble(String... parameters) {
		List<Double> doubleList = new ArrayList<>();
		for (String element : parameters) {
			try {
				doubleList.add(Double.parseDouble(element));
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return doubleList;
	}

}
