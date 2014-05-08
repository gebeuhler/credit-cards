import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

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
	public void addZeroBalance()
	{
		CreditCardProcessor ccProcessor = new CreditCardProcessor();
		ccProcessor.addCreditCard("Tom", "4012888888881881", 2000);
		assertEquals("Balance of new card equals 0", ccProcessor.getCreditCards().get("Tom").getBalance(), 0);
	}

	@Test
	public void addLuhnCreditCard()
	{
		CreditCardProcessor ccProcessor = new CreditCardProcessor();
		ccProcessor.addCreditCard("Tom", "4012888888881881", 2000);
		assertTrue(ccProcessor.getCreditCards().get("Tom").getIsLuhnCompliant());
	}

	@Test
	public void addNonLuhnCreditCard()
	{
		CreditCardProcessor ccProcessor = new CreditCardProcessor();
		ccProcessor.addCreditCard("Tom", "1234123412341234", 2000);
		assertFalse(ccProcessor.getCreditCards().get("Tom").getIsLuhnCompliant());
	}

}
