package se.rodion.e_shop_jpa.repository;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static java.util.function.Function.identity;

import se.rodion.e_shop_jpa.exceptions.RepositoryException;
import se.rodion.e_shop_jpa.model.Product;
import se.rodion.e_shop_jpa.model.status.ProductStatus;

public final class JPAProductRepository extends AbstractJPARepository<Product> implements ProductRepository
{
	public JPAProductRepository(EntityManagerFactory factory)
	{
		super(factory, Product.class);
	}

	@Override
	public Collection<Product> getAllProducts() throws RepositoryException
	{
		try
		{
			return query("Product.GetAll", identity());
		}
		catch (Exception e)
		{
			throw new RepositoryException("Could not fetch products");
		}
	}

	@Override
	public Collection<Product> searchProductsByProductName(String productName) throws RepositoryException
	{
		try
		{
			return query("Product.SearchProductsByProductName", query -> query.setParameter("productName", "%" + productName + "%"));
		}
		catch (Exception e)
		{
			throw new RepositoryException("Could not fetch product(s) by productName");
		}
	}

	

	@Override
	public Product changeStatus(Product product, ProductStatus status) throws RepositoryException
	{
		product.setProductStatus(status);
		EntityManager manager = factory.createEntityManager();
		try
		{
			manager.getTransaction().begin();
			product = manager.merge(product);
			manager.getTransaction().commit();

			return product;

		}
		catch (Exception e)
		{
			throw new RepositoryException("Could not change status of product");
		}
		finally
		{
			manager.close();
		}
	}
}
