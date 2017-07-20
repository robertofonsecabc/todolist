package br.com.rhfactor.todolist.rest;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import br.com.rhfactor.todolist.model.Todo;
import br.com.rhfactor.todolist.services.ITodoService;

/**
 * 
 */
@Stateless
@Path("/todos")
public class TodoEndpoint {
	
	@Inject private ITodoService todoService;
	
	@POST
	@Consumes("application/json")
	public Response create(Todo todo) {
		this.todoService.persist(todo);
		return Response.created(UriBuilder.fromResource(TodoEndpoint.class).path(String.valueOf(todo.getId())).build()).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id) {
		Todo todo = this.todoService.find(id);
		if (todo == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		todo.setArchived(true);
		this.todoService.merge(todo);
		return Response.noContent().build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Response findById(@PathParam("id") Long id) {
		Todo todo = this.todoService.find(id);
		if (todo == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(todo).build();
	}

	@GET
	@Produces("application/json")
	public List<Todo> listAll() {
		return this.todoService.listAll();
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public Response update(@PathParam("id") Long id, Todo todo) {
		if (todo == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (id == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (!id.equals(todo.getId())) {
			return Response.status(Status.CONFLICT).entity(todo).build();
		}
		if (this.todoService.find(id) == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		try {
			this.todoService.merge(todo);
		} catch (OptimisticLockException e) {
			return Response.status(Response.Status.CONFLICT).entity(e.getEntity()).build();
		}

		return Response.noContent().build();
	}
}
