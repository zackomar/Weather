package com.tieto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
	public List<MaxTemperatureDTO> findCityTemperatures(@PathVariable String city) {
		List<MaxTemperature> temperatures = cityService.findCityTemperatures(city);

		return maxTemperatureMapper.mapDTO(temperatures);
	}

	@GetMapping("/api/weather/cities")
	public List<CityDTO> findAllCities() {
		List<City> cities = cityService.findAllCities();

		return cityMapper.mapDTO(cities);
	}
}
