package br.com.rhfactor.todolist.dao;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.rhfactor.todolist.model.Item;

public interface IItemDao {
	
	public abstract Item find(Long id);

	public abstract Item findById(Long id) throws NoResultException;

	public abstract void persist(Item item);

	public abstract void remove(Item item);

	public abstract void merge(Item item);

	public abstract List<Item> listAll(Integer startPosition, Integer maxResult);


}
