package com.tieto.configs;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tieto.models.City;
import com.tieto.models.MaxTemperature;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WeatherLoader {

	public static List<City> weather = new ArrayList<>();

	String weatherDataLocation = "max_daily_temperatures.json";

	@PostConstruct
	void init() {
		loadWeatherFile();
	}

	public void loadWeatherFile() {
		try (InputStream weatherStream = getClass().getClassLoader().getResourceAsStream(weatherDataLocation)) {
			Map<String, List<Map<String, List<Map<String, Integer>>>>> weatherData;

			ObjectMapper objectMapper = new ObjectMapper();

			// gets all data from JSON file
			weatherData = objectMapper.readValue(weatherStream,
					new TypeReference<Map<String, List<Map<String, List<Map<String, Integer>>>>>>() {
					});

			formatWeatherData(weatherData);

		} catch (IOException e) {
			log.error("Weather data file cannot be loaded.");
		}
	}

	/*
	 * Makes data from JSON file more readable and more accesible and saving them in
	 * weather variable
	 */
	private void formatWeatherData(Map<String, List<Map<String, List<Map<String, Integer>>>>> weatherData) {
		Map<String, List<Map<String, Integer>>> citiesMap = citiesListToMap(weatherData.get("cities"));

		citiesMap.forEach((city, listOfTemperatures) -> {
			Map<String, Integer> temperaturesMap = temperaturesListToMap(listOfTemperatures);
			weather.add(new City(city, mapToListOfTemperatures(temperaturesMap)));
		});
	}

	/*
	 * Creates List<MaxTemperature> from given map
	 */
	private List<MaxTemperature> mapToListOfTemperatures(Map<String, Integer> temperaturesMap) {
		List<MaxTemperature> temperaturesList = new ArrayList<>();

		temperaturesMap.forEach((date, temperature) -> {
			temperaturesList.add(new MaxTemperature(date, temperature));
		});

		return temperaturesList;
	}

	/*
	 * Creates new map which contains all maps from given list of maps
	 */
	private Map<String, List<Map<String, Integer>>> citiesListToMap(
			List<Map<String, List<Map<String, Integer>>>> citiesList) {
		Map<String, List<Map<String, Integer>>> citiesMap = new HashMap<>();

		citiesList.forEach(cityMap -> {
			citiesMap.putAll(cityMap);
		});

		return citiesMap;
	}

	/*
	 * Creates new map which contains all maps from given list of maps
	 */
	private Map<String, Integer> temperaturesListToMap(List<Map<String, Integer>> temperaturesList) {
		Map<String, Integer> temperaturesMap = new HashMap<>();

		temperaturesList.forEach(temperatureMap -> {
			temperaturesMap.putAll(temperatureMap);
		});

		return temperaturesMap;
	}
}
