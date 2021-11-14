package dungeonmania;

import dungeonmania.allEntities.ZombieToastSpawner;

public class GoalEnemies extends GoalLeaf{

	private Boolean hasCompleted = true;
	public GoalEnemies(String goal) {
		super(goal);
	}

	@Override
	public Boolean evaluate(Dungeon currentDungeon) {
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
		if (hasCompleted || getGoal().equals("")) {
			return "";
		}
		return ":" + this.getGoal();
	}
	
}
