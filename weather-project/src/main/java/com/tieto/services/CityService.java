package com.tieto.services;

import java.util.List;

import com.tieto.models.City;
import com.tieto.models.MaxTemperature;

public interface CityService {
	/**
	 * Fetch all {@link MaxTemperature} for {@link City} with given name if city
	 * exists.
	 * 
	 * @param city
	 *            {@link String} name of the city
	 * @return all temperatures of City
	 */
	List<MaxTemperature> findCityTemperatures(String city);

	/**
	 * Fetch all existing {@link City}.
	 * 
	 * @return all existing cities.
	 */
	List<City> findAllCities();

	/**
	 * Creates new {@link City} if another city with given name does not exists.
	 * 
	 * @param city
	 *            {@link City}
	 * @return newly created {@link City} if city was created.
	 */
	City createCity(City city);
}
