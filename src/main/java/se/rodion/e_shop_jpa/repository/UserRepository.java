package se.rodion.e_shop_jpa.repository;

import java.util.Collection;

import se.rodion.e_shop_jpa.exceptions.RepositoryException;
import se.rodion.e_shop_jpa.model.User;
import se.rodion.e_shop_jpa.model.status.UserStatus;

public interface UserRepository extends CrudRepository<User>
{

	Collection<User> getAllUsers() throws RepositoryException;

	User getUserByUsername(String username) throws RepositoryException;

	User changeStatus(User user, UserStatus status) throws RepositoryException;
}
