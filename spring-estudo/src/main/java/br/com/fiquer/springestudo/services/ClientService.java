package br.com.fiquer.springestudo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiquer.springestudo.dto.ClientDTO;
import br.com.fiquer.springestudo.entities.Client;
import br.com.fiquer.springestudo.exceptions.ClientNotFoundException;
import br.com.fiquer.springestudo.repositories.ClientRepository;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		Page<Client> list = repository.findAll(pageRequest);
		return list.map(x -> new ClientDTO(x));
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> obs = repository.findById(id);
		Client client = obs.orElseThrow(() -> new ClientNotFoundException("Client not found"));
		return new ClientDTO(client);
	}

	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client entity = new Client();
		entity.setName(dto.getName());
		entity.setBirthDate(dto.getBirthDate());
		entity.setchildren(dto.getChildren());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity = repository.save(entity);
		return new ClientDTO(entity);
	}

}
