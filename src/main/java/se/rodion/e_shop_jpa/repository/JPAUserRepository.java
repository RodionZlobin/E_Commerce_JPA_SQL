package se.rodion.e_shop_jpa.repository;

import java.util.Collection;
import static java.util.function.Function.identity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import se.rodion.e_shop_jpa.exceptions.RepositoryException;
import se.rodion.e_shop_jpa.model.User;
import se.rodion.e_shop_jpa.model.status.UserStatus;

public final class JPAUserRepository extends AbstractJPARepository<User>implements UserRepository
{

	public JPAUserRepository(EntityManagerFactory factory)
	{
		super(factory, User.class);
	}

	@Override
	public Collection<User> getAllUsers() throws RepositoryException
	{
		try
		{
			return query("User.GetAll", identity());
		}
		catch (RuntimeException e)
		{
			throw new RepositoryException("could not fetch all users", e);
		}
	}

	@Override
	public User getUserByUsername(String username) throws RepositoryException
	{
		try
		{
			return querySingle("User.GetUserByUsername", query -> query.setParameter("username", username));
		}
		catch (RuntimeException e)
		{
			throw new RepositoryException("could not fetch user with this username", e);
		}
	}

	@Override
	public User changeStatus(User user, UserStatus status) throws RepositoryException
	{
		user.setUserStatus(status);
		EntityManager manager = factory.createEntityManager();
		try
		{
			manager.getTransaction().begin();
			user = manager.merge(user);
			manager.getTransaction().commit();

			return user;

		}
		catch (Exception e)
		{
			throw new RepositoryException("Could not change status of user");
		}
		finally
		{
			manager.close();
		}
	}
}
