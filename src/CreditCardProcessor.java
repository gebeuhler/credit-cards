import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.lang.StringBuilder;
import java.io.File;
import java.io.FileNotFoundException;

public class CreditCardProcessor
{
	private class CreditCard
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

	private Map<String, CreditCard> creditCards;

	public CreditCardProcessor()
	{
		creditCards = new HashMap<String, CreditCard>();
	}

	public boolean addCreditCard(String name, String number, int limit)
	{
		if(luhnTest(number) 
			&& !creditCards.containsKey(name))
		{
			CreditCard newCard = new CreditCard(name, number, limit, true);
			creditCards.put(name, newCard);
			return true;
		}
		else
		{
			CreditCard newCard = new CreditCard(name, number, limit, false);
			creditCards.put(name, newCard);
			return false;
		}
	}

	public boolean chargeCard(String name, int amount)
	{
		if(creditCards.containsKey(name))
		{
			return creditCards.get(name).charge(amount);
		}
		return false;
	}

	public void creditCard(String name, int amount)
	{
		if(creditCards.containsKey(name))
		{
			creditCards.get(name).credit(amount);
		}
	}

	private boolean luhnTest(String number)
	{
        int oddSum = 0, evenSum = 0;
        String reverse = new StringBuffer(number).reverse().toString();
        for(int i = 0 ;i < reverse.length();i++){
            int digit = Character.digit(reverse.charAt(i), 10);
            if(i % 2 == 0)
			{
                oddSum += digit;
            }
			else
			{
                evenSum += 2 * digit;
                if(digit >= 5){
                    evenSum -= 9;
                }
            }
        }
        return (oddSum + evenSum) % 10 == 0;
    }

	public void runAsInterpreter()
	{
		System.out.println("Please Add, Credit, Charge or Quit:");
		Scanner scanner = new Scanner(System.in);
		
		String command = scanner.nextLine();
		String[] splitCommand;

		while(!command.equalsIgnoreCase("quit"))
		{
			splitCommand = command.split("\\s+");
			processCommand(splitCommand);
			System.out.println("Transaction processed! Please enter another or 'quit'");
			command = scanner.nextLine();
		}
		
		System.out.println(generateTransactionSummary());
	}

	public void runOnFile(String filename)
	{
		File file = new File(filename);
		Scanner scanner;

		try
		{
			scanner = new Scanner(file);
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e.getMessage());
			return;
		}

		System.out.println("\nTransactions are being processed");

		String command = scanner.nextLine();
		String[] splitCommand;

		while(scanner.hasNextLine())
		{
			splitCommand = command.split("\\s+");
			processCommand(splitCommand);
			command = scanner.nextLine();
		}

		System.out.println("\nTransaction processing has finished");
		System.out.println(generateTransactionSummary());
	}

	private void processCommand(String[] args)
	{
		if(args[0].equals("Add") 
			&& args[1].matches("[a-zA-Z]+") 
			&& args[2].matches("\\d{16,19}")
			&& args[3] != null)
		{
			addCreditCard(args[1], args[2], Integer.parseInt(args[3].substring(1)));
		}
		else if(args[0].equals("Credit") 
			&& args[1].matches("[a-zA-Z]+") 
			&& args[2] != null)
		{
			creditCard(args[1], Integer.parseInt(args[2].substring(1)));
		}
		else if(args[0].equals("Charge") 
			&& args[1].matches("[a-zA-Z]+") 
			&& args[2] != null)
		{
			chargeCard(args[1], Integer.parseInt(args[2].substring(1)));
		}
	}
	private String generateTransactionSummary()
	{
		StringBuilder transactionSummary = new StringBuilder("\nBegin Transaction Summary\n"
												  + "==========================\n");

		if(creditCards.isEmpty())
		{
			transactionSummary.append("No transactions processed\n");
		}
		else
		{
			for(Map.Entry<String, CreditCard> entry : creditCards.entrySet())
			{
				if(entry.getValue().getIsLuhnCompliant())
				{
					transactionSummary.append(entry.getKey() + ": $" + entry.getValue().getBalance() + "\n");
				}
				else
				{
					transactionSummary.append(entry.getKey() + ": error\n");
				}
			}
		}
		
		transactionSummary.append("==========================\n"
					  + "End Transaction Summary\n");

		return transactionSummary.toString();
	}
}
