package dungeonmania;

import org.json.JSONObject;

public interface GoalNode {
	
	public Boolean evaluate();
	public String remainingString();
	public abstract JSONObject saveGameJSON();
}

