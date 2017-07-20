package br.com.rhfactor.todolist.services;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import br.com.rhfactor.todolist.dao.ITodoDao;
import br.com.rhfactor.todolist.model.Todo;

/**
 * Teste básico para funcionamento de rota entre serviço e dao
 * 
 * @author roberto
 *
 */
public class ITodoServiceTest {

	private ITodoService service;
	private ITodoDao dao;

	@Before
	public void init() {
		this.dao = mock(ITodoDao.class);
		this.service = new TodoService(dao);
	}

	/**
	 * Exemplo usando Mock de objetos para retornar algo
	 */
	@Test
	public void testList() {

		Todo todo1 = new Todo("nome1");
		Todo todo2 = new Todo("nome2");
		when(dao.listAll(null, null)).thenReturn(Arrays.asList(todo1, todo2));

		List<Todo> list = this.service.listAll();

		assertThat(list, hasItem(todo1));
		assertThat(list, hasSize(2));
	}

	/**
	 * Exemplo capturando dados que são passados para o teste
	 */
	@Test
	public void testInsert() {

		Todo todo = new Todo("Nome");
		this.service.persist(todo);

		ArgumentCaptor<Todo> argumentCaptorTodo = ArgumentCaptor.forClass(Todo.class);
		verify(dao).persist(argumentCaptorTodo.capture());
		Todo todoSalvo = argumentCaptorTodo.getValue();
		assertEquals(todo.getName(), todoSalvo.getName());
	}

}
