package DoND;

import java.util.Scanner;

public class Player 
{
	private boolean deal;
	private int caseNumber;
	private int chosenCase;
	private static Scanner scanUserInput = new Scanner(System.in); // Made Scanner global so we only have to close it once in order to avoid memory leak
																   // and it won't cause an exception when closed at the end of the class
	protected int setChosenCase() 
	{ 
		chosenCase = scanUserInput.nextInt(); // Set chosenCase = the next number input by the user
	    return chosenCase;
	} // end setChosenCase
	
	protected void dealOrNoDeal() 
	{
		System.out.println("\nDeal or no deal?");
		System.out.println("Deal = Y    No Deal = N\n");
		
		char dond = scanUserInput.next().charAt(0); // Set dond = the first char input by the user

		if (dond == 'y' || dond == 'Y') 
		{
			this.deal = true;
		}
		else if (dond == 'n' || dond == 'N')
		{
			this.deal = false;
		}
		else 
		{
			System.out.println("Please enter a valid character");
			dealOrNoDeal();
		}
	} // end dealOrNoDeal
	
	protected boolean getDealChoice() 
	{
		return deal;
	} // end getDealChoice
	
	protected int caseToOpen() 
	{
		caseNumber = scanUserInput.nextInt(); // Set caseNumber = the next number input by the user
		return caseNumber;
	} // end caseToOpen
	
	protected void closeScanner()
	{
		scanUserInput.close();
	} // end closeScanner
} // end Player
