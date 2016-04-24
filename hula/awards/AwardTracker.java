package hula.awards;

public class AwardTracker {
	/**
	 * boolean array the length of Award enum
	 */
	public boolean[] booleanAward;
	
	public AwardTracker() {
		booleanAward = new boolean[Award.values().length];
	}

	public void setAwardState(boolean[] awardState) {
		if (awardState.length == booleanAward.length) {
			for (int i = 0; i < awardState.length; i++) {
				booleanAward[i] = awardState[i];
			}
		} else {
			System.out.println("Boolean lengths do not match! Not continuing");
		}
	}
}
