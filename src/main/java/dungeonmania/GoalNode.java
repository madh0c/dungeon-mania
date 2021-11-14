package dungeonmania;

import org.json.JSONObject;

public interface GoalNode {
	
	public Boolean evaluate(Dungeon dungeon);
	public String remainingString();
}

