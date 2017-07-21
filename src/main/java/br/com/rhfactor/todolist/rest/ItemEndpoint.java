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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import br.com.rhfactor.todolist.model.Item;
import br.com.rhfactor.todolist.services.IItemService;

/**
 * 
 */
@Stateless
@Path("/items")
public class ItemEndpoint {
	
	@Inject private IItemService em;

	@POST
	@Consumes("application/json")
	public Response create(Item item) {
		em.persist(item);
		return Response
				.created(UriBuilder.fromResource(ItemEndpoint.class).path(String.valueOf(item.getId())).build())
				.build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id) {
		Item entity = em.find(id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		em.remove(entity);
		return Response.noContent().build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Response findById(@PathParam("id") Long id) {
		Item entity = this.em.findById(id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(entity).build();
	}

	@GET
	@Produces("application/json")
	public List<Item> listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult) {
		return this.em.listAll(startPosition, maxResult);
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public Response update(@PathParam("id") Long id, Item item) {
		if (item == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		if (id == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
//		if (!id.equals(entity.getId())) {
//			return Response.status(Status.CONFLICT).entity(entity).build();
//		}
		
		Item itemDB = this.em.find(id);
		if (itemDB == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		// Autorizar somente esta mudança de comportamento
		itemDB.setDone( !itemDB.getDone() );
		
		try {
			em.merge(itemDB);
		} catch (OptimisticLockException e) {
			return Response.status(Response.Status.CONFLICT).entity(e.getEntity()).build();
		}

		return Response.noContent().build();
	}
}
