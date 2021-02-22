package br.com.petz.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Results implements Serializable {
  
	private static final long serialVersionUID = -1654916666605691986L;
	 
	private Geometry geometry;
	 
}
