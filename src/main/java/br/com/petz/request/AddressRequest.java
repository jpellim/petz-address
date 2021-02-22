package br.com.petz.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class AddressRequest {
   
	private String streetName;
	  
	private Integer number;
	
	private String complement;
	
	private String neighbourhood;
 
	private Long cityId;
	
	private String zipCode;
	
	private String latitude;
	
	private String longitude;
}
