package br.com.fiquer.springestudo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiquer.springestudo.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
	
}
