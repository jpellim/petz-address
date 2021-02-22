package br.com.petz.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeoLocalizacaoDto implements Serializable {

	 
	private static final long serialVersionUID = 347815628707100516L;

	private String latitude;
	private String longitude;
	
}
