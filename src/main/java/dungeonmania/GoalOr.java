package dungeonmania;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;


public class GoalOr implements GoalNode{
	private List<GoalNode> subGoals = new ArrayList<>();
	private Boolean hasCompleted;
	private String operator;
	public GoalOr(String operator) {
		this.operator = operator;
		hasCompleted = false;
	}

	@Override
	public Boolean evaluate(Dungeon dungeon) {
		if (subGoals.stream().anyMatch(x-> x.evaluate(dungeon).equals(true))) {
			hasCompleted = true;
		} else {
			hasCompleted = false;
		}
		return hasCompleted;
	}

	@Override
	public String remainingString() {
		if (hasCompleted) {
			return "";
		} 
		String notDoneGoals = subGoals.stream().map(GoalNode :: remainingString) .filter(x -> !x.equals("")).collect(Collectors.joining(" "  + operator + " "));
		if (notDoneGoals.contains(operator)) {
			return "(" + notDoneGoals + ")";
		} else {
			return notDoneGoals;
		}	
	}

	@Override
    public JSONObject saveGameJSON() {
        JSONObject compositeOrJSON = new JSONObject();
        compositeOrJSON.put("goal", operator);

        JSONArray subGoalsJSON = new JSONArray();
        subGoals.stream().map(GoalNode :: saveGameJSON).forEach(x -> subGoalsJSON.put(x));
        
        compositeOrJSON.put("subgoals", subGoalsJSON);
        return compositeOrJSON;
    }

	public boolean add(GoalNode goal) {
		subGoals.add(goal);
		return true;
	}

	public List<GoalNode> getList() {
		return subGoals;
	}

	public void setHasCompleted(Boolean hasCompleted) {
		this.hasCompleted = hasCompleted;
	}

}
