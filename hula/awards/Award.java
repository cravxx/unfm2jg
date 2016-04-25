package hula.awards;

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
	
	public void setStateTrue() {
		state = true;
	}
	
	public boolean getState() {
		return state;
	}
}