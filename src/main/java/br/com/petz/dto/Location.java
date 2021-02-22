package br.com.petz.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location implements Serializable {
    
	private static final long serialVersionUID = 5639171394287823669L;
	
	private String lat;
	private String lng;
	
}
