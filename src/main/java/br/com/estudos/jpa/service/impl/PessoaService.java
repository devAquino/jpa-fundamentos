package br.com.estudos.jpa.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;

import br.com.estudos.jpa.model.Pessoa;
import br.com.estudos.jpa.service.interfaces.PessoaBuscaPorNome;
import br.com.estudos.jpa.util.JpaUtils;

public class PessoaService implements PessoaBuscaPorNome {

	@Override
	public List<Pessoa> all() {
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		EntityManager entityManager = null;
		try {
			entityManager = JpaUtils.getEntityManager();
			pessoas = entityManager.createQuery("from Pessoa", Pessoa.class).getResultList();
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
		return pessoas;
	}

	@Override
	public Pessoa byId(Integer id) {
		EntityManager entityManager = null;
		try {
			entityManager = JpaUtils.getEntityManager();
			return entityManager.find(Pessoa.class, id);

		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	@Override
	public Pessoa inserir(Pessoa entity) {
		EntityManager entityManager = null;
		try {
			entityManager = JpaUtils.getEntityManager();
			entityManager.getTransaction().begin();
			entityManager.persist(entity);
			entityManager.getTransaction().commit();
			return entity;
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	@Override
	public Pessoa update(Pessoa entity) {
		EntityManager entityManager = null;
		try {
			entityManager = JpaUtils.getEntityManager();
			entityManager.getTransaction().begin();
			// entityManager.merge(entity); método da JPA
			entityManager.unwrap(Session.class).update(entity);// método do hibernate(Só funciona com o provide
																// Hibernate)
			entityManager.getTransaction().commit();
			return entity;
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}

	}

	@Override
	public void deletar(Pessoa entity) {
		EntityManager entityManager = null;
		try {
			entityManager = JpaUtils.getEntityManager();
			entityManager.getTransaction().begin();
			entityManager.remove(entity);
			entityManager.getTransaction().commit();
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}

	}

	@Override
	public void deletarById(Integer id) {
		EntityManager entityManager = null;
		try {
			entityManager = JpaUtils.getEntityManager();
			Pessoa pessoaDeletar = entityManager.find(Pessoa.class, id);
			if (pessoaDeletar != null) {
				entityManager.getTransaction().begin();
				entityManager.remove(pessoaDeletar);
				entityManager.getTransaction().commit();
			}

		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}

	}

	@Override
	public List<Pessoa> pessoaByName(String nome) {
		EntityManager entityManager = null;
		try {
			entityManager = JpaUtils.getEntityManager();
			List<Pessoa> pessoas = entityManager
					.createQuery("from Pessoa p where lower(p.nome) like lower(concat('%', :nome, '%'))", Pessoa.class)
					.setParameter("nome", nome).getResultList();
			return pessoas;
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
		
	}

}
