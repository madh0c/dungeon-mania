package dungeonmania;

import org.json.JSONObject;

import dungeonmania.allEntities.Exit;
import dungeonmania.util.Position;

public class GoalExit implements GoalNode{

	private Boolean hasCompleted = false;
	private String goal;
	public GoalExit(String goal) {
		this.goal = goal;
	}
	
	@Override
	public Boolean evaluate(Dungeon currentDungeon) {
		hasCompleted = false;
		for (Entity ent: currentDungeon.getEntities()) {
			if (ent instanceof Exit) {
				Position playerPos = currentDungeon.getPlayerPosition();
				Position exitPos = ent.getPosition();
				if (playerPos == null ) {
					continue;
				} else if (playerPos.equals(exitPos)) {
					hasCompleted = true;
					continue;
				} 
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
