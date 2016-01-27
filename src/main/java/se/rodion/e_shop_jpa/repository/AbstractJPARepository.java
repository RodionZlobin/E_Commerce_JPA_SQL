package se.rodion.e_shop_jpa.repository;

import java.util.List;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import se.rodion.e_shop_jpa.exceptions.RepositoryException;
import se.rodion.e_shop_jpa.model.AbstractEntity;

public abstract class AbstractJPARepository<E extends AbstractEntity> implements CrudRepository<E>
{
	protected final EntityManagerFactory factory;
	private final Class<E> entityClass;

	protected AbstractJPARepository(EntityManagerFactory factory, Class<E> entityClass)
	{
		this.factory = factory;
		this.entityClass = entityClass;
	}

	protected E querySingle(String queryName, Function<TypedQuery<E>, TypedQuery<E>> query)
	{
		return query(queryName, query).get(0);
	}

	protected List<E> query(String queryName, Function<TypedQuery<E>, TypedQuery<E>> query)
	{
		EntityManager manager = factory.createEntityManager();
		try
		{
			TypedQuery<E> typedQuery = manager.createNamedQuery(queryName, entityClass);
			return query.apply(typedQuery).getResultList();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			manager.close();
		}
	}

	protected E execute(Function<EntityManager, E> operation)
	{
		EntityManager manager = factory.createEntityManager();
		try
		{
			manager.getTransaction().begin();
			E result = operation.apply(manager);
			manager.getTransaction().commit();

			return result;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			manager.close();
		}
	}

//	protected void executeVoid(Consumer<EntityManager> operation)
//	{
//		EntityManager manager = factory.createEntityManager();
//		try
//		{
//			manager.getTransaction().begin();
//			operation.accept(manager);
//			manager.getTransaction().commit();
//		}
//		catch (Exception e)
//		{
//			throw new RuntimeException(e);
//		}
//		finally
//		{
//			manager.close();
//		}
//	}

	@Override
	public E saveOrUpdate(E entity) throws RepositoryException
	{
		try
		{
			return entity.getId() == null ? execute(manager -> {
			manager.persist(entity);
			return entity;
		}) : execute(manager -> manager.merge(entity));
		}
		catch (RuntimeException e)
		{
			throw new RepositoryException("could not upSert entity", e);
		}
	}

	@Override
	public E remove(E entity) throws RepositoryException
	{
		try
		{
			return execute(manager -> {
			E entityToRemove = manager.find(entityClass, entity.getId());
			manager.remove(entityToRemove);
			return entityToRemove;
		});
		}
		catch (RuntimeException e)
		{
			throw new RepositoryException("could not remove entity");
		}
	}

	@Override
	public E findById(Long id)
	{
		EntityManager manager = factory.createEntityManager();
		try
		{
			return manager.find(entityClass, id);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			manager.close();
		}
	}
}
