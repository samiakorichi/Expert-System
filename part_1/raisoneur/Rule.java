package part_1.raisoneur;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class Rule implements Iterable<Condition>{

    private ArrayList<Condition> conditions;
    private final String rulename;
    private String resultats, variableResultat;
    private boolean ignore = false;

    public Rule(String rulename, ArrayList<Condition> conditions, String resultats, String variableResultat) {
        this.conditions = conditions;
        this.rulename = rulename;
        this.resultats = resultats;
        this.variableResultat = variableResultat;
    }

    public Rule(String rulename, JSONObject jsonRule){
        this.rulename = rulename;
        this.conditions = new ArrayList<>();
        this.JSONToRule(jsonRule);
    }

    public boolean IsRuleIgnored() {
        return ignore;
    }

    public void ignoreRule(boolean ignore) {
        this.ignore = ignore;
    }

    public boolean areThereConditions(){
        return !conditions.isEmpty();
    }

    public String getResultats() {
        return resultats;
    }

    public String getVariableResultat() {
        return variableResultat;
    }

    public String getRulename() {
        return rulename;
    }

    public void addCondition(Condition cond){
        this.conditions.add(cond);
    }

    public void JSONToRule(JSONObject jsonRule) {
        JSONObject jsonCondition;
        String type_operator, operation, value_operator;
        JSONArray listConditions = jsonRule.getJSONArray("AND");
        for (int i=0; i<listConditions.length(); i++){
            jsonCondition = listConditions.getJSONObject(i);
            type_operator = jsonCondition.getString("type_operator");
            operation = jsonCondition.getString("operation");
            value_operator = jsonCondition.getString("value_operator");
            this.addCondition(new Condition(type_operator, operation, value_operator));
        }

        this.resultats = jsonRule.getJSONObject("THEN").getString("result");
        this.variableResultat = jsonRule.getJSONObject("THEN").getString("type_result");
    }

    @Override
    public Iterator<Condition> iterator() {
        return conditions.iterator();
    }
}
