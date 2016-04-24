package hula.awards;

import hula.awards.Award;
import hula.awards.AwardTracker;

public class AwardEvent {

	private final AwardTracker awardtracker;

	public AwardEvent(AwardTracker awardtracker) {
		this.awardtracker = awardtracker;
	}

	/**
	 * give out an award
	 * 
	 * @param award
	 *            award to give
	 */
	public void giveAward(Award award) {
		if (IfNotAwarded(award)) {
			awardtracker.booleanAward[award.ordinal()] = true;
			System.out.println(award + " given!");
		}
	}

	/**
	 * check if the award is already given
	 * 
	 * @param award
	 *            award to check
	 * @return boolean
	 */
	public boolean IfNotAwarded(Award award) {
		return awardtracker.booleanAward[award.ordinal()] == false;
	}

}
