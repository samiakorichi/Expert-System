package part_1.raisoneur;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class Rules implements Iterable<Rule>{

    private final ArrayList<Rule> rules;

    public Rules() {
        this.rules = new ArrayList<>();
    }

    public Rules(JSONObject JSONrules) {
        this();
        this.JSONToRules(JSONrules);
    }

    public void addRule(Rule rule){
        this.rules.add(rule);
    }

    @Override
    public Iterator<Rule> iterator() {
        return rules.iterator();
    }

    public void JSONToRules(JSONObject JSONrules) {
        for (Iterator<String> it = JSONrules.keys(); it.hasNext(); ){
            String ruleName = it.next();
            addRule(new Rule(ruleName ,JSONrules.getJSONObject(ruleName)));
        }
    }
}
