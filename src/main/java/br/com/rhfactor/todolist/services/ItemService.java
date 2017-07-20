package br.com.rhfactor.todolist.services;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import br.com.rhfactor.todolist.dao.IItemDao;
import br.com.rhfactor.todolist.model.Item;

public class ItemService implements IItemService {

	@Inject
	private IItemDao dao;

	@Override
	public void persist(Item item) {
		this.dao.persist(item);
	}

	@Override
	public Item find(Long id) {
		return this.dao.find(id);
	}

	@Override
	public Item findById(Long id) throws NoResultException {
		return this.dao.findById(id);
	}

	@Override
	public void remove(Item item) {
		this.dao.remove(item);
	}

	@Override
	public void merge(Item item) {
		this.dao.merge(item);
	}

	@Override
	public List<Item> listAll(Integer startPosition, Integer maxResult) {
		return this.dao.listAll(startPosition, maxResult);
	}

}
