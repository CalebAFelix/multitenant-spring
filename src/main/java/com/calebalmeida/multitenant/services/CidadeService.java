package com.calebalmeida.multitenant.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calebalmeida.multitenant.entities.Cidade;
import com.calebalmeida.multitenant.repositories.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;

	public List<Cidade> findAll() {
		return cidadeRepository.findAll();
	}

	public Cidade insert(Cidade cidade) {
		cidade.setId(null);
		return cidadeRepository.save(cidade);
	}
	
}
