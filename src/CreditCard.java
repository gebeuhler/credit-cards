import java.util.Map;

public class CreditCard
{
	private String name;
	private String number;	
	private int limit;
	private int balance;
	private boolean isLuhnCompliant;

	public CreditCard(String name, String number, int limit, boolean isLuhnCompliant)
	{
		this.name = name;
		this.number = number;
		this.limit = limit;
		balance = 0;
		this.isLuhnCompliant = isLuhnCompliant;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public int getLimit()
	{
		return limit;
	}

	public void setLimit(int limit)
	{
		this.limit = limit;
	}

	public int getBalance()
	{
		return balance;
	}

	public void setBalance(int balance)
	{
		this.balance = balance;
	}
	
	public boolean getIsLuhnCompliant()
	{
		return isLuhnCompliant;
	}

	public void setIsLuhnCompliant(boolean isLuhnCompliant)
	{
		this.isLuhnCompliant = isLuhnCompliant;
	}

	public boolean charge(int amount)
	{
		if(balance + amount <= limit)
		{
			balance += amount;
			return true;
		}
		else
		{
			return false;
		}
	}

	public void credit(int amount)
	{
		balance -= amount;
	}
}
