package DoND;

import java.text.NumberFormat;
import java.util.Scanner;

public class Gameplay 
{
	private static Briefcase briefcases = new Briefcase();
	private static Player player = new Player();
	private static Banker banker = new Banker();
	
	private char startNewGame;
	private int playersCase;
	private static double playersCaseValue;
	private int roundNumber; // 10 rounds total
	private static int briefcaseTotal; // 26 total - 1(player chooses one) = 25
	private int briefcasesToOpen;
	private boolean continueGame; 
	private static NumberFormat formatNumbers = NumberFormat.getInstance();	  		 
	private static Scanner scanUserInput = new Scanner(System.in); // Made Scanner global so we only have to close it once in order to avoid memory leak
																   // and it won't cause an exception when closed at the end of the class
	
	public void startGame() 
	{
		System.out.println("Would you like to play Deal or no Deal?");
		System.out.println("Play = Y    No Thanks = N");
		startNewGame = scanUserInput.next().charAt(0); // Set startNewGame = the first char input by the user
		
		if (startNewGame == 'y' || startNewGame == 'Y')
		{
			initializeGame();
		}
		else if (startNewGame == 'n' || startNewGame == 'N')
		{
			System.out.println("Play next time!"); // Otherwise, end program
			return; // Exits method
		}
		else 
		{
			System.out.println("Please enter a valid character");
			startGame();
		}
	} // end startGame

	private void initializeGame() 
	{
		roundNumber = 1;
		briefcaseTotal = 26;
		continueGame = true;

		// Fill cases
		briefcases.fillCases();
		
		// Display Cases
		briefcases.displayCases();

		// Player's choice
		System.out.println("Which one of the cases above would you like to choose?");
		playersCase = player.setChosenCase();
		
		// Save the value of Player's case so we can access it later
		playersCaseValue = briefcases.getCaseValue(playersCase);

		// Set the player's case state to true (open)
		briefcases.setOutOfPlay(playersCase);

		briefcaseTotal--;

		// Enter the rounds
		while (continueGame == true)
		{
			playGame();
		}
	} // end initializeGame
	
	protected static double getPlayersCaseValue()
	{
		return playersCaseValue;
	} // end getPlayersCaseValue

