package part_1.raisoneur;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class Raisonneur {

    public static String raisonner(JSONObject JSONrules, HashMap<String, String> facts, String but){

        facts = checkFacts(facts);

        Rules activeRules = new Rules(JSONrules);
        boolean isStatic = false, ruleValue;

        while (!isStatic){
            isStatic = true;
            for (Rule rule:activeRules){
                if (!rule.IsRuleIgnored()){
                    if (!rule.areThereConditions()){
                        continue;
                    }
                    ruleValue = true;
                    for (Condition condition:rule){
                        if (facts.containsKey(condition.getVariableName())){
                            rule.ignoreRule(true);
                            if (!condition.makeOperation(facts.get(condition.getVariableName()))){
                                ruleValue = false;
                                break;
                            }
                        } else {
                            ruleValue = false;
                            break;
                        }
                    }
                    if (ruleValue){
                        isStatic = false;
                        // add the rule to he base fact
                        if (!facts.containsKey(rule.getVariableResultat())){
                            facts.put(rule.getVariableResultat(), rule.getResultats());
                        } else {
                            if(!rule.getResultats().equals(facts.get(rule.getVariableResultat()))){
                                System.out.println("PROBLEM: Facts are incosistants, we must stop the reasoning");
                            }
                        }
                    }
                }
            }
        }

        if(facts.containsKey(but)){
            return facts.get(but);
        } else {
            System.out.println("Impossible de trouver le but");
            return null;
        }
    }

    public static HashMap<String, String> checkFacts (HashMap<String, String> facts){
        ArrayList<String> to_remove = new ArrayList<>();
        for (String key: facts.keySet()){
            if (facts.get(key) == null){
                to_remove.add(key);
            }
        }
        for (String key: to_remove){
            facts.remove(key);
        }
        return facts;
    }

}
