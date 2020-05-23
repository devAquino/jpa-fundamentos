package br.com.estudos.jpa.service.interfaces;

import java.util.List;

public interface CrudService<T, K> {
	
	List<T> all();
	T byId(K id);
	T inserir(T entity);
	T update(T entity);
	void deletar(T entity);
	void deletarById(K id);
	

}
