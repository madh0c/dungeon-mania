package dungeonmania;

public class GoalLeaf implements GoalNode{

	private String goal;
	private Boolean hasCompleted;
	public GoalLeaf(String goal) {
		this.goal = goal;
	}
	// @Override
	// public Boolean evaluate() {
	// 	return true;
	// }
	@Override 
	public String remainingString() {
		return ":" + this.goal;
	}
	
}

