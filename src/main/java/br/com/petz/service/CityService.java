package br.com.petz.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.petz.entity.City;
import br.com.petz.repository.CityRepository;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;
	 
	public Optional<City> findById(Long id) {
		return Optional.ofNullable(cityRepository.getOne(id));
	}
	
	public City findCompleteById(Long id) {
		return cityRepository.findCompleteById(id);
	}
	
}
