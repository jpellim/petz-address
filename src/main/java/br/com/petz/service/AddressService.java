package br.com.petz.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.petz.dto.GeoLocalizacaoDto;
import br.com.petz.entity.Address;
import br.com.petz.entity.City;
import br.com.petz.repository.AddressRepository;
import br.com.petz.request.AddressRequest;
import br.com.petz.response.AddressResponse;

@Service
public class AddressService {


	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private GeocodingService geoService;
 
	
	public static final Address converter(AddressRequest request, City city) {
 
		return  Address.builder()
					.streetName(request.getStreetName())
				    .number(request.getNumber())
				    .neighbourhood(request.getNeighbourhood())
				    .city(city)
				    .zipCode(request.getZipCode())
				    .latitude(request.getLatitude())
				    .longitude(request.getLongitude())
				    .build();
	}
	 
	
	public Optional<AddressResponse> update(Long id, AddressRequest addressRequest) {
		
		Address address = addressRepository.getOne(id);
		address.setStreetName(addressRequest.getStreetName());
		address.setNumber(addressRequest.getNumber());
		address.setComplement(addressRequest.getComplement());
		address.setNeighbourhood(addressRequest.getNeighbourhood());
		address.setZipCode(addressRequest.getZipCode());
		 
		City city = cityService.findCompleteById(addressRequest.getCityId());
		if (city == null) {
			return Optional.empty();
		}
		address.setCity(city);
		
		tratarGeoLocation(addressRequest, city);
		address.setLatitude(addressRequest.getLatitude());
		address.setLongitude(addressRequest.getLongitude());
		
		return Optional.ofNullable(new AddressResponse(addressRepository.save(address)));
	}

	public Page<AddressResponse> findAll(Pageable pageable) {
		Page<Address> listAddress = addressRepository.findAll(pageable);
		return listAddress.map(AddressResponse::new);
	}

	public Optional<AddressResponse> findById(Long id) {
		return Optional.ofNullable(
				 new AddressResponse(addressRepository.getOne(id) ));
	}
	
	public void remove(Long id) {
		addressRepository.deleteById(id);
	}
 	
	public Optional<AddressResponse> create(AddressRequest addressRequest) {
		
		City city = cityService.findCompleteById(addressRequest.getCityId());
		 
		if (city == null) {
			return Optional.empty();
		}
		
		tratarGeoLocation(addressRequest, city);
		
		return Optional.ofNullable(new AddressResponse(addressRepository.save(converter(addressRequest, city))));
	}


	private void tratarGeoLocation(AddressRequest addressRequest, City city) {

		if (addressRequest.getLatitude() == null && addressRequest.getLongitude() == null) {
			GeoLocalizacaoDto dto = geoService.getGeoLocalizacao(addressRequest, city);
			addressRequest.setLatitude(dto.getLatitude());
			addressRequest.setLongitude(dto.getLongitude());
		}
		
	}
}
