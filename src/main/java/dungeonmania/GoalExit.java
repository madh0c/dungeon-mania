package dungeonmania;

import dungeonmania.allEntities.Exit;
import dungeonmania.util.Position;

public class GoalExit extends GoalLeaf{

	public GoalExit(String goal) {
		super(goal);
	}
	
	@Override
	public Boolean evaluate(Dungeon currentDungeon) {
		boolean exit = false;
		for (Entity ent: currentDungeon.getEntities()) {
			if (ent instanceof Exit) {
				Position playerPos = currentDungeon.getPlayerPosition();
				Position exitPos = ent.getPosition();
				if (playerPos == null ) {
					continue;
				} else if (playerPos.equals(exitPos)) {
					exit = true;
					continue;
				} 
			}
		}
		return exit;
	}

}
