package br.com.petz.service;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.petz.entity.Address;
import br.com.petz.entity.City;
import br.com.petz.entity.Country;
import br.com.petz.entity.State;
import br.com.petz.repository.AddressRepository;
import br.com.petz.request.AddressRequest;
import br.com.petz.response.AddressResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AddressServiceTest {
 
	@InjectMocks
	private AddressService addressService;

	@Mock
	private AddressRepository addressRepository;
	
	@Mock
	private CityService cityService;
	
	@Test
	public void salvarAddressTeste() {
		final Country country = Country.builder().id(1L).code("BR").name("BRASIL").build();
		final State state = State.builder().id(1L).code("SP").name("SAO PAULO").country(country).build();
		final City city = City.builder().id(1L).name("Sao Paulo").state(state).build();
		Mockito.when(cityService.findCompleteById(1L)).thenReturn(city);
		
		final Address address = Address.builder().streetName("Rua nomeRua").number(123).neighbourhood("bairro ABC").city(city).zipCode("09867-309").latitude("23.932736").longitude("-8.234325").build();
		Mockito.when(addressRepository.save(address)).thenReturn(address);
		
		final AddressRequest addressRequest = AddressRequest.builder().streetName("Rua nomeRua").number(123).neighbourhood("bairro ABC").cityId(1L).zipCode("09867-309").latitude("23.932736").longitude("-8.234325").build();
		final Optional<AddressResponse> result =  addressService.create(addressRequest);
		
		assertEquals(result.get().getStreetName(), address.getStreetName());
		assertEquals(result.get().getNumber(), address.getNumber());
		assertEquals(result.get().getNeighbourhood(), address.getNeighbourhood());
		assertEquals(result.get().getZipCode(), address.getZipCode());
		assertEquals(result.get().getCityId(), address.getCity().getId());
		
	}
	
	@Test
	public void consultarAddressTest() { 
		final Country country = Country.builder().id(1L).code("BR").name("BRASIL").build();
		final State state = State.builder().id(1L).code("SP").name("SAO PAULO").country(country).build();
		final City city = City.builder().id(1L).name("Sao Paulo").state(state).build();
		final Address address = Address.builder().id(1L).streetName("Rua nome Rua").neighbourhood("bairro X").number(123).city(city).zipCode("02394-983").latitude("23.932736").longitude("-8.234325").build();
		Mockito.when(addressRepository.getOne(1L)).thenReturn(address);
		
		final Optional<AddressResponse> result = addressService.findById(1L);
		assertEquals(result.get().getStreetName(), address.getStreetName());
	}

}
