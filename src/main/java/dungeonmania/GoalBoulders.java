package dungeonmania;

import dungeonmania.allEntities.Switch;

public class GoalBoulders extends GoalLeaf{

	private Boolean hasCompleted = false;
	public GoalBoulders(String goal) {
		super(goal);
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
		if (hasCompleted || getGoal().equals("")) {
			return "";
		}
		return ":" + this.getGoal();
	}
	
}
