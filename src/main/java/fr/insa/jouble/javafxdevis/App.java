package fr.insa.jouble.javafxdevis;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class App extends Application {

    private Stage primaryStage;
    private Scene welcomeScene;
    private Scene mainScene;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;

        createWelcomeScene();
        createMainScene();

        primaryStage.setScene(welcomeScene);
        primaryStage.setTitle("Projet Devis Batiment");
        primaryStage.show();
    }

    private void createWelcomeScene() {
        Label welcomeLabel = new Label("Bienvenue dans notre simulateur.");
        welcomeLabel.setStyle("-fx-font-size: 20px;");
        Label Nom1 = new Label("JOUBLE LAGADEC KONE-KABAMBA");
        
        Button arrowButton = new Button(">>");
        arrowButton.setOnAction(event -> primaryStage.setScene(mainScene));

        VBox welcomeRoot = new VBox(10); // met un espacement vertical de 10 pixels entre les éléments
        welcomeRoot.setAlignment(Pos.CENTER); // Centre les éléments verticalement
        welcomeRoot.getChildren().addAll(welcomeLabel, arrowButton, Nom1);

        StackPane.setAlignment(welcomeRoot, Pos.TOP_RIGHT); // pour aligner le VBox en haut à droite

        welcomeScene = new Scene(welcomeRoot, 640, 480);
    }

    private void createMainScene() {
        Button button = new Button("Cliquez-moi !");
        button.setOnAction(event -> {
            FileWriter fileWriter = null;
            BufferedWriter bufferedWriter = null;
            try {
                fileWriter = new FileWriter("DevisBatiment.txt");
                bufferedWriter = new BufferedWriter(fileWriter);

                // Ajoute le code de la classe ProjetDevisBatiment ici
                // ...

                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        StackPane mainRoot = new StackPane();
        mainRoot.getChildren().add(button);

        mainScene = new Scene(mainRoot, 640, 480);
    }

    public static void main(String[] args) {
        launch();
    }
}