package com.tieto.services;

import java.util.List;

import com.tieto.models.City;
import com.tieto.models.MaxTemperature;

public interface CityService {
	/**
	 * Fetch all {@link MaxTemperature} for given City
	 * 
	 * @param city
	 *            {@link String} name of the city
	 * @return {@link List} of {@link MaxTemperature}
	 */
	List<MaxTemperature> findCityTemperatures(String city);

	/**
	 * Fetch all existing {@link City}
	 * 
	 * @return {@link List} of {@link City}
	 */
	List<City> findAllCities();
}
