package hula.awards;

import hula.awards.Award;
import hula.awards.AwardIO;

/**
 * Award Event<br>
 *  - Give awards
 * @author eli
 *
 */
public class AwardEvent {

	/**
	 * give out an award
	 * 
	 * @param award
	 *            award to give
	 */
	public static void giveAward(Award award) {
		/**
		 * make sure it hasn't been awarded already
		 */
		if (IfNotAwarded(award)) {
			/**
			 * make it true
			 */
			award.setStateTrue();
			/**
			 * save it
			 */
			AwardIO.saveAward(award.toString());

			System.out.println(award + " given!");
		}
	}

	/**
	 * check if the award is already given
	 * 
	 * @param award to check
	 * @return boolean <b>true</b> if the award is not given
	 */
	private static boolean IfNotAwarded(Award award) {
		return award.getState() == false;
	}

}
