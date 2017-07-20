package br.com.rhfactor.todolist.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.rhfactor.todolist.model.Item;

public class ItemDao implements IItemDao {

	@PersistenceContext(unitName = "todolist-persistence-unit")
	private EntityManager em;

	@Override
	public void persist(Item item) {
		this.em.persist(item);
	}

	@Override
	public Item find(Long id) {
		return this.em.find(Item.class, id);
	}

	@Override
	public Item findById(Long id) throws NoResultException {
		TypedQuery<Item> findByIdQuery = this.em.createQuery("SELECT DISTINCT t FROM Item t WHERE t.id = :entityId ORDER BY t.id", Item.class);
		findByIdQuery.setParameter("entityId", id);
		return findByIdQuery.getSingleResult();
	}

	@Override
	public void remove(Item item) {
		this.em.remove(item);
	}

	@Override
	public void merge(Item item) {
		this.em.merge(item);
	}

	@Override
	public List<Item> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Item> findAllQuery = this.em.createQuery("SELECT DISTINCT t FROM Item t ORDER BY t.id", Item.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}

}
