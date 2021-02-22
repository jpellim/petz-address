package br.com.petz.tests;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import br.com.petz.PetzApplication;
import br.com.petz.entity.Address;
import br.com.petz.entity.City;
import br.com.petz.entity.Country;
import br.com.petz.entity.State;
import br.com.petz.request.AddressRequest;
import br.com.petz.response.AddressResponse;
import br.com.petz.service.AddressService;
import br.com.petz.service.CityService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = { PetzApplication.class })
public class TestCreateAddress {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@MockBean
	AddressService addressService;

	@MockBean
	CityService cityService;

	@Test
	public void testAddEmployee() throws Exception {

		final Country country = Country.builder().id(1L).code("BR").name("BRASIL").build();
		final State state = State.builder().id(1L).code("SP").name("SAO PAULO").country(country).build();
		final City city = City.builder().id(1L).name("Sao Paulo").state(state).build();
		Mockito.when(cityService.findCompleteById(1L)).thenReturn(city);

		final AddressRequest addressRequestStub = AddressRequest.builder().streetName("Rua nomeRua").number(123)
				.neighbourhood("bairro ABC").cityId(1L).zipCode("09867-309").latitude("23.932736")
				.longitude("-8.234325").build();

		final Address addressStub = Address.builder().streetName("Rua nomeRua").number(123).neighbourhood("bairro ABC")
				.city(city).zipCode("09867-309").latitude("23.932736").longitude("-8.234325").build();
		final AddressResponse addressResponse = new AddressResponse(addressStub);

		when(addressService.create(addressRequestStub)).thenReturn(Optional.of(addressResponse));

		Gson GSON = new Gson();

		mockMvc.perform(MockMvcRequestBuilders.post("/petz/v1/address")
				.contentType(MediaType.APPLICATION_JSON)
				.content(GSON.toJson(addressRequestStub))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.number").value("123"))
				.andExpect(jsonPath("$.streetName").value("Rua nomeRua"));

		verify(addressService).create(addressRequestStub);

	}
}
