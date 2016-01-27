package se.rodion.e_shop_jpa.repository;

import se.rodion.e_shop_jpa.exceptions.RepositoryException;
import se.rodion.e_shop_jpa.model.AbstractEntity;

public interface CrudRepository <E extends AbstractEntity>
{
	E saveOrUpdate(E entity) throws RepositoryException;
	E findById(Long id) throws RepositoryException;
	E remove(E entity) throws RepositoryException;
}
