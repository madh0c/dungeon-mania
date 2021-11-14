package dungeonmania;

import dungeonmania.allEntities.Treasure;

public class GoalTreasure extends GoalLeaf{

	public GoalTreasure(String goal) {
		super(goal);
	}
	
	@Override
	public Boolean evaluate(Dungeon currentDungeon) {
		boolean treasure = true;
		for (Entity ent: currentDungeon.getEntities()) {
			if (ent instanceof Treasure) {
				treasure = false; 
				continue;
			}
		}
		return treasure;
	}
}
