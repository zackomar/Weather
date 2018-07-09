package com.tieto.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tieto.models.MaxTemperature;
import com.tieto.models.dtos.MaxTemperatureDTO;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MaxTemperatureMapper {

	public MaxTemperature mapDO(MaxTemperatureDTO source) {
		if (source == null) {
			return null;
		}

		return new MaxTemperature()
				.setDate(source.getDate())
				.setTemperature(source.getTemperature());
	}

	public MaxTemperatureDTO mapDTO(MaxTemperature source) {
		if (source == null) {
			return null;
		}

		return MaxTemperatureDTO.builder()
				.date(source.getDate())
				.temperature(source.getTemperature())
				.build();
	}

	public List<MaxTemperatureDTO> mapDTO(List<MaxTemperature> source) {
		return source == null ? new ArrayList<>() : source.stream().map(this::mapDTO).collect(Collectors.toList());
	}
}
