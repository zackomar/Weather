package com.tieto.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tieto.models.City;
import com.tieto.models.dtos.CityDTO;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CityMapper {

	public City mapDO(CityDTO source) {
		if (source == null) {
			return null;
		}

		return new City()
				.setName(source.getName())
				.setTemperatures(source.getTemperatures());
	}

	public CityDTO mapDTO(City source) {
		if (source == null) {
			return null;
		}

		return CityDTO.builder()
				.name(source.getName())
				.temperatures(source.getTemperatures())
				.build();
	}
	
	public List<CityDTO> mapDTO(List<City> source) {
        return source == null ? new ArrayList<>() : source.stream().map(this::mapDTO).collect(Collectors.toList());
    }
}
