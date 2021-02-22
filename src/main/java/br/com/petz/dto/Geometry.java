package br.com.petz.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Geometry implements Serializable {
   
	private static final long serialVersionUID = 7778999761710481747L;
	
	private Location location; 
	
}
