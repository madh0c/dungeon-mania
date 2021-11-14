package dungeonmania;

import dungeonmania.allEntities.Treasure;

public class GoalTreasure extends GoalLeaf{

	private Boolean hasCompleted = false;
	public GoalTreasure(String goal) {
		super(goal);
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
		if (hasCompleted || getGoal().equals("")) {
			return "";
		}
		return ":" + this.getGoal();
	}
}
