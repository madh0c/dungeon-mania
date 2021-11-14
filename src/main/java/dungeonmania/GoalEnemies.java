package dungeonmania;

import org.json.JSONObject;

import dungeonmania.allEntities.ZombieToastSpawner;

public class GoalEnemies implements GoalNode{

	private Boolean hasCompleted = false;
	private String goal;
	public GoalEnemies(String goal) {
		this.goal = goal;
	}

	@Override
	public Boolean evaluate(Dungeon currentDungeon) {
		hasCompleted = true;
		for (Entity ent: currentDungeon.getEntities()) {
			if (ent instanceof MovingEntity || ent instanceof ZombieToastSpawner) {
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
		return ":" + goal;
	}

	@Override
	public JSONObject saveGameJSON() {
		JSONObject compositeLeafJSON = new JSONObject();
        compositeLeafJSON.put("goal", goal);
		return compositeLeafJSON;
	}
	
}
