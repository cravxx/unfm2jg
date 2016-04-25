package hula.awards;

import hula.awards.Award;
import hula.awards.AwardIO;

public class AwardEvent {

	/**
	 * give out an award
	 * 
	 * @param award
	 *            award to give
	 */
	public void giveAward(Award award) {
		if (IfNotAwarded(award)) {
			award.setStateTrue();
			AwardIO.saveAward(award.toString());
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
		return award.getState() == false;
	}

}
