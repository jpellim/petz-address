package br.com.petz.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeoLocalizacaoResponseDto implements Serializable {
 
	private static final long serialVersionUID = 3716360774760405814L;
	 
	private Results[] results; 
	
}
