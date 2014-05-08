import static org.junit.Assert.assertEquals;

import org.junit.*;

//@RunWith(Junit4.class)
public class CreditCardProcessorTests
{

	private static final String SUMMARY_HEADER = "\nBegin Transaction Summary\n"
												  				+ "==========================\n";

	private static final String SUMMARY_FOOTER = "==========================\n"
					  											+ "End Transaction Summary\n";

	@BeforeClass
	public static void testSetup() 
	{
		
	}

	@AfterClass
	public static void testCleanup() 
	{
	    // Teardown for data used by the unit tests
	}

	@Test
	public void add()
	{
		CreditCardProcessor ccProcessor = new CreditCardProcessor();
		//Luhn Compliant Card
		ccProcessor.addCreditCard("Tom", "4012888888881881", 2000);
		assertEquals("Balance of new card equals 0", ccProcessor.getCreditCards().get("Tom").getBalance(), 0);
		//assertEquals("Adding Luhn Compliant Credit Card", ccProcessor.generateTransactionSummary(), SUMMARY_HEADER + "Tom: $0\n" + SUMMARY_FOOTER);
		//Non Luhn Compliant Card
		ccProcessor.addCreditCard("Tom", "1234123412341234", 2000);
		//assertEquals
	}

}
