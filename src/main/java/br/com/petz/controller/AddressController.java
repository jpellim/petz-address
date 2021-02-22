package br.com.petz.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.petz.request.AddressRequest;
import br.com.petz.response.AddressResponse;
import br.com.petz.service.AddressService;

@RestController
@RequestMapping(value = "/petz/v1/address")
public class AddressController {

	@Autowired
	private AddressService addressService;
 
	
	@PostMapping 
	public ResponseEntity<AddressResponse> createAddress(@Valid @RequestBody AddressRequest AddressRequest) {
		Optional<AddressResponse> response = addressService.create(AddressRequest);
		return (response.isPresent()) ? new ResponseEntity<>(response.get(), HttpStatus.CREATED)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/{id}")
	public ResponseEntity<AddressResponse> updateAddress(@PathVariable Long id, @RequestBody @Valid AddressRequest AddressRequest) {
		Optional<AddressResponse> response = addressService.update(id, AddressRequest);
		return (response.isPresent()) ? new ResponseEntity<>(response.get(), HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{id}")
	public void deleteAddress(@PathVariable Long id) {
		addressService.remove(id);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AddressResponse> findAddressById(@NotNull @PathVariable Long id) {
		Optional<AddressResponse> response = addressService.findById(id);
		return (response.isPresent()) ? new ResponseEntity<>(response.get(), HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
 
	@GetMapping 
	public ResponseEntity<List<AddressResponse>> findAllAddress(@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "3") int size) {
		
	    Pageable paging = PageRequest.of(page, size);
	      
		Page<AddressResponse> response = addressService.findAll(paging);
		
		return (response != null  ? new ResponseEntity<>(response.getContent(), HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
}
