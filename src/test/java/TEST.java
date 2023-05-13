package fr.insa.jouble.javafxdevis;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;


public class TEST extends Application {

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

    Coin coin1, coin2;
private void createThirdScene() {
    Pane drawingPane = new Pane(); // Conteneur pour dessiner les points et les lignes
    drawingPane.setPrefSize(640, 480);

    VBox infoContainer = new VBox(10); // Conteneur pour afficher les informations
    infoContainer.setAlignment(Pos.TOP_RIGHT);
    infoContainer.setPadding(new Insets(10));
    infoContainer.setMaxWidth(200);
    int idCoin=1;
    int idMur=1;
    drawingPane.setOnMouseClicked(event -> {
        double x1, x2, y1, y2;
        if (coin1 == null) {
                // Créer un nouveau Coin pour le premier clic
                coin1 = new Coin(idCoin, event.getX(), event.getY());
                x1 = event.getX(); // Coordonnée X du clic de la souris
                y1 = event.getY(); // Coordonnée Y du clic de la souris
                Circle point = new Circle(event.getX(), event.getY(), 5, Color.BLACK); // Création du cercle représentant le point
                drawingPane.getChildren().add(point); // Ajout du cercle au conteneur
            } else {
                // Créer un nouveau Coin pour le deuxième clic
                coin2 = new Coin(idCoin+1, event.getX(), event.getY());
                x2 = event.getX(); // Coordonnée X du clic de la souris
                y2 = event.getY(); // Coordonnée Y du clic de la souris
                Circle point = new Circle(event.getX(), event.getY(), 5, Color.BLACK); // Création du cercle représentant le point
                drawingPane.getChildren().add(point); // Ajout du cercle au conteneur
                
                double distance = calculateDistance(coin1.getX(), coin1.getY(), x2, y2);
                // Affichage de la distance
            Label distanceLabel = new Label("Distance: " + distance + " pixels");
            distanceLabel.setStyle("-fx-text-fill : red;");
            // Affichage de la question
            Label heightLabel = new Label("Quelle est la hauteur du mur?");

            // Zone de réponse
            TextField heightInput = new TextField();
            heightInput.setOnKeyPressed(event1 -> {
                if (event1.getCode() == KeyCode.ENTER) {
                    String heightText = heightInput.getText();
                    double height = Double.parseDouble(heightText);

                    // Calcul de l'aire du mur
                    double area = distance * height;

                    // Affichage de l'aire du mur
                    Label areaLabel = new Label("Aire du mur: " + area + " pixels carrés");
                    areaLabel.setStyle("-fx-text-fill: green;");
                    // Ajout des labels au conteneur
                    infoContainer.getChildren().clear();
                    infoContainer.getChildren().addAll(distanceLabel, heightLabel, heightInput, areaLabel);
                }
            });

            // Ajout des labels au conteneur
            infoContainer.getChildren().clear();
            infoContainer.getChildren().addAll(distanceLabel, heightLabel, heightInput);
        

            // Créer un nouveau mur avec les deux Coins
            Mur mur = new Mur(idMur, coin1, coin2);

            // Dessiner le mur
            Line ligne = new Line(coin1.getX(), coin1.getY(), coin2.getX(), coin2.getY());
            ligne.setStrokeWidth(5);
            drawingPane.getChildren().add(ligne);

            // Réinitialiser le premier Coin
            coin1 = null;
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

    HBox rootContainer = new HBox(10);
    rootContainer.getChildren().addAll(drawingPane, infoContainer);

    VBox mainRoot = new VBox(10);
    mainRoot.setAlignment(Pos.CENTER);
    mainRoot.getChildren().addAll(rootContainer, button);

    thirdScene = new Scene(mainRoot, 800, 480);
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