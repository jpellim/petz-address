package br.com.petz.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
 
	@NotBlank(message = "Nome do logradouro é obrigatório") 
	private String streetName;
 
	@NotNull
	private Integer number;
	
	private String complement;
	
	@NotBlank(message = "Nome do bairro é obrigatório") 
	private String neighbourhood;
	 
	@NotNull
	@ManyToOne
    @JoinColumn(name = "id_city", nullable = false)
    private City city;
	
	@NotBlank(message = "Cep é obrigatório") 
	private String zipCode;
	
	private String latitude;
	
	private String longitude;
}
