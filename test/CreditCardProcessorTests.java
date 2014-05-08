import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.*;

public class CreditCardProcessorTests
{

	private static final String SUMMARY_HEADER = "\nBegin Transaction Summary\n"
												  				+ "==========================\n";

	private static final String TEST_DATA = "Lisa: $-93\n"
														 + "Quincy: error\n"
														 + "Tom: $500\n";

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
	public void addCreditCardHasZeroBalance()
	{
		CreditCardProcessor ccProcessor = new CreditCardProcessor();
		ccProcessor.addCreditCard("Tom", "4012888888881881", 2000);
		assertEquals("Balance of new card equals 0", 0, ccProcessor.getCreditCards().get("Tom").getBalance());
	}

	@Test
	public void addLuhnCompliantCreditCardSucceeds()
	{
		CreditCardProcessor ccProcessor = new CreditCardProcessor();
		ccProcessor.addCreditCard("Tom", "4012888888881881", 2000);
		assertTrue(ccProcessor.getCreditCards().get("Tom").getIsLuhnCompliant());
	}

	@Test
	public void addNonLuhnCompliantCreditCardFails()
	{
		CreditCardProcessor ccProcessor = new CreditCardProcessor();
		ccProcessor.addCreditCard("Tom", "1234123412341234", 2000);
		assertFalse(ccProcessor.getCreditCards().get("Tom").getIsLuhnCompliant());
	}

	@Test
	public void chargeCardWithPositiveBalance()
	{
		CreditCardProcessor ccProcessor = new CreditCardProcessor();
		ccProcessor.addCreditCard("Tom", "4012888888881881", 2000);
		ccProcessor.chargeCard("Tom", 500);
		assertEquals("Card has balance of 500", 500, ccProcessor.getCreditCards().get("Tom").getBalance());
	}

	@Test
	public void chargeCardOverLimit()
	{
		CreditCardProcessor ccProcessor = new CreditCardProcessor();
		ccProcessor.addCreditCard("Tom", "4012888888881881", 2000);
		ccProcessor.chargeCard("Tom", 2500);
		assertEquals("Card has balance of 0", 0, ccProcessor.getCreditCards().get("Tom").getBalance());
	}

	@Test
	public void chargeNonLuhnCompliantCard()
	{
		CreditCardProcessor ccProcessor = new CreditCardProcessor();
		ccProcessor.addCreditCard("Tom", "1111111111111111", 2000);
		ccProcessor.chargeCard("Tom", 500);
		assertEquals("Card has balance of 0", 0, ccProcessor.getCreditCards().get("Tom").getBalance());
	}

	@Test
	public void creditCardWithNegativeBalance()
	{
		CreditCardProcessor ccProcessor = new CreditCardProcessor();
		ccProcessor.addCreditCard("Tom", "4012888888881881", 2000);
		ccProcessor.creditCard("Tom", 500);
		assertEquals("Card has balance of -500", -500, ccProcessor.getCreditCards().get("Tom").getBalance());
	}

	@Test
	public void creditNonLuhnCompliantCard()
	{
		CreditCardProcessor ccProcessor = new CreditCardProcessor();
		ccProcessor.addCreditCard("Tom", "1111111111111111", 2000);
		ccProcessor.creditCard("Tom", 500);
		assertEquals("Card has balance of 0", 0, ccProcessor.getCreditCards().get("Tom").getBalance());
	}

	@Test
	public void generateTransactionSummary()
	{
		CreditCardProcessor ccProcessor = new CreditCardProcessor();
		ccProcessor.addCreditCard("Tom", "4111111111111111", 1000);
		ccProcessor.addCreditCard("Lisa", "5454545454545454", 3000);
		ccProcessor.addCreditCard("Quincy", "1234567890123456", 3000);
		ccProcessor.chargeCard("Tom", 500);
		ccProcessor.chargeCard("Tom", 800);
		ccProcessor.chargeCard("Lisa", 7);
		ccProcessor.creditCard("Lisa", 100);
		ccProcessor.creditCard("Quincy", 200);
		assertEquals("Transaction summary is correct", SUMMARY_HEADER + TEST_DATA + SUMMARY_FOOTER, ccProcessor.getTransactionSummary());
	}

}
