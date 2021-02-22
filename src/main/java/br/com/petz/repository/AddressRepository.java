package br.com.petz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.petz.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
