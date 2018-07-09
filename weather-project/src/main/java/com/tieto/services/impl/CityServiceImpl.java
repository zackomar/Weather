package com.tieto.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tieto.configs.WeatherLoader;
import com.tieto.models.City;
import com.tieto.models.MaxTemperature;
import com.tieto.services.CityService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CityServiceImpl implements CityService {

	@Override
	public List<MaxTemperature> findCityTemperatures(String city) {

		if (city == null) {
			throw new IllegalArgumentException("Name of the city can not be null.");
		}

		for (City c : WeatherLoader.weather) {
			if (city.equals(c.getName())) {
				return c.getTemperatures();
			}
		}
		throw new IllegalArgumentException("City does not exists.");
	}

	@Override
	public List<City> findAllCities() {
		return WeatherLoader.weather;
	}

	@Override
	public City createCity(City city) {
		if (city == null) {
			throw new IllegalArgumentException("City can not be null.");
		}

		if (checkIfCityExists(city.getName())) {
			throw new IllegalArgumentException("City with given name already exists.");
		}

		WeatherLoader.weather.add(city);

		return city;
	}

	@Override
	public City addTemperatureToCity(String city, MaxTemperature temperature) {
		if (city == null || temperature == null) {
			throw new IllegalArgumentException("City and temperature can not be null.");
		}

		City c = findCity(city);

		// checks if given temperature does not already exists
		if (!temperatureExists(temperature.getTemperature(), c.getTemperatures())) {
			List<MaxTemperature> temperatures = c.getTemperatures();
			temperatures.add(temperature);
			c.setTemperatures(temperatures);
		}

		return c;
	}

	@Override
	public City addTemperaturesToCity(String city, List<MaxTemperature> temperatures) {
		if (city == null || temperatures == null) {
			throw new IllegalArgumentException("City and temperatures can not be null.");
		}

		City c = findCity(city);

		// check if any of given temperatures already exist
		for (MaxTemperature t : temperatures) {
			if (temperatureExists(t.getTemperature(), c.getTemperatures())) {
				throw new IllegalArgumentException("Temperature " + t.getTemperature() + " already exists.");
			}
		}

		// if none of temperatures already exists, then we add all of new temperatures
		// to city
		List<MaxTemperature> temps = c.getTemperatures();
		temps.addAll(temperatures);
		c.setTemperatures(temps);

		return c;
	}

	/**
	 * Checks if city with given name already exists.
	 * 
	 * @param city
	 *            {@link String}
	 * @return true if city already exists
	 */
	private Boolean checkIfCityExists(String city) {
		for (City c : WeatherLoader.weather) {
			if (city.equals(c.getName())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks if temperature already exists
	 * 
	 * @param temperature
	 * @param temperatures
	 * @return true if temperature already exists
	 * @throws IllegalArgumentException
	 *             if temperature already exists
	 */
	private boolean temperatureExists(Integer temperature, List<MaxTemperature> temperatures) {
		for (MaxTemperature t : temperatures) {
			if (temperature == t.getTemperature()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Finds {@link City} by given {@link String}
	 * 
	 * @param city
	 * @return {@link City} with correct name
	 */
	private City findCity(String city) {
		for (City c : WeatherLoader.weather) {
			if (city.equals(c.getName())) {
				return c;
			}
		}
		throw new IllegalArgumentException("City does not exists.");
	}
}
