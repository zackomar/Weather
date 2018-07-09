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

	public List<City> findAllCities() {
		return WeatherLoader.weather;
	}
}
