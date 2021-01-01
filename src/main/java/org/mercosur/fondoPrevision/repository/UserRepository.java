package org.mercosur.fondoPrevision.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import org.mercosur.fondoPrevision.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

	public Optional<User> findByusername(String username);
	
	public Optional<User> findByIdAndPassword(Long id, String password);

	public Optional<User> findByTarjeta(Integer tarjeta);
}
