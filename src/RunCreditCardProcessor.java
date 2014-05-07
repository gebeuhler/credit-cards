public class RunCreditCardProcessor
{
	public static void main(String[] args)
	{
		CreditCardProcessor ccProcessor = new CreditCardProcessor();

		if(args.length == 0)
			ccProcessor.runAsInterpreter();
		else
			ccProcessor.runOnFile(args[0]);
	}
}
