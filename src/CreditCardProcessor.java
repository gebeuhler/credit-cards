//package com.george.creditcards;

import java.util.*;
import java.lang.StringBuilder;
//import java.math.BigInteger;

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
	private String output;

	public CreditCardProcessor()
	{
		creditCards = new HashMap<String, CreditCard>();
		output = "Transaction Summary\n" +
					"===================\n";
	}

	private boolean addCreditCard(String name, String number, int limit)
	{
		if(//String.valueOf(number).length() < 20 && 
			luhnTest(number) 
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

	private boolean chargeCard(String name, int amount)
	{
		if(creditCards.containsKey(name))
		{
			return creditCards.get(name).charge(amount);
		}
		return false;
	}

	private void creditCard(String name, int amount)
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
			{//this is for odd digits, they are 1-indexed in the algorithm
                oddSum += digit;
            }
			else
			{//add 2 * digit if 0-4, add 2 * digit - 9 if 5-9
                evenSum += 2 * digit;
                if(digit >= 5){
                    evenSum -= 9;
                }
            }
        }
        return (oddSum + evenSum) % 10 == 0;
    }

	public void runOnCommands()
	{
		//can process input through command line args or text file

		System.out.println("Please Add, Credit, Charge or Quit:");
		Scanner scanner = new Scanner(System.in);
		
		String command = scanner.nextLine();
		String[] splitCommand;

		while(!command.equalsIgnoreCase("quit"))
		{
			splitCommand = command.split("\\s+");
			processCommand(splitCommand);
			command = scanner.nextLine();
		}
		
		System.out.println(generateOutput());
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
		//if(args
	}
	private String generateOutput()
	{
		StringBuilder output = new StringBuilder("Transaction Summary\n" +
															  "==========================\n");

		for(Map.Entry<String, CreditCard> entry : creditCards.entrySet())
		{
			if(entry.getValue().getIsLuhnCompliant())
			{
				output.append(entry.getKey() + ": $" + entry.getValue().getBalance() + "\n");
			}
			else
			{
				output.append(entry.getKey() + ": error\n");
			}
		}
		
		output.append("==========================\n" +
						  "End of Transaction Summary\n");

		return output.toString();
	}
/*
evenSum = 6 + 6 + 2 + 4 + -7 +  + 12 + 8 = 56 - (4*9) = 56-36 = 20
evenSum = 6 + 6 + 2 + 4 + 2 + 7 + 3 + 8 = 38
digit = 0 1 2 3 4 5 6 7 8

4266 8413 2316 3335

53 33 61 32 31 48 66 24

odd
5 + 3 + 6 + 3 + 3 + 4 + 6 + 2 = 32

even
3,3,1,2,1,8,6,4
*2
6,6,2,4,2,16,12,8

6+6+2+4+2+7+3+8 = 38

32 + 38 = 70

odd sum += 5

even sum += 2*3
*/
}
