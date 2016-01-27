package se.rodion.e_shop_jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import se.rodion.e_shop_jpa.model.status.UserStatus;

@Entity
@NamedQueries(value = {
		@NamedQuery(name = "User.GetAll", query = "SELECT e FROM User e"),
		@NamedQuery(name = "User.GetUserByUserId", query = "SELECT e FROM User e WHERE e.id = :userId"),
		@NamedQuery(name = "User.GetUserByUsername", query = "SELECT e FROM User e WHERE e.username = :username")
})
public class User extends AbstractEntity
{
	@Column(name = "username")
	private String username;

	@Column(name = "FirstName")
	private String userFirstName;

	@Column(name = "LastName")
	private String userLastName;

	@Enumerated(EnumType.STRING)
	@Column(name = "UserStatus")
	private UserStatus userStatus;

	protected User()
	{
	}

	public User(
			String username,
			String userFirstName,
			String userLastName,
			UserStatus userStatus)
	{
		this.username = username;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userStatus = userStatus;
	}

	public User(
			Long userId,
			String username,
			String userFirstName,
			String userLastName,
			UserStatus userStatus)
	{
		this.setId(userId);
		this.username = username;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userStatus = userStatus;
	}

	public String getUsername()
	{
		return username;
	}

	public String getUserFirstName()
	{
		return userFirstName;
	}

	public String getUserLastName()
	{
		return userLastName;
	}

	public UserStatus getUserStatus()
	{
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus)
	{
		this.userStatus = userStatus;
	}

	@Override
	public String toString()
	{
		return getId() + " " + getUserFirstName() + " " + getUserLastName();
	}

	@Override
	public boolean equals(Object other)
	{
		if (other == this)
		{
			return true;
		}

		if (other instanceof User)
		{
			User otherUser = (User) other;

			if (this.username.equals(otherUser.username)
					&& this.userFirstName.equals(otherUser.userFirstName)
					&& this.userLastName.equals(otherUser.userLastName))
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
		result += 37 * this.username.hashCode();
		result += 37 * this.userFirstName.hashCode();
		result += 37 * this.userLastName.hashCode();

		return result;
	}
}
