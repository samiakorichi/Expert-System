package part_1.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import part_1.raisoneur.Raisonneur;
import org.json.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class Main_Controller implements Initializable {


    int margin_label_left = 10;
    int margin_label_top = 5;
    int margin_combox_left = 30;
    int margin_combox_top = 5;

    private JSONObject selected_base;
    private ArrayList<JSONObject> bases;

    String base_folder = System.getProperty("user.dir") + "\\src\\part_1\\bases";


    @FXML
    private VBox facts_container;

    @FXML
    private VBox domain_container;

    @FXML
    private Button select_rules_btn;


    @FXML
    void select_rules(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/Rules_Scene.fxml"));
        Parent rules_scene = loader.load();

        Rules_Controller controller = loader.getController();

        controller.init(bases, selected_base);

        Stage window = (Stage) select_rules_btn.getScene().getWindow();
        window.setScene(new Scene(rules_scene));

    }


    @FXML
    void reason(ActionEvent event) {

        JSONObject rules = selected_base.getJSONObject("rules");
        String but  = selected_base.getString("but");
        HashMap<String, String> values = get_values();

        String result = Raisonneur.raisonner(rules, values, but);
        System.out.println(result);


        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(null);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().add(new Text(result));
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();


    }

    private HashMap<String, String> get_values(){

        HashMap<String, String> values = new HashMap<>();

        ObservableList<Node> nodes = facts_container.getChildren();

        for (int i = 0; i < nodes.size();){

            Text text = (Text) nodes.get(i);
            String key = text.getText();

            i = i + 1;

            ComboBox comboBox = (ComboBox) nodes.get(i);
            String value = (String) comboBox.getSelectionModel().getSelectedItem();

            i = i + 1;

            values.put(key, value);
        }

        return  values;
    }

    private ArrayList<JSONObject> load_bases(){

        ArrayList<JSONObject> files_content = new ArrayList<>();
        try {

            for(String file : Objects.requireNonNull(new File(base_folder).list())){

                Path path = Paths.get(base_folder + "//" + file);
                String content = Files.readString(path, StandardCharsets.UTF_8);

                JSONObject jsonObject = new JSONObject(content);
                files_content.add(jsonObject);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return files_content;
    }

    private void select_base(JSONObject base){

        this.selected_base = base;

        facts_container.getChildren().clear();

        JSONObject facts = base.getJSONObject("facts");

        for (Iterator<String> it = facts.keys(); it.hasNext(); ) {
            String fact = it.next();

            Text text = new Text(fact);
            text.getStyleClass().add("label");
            Translate translate_text = new Translate(text.getX() + margin_label_left, text.getY() + margin_label_top,0);
            text.getTransforms().add(translate_text);
            facts_container.getChildren().add(text);

            ComboBox<String> comboBox = new ComboBox<>();
            ObservableList<String> obs = FXCollections.observableArrayList();



            for (Object value : facts.getJSONArray(fact)) {
                obs.add(value.toString());}

            comboBox.setItems(obs);

            Translate translate_combo = new Translate(comboBox.getLayoutX() + margin_combox_left, comboBox.getLayoutX() + margin_combox_top,0);
            comboBox.getTransforms().add(translate_combo);
            comboBox.setPrefWidth(facts_container.getPrefWidth());
            facts_container.getChildren().add(comboBox);

        }
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println(System.getProperty("user.dir"));
        System.out.println(base_folder);


        bases = load_bases();
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

        this.selected_base = bases.get(0);
        select_base(this.selected_base);


    }
}