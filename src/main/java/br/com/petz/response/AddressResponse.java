package br.com.petz.response;

import br.com.petz.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponse {


	public AddressResponse(Address address) {

		this.streetName = address.getStreetName();
		this.number = address.getNumber();
		this.complement = address.getComplement();
		this.neighbourhood = address.getNeighbourhood();
		this.cityId = address.getCity().getId();
		this.zipCode = address.getZipCode();
		this.latitude = address.getLatitude();
		this.longitude = address.getLongitude();
	}
 
	private String streetName;
 
	private Integer number;
	
	private String complement;
	 
	private String neighbourhood;
	  
    private Long cityId;
	 
	private String zipCode;
	
	private String latitude;
	
	private String longitude;
}
