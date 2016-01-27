package se.rodion.e_shop_jpa.exceptions;

public final class RepositoryException extends Exception
{
	private static final long serialVersionUID = 609521475288739858L;
	
	public RepositoryException (String message)
	{
		super(message);
	}
	
	public RepositoryException (String message, Throwable cause)
	{
		super(message, cause);
	}

}