	private void playGame() throws IndexOutOfBoundsException  
	{
		formatNumbers.setMinimumIntegerDigits(1);
		
		if (roundNumber == 1) // Round 1: 6 briefcases opened
		{
			briefcasesToOpen = 6;  
		}
		else if (roundNumber == 2) // Round 2: 5 briefcases opened
		{
			briefcasesToOpen = 5;
		}
		else if (roundNumber == 3) // Round 3: 4 briefcases opened
		{
			briefcasesToOpen = 4;
		}
		else if (roundNumber == 4) // Round 4: 3 briefcases opened
		{
			briefcasesToOpen = 3;
		}
		else if (roundNumber == 5) // Round 5: 2 briefcases opened
		{
			briefcasesToOpen = 2;
		}
		else if (roundNumber == 6) // Round 6-10: 1 briefcase opened
		{
			briefcasesToOpen = 1;
		}
		else if (roundNumber == 7) // Round 6-10: 1 briefcase opened
		{
			briefcasesToOpen = 1;
		}
		else if (roundNumber == 8) // Round 6-10: 1 briefcase opened
		{
			briefcasesToOpen = 1; 
		}
		else if (roundNumber == 9) // Round 6-10: 1 briefcase opened
		{
			briefcasesToOpen = 1;
		}
		else if (roundNumber == 10) // Round 6-10: 1 briefcase opened
		{
			briefcasesToOpen = 1;
		} // end if/else if block
			
			if (briefcaseTotal > 1) 
			{
				briefcases.displayCases();
				System.out.println("\n\nPlayer, pick " + briefcasesToOpen + " cases to open");
				for (int i = 1; i <= briefcasesToOpen; i++) 
				{
					try 
					{ 
						// Player chooses a case to open
						int openedCase = player.caseToOpen();
						if (openedCase > 26)
						{
							// If the chosen briefcase is outside the scope of the game
							throw new IndexOutOfBoundsException();
						}
						
						else
						{
							// If the chosen briefcase was already revealed.. choose another case
							if (briefcases.isOutOfPlay(openedCase)) 
							{
								System.out.println("This case was already revealed. Please choose another case.");
								briefcases.displayCases();
								i--;
							}
							else if (!briefcases.isOutOfPlay(openedCase))
							{
								// Show value of each chosen case
								double showCase = briefcases.revealCase(openedCase);
								System.out.print("\nCase #" + openedCase + " contains... ");
								try 
								{
									Thread.sleep(1000); //  Sleeps for dramatic effect . . .
								} 
								catch(InterruptedException ex) 
								{
									Thread.currentThread().interrupt();
								} 
								System.out.print("[$" + formatNumbers.format(showCase) + "]\n");

								briefcases.setOutOfPlay(openedCase);
								briefcaseTotal--;
								briefcases.displayCases();
							} 
						}
					}
					catch(IndexOutOfBoundsException e) 
					{
				    	System.out.println("\nYou must choose from the cases left unopened from 1-26\n");
				    	briefcases.displayCases();
				    	i--;
				    }
				}
			}
			
				// Banker generates offer
				System.out.print("\nThe Banker's offer = " + "$" + formatNumbers.format(Math.round(banker.presentOffer())) + "\n");

				// Accept or decline offer
				player.dealOrNoDeal();

				// Set continue
				if (!player.getDealChoice()) 
				{
					continueGame = true;
					System.out.println("\nYou chose NO DEAL!\n");
				}
				else if (player.getDealChoice() && continueGame != false) 
				{
					continueGame = false;
					System.out.println("\nYou chose DEAL!");
					String bankersOfferString = formatNumbers.format(Math.round(banker.presentOffer()));
					double bankersOfferDouble = banker.presentOffer();
					
					
					if (briefcaseTotal > 4)
					{
						System.out.println("\nYou walk away with $" + bankersOfferString);
					} // end if (briefcasesLeft > 4)
					
					
					else if (briefcaseTotal <= 4 && briefcaseTotal != 1)
					{
						System.out.println("\nNow, it's time to see if you made a good deal...");
						System.out.println("\nHad you said 'no deal', which case would you have opened next?");
						briefcases.displayCases();
						for (int i = 0; i <= briefcaseTotal; i++) 
						{
							// Player chooses a case to open
							System.out.println("\nChoose the next case");
							int openedCase = player.caseToOpen();
							double showCase = briefcases.revealCase(openedCase);
							System.out.print("\nCase #" + openedCase + " contains... ");
							try 
							{
								Thread.sleep(1000); //  Sleeps for dramatic effect . . .
							} 
							catch(InterruptedException ex) 
							{
								Thread.currentThread().interrupt();
							} 
							System.out.print("[$" + formatNumbers.format(showCase) + "]\n");
							if (briefcaseTotal != 1)
							{
								briefcases.setOutOfPlay(openedCase);
								System.out.print("\nThe banker's next offer would have been... ");
								System.out.print("$" + formatNumbers.format(Math.round(banker.presentOffer())) + "\n");
								briefcases.displayCases();
								briefcaseTotal--;
							}

							if (briefcaseTotal == 1)
							{
								System.out.print("\nLast but not least, the prize inside the last case is $" + formatNumbers.format(briefcases.getLastPrizeLeft()));
								if (playersCaseValue > bankersOfferDouble)
								{
									System.out.println("\n\nYou made a bad deal");
									System.out.print("\nYour case was worth $" + formatNumbers.format(playersCaseValue));
									System.out.print("\nThe deal you accepted is a total of $" + bankersOfferString + "\n");
								}
								else if (playersCaseValue < bankersOfferDouble)
								{
									System.out.println("\n\nYou made a good deal!");
									System.out.print("\nYour case was worth $" + formatNumbers.format(playersCaseValue));
									System.out.print("\nThe deal you accepted is a total of $" + bankersOfferString + "\n");
								}
							}
						}
						System.out.println("\nYou walk away with $" + bankersOfferString);
					} // end else if (briefcasesLeft <= 4 && briefcasesLeft != 1)	
					
					else if (briefcaseTotal== 1)
					{
						System.out.print("\nLast but not least, the prize inside the last case is $" + formatNumbers.format(briefcases.getLastPrizeLeft()));
						if (playersCaseValue > bankersOfferDouble)
						{
							System.out.println("\n\nYou made a bad deal");
							System.out.print("\nYour case was worth $" + formatNumbers.format(playersCaseValue));
							System.out.print("\nThe deal you accepted is a total of $" + bankersOfferString + "\n");
						}
						else if (playersCaseValue < bankersOfferDouble)
						{
							System.out.println("\n\nYou made a good deal!");
							System.out.print("\nYour case was worth $" + formatNumbers.format(playersCaseValue));
							System.out.print("\nThe deal you accepted is a total of $" + bankersOfferString + "\n");
						}
						System.out.println("\nYou walk away with $" + bankersOfferString);
					} // end else if (briefcasesLeft == 1)
				} // end else if (player.getDealChoice() && continueGame != false)
			
			if (briefcaseTotal == 1 && continueGame != false) 
			{
				continueGame = false;
				briefcases.displayCases();
				System.out.println("\nThere is only one case left to open, this is your last chance...\n");
				System.out.println("\nWill you accept the banker's offer of $" + formatNumbers.format(Math.round(banker.presentOffer())) + "?\n");
				player.dealOrNoDeal();
				
				if (!player.getDealChoice() && continueGame == false) 
				{
					System.out.println("\nYou chose NO DEAL!");
					System.out.println("\nTime to reveal what's in your case...");
					System.out.println("\n$" + formatNumbers.format(playersCaseValue));
					System.out.println("\nAnd in the case left standing...");
					System.out.println("\n$" + formatNumbers.format(briefcases.getLastPrizeLeft()));
					System.out.println("\nYou walk away with $" + formatNumbers.format(playersCaseValue));
				}
				else if (player.getDealChoice() && continueGame == false) 
				{
					System.out.println("\nYou chose DEAL!");
					System.out.println("\nNow it's time to see if you made a good deal...");
					System.out.println("\nYou sold this case for $" + formatNumbers.format(Math.round(banker.presentOffer())) + " ...");
					System.out.println("\nLet's see how much is in your case");
					System.out.println("\n$" + formatNumbers.format(playersCaseValue));
					if (playersCaseValue > banker.presentOffer())
					{
						System.out.println("\nYou made a bad deal...");
						System.out.println("\nYou walk away tonight with $" + formatNumbers.format(Math.round(banker.presentOffer())));
					}
					else if (playersCaseValue < banker.presentOffer())
					{
						System.out.println("\nYou made a good deal!");
						System.out.println("\nYou walk away tonight with $" + formatNumbers.format(Math.round(banker.presentOffer())));
					}
				}
			}
			roundNumber++;
	} // end playGame
	
	private static void playAgain() 
	{
		System.out.println("\nWould you like to play again?");
		System.out.println("Play = Y    No Thanks = N");
		char startNewGame = scanUserInput.next().charAt(0); // Set startNewGame = the first char input by the user
			
		if (startNewGame == 'y' || startNewGame == 'Y')
		{
			Gameplay newGame = new Gameplay(); // New instance of Gameplay for a new game
			briefcases.closeCases();
			newGame.initializeGame();
			playAgain();					   // Continue playing until 'n' is typed 
		}
		else if (startNewGame == 'n' || startNewGame == 'N')
		{
			System.out.println("Thanks for playing!"); // Otherwise, end program
			return; // Exits method
		}
		else 
		{
			System.out.println("Please enter a valid character");
			playAgain();
		}
	} //end playAgain

	public static void main(String[] args)
	{
		Gameplay newGame = new Gameplay();
		newGame.startGame();
		playAgain();
		player.closeScanner();
		scanUserInput.close();
	} // end main
} // end Gameplay