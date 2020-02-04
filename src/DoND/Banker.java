package DoND;

public class Banker 
{
	private static Briefcase briefcaseInfo = new Briefcase();
	private static Gameplay gameInfo = new Gameplay();

	private double calculateOffer() // Price may be lowered for "certainty factor"
	{	
		double calculatedOffer;
		if (briefcaseInfo.getNumberOfPrizesLeft() > 1)
		{
			calculatedOffer = (briefcaseInfo.getHighestPrizeValue() + briefcaseInfo.getLowestPrizeValue()) / (briefcaseInfo.getNumberOfPrizesLeft());
			return calculatedOffer;	
		}
		else
		{
			double addPlayersCaseAndLastCase = briefcaseInfo.getHighestPrizeValue() + gameInfo.getPlayersCaseValue();
			calculatedOffer = addPlayersCaseAndLastCase / 2;
			return calculatedOffer;
		}
	} // end calculateOffer					
	
	protected double presentOffer()
	{
		double offer = calculateOffer();
		return offer;
	} // end presentOffer
} // end Banker
