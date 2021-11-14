package dungeonmania;

import dungeonmania.allEntities.Switch;

public class GoalBoulders extends GoalLeaf{

	public GoalBoulders(String goal) {
		super(goal);
	}

	@Override
	public Boolean evaluate(Dungeon currentDungeon) {
		boolean boulders = false;
		for (Entity ent: currentDungeon.getEntities()) {
			if (ent instanceof Switch) {
				Switch swtch = (Switch) ent;
				if (!swtch.getStatus()) {
					boulders = false;
					continue;
				}
			}
		}
		return boulders;
	}
	
}
