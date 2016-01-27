package se.rodion.e_shop_jpa.repository;

import java.util.Collection;

import se.rodion.e_shop_jpa.exceptions.RepositoryException;
import se.rodion.e_shop_jpa.model.Product;
import se.rodion.e_shop_jpa.model.status.ProductStatus;

public interface ProductRepository extends CrudRepository<Product>
{
	Collection<Product> getAllProducts() throws RepositoryException;

	Collection<Product> searchProductsByProductName(String productName) throws RepositoryException;

	Product changeStatus(Product product, ProductStatus status) throws RepositoryException;
}
