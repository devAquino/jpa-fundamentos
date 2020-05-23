package br.com.estudos.jpa.service.interfaces;

import java.util.List;

import br.com.estudos.jpa.model.Pessoa;

public interface PessoaBuscaPorNome extends CrudService<Pessoa, Integer>{
	
	List<Pessoa> pessoaByName(String nome);
}
