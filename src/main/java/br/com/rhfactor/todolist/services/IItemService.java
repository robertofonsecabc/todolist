package br.com.rhfactor.todolist.services;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.rhfactor.todolist.model.Item;

public interface IItemService {

	public abstract Item find(Long id);

	public abstract Item findById(Long id) throws NoResultException;

	public abstract void persist(Item todo);

	public abstract void remove(Item todo);

	public abstract void merge(Item todo);

	public abstract List<Item> listAll(Integer startPosition, Integer maxResult);

}
