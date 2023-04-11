This is a university project of agent technology course implementing an expert system .

# Part 1

the part 1 of this projec implement two expert system, a first one for headache diagnosis, and a second one as a Touristic guide in Algeria. The software can implement any expert system as long as the rules (composed by ands) are specified in a json file in Multi-Agent-system-for-flight-managment/src/part_1/bases/

## JSON File Structure

```json
{
   "title":"base of rule title",
   "but": "the goal we're trying to reach",
   "facts":{
      "_comment": "list of facts with it's possible values",
      "fact1":[
         "possible value 1",
         "possible value 2",
         "possible value 3"
      ],
      "fact2":[
         "possible value 1",
         "possible value 2",
         "possible value 3"
      ]
   },
   "rules":{
      "_comment": "list of facts with it's possible values",
      "rule1":{
         "_comment": "a rule is a set of ANDs where each AND is a condition with the following operators (==, >, >=, <, <=)",
         "AND": [
            {
               "type_operator": "operator to compare (one of the facts)",
               "operation": "==",
               "value_operator": "value (one of the possible values of the fact)" 
            },
            {
               "type_operator": "operator to compare (one of the facts)", 
               "operation": ">=",
               "value_operator": "value (one of the possible values of the fact)" 
            }
         ],
         "THEN": {
            "type_result": "Vehicle",
            "result": "Bicycle"
         }
      }
   }
}
```

# Dependencies
* JAVA
* JAVAFX
* json-20210307
* gson-2.8.7
