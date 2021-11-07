package dungeonmania;

public class GoalLeaf implements GoalNode{

	private String goal;
	private Boolean hasCompleted = false;
	public GoalLeaf(String goal) {
		this.goal = goal;
	}

	@Override
	public Boolean evaluate(Dungeon dungeon) {
		return hasCompleted;
	}

	@Override 
	public String remainingString() {
		if (hasCompleted) {
			return "";
		}
		return ":" + this.goal;
	}
	
	public void sethasCompleted(Boolean hasCompleted) {
		this.hasCompleted = hasCompleted;
	}
}

