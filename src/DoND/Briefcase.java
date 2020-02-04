package DoND;

import java.text.NumberFormat; // Places comma's in the appropriate places and sets minimum number of digits
import java.util.ArrayList; // In order to retrieve the values in the array and modify them
import java.util.Arrays;
import java.util.Collections; // For shuffling values
import java.util.List;

public class Briefcase
{
	private static final Double PRIZE_VALUES [] = new Double[]{.01, 1.0, 5.0, 10.0, 25.0, 50.0, 75.0, 100.0, 200.0, 300.0, 400.0, 500.0, 750.0, 
															 	1000.0, 5000.0, 10000.0, 25000.0, 50000.0, 75000.0, 100000.0, 200000.0, 
															 	300000.0, 400000.0, 500000.0, 750000.0, 1000000.0};
	private static Gameplay gameplay = new Gameplay();
	/**
	 * ArrayList(Arrays.asList(array)) is used instead of Array.asList because the former creates a modifiable list 
	 * while the latter creates a list that can only be read or overwritten (no operations like adding or removing are allowed on this wrapper)
	 */
	private static List <Double> prizeValuesAsList = new ArrayList<Double>(Arrays.asList(PRIZE_VALUES)); 
	private List<Double> briefcases;
	private static boolean[] stateOfBriefcases = new boolean[26]; // Array which holds the state of each briefcase (whether it was opened or not)
	private static NumberFormat formatNumbers = NumberFormat.getInstance();
	
	
	public Briefcase() // Constructor
	{
		this.briefcases = prizeValuesAsList;
	} // end Briefcase

	protected void fillCases()
	{
		Collections.shuffle(prizeValuesAsList);
		for(int i = 0; i < briefcases.size(); i++)
		{
			Double prizeInCase = prizeValuesAsList.get(i);
			briefcases.set(i, prizeInCase);
		}
	} // end fillCases
	
	protected void displayCases()
	{
		formatNumbers.setMinimumIntegerDigits(1);
		System.out.print("\n");
		
		
		 /* In order to print out the values, they had to be split in half: Prize values 1-13 and 14-26 
	      * The lower money values appear on the left hand side of the cases
	      * The higher money values appear on the right hand side of the cases
	      * 
	      * Everything is being printed at once while checking whether or not the case was opened or if the prize value was eliminated
	      * (Alot of the time, we needed to print out white spaces in order to keep the format of the gameboard neat)
	      */
		
		int lowerValCount = 0, 
			higherValCount = 13, 
			lowCaseIndex = 0, 
			highCaseIndex = 0, 
			briefcaseCount = 0, 
			end = 0;
		
	       for ( int i = 0; i < 13; i++) //The number of rows
	       {			
	    	   lowCaseIndex = findIndex(PRIZE_VALUES[lowerValCount]);			
	    	   highCaseIndex = findIndex(PRIZE_VALUES[higherValCount]);
	    	   
	           //Prints briefcases in specified rows
	           if (i == 2 || i == 11 || i == 5 || i == 8)
	           {  
	        	   	// Checks if the case value is out of play and if it's the player's choice
	        		if(Gameplay.getPlayersCaseValue() != PRIZE_VALUES[lowerValCount] && isOutOfPlay(lowCaseIndex))
	        		{
	        			System.out.print("       ");
	        		}        		
	        		else
	        		{
	        			System.out.printf("$%-5.2f",PRIZE_VALUES[lowerValCount]);	// Prints lower money values to the left of the briefcases
	        			if(PRIZE_VALUES[lowerValCount] < 100)
	        			{
	        				System.out.print(" ");
	        			}
	        			else if(PRIZE_VALUES[lowerValCount] < 10)
	        			{
	        				System.out.print("  ");
	        			}
	        		}
	        	 
	               if (i == 2 || i == 11) // Puts 6 briefcases per row in rows 2 or 11
	               {
	                   end = briefcaseCount + 6;
	               }
	               else if (i == 5 || i == 8) // Puts 7 briefcases per row in rows 5 or 8
	               {
	                   end = briefcaseCount + 7;
	               }
	               
	               do 
	               {
	                   if (!isOutOfPlay(briefcaseCount + 1))
	                   {
	                	   int caseNumber = briefcaseCount + 1;
	                	   if ((caseNumber >= 1 && caseNumber <= 6) || (caseNumber >= 21 && caseNumber <= 26))
	                	   {
	                		   System.out.printf( "    [ %02d ] ",  caseNumber);
	                	   }
	                	   else
	                	   {
	                		   System.out.printf( "   [ %02d ] ",  caseNumber);
	                	   }
	                   }
	                   else 
	                   {
	                	   if (i == 2 || i == 11)
	                	   {
	                		   System.out.print("           ");
	                	   }
	                	   else
	                	   {
	                		   System.out.print("          ");
	                	   }
	                   }
	                   briefcaseCount++;
	               } while (briefcaseCount < end);
	               
	               if (i == 2 || i == 11) // Spaces to keep the format neat
	               {
	                   System.out.print("      ");
	               }
	               if (i == 5 || i == 8)
	               {
	                   System.out.print("  ");
	               }
	           
	               if(Gameplay.getPlayersCaseValue() != PRIZE_VALUES[higherValCount] && isOutOfPlay(highCaseIndex)) // Checks if the case value is out of play 
	               {
	       				System.out.print("  ");
	       				System.out.print("\n");
	               }
	               else
	               {
	            	   System.out.printf("$%,-10.2f", PRIZE_VALUES[higherValCount]);   // Prints higher money values to the right of the briefcases
	            	   System.out.print("\n");
	               }
	           }
	           else // Prints just the values 
	           {
	        	   	// This block of code prints out the rows that don't have briefcases in them
	        	    if(Gameplay.getPlayersCaseValue() != PRIZE_VALUES[lowerValCount] && isOutOfPlay(lowCaseIndex))               
	        	    {
	           			System.out.print("\t\t\t\t\t\t\t\t\t       ");	       
	        	    }
	        	    else
	        	    {
	        	    	System.out.printf("$%,-78.2f", PRIZE_VALUES[lowerValCount]);    
	        	    }
	        	    if(Gameplay.getPlayersCaseValue() != PRIZE_VALUES[higherValCount] && isOutOfPlay(highCaseIndex))	
	        	    {
	           			System.out.print("  ");  
	        	    }
	        	    else
	        	    {
	        	    	System.out.printf("$%,-78.2f", PRIZE_VALUES[higherValCount]);    
	        	    }
	        	    System.out.print("\n");
	           }
	           lowerValCount++;
	           higherValCount++;
	       }
		System.out.print("\n");
	} // end displayCases
	
