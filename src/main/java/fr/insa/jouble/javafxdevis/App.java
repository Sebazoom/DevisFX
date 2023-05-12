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
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;


public class App extends Application {

    private Stage primaryStage;
    private Scene welcomeScene;
    private Scene mainScene;
    private Scene thirdScene;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;

        createWelcomeScene();
        createMainScene();
        createThirdScene();

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
        button.setOnAction(event -> primaryStage.setScene(thirdScene));

        StackPane mainRoot = new StackPane();
        mainRoot.getChildren().add(button);

        mainScene = new Scene(mainRoot, 640, 480);
    }
    
    private void createThirdScene() {
        Pane drawingPane = new Pane(); // Conteneur pour dessiner les points et les lignes
    drawingPane.setPrefSize(640, 480);

    drawingPane.setOnMouseClicked(event -> {
        double x = event.getX(); // Coordonnée X du clic de la souris
        double y = event.getY(); // Coordonnée Y du clic de la souris

        Circle point = new Circle(x, y, 5, Color.BLACK); // Création du cercle représentant le point

        drawingPane.getChildren().add(point); // Ajout du cercle au conteneur

        if (drawingPane.getChildren().size() >= 2) {
            // S'il y a déjà au moins 2 points, on relie les deux derniers points par une ligne
            Node point1 = drawingPane.getChildren().get(drawingPane.getChildren().size() - 2);
            Node point2 = drawingPane.getChildren().get(drawingPane.getChildren().size() - 1);

            Line line = new Line();
            line.setStartX(point1.getTranslateX());
            line.setStartY(point1.getTranslateY());
            line.setEndX(point2.getTranslateX());
            line.setEndY(point2.getTranslateY());
            line.setStroke(Color.BLACK);

            drawingPane.getChildren().add(line); // Ajout de la ligne au conteneur
        }
    });

    Button button = new Button("Terminer");
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

    VBox mainRoot = new VBox(10);
    mainRoot.setAlignment(Pos.CENTER);
    mainRoot.getChildren().addAll(drawingPane, button);

    thirdScene = new Scene(mainRoot, 640, 480);}

    public static void main(String[] args) {
        launch();
    }
}