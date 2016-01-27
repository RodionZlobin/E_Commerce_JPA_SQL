package se.rodion.e_shop_jpa.exceptions;

public final class ModelException extends Exception
{	
	private static final long serialVersionUID = 1188789358022827921L;

	public ModelException(String message)
	{
		super(message);
	}
	
	public ModelException (String message, Throwable cause)
	{
		super(message, cause);
	}

}
