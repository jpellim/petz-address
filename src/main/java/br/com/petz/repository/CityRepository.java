package br.com.petz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.petz.entity.City;

public interface CityRepository extends JpaRepository<City, Long> {
	
	@Query("SELECT city FROM City city"
			+" INNER JOIN FETCH city.state state"
			+" INNER JOIN FETCH state.country country" 
			+" WHERE city.id = :id" 
	)
	City findCompleteById(Long id);

}
