package dungeonmania;

import org.json.JSONObject;

import dungeonmania.allEntities.Treasure;

public class GoalTreasure implements GoalNode{

	private Boolean hasCompleted = false;
	private String goal;
	public GoalTreasure(String goal) {
		this.goal = goal;
	}
	
	@Override
	public Boolean evaluate(Dungeon currentDungeon) {
		hasCompleted = true;
		for (Entity ent: currentDungeon.getEntities()) {
			if (ent instanceof Treasure) {
				hasCompleted = false; 
				continue;
			}
		}
		return hasCompleted;
	}

	@Override 
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
}
