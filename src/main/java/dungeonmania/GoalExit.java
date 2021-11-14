package dungeonmania;

import dungeonmania.allEntities.Exit;
import dungeonmania.util.Position;

public class GoalExit extends GoalLeaf{

	private Boolean hasCompleted = false;
	public GoalExit(String goal) {
		super(goal);
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
		if (hasCompleted || getGoal().equals("")) {
			return "";
		}
		return ":" + this.getGoal();
	}
}