	protected void setOutOfPlay(int caseToSet)
	{
		// Case is now open (out of play)
		stateOfBriefcases[caseToSet-1] = true;  
	} // end setOutOfPlay
	
	protected void setInPlay(int caseToSet)
	{
		//Case is now closed (ready for a new game)
		stateOfBriefcases[caseToSet-1] = false;
	}//end setInPlay
	
	public boolean isOutOfPlay(int caseToCheck) 
	{
		return stateOfBriefcases[caseToCheck-1];
	} // end isOutOfPlay
	
	protected double getCaseValue(int playersChoice) 
	{
        return briefcases.get(playersChoice-1);
    } // end getCaseValue

	protected double getHighestPrizeValue() 
	{
		double maxPrizeValue = briefcases.get(0); 
		for(int i = 1; i < briefcases.size(); i++)
		{
			if (!isOutOfPlay(i+1))
			{
				if(briefcases.get(i) > maxPrizeValue)
				{
					maxPrizeValue = briefcases.get(i);
				}
			}
		 }
			  return maxPrizeValue;
	} // end getHighestPrizeValue

	protected double getLowestPrizeValue()
	{
		double minPrizeValue = briefcases.get(0);
		for(int i = 1; i < briefcases.size(); i++)
		{
			if (!isOutOfPlay(i+1))
			{
				if(briefcases.get(i) < minPrizeValue)
				{
					minPrizeValue = briefcases.get(i);
				}
			}
		 }
			  return minPrizeValue;
	} // end getLowestPrizeValue

	protected int getNumberOfPrizesLeft()
	{
		int numberOfPrizesLeft = 0;
		for(int i = 0; i < briefcases.size(); i++)
		{
			if (!isOutOfPlay(i+1))
			{
				numberOfPrizesLeft++;
			}
		}
		return numberOfPrizesLeft;
	} // end getNumberOfPrizesLeft
	
	protected double getLastPrizeLeft()
	{
		double lastPrizeValue = 0;
		for(int i = 0; i < briefcases.size(); i++)
		{
			if (!isOutOfPlay(i+1))
			{
				lastPrizeValue = briefcases.get(i);
			}
		}
		return lastPrizeValue;
	} // end getLastPrizeLeft
	
	protected double revealCase(int caseToReveal)
	{
		return briefcases.get(caseToReveal-1);
	} // end revealCase
	
	protected void closeCases()
	{
		for(int i = 1; i < briefcases.size(); i++)
		{
			setInPlay(i);
		}
	} //end closeCases
	
	protected int findIndex(double prizeValue)
	{
		for(int i=0; i< 26;i++)
		{
			if(prizeValue == briefcases.get(i))
			{
					 return i+1;
			}
		}
		return -1;
	} //end findIndex
} // end Briefcase