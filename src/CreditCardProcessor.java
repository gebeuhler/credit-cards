import java.util.Map;
import java.util.TreeMap;
import java.util.Scanner;
import java.lang.StringBuilder;
import java.io.File;
import java.io.FileNotFoundException;

public class CreditCardProcessor
{
	private Map<String, CreditCard> creditCards;

	public CreditCardProcessor()
	{
		creditCards = new TreeMap<String, CreditCard>();
	}

	public void addCreditCard(String name, String number, int limit)
	{
		if(luhnTest(number) 
			&& !creditCards.containsKey(name))
		{
			CreditCard newCard = new CreditCard(name, number, limit, true);
			creditCards.put(name, newCard);
		}
		else
		{
			CreditCard newCard = new CreditCard(name, number, limit, false);
			creditCards.put(name, newCard);
		}
	}

	public void chargeCard(String name, int amount)
	{
		if(creditCards.containsKey(name) && creditCards.get(name).getIsLuhnCompliant())
		{
			creditCards.get(name).charge(amount);
		}
	}

	public void creditCard(String name, int amount)
	{
		if(creditCards.containsKey(name) && creditCards.get(name).getIsLuhnCompliant())
		{
			creditCards.get(name).credit(amount);
		}
	}

	private boolean luhnTest(String number)
	{
		int oddSum = 0, evenSum = 0;
      String reverse = new StringBuffer(number).reverse().toString();
      for(int i = 0 ;i < reverse.length();i++)
		{
			int digit = Character.digit(reverse.charAt(i), 10);
         if(i % 2 == 0)
			{
				oddSum += digit;
            }
				else
				{
                evenSum += 2 * digit;
                if(digit >= 5)
                    evenSum -= 9;
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
		
		System.out.println(getTransactionSummary());
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
		System.out.println(getTransactionSummary());
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

	public String getTransactionSummary()
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

	public Map<String, CreditCard> getCreditCards()
	{
		return creditCards;
	}
}
