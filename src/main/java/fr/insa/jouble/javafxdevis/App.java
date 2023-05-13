package fr.insa.jouble.javafxdevis;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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


public class App extends Application {

    private Stage primaryStage;
    private Scene welcomeScene;
    private Scene mainScene;
    private Scene thirdScene;
    int idCoin = 1;
    int idMur = 1;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;

        createWelcomeScene();
        createMainScene();
        try {
            createThirdScene();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NoSuchFieldException ex) {
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }

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
    
    
    private void createThirdScene() throws IOException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Pane drawingPane = new Pane(); // Conteneur pour dessiner les points et les lignes
        drawingPane.setPrefSize(640, 480);
        


        ArrayList<Coin> ListeCoin = new ArrayList<>();
        ArrayList<Mur> ListeMur = new ArrayList<>();

        final Circle[] previousPoint = {null}; // Utilisation d'un tableau pour stocker la référence du point précédent

        VBox infoContainer = new VBox(10); // Conteneur pour afficher les informations
        infoContainer.setAlignment(Pos.TOP_RIGHT);
        infoContainer.setPadding(new Insets(10));
        infoContainer.setMaxWidth(200);

        drawingPane.setOnMouseClicked(event -> {
            double x = event.getX(); // Coordonnée X du clic de la souris
            double y = event.getY(); // Coordonnée Y du clic de la souris

            Circle point = new Circle(x, y, 5, Color.BLACK); // Création du cercle représentant le point

            drawingPane.getChildren().add(point); // Ajout du cercle au conteneur
            Coin coin = new Coin(idCoin,x, y); // Création d'un objet Coin représentant le point
            ListeCoin.add(coin);
            idCoin++;
            
            if (idCoin >= 2 && ListeCoin.size() >= idCoin) {
                Coin debut = ListeCoin.get(idCoin-2);
                Coin fin = ListeCoin.get(idCoin-1);
                Mur mur = new Mur(idMur, debut, fin);
                ListeMur.add(mur);
                idMur++;
            }
            

            if (previousPoint[0] != null) {
                // S'il y a déjà un point précédent, on calcule la distance entre les deux points
                double distance = calculateDistance(previousPoint[0].getCenterX(), previousPoint[0].getCenterY(), point.getCenterX(), point.getCenterY());

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
            }

            previousPoint[0] = point; // Met à jour le point précédent
        });

        Button button = new Button("Terminer");
        button.setOnAction(event -> {
            FileWriter fileWriter = null;
            BufferedWriter bufferedWriter = null;
            primaryStage.close();
            try {
                fileWriter = new FileWriter("DevisBatiment.txt");
                bufferedWriter = new BufferedWriter(fileWriter);

                // Ajoute le code pour traiter la liste des points ici
                for (Coin coin : ListeCoin) {
                    // Traiter chaque objet Coin de la liste
                    bufferedWriter.write(coin.toString());
                    bufferedWriter.newLine();
                }
                
                System.out.println("Nombre de murs: " + ListeMur.size());
                bufferedWriter.flush();
                for (Mur mur : ListeMur) {
                    // Traiter chaque objet Coin de la liste
                    bufferedWriter.write(mur.toString());
                    bufferedWriter.newLine();
                }

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