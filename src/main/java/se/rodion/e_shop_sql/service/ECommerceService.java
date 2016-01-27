package se.rodion.e_shop_sql.service;

import java.util.Collection;
import java.util.List;

import org.hibernate.service.spi.ServiceException;

import se.rodion.e_shop_jpa.model.Order;
import se.rodion.e_shop_jpa.model.OrderRow;
import se.rodion.e_shop_jpa.model.Product;
import se.rodion.e_shop_jpa.model.User;
import se.rodion.e_shop_jpa.model.status.OrderStatus;
import se.rodion.e_shop_jpa.model.status.ProductStatus;
import se.rodion.e_shop_jpa.model.status.UserStatus;
import se.rodion.e_shop_jpa.repository.IDGenerator;
import se.rodion.e_shop_jpa.repository.OrderRepository;
import se.rodion.e_shop_jpa.repository.ProductRepository;
import se.rodion.e_shop_jpa.repository.UserRepository;

public class ECommerceService
{
	private UserRepository userRepository;
	private ProductRepository productRepository;
	private OrderRepository orderRepository;
	private IDGenerator orderIdGenerator;

	public ECommerceService(UserRepository userRepository,
			ProductRepository productRepository,
			OrderRepository orderRepository,
			IDGenerator orderIdGenerator)
	{
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.orderRepository = orderRepository;
		this.orderIdGenerator = orderIdGenerator;
	}

	public void saveOrUpdateUser(User user)
	{
		try
		{
			userRepository.saveOrUpdate(user);
		}
		catch (Exception e)
		{
			throw new ServiceException("Could not save user");
		}
	}

	public Collection<User> getAllUsers()
	{
		try
		{
			return userRepository.getAllUsers();
		}
		catch (Exception e)
		{
			throw new ServiceException("Could not fetch users");
		}
	}

	public User getUserByUserId(Long id)
	{
		try
		{
			return userRepository.findById(id);
		}
		catch (Exception e)
		{
			throw new ServiceException("Could not fetch user by ID");
		}
	}

	public User removeUser(User user)
	{
		try
		{
			return userRepository.remove(user);
		}
		catch (Exception e)
		{
			throw new ServiceException("Could not remove user");
		}
	}

	public User getUserByUsername(String username)
	{
		try
		{
			return userRepository.getUserByUsername(username);
		}
		catch (Exception e)
		{
			throw new ServiceException("Could not fetch user by username");
		}
	}

	public User updateUserStatus(User user, UserStatus status)
	{
		try
		{
			return userRepository.changeStatus(user, status);
		}
		catch (Exception e)
		{
			throw new ServiceException("Could not update users status");
		}
	}

	public void saveOrUpdateProduct(Product product)
	{
		try
		{
			productRepository.saveOrUpdate(product);
		}
		catch (Exception e)
		{
			throw new ServiceException("Could not save product");
		}
	}

	public Collection<Product> getAllProducts()
	{
		try
		{
			return productRepository.getAllProducts();
		}
		catch (Exception e)
		{
			throw new ServiceException("Could not fetch products");
		}
	}

	public Product getProductByProductId(Long productId)
	{
		try
		{
			return productRepository.findById(productId);
		}
		catch (Exception e)
		{
			throw new ServiceException("Could not fetch product by ID");
		}
	}

	public Collection<Product> searchProductByProductName(String productName)
	{
		try
		{
			return productRepository.searchProductsByProductName(productName);
		}
		catch (Exception e)
		{
			throw new ServiceException("Could not fetch product(s) by productName");
		}
	}

	public Product updateProductStatus(Product product, ProductStatus status)
	{
		try
		{
			return productRepository.changeStatus(product, status);
		}
		catch (Exception e)
		{
			throw new ServiceException("Could not update products status");
		}
	}

	public Order createOrder(User user, Collection<OrderRow> orderRows)
	{
		try
		{
			int newOrderNumber = orderIdGenerator.getNextOrderId();
			return orderRepository.createOrder(user, orderRows, newOrderNumber);
		}
		catch (Exception e)
		{
			throw new ServiceException("Could not create order", e);
		}
	}

	public Collection<Order> getAllOrders()
	{
		try
		{
			return orderRepository.getAllOrders();
		}
		catch (Exception e)
		{
			throw new ServiceException("Could not fetch all orders", e);
		}
	}

	public Order updateOrder(Order order)
	{
		if (order.getStatus().equals(OrderStatus.PLACED))
		{
			try

			{
				return orderRepository.updateOrder(order);
			}
			catch (Exception e)
			{
				throw new ServiceException("Could not update order", e);
			}
		}
		else
			throw new ServiceException("It is possible to update orders with 'PLACED' status only");
	}

	public Order getOrderById(Long orderId)
	{
		try
		{
			return orderRepository.getOrderById(orderId);
		}
		catch (Exception e)
		{
			throw new ServiceException("Could not get order with specified ID", e);
		}
	}

	public Collection<Order> getOrdersByUser(String username)
	{
		try
		{
			return orderRepository.getOrdersByUser(username);
		}
		catch (Exception e)
		{
			throw new ServiceException("Could fetch orders from this user", e);
		}
	}

	public Collection<Order> getOrdersByStatus(OrderStatus status)
	{
		try
		{
			return orderRepository.getOrdersByStatus(status);
		}
		catch (Exception e)
		{
			throw new ServiceException("Could fetch orders with this status", e);
		}
	}

	public Order updateOrderStatus(Order order, OrderStatus status)
	{
		try
		{
			return orderRepository.updateStatus(order, status);
		}
		catch (Exception e)
		{
			throw new ServiceException("Could not update order status", e);
		}
	}

	public List<Order> getOrderWithValueOverAmount(double amount)
	{
		try
		{
			return orderRepository.getOrdersWithValueOverAmount(amount);
		}
		catch (Exception e)
		{
			throw new ServiceException("Could not fetch orders with value over 10K", e);
		}
	}
}
