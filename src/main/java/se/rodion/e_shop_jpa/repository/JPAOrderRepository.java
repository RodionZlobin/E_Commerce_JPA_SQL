package se.rodion.e_shop_jpa.repository;

import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import se.rodion.e_shop_jpa.exceptions.RepositoryException;
import se.rodion.e_shop_jpa.model.Order;
import se.rodion.e_shop_jpa.model.OrderRow;
import se.rodion.e_shop_jpa.model.User;
import se.rodion.e_shop_jpa.model.status.OrderRowStatus;
import se.rodion.e_shop_jpa.model.status.OrderStatus;

public class JPAOrderRepository implements OrderRepository
{
	private final EntityManagerFactory factory;

	public JPAOrderRepository(EntityManagerFactory factory)
	{
		this.factory = factory;
	}

	@Override
	public Order createOrder(User user, Collection<OrderRow> orderRows, int orderNumber) throws RepositoryException
	{
		EntityManager manager = factory.createEntityManager();
		try
		{
			manager.getTransaction().begin();
			Order order = new Order(orderNumber, user, orderRows, OrderStatus.PLACED);
			manager.persist(order);
			orderRows.forEach(e -> e.setStatus(OrderRowStatus.CONFIRMED));
			orderRows.forEach(e -> e.setOrder(order));
			orderRows.forEach(e -> manager.merge(e));
			manager.getTransaction().commit();

			return order;
		}
		catch (Exception e)
		{
			throw new RepositoryException("Could not create order", e);
		}
		finally
		{
			manager.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Order> getAllOrders() throws RepositoryException
	{
		EntityManager manager = factory.createEntityManager();
		try
		{
			return manager.createNamedQuery("OrdersGetAll").getResultList();

		}
		catch (Exception e)
		{
			throw new RepositoryException("Could not fetch all orders", e);
		}
		finally
		{
			manager.close();
		}
	}

	@Override
	public Order updateOrder(Order order) throws RepositoryException
	{
		EntityManager manager = factory.createEntityManager();
		
		try
			{				
				@SuppressWarnings("unchecked")
				Collection<OrderRow> orderRowsByOrder = manager.createNamedQuery("OrderRows.GetOrderRowsByOrder")
						.setParameter("order", order)
						.getResultList();
						
				manager.getTransaction().begin();
				orderRowsByOrder.forEach(e -> e.setStatus(OrderRowStatus.CANCELED));
				order.getOrderRows().forEach(e -> e.setStatus(OrderRowStatus.CONFIRMED));
				order.getOrderRows().forEach(e -> e.setOrder(order));
				manager.merge(order);

				manager.getTransaction().commit();

				return order;
			}
			catch (Exception e)
			{
				throw new RepositoryException("Could not update order", e);
			}
			finally
			{
				manager.close();
			}
	}

	@Override
	public Order getOrderById(Long orderId) throws RepositoryException
	{
		EntityManager manager = factory.createEntityManager();
		try
		{
			return (Order) manager.createNamedQuery("Order.GetOrderById")
					.setParameter("orderId", orderId)
					.getSingleResult();

		}
		catch (Exception e)
		{
			throw new RepositoryException("Could not fetch order with specified ID", e);
		}
		finally
		{
			manager.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Order> getOrdersByUser(String username) throws RepositoryException
	{
		EntityManager manager = factory.createEntityManager();
		try
		{
			return manager.createNamedQuery("Order.GetOrdersByUser")
					.setParameter("username", username)
					.getResultList();

		}
		catch (Exception e)
		{
			throw new RepositoryException("Could not fetch order with specified username", e);
		}
		finally
		{
			manager.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Order> getOrdersByStatus(OrderStatus status) throws RepositoryException
	{
		EntityManager manager = factory.createEntityManager();
		try
		{
			return manager.createNamedQuery("Order.GetOrdersByStatus")
					.setParameter("status", status)
					.getResultList();

		}
		catch (Exception e)
		{
			throw new RepositoryException("Could not fetch order with specified status", e);
		}
		finally
		{
			manager.close();
		}
	}

	@Override
	public Order updateStatus(Order order, OrderStatus status) throws RepositoryException
	{
		EntityManager manager = factory.createEntityManager();
		try
		{
			manager.getTransaction().begin();
			order = manager.merge(order);
			order.setStatus(status);
			manager.getTransaction().commit();
			return order;
		}
		catch (Exception e)
		{
			throw new RepositoryException("Could not update order status", e);
		}
		finally
		{
			manager.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getOrdersWithValueOverAmount(double amount) throws RepositoryException
	{
		EntityManager manager = factory.createEntityManager();
		try
		{
			return manager.createNamedQuery("Orders.GetAllOrdersWithValueOverAmount")
					.setParameter("amount", amount)
					.getResultList();

		}
		catch (Exception e)
		{
			throw new RepositoryException("Could not fetch orders with specified amount", e);
		}
		finally
		{
			manager.close();
		}
	}
}
