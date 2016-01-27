package se.rodion.e_shop_jpa.repository;

import java.util.Collection;
import java.util.List;

import se.rodion.e_shop_jpa.exceptions.RepositoryException;
import se.rodion.e_shop_jpa.model.Order;
import se.rodion.e_shop_jpa.model.OrderRow;
import se.rodion.e_shop_jpa.model.User;
import se.rodion.e_shop_jpa.model.status.OrderStatus;

public interface OrderRepository 
{
	public Collection<Order> getAllOrders() throws RepositoryException;

	public Order updateOrder(Order order) throws RepositoryException;

	public Order getOrderById(Long OrderId) throws RepositoryException;

	public Collection<Order> getOrdersByUser(String username) throws RepositoryException;

	public Collection<Order> getOrdersByStatus(OrderStatus status) throws RepositoryException;

	Order createOrder(User user, Collection<OrderRow> orderRows, int orderNumber) throws RepositoryException;

	List<Order> getOrdersWithValueOverAmount(double amount) throws RepositoryException;

	Order updateStatus(Order order, OrderStatus status) throws RepositoryException;
}
