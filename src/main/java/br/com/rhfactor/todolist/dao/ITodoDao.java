package br.com.rhfactor.todolist.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.rhfactor.todolist.model.Todo;

public interface ITodoDao {

	public abstract Todo find(Long id);

	public abstract Todo findById(Long id) throws NoResultException;

	public abstract void persist(Todo todo);

	public abstract void remove(Todo todo);

	public abstract void merge(Todo todo);

	public abstract List<Todo> listAll(Integer startPosition, Integer maxResult);

}
