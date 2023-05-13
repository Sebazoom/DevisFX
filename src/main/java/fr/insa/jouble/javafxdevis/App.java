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
        welcomeLabel.setStyle("-fx-font-size: 25px;");
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

    final Circle[] previousPoint = {null}; // Utilisation d'un tableau pour stocker la référence du point précédent

    drawingPane.setOnMouseClicked(event -> {
        double x = event.getX(); // Coordonnée X du clic de la souris
        double y = event.getY(); // Coordonnée Y du clic de la souris

        Circle point = new Circle(x, y, 5, Color.BLACK); // Création du cercle représentant le point

        drawingPane.getChildren().add(point); // Ajout du cercle au conteneur

        if (previousPoint[0] != null) {
            // S'il y a déjà un point précédent, on calcule la distance entre les deux points
            double distance = calculateDistance(previousPoint[0].getCenterX(), previousPoint[0].getCenterY(), point.getCenterX(), point.getCenterY());
            Label distanceLabel = new Label("Distance: " + distance + " pixels");
            distanceLabel.setLayoutX((previousPoint[0].getCenterX() + point.getCenterX()) / 2);
            distanceLabel.setLayoutY((previousPoint[0].getCenterY() + point.getCenterY()) / 2);
            drawingPane.getChildren().add(distanceLabel);
        }

        previousPoint[0] = point; // Met à jour le point précédent
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

    thirdScene = new Scene(mainRoot, 640, 480);
}

private double calculateDistance(double x1, double y1, double x2, double y2) {
    double deltaX = x2 - x1;
    double deltaY = y2 - y1;
    return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
}
    public static void main(String[] args) {
        launch();
    }

    private double calculateDistance(Circle circle, Circle point) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}