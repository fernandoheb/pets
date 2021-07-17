package com.ifsp.pets.pets;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;


public interface PetRepository extends CrudRepository<Pet, Integer> {
	
	 List<Pet> findByNome(String Nome);
	 
	 List<Pet> findByEspecie(String Especie);
	 
	 List<Pet> findByRaca(String Raca);	 
	 
	 Optional<Pet> findById(Integer Id);
	 
}
