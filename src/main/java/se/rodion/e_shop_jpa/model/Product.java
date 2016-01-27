package se.rodion.e_shop_jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import se.rodion.e_shop_jpa.model.status.ProductStatus;

@Entity
@NamedQueries(value = {
		@NamedQuery(name = "Product.GetAll", query = "SELECT e FROM Product e"),
		@NamedQuery(name = "Product.GetProductByProductId", query = "SELECT e FROM Product e WHERE e.id = :productId"),
		@NamedQuery(name = "Product.SearchProductsByProductName", query = "SELECT e FROM Product e WHERE e.productName LIKE :productName "
				+ "ORDER BY e.productName ASC"),
		@NamedQuery(name = "Product.GetProductByProductName", query = "SELECT e FROM Product e WHERE e.productName = :productName")
})
public class Product extends AbstractEntity
{
	@Column(name = "ProductName")
	private String productName;

	@Column(name = "ProductPrice")
	private double productPrice;

	@Enumerated(EnumType.STRING)
	@Column(name = "ProductStatus")
	private ProductStatus productStatus;

	protected Product()
	{
	}

	public Product(
			String productName,
			double productPrice,
			ProductStatus productStatus)
	{
		this.productName = productName;
		this.productPrice = productPrice;
		this.productStatus = productStatus;
	}

	public Product(
			Long productId,
			String productName,
			double productPrice,
			ProductStatus productStatus)
	{
		this.setId(productId);
		this.productName = productName;
		this.productPrice = productPrice;
		this.productStatus = productStatus;
	}

	public String getProductName()
	{
		return productName;
	}

	public double getProductPrice()
	{
		return productPrice;
	}

	public ProductStatus getProductStatus()
	{
		return productStatus;
	}

	public void setProductStatus(ProductStatus productStatus)
	{
		this.productStatus = productStatus;
	}

	public String toString()
	{
		return getId() + " " + getProductName() + " "
				+ getProductPrice() + " " + getProductStatus();
	}

	@Override
	public boolean equals(Object other)
	{
		if (other == this)
		{
			return true;
		}

		if (other instanceof Product)
		{
			Product otherProduct = (Product) other;

			if (this.getProductName().equals(otherProduct.getProductName())
					&& this.getProductPrice() == otherProduct.getProductPrice()
					&& this.getProductStatus().equals(otherProduct.getProductStatus()))
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
		result += 37 * this.productName.hashCode();
		result += 37 * this.productPrice * 100;
		result += 37 * this.productStatus.hashCode();

		return result;
	}
}
