package part_1.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Rules_Controller {

    @FXML
    private VBox domain_container;

    @FXML
    private Button select_facts_btn;

    @FXML
    private Button select_rules_btn;

    @FXML
    private AnchorPane Right_Container;

    @FXML
    private AnchorPane rules_content;

    @FXML
    private VBox rules_container;

    private JSONObject selected_base;
    private ArrayList<JSONObject> bases;

    public void init(ArrayList<JSONObject> bases, JSONObject selected_base){

        this.bases = bases;
        this.selected_base = selected_base;

        domain_container.getChildren().clear();

        for(JSONObject base : bases){



            Button button = new Button();
            button.setText(base.getString("title"));
            button.getStyleClass().add("side_button");
            button.setPrefWidth(domain_container.getPrefWidth());
            button.setOnAction((actionEvent -> {

                String title = button.getText();

                if(! title.equals(this.selected_base)){
                    for(JSONObject b : bases){
                        if (b.get("title").equals(title))
                            select_base(b);

                    }
                }
            }));
            domain_container.getChildren().add(button);
        }

        this.selected_base = selected_base;
        rules_container.getChildren().clear();
        select_base(this.selected_base);
    }

    private void add_rule(JSONArray and, JSONObject then){

        Text text = new Text("IF");
        text.getStyleClass().addAll("key_word");

        rules_container.getChildren().add(text);

        for (int i = 0 ; i < and.length() ; i++){

            JSONObject condition = (JSONObject) and.get(i);
            String type_operator = condition.getString("type_operator");
            String operation = condition.getString("operation");
            String value_operator = condition.getString("value_operator");

            HBox hBox = new HBox();
            hBox.spacingProperty().setValue(30);
            hBox.paddingProperty().setValue(new Insets(0, 0, 0,30 ));
            Text condition_text = new Text(type_operator +" "+  operation +" "+ value_operator +" ");
            hBox.getChildren().add(condition_text);

            if (i != and.length() - 1){

                Text and_kw = new Text("AND");
                and_kw.getStyleClass().add("key_word");

                hBox.getChildren().add(and_kw);
            }

            rules_container.getChildren().add(hBox);
        }

        text = new Text("THEN");
        text.getStyleClass().addAll("key_word");

        rules_container.getChildren().add(text);

        HBox hBox = new HBox();
        String result = then.getString("result");
        text = new Text(result);
        hBox.getChildren().add(text);

        hBox.spacingProperty().setValue(30);
        hBox.paddingProperty().setValue(new Insets(0, 0, 0,30 ));

        rules_container.getChildren().add(hBox);

    }

    private void select_base(JSONObject base){

        rules_container.getChildren().clear();

        this.selected_base = base;
        JSONObject rules = base.getJSONObject("rules");



        for(String rule : rules.keySet()){

            JSONArray and = rules.getJSONObject(rule).getJSONArray("AND");
            JSONObject then = rules.getJSONObject(rule).getJSONObject("THEN");

            add_rule(and, then);

            Separator separator = new Separator();
            separator.paddingProperty().setValue(new Insets(5, 0, 5, 0));

            rules_container.getChildren().add(separator);

        }

    }


    @FXML
    void select_facts(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../fxml/Main_Scene.fxml"));
        Stage window = (Stage) select_rules_btn.getScene().getWindow();
        window.setScene(new Scene(root));

    }

}
