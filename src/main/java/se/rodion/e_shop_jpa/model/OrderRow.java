package se.rodion.e_shop_jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import se.rodion.e_shop_jpa.model.status.OrderRowStatus;

@Entity
@NamedQueries(value = {
		@NamedQuery(name = "OrderRows.GetAll", query = "SELECT e FROM OrderRow e"),
		@NamedQuery(name = "OrderRows.GetOrderRowsByOrder", query = "SELECT e FROM OrderRow e WHERE e.order = :order AND e.status IS 'CONFIRMED'")
})
public class OrderRow extends AbstractEntity
{
	@ManyToOne
	private Product product;

	@Column(name = "Quantity")
	private double orderedQuantity;
	
	@ManyToOne (fetch = FetchType.EAGER)
	private Order order;

	@Column(name = "OrderRaw_Price")
	private double orderRowCost;

	@Enumerated(EnumType.STRING)
	@Column
	private OrderRowStatus status;

	protected OrderRow()
	{
	}

	public OrderRow(Product product, double orderedQuantity)
	{
		this.product = product;
		this.orderedQuantity = orderedQuantity;
		this.orderRowCost = orderedQuantity * product.getProductPrice();
		this.status = OrderRowStatus.IN_PROGRESS;
	}

	public OrderRow(Long orderRowId, Order order, Product product, double orderedQuantity)
	{
		this.setId(orderRowId);
		this.order = order;
		this.product = product;
		this.orderedQuantity = orderedQuantity;
		this.orderRowCost = orderedQuantity * product.getProductPrice();
		this.status = OrderRowStatus.IN_PROGRESS;
	}

	public double getOrderedQuantity()
	{
		return orderedQuantity;
	}

	public Product getProduct()
	{
		return product;
	}

	public Order getOrder()
	{
		return order;
	}

	public void setOrder(Order order)
	{
		this.order = order;
	}

	public double getOrderRowCost()
	{
		return orderedQuantity * product.getProductPrice();
	}

	public void setStatus(OrderRowStatus status)
	{
		this.status = status;
	}

	public OrderRowStatus getStatus()
	{
		return status;
	}

	@Override
	public String toString()
	{
		return (order == null) ? product.getProductName() + " " + orderedQuantity + " " + getStatus() + " " + "Unconfirmed"
				: product.getProductName() + " " + orderedQuantity + " " + getStatus() + " " + order.getId();

	}

	@Override
	public boolean equals(Object other)
	{
		if (other == this)
		{
			return true;
		}

		if (other instanceof OrderRow)
		{
			OrderRow otherOrderRow = (OrderRow) other;

			if (this.product.equals(otherOrderRow.product)
					&& this.orderedQuantity == (otherOrderRow.orderedQuantity)
					&& this.status.equals(otherOrderRow.status))
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
		result += 37 * this.product.hashCode();
		result += 37 * this.orderedQuantity * 100;
		result += 37 * this.status.hashCode();

		return result;
	}
}
