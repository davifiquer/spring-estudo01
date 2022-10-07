package br.com.fiquer.springestudo.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiquer.springestudo.dto.ClientDTO;
import br.com.fiquer.springestudo.entities.Client;
import br.com.fiquer.springestudo.exceptions.ClientNotFoundException;
import br.com.fiquer.springestudo.exceptions.DataBaseException;
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

	@Transactional
	public ClientDTO update(ClientDTO dto, Long id) {
		try {
			Client entity = repository.getReferenceById(id);
			entity.setName(dto.getName());
			entity.setBirthDate(dto.getBirthDate());
			entity.setchildren(dto.getChildren());
			entity.setCpf(dto.getCpf());
			entity.setIncome(dto.getIncome());
			
			return new ClientDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ClientNotFoundException("Client not found: " + id);
		}
		
	}

	@Transactional
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ClientNotFoundException("ID not found " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity violation");
		}
	}

}
