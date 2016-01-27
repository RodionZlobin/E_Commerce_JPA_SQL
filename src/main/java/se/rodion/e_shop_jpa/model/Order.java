package se.rodion.e_shop_jpa.model;

import java.util.Collection;
import java.util.Iterator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import se.rodion.e_shop_jpa.model.status.OrderStatus;

@Entity
@Table(name = "Orders")
@NamedQueries(value = {
		@NamedQuery(name = "OrdersGetAll", query = "SELECT e FROM Order e"),
		/*
		 * SELECT Orders.orderId, OrderRow.status FROM Orders JOIN OrderRow ON
		 * Orders.orderId = OrderRow.order_orderId WHERE Orders.orderId = 10 AND
		 * OrderRow.status = 'Active';
		 */
		// @NamedQuery(name = "Order.GetOrderById", query = "SELECT e.order FROM
		// OrderRow e "
		// + "JOIN e.order p "
		// + "WHERE p.orderId = :orderId AND e.status IS 'CONFIRMED'"),
		@NamedQuery(name = "Order.GetOrderById", query = "SELECT e FROM Order e WHERE e.id = :orderId"),
		@NamedQuery(name = "Order.GetOrdersByStatus", query = "SELECT e FROM Order e WHERE e.status = :status"),
		@NamedQuery(name = "Order.GetOrdersByUser", query = "SELECT e FROM Order e JOIN e.user u WHERE u.username = :username"),
		/*
		 * SELECT order_orderId, SUM(orderRawCost) AS OrderAmount FROM OrderRaw
		 * WHERE orderRawCost > 17000 GROUP BY OrderRaw.order_orderId ORDER BY
		 * OrderAmount;
		 */
		// @NamedQuery(name = "Orders.GetAllOrdersWithValueOverAmount", query =
		// "SELECT e.order"
		// + " FROM OrderRow e WHERE e.order IS NOT NULL AND e.status IS
		// 'CONFIRMED' GROUP BY e.order HAVING SUM(e.orderRowCost) > :amount")
		@NamedQuery(name = "Orders.GetAllOrdersWithValueOverAmount", query = "SELECT e"
				+ " FROM Order e WHERE e.ordersValue > :amount")
})
public class Order extends AbstractEntity
{
//	@Id
//	@GeneratedValue
//	private Long orderId;
	
	@Column (name = "OrderNr")
	private Integer orderNumber;
	
	@ManyToOne
	private User user;
	
	@Column
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, mappedBy = "order")
	private Collection<OrderRow> orderRows;

	@Enumerated(EnumType.STRING)
	@Column
	private OrderStatus status;

	@Column(name = "Amount")
	private double ordersValue;

	protected Order()
	{
	}

	public Order(int orderNumber, User user, Collection<OrderRow> orderRows, OrderStatus status)
	{
		this.orderNumber = orderNumber;
		this.user = user;
		this.orderRows = orderRows;
		this.status = status;
		this.ordersValue = getOrdersValue(orderRows);
	}

	public Order(Long orderId, int orderNumber, User user, Collection<OrderRow> orderRows, OrderStatus status)
	{
		this.setId(orderId);
		this.orderNumber = orderNumber;
		this.user = user;
		this.orderRows = orderRows;
		this.status = status;
		this.ordersValue = getOrdersValue(orderRows);
	}

	public Integer getOrderNumber()
	{
		return orderNumber;
	}
	
	public User getUser()
	{
		return user;
	}

	public Collection<OrderRow> getOrderRows()
	{
		return orderRows;
	}

	public OrderStatus getStatus()
	{
		return status;
	}

	public void setStatus(OrderStatus status)
	{
		this.status = status;
	}

	public void setOrdersValue(double ordersValue)
	{
		this.ordersValue = ordersValue;
	}

	public double getOrdersValue(Collection<OrderRow> orderRows)
	{
		double ordersValue = 0;
		OrderRow orderRowValue;
		Iterator<OrderRow> iterator = orderRows.iterator();

		while (iterator.hasNext())
		{
			orderRowValue = (OrderRow) iterator.next();
			ordersValue += orderRowValue.getOrderRowCost();
		}
		return ordersValue;

	}

	@Override
	public String toString()
	{
		return getId() + " " + getOrderRows() + " " + getUser() + " " + getStatus();
	}

	@Override
	public boolean equals(Object other)
	{
		if (other == this)
		{
			return true;
		}

		if (other instanceof Order)
		{
			Order otherOrder = (Order) other;

			if (this.user.equals(otherOrder.user)
					&& this.orderRows.containsAll(otherOrder.orderRows) 
					&& otherOrder.orderRows.containsAll(this.orderRows)
					&& this.status.equals(otherOrder.status))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public int hashCode()
	{
		int result = 1;
		result += 37 * this.user.hashCode();
		result += 37 * this.orderRows.hashCode();
		result += 37 * this.status.hashCode();

		return result;
	}
}