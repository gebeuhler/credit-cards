//package com.george.creditcards;

public class RunCreditCardProcessor
{
	public static void main(String[] args)
	{
		CreditCardProcessor ccProcessor = new CreditCardProcessor();
		//can work through command line or read a file
		if(args.length == 0)
			ccProcessor.runOnCommands();
		else
			ccProcessor.runOnFile(args[0]);
	}
}
