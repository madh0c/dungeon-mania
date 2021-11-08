package dungeonmania;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

public class GoalAnd implements GoalNode{
	private List<GoalNode> subGoals = new ArrayList<>();
	private String operator;
	private Boolean hasCompleted;
	public GoalAnd(String operator) {
		this.operator = operator;
		hasCompleted = false;
	}

	@Override
	public Boolean evaluate() {
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
        JSONObject compositeAndJSON = new JSONObject();
        compositeAndJSON.put("goal", operator);

        JSONArray subGoalsJSON = new JSONArray();
        subGoals.stream().map(GoalNode :: saveGameJSON).forEach(x -> subGoalsJSON.put(x));
        
        compositeAndJSON.put("subgoals", subGoalsJSON);
        return compositeAndJSON;
    }


	public boolean add(GoalNode goal) {
		subGoals.add(goal);
		return true;
	}

	public boolean remove(GoalNode goal) {
		subGoals.remove(goal);
		return true;
	}

	public List<GoalNode> getList() {
		return subGoals;
	}

	public void setHasCompleted(Boolean hasCompleted) {
		this.hasCompleted = hasCompleted;
	}
	
}
