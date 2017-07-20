package br.com.rhfactor.todolist.services;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import br.com.rhfactor.todolist.dao.ITodoDao;
import br.com.rhfactor.todolist.model.Item;
import br.com.rhfactor.todolist.model.Todo;

public class TodoService implements ITodoService {

	private final ITodoDao dao;
	
	@Deprecated
	public TodoService() {
		this(null);
	}

	@Inject
	public TodoService(ITodoDao dao) {
		super();
		this.dao = dao;
	}

	@Override
	public void persist(Todo todo) {
		if( todo.getItems().size() > 0 ){
			// Forçar os itens a terem o mesmo Todo
			for( Item item : todo.getItems() ){
				item.setTodo(todo);
			}
		}
		this.dao.persist(todo);
	}

	@Override
	public Todo find(Long id) {
		return this.dao.find(id);
	}

	@Override
	public Todo findById(Long id) throws NoResultException {
		return this.dao.findById(id);
	}

	@Override
	public void remove(Todo todo) {
		this.dao.remove(todo);
	}

	@Override
	public void merge(Todo todo) {
		this.dao.merge(todo);
	}
	
	@Override
	public List<Todo> listAll() {
		return this.dao.listAll(null, null);
	}

	@Override
	public List<Todo> listAll(Integer startPosition, Integer maxResult) {
		return this.dao.listAll(startPosition, maxResult);
	}

	

}
