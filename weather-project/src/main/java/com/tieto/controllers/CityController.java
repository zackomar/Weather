package com.tieto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.tieto.mappers.CityMapper;
import com.tieto.mappers.MaxTemperatureMapper;
import com.tieto.models.City;
import com.tieto.models.MaxTemperature;
import com.tieto.models.dtos.CityDTO;
import com.tieto.models.dtos.MaxTemperatureDTO;
import com.tieto.services.CityService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CityController {

	CityService cityService;
	CityMapper cityMapper;
	MaxTemperatureMapper maxTemperatureMapper;

	@Autowired
	public CityController(CityService cityService, 
			CityMapper cityMapper, 
			MaxTemperatureMapper maxTemperatureMapper) {
		this.cityService = cityService;
		this.cityMapper = cityMapper;
		this.maxTemperatureMapper = maxTemperatureMapper;
	}

	@GetMapping("/api/weather/cities/{city}/temperatures")
	@ResponseStatus(HttpStatus.OK)
	public List<MaxTemperatureDTO> findCityTemperatures(@PathVariable String city) {
		List<MaxTemperature> temperatures = cityService.findCityTemperatures(city);

		return maxTemperatureMapper.mapDTO(temperatures);
	}

	@GetMapping("/api/weather/cities")
	@ResponseStatus(HttpStatus.OK)
	public List<CityDTO> findAllCities() {
		List<City> cities = cityService.findAllCities();

		return cityMapper.mapDTO(cities);
	}
	
	@PostMapping("/api/weather/cities")
	@ResponseStatus(HttpStatus.CREATED)
	public CityDTO createCity(@RequestBody CityDTO city) {
		City c = cityService.createCity(cityMapper.mapDO(city));

		return cityMapper.mapDTO(c);
	}
	
	@PostMapping("/api/weather/cities/{city}/temperature")
	@ResponseStatus(HttpStatus.OK)
	public CityDTO addTemperatureToCity(@PathVariable String city, @RequestBody MaxTemperatureDTO temperature) {
		City c = cityService.addTemperatureToCity(city, maxTemperatureMapper.mapDO(temperature));

		return cityMapper.mapDTO(c);
	}
	
	@PostMapping("/api/weather/cities/{city}/temperatures")
	@ResponseStatus(HttpStatus.OK)
	public CityDTO addTemperaturesToCity(@PathVariable String city, @RequestBody List<MaxTemperatureDTO> temperatures) {
		City c = cityService.addTemperaturesToCity(city, maxTemperatureMapper.mapDO(temperatures));

		return cityMapper.mapDTO(c);
	}
}
