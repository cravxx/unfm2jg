package hula.awards;

/**
 * Award Enum
 * @author eli
 *
 */
public enum Award {
	FinishFirstThreeTimes(false), 
	CompleteFiveRacesWithoutPlacingLast(false), 
	EnterTheArena(false),
	PlaceholderOne(false),
	PlaceholderTwo(false);
	
	private boolean state;
	
	Award(boolean state) {
        this.state = state;
    }
	
	/**
	 * set the enum's state to true
	 */
	public void setStateTrue() {
		state = true;
	}
	
	/**
	 * getState of enum<br>false by default
	 * @return boolean 
	 */
	public boolean getState() {
		return state;
	}
}