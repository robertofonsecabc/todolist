package br.com.rhfactor.todolist.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.rhfactor.todolist.model.Todo;

public class TodoDao implements ITodoDao {
	
	@PersistenceContext(unitName = "todolist-persistence-unit")
	private final EntityManager em;

	@Deprecated
	public TodoDao() {
		this(null);
	}
	
	public TodoDao(EntityManager em) {
		super();
		this.em = em;
	}

	@Override
	public void persist(Todo todo) {
		this.em.persist(todo);
	}

	@Override
	public Todo find(Long id){
		return this.em.find(Todo.class, id);
	}
	
	@Override
	public Todo findById(Long id) throws NoResultException{
		TypedQuery<Todo> findByIdQuery = this.em.createQuery("SELECT DISTINCT t FROM Todo t LEFT JOIN FETCH t.items WHERE t.id = :todoId ORDER BY t.id",Todo.class);
		findByIdQuery.setParameter("todoId", id);
		return findByIdQuery.getSingleResult();
	}

	@Override
	public void remove(Todo todo) {
		this.em.remove(todo);
	}

	@Override
	public void merge(Todo todo){
		this.em.merge(todo);
	}

	@Override
	public List<Todo> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Todo> findAllQuery = this.em.createQuery("SELECT DISTINCT t FROM Todo t LEFT JOIN FETCH t.items WHERE t.archived = false ORDER BY t.id", Todo.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		
		return findAllQuery.getResultList();
	}
	
	
}
