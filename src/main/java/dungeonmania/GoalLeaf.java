package dungeonmania;

import org.json.JSONObject;

public class GoalLeaf implements GoalNode {

	private String goal;
	private Boolean hasCompleted = false;
	public GoalLeaf(String goal) {
		this.goal = goal;
	}

	
	public Boolean evaluate(Dungeon dungeon) {
		return hasCompleted;
	}

	 
	public String remainingString() {
		if (hasCompleted || goal.equals("")) {
			return "";
		}
		return ":" + this.goal;
	}

	@Override
	public JSONObject saveGameJSON() {
		JSONObject compositeLeafJSON = new JSONObject();
        compositeLeafJSON.put("goal", goal);
		return compositeLeafJSON;
	}

	public String getGoal() {
		return goal;
	}

	public void setHasCompleted(Boolean hasCompleted) {
		this.hasCompleted = hasCompleted;
	}
}

