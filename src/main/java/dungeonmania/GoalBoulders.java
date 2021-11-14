package dungeonmania;

import org.json.JSONObject;

import dungeonmania.allEntities.Switch;

public class GoalBoulders implements GoalNode{

	private Boolean hasCompleted = false;
	private String goal;
	public GoalBoulders(String goal) {
		this.goal = goal;
	}

	@Override
	public Boolean evaluate(Dungeon currentDungeon) {
		hasCompleted = true;		
		for (Entity ent: currentDungeon.getEntities()) {
			if (ent instanceof Switch) {
				Switch swtch = (Switch) ent;
				if (!swtch.getStatus()) {
					hasCompleted = false;
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
		return ":" + this.goal;
	}
}
