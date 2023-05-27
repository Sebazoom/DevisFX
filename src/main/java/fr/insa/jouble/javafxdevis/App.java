package fr.insa.jouble.javafxdevis;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
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
    private Scene finScene;
    String ligne;
    int compt = 0;
    int firstpoint = 0;
    boolean piecefinie =false;
    double area;
    double areaSol;
    double areaPlafond;
    int idCoin = 1;
    int idSol = 1;
    int idPlafond = 1;
    int idMur = 1;
    int idPiece = 1;
    int idAppart = 1;
    int idNiveau = 1;
    int idRevetement;
    int nbrfenetre = 0, nbrporte = 0;
    String[] morceauxSplit = new String[6];
    double prix = -1, hmax=0;
    ArrayList<Double> prixsurface = new ArrayList<>();
    // Zone de réponse
    TextField heightInput = new TextField();
    TextField porteInput = new TextField();
    TextField fenetreInput = new TextField();
    
    ArrayList<Coin> ListeCoin = new ArrayList<>();
    ArrayList<Mur> ListeMur = new ArrayList<>();
    ArrayList<Sol> ListeSol = new ArrayList<>();
    ArrayList<Plafond> ListePlafond = new ArrayList<>();
    ArrayList<Piece> ListePiece = new ArrayList<>();
    ArrayList<Coin> ListeCoinTEMP = new ArrayList<>();
    ArrayList<Mur> ListeMurTEMP = new ArrayList<>();
    ArrayList<Piece> ListePieceTEMP = new ArrayList<>();
    ArrayList<Appartement> ListeAppart = new ArrayList<>();
    ArrayList<Appartement> ListeAppartTEMP = new ArrayList<>();
    ArrayList<Niveau> ListeNiveau = new ArrayList<>();

    
    

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
    
    private Scene finScene() {
        double prix = 0;
        
        for(int i=0; i<=prixsurface.size()-1;i++){
            prix = prix+ prixsurface.get(i);
        }
        
        Label prixtotal = new Label("Le prix total de votre batîment est :");
        Label prixtotal2 = new Label(Math.ceil(prix*100)/100 + " euros");
        
        Button button = new Button("Terminer");
        button.setOnAction(event -> {
            primaryStage.close();
        });

        VBox root = new VBox(10); // espacement de 10 pixels
        root.getChildren().addAll(prixtotal,prixtotal2, button);
        root.setAlignment(Pos.CENTER); // alignement vertical au centre

        // Création du conteneur BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(root);
        
        Scene scene = new Scene(borderPane, 800, 480);

        return scene;
    }
    

    private void createMainScene() {
        Button button = new Button("Cliquez-moi !");
        button.setOnAction(event -> primaryStage.setScene(thirdScene));

        StackPane mainRoot = new StackPane();
        mainRoot.getChildren().add(button);

        mainScene = new Scene(mainRoot, 640, 480);
    }

    private Scene createSceneRevetement(boolean mur, boolean sol, boolean plafond, double area) {
      
        ArrayList<String> listeRevetement = ProjetDevisBatiment.RevetementDispo(mur, sol, plafond);


        // Création de la liste des revêtements disponibles
        ListView<String> listView = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList();
        for (int j = 0; j <= listeRevetement.size()-1; j++) {
            ligne = listeRevetement.get(j);
            morceauxSplit = ligne.split(";");
            items.add(morceauxSplit[0] + " : " +morceauxSplit[1]+" à "+ morceauxSplit[5]+ " euros");
        }
        listView.setItems(items);
        Label ConsigneLabel = new Label("Indiquez l'indice du revetement choisi.");
        // Création de la zone d'entrée clavier
        TextField textField = new TextField();
        textField.setPrefWidth(200);
        
        textField.setOnAction(event -> {
            int prixTexte = Integer.parseInt(textField.getText());
            try {
                prix = ProjetDevisBatiment.LectureRevetement(prixTexte);
            } catch (NumberFormatException e) {
                prix = 0;
            }
            
            prixsurface.add(prix * area/900);
            idRevetement = prixTexte;
            primaryStage.setScene(thirdScene);
        });
        
        // Création du bouton pour valider la saisie
        Button button = new Button("Valider");
        button.setOnAction (event -> {
            int prixTexte = Integer.parseInt(textField.getText());
            try {
                prix = ProjetDevisBatiment.LectureRevetement(prixTexte);
            } catch (NumberFormatException e) {
                prix = 0;
            }
            prixsurface.add(prix * area/900);
            idRevetement = prixTexte;
            primaryStage.setScene(thirdScene);
        });

        // Création du conteneur VBox pour la liste des revêtements et la zone d'entrée clavier
        VBox root = new VBox(10); // espacement de 10 pixels
        root.getChildren().addAll(ConsigneLabel,listView, textField, button);
        root.setAlignment(Pos.CENTER); // alignement vertical au centre

        // Création du conteneur BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(root); // placement du conteneur VBox au centre

        // Création de la scène
        Scene scene = new Scene(borderPane, 850, 480);
        return scene;
    }
    
    
    public void createThirdScene() throws IOException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Pane drawingPane = new Pane(); // Conteneur pour dessiner les points et les lignes
        drawingPane.setPrefSize(640, 480);
        
        
        
        final double rayon = 15.0;  // rayon de la zone de recherche autour du clic de souris
        
        VBox infoContainer = new VBox(10); // Conteneur pour afficher les informations
        infoContainer.setAlignment(Pos.TOP_RIGHT);
        infoContainer.setPadding(new Insets(10));
        infoContainer.setMaxWidth(250);

         
        // BOUTON POUR OUVRIR LA SCENE CREATESCENEREVETEMENT
        Button MurRevetement = new Button("Choisir un revetement pour un mur");
        MurRevetement.setOnAction(event -> {
            Scene sceneRevetement = createSceneRevetement(true,false,false, area);
            primaryStage.setScene(sceneRevetement);
            if (!piecefinie) {
                drawingPane.setDisable(false);
                compt = 0;//Si on clique sur le bouton, on peut à nouveau dessiner les nouveaux 
            }
        });
        Button SolRevetement = new Button("Choisir un revetement pour un sol");
        SolRevetement.setOnAction(event -> {
            Scene sceneRevetement = createSceneRevetement(false,true,false, areaSol);
            primaryStage.setScene(sceneRevetement);
        });
        Button PlafondRevetement = new Button("Choisir un revetement pour un plafond");
        PlafondRevetement.setOnAction(event -> {
            Scene sceneRevetement = createSceneRevetement(false,false,true, areaPlafond);
            primaryStage.setScene(sceneRevetement);
        });
        
        Button buttonPieceSuivante = new Button("Passer à la pièce suivante");
        buttonPieceSuivante.setOnAction(event -> {
           drawingPane.getChildren().clear();
           drawingPane.setDisable(false);
           
           compt = 0;
           firstpoint = 0;
           idCoin--;
           piecefinie = false;
        });
        
        Button buttonAppartSuivant = new Button("Passer à l'appartement suivant");
        buttonAppartSuivant.setOnAction(event -> {
           drawingPane.getChildren().clear();
           drawingPane.setDisable(false);
           Appartement appart= new Appartement(idAppart, idNiveau, ListePieceTEMP);
           ListeAppart.add(appart);
           ListeAppartTEMP.add(appart);
           ListePieceTEMP.clear();
           compt = 0;
           firstpoint = 0;
           piecefinie = false;
           idCoin--;
           idAppart++;
        });
        
        Button buttonNiveauSuivant = new Button("Passer au niveau suivant");
        buttonNiveauSuivant.setOnAction(event -> {
           drawingPane.getChildren().clear();
           drawingPane.setDisable(false);
           Niveau niveau = new Niveau (idNiveau, hmax, ListeAppartTEMP);
           ListeNiveau.add(niveau);
           ListeAppartTEMP.clear();
           compt = 0;
           firstpoint = 0;
           piecefinie = false;
           idCoin--;
           idNiveau++;
        });
        
        
        drawingPane.setOnMouseClicked(event -> {
            compt++; 
            System.out.println(idCoin);
            System.out.println(idMur);
            if(compt==0 || compt==1) {
                drawingPane.setDisable(false);
            } else {drawingPane.setDisable(true);}
            double x = event.getX(); // Coordonnée X du clic de la souris
            double y = event.getY(); // Coordonnée Y du clic de la souris
            boolean r = false;
            for (Coin test : ListeCoin) {
                double dist = calculateDistance(test.getX(), test.getY(), x, y);
                if (dist <= rayon) {
              
                    // un point existe déjà dans le rayon spécifié
                    drawingPane.setDisable(true);
                    Coin debut = ListeCoin.get(idCoin-2);
                    Coin fin = test;
                    Mur mur = new Mur(idMur, debut, fin, idRevetement);
                    ListeMur.add(mur);
                    ListeMurTEMP.add(mur);
                    
                    Line ligne = new Line(debut.getX(), debut.getY(), fin.getX(), fin.getY());
                    ligne.setStrokeWidth(5); ligne.setStroke(Color.BLACK);
                    drawingPane.getChildren().add(ligne);
                    
                    Sol sol = new Sol(idSol, new ArrayList<>(ListeCoinTEMP), new ArrayList<>(ListeMurTEMP), idRevetement); // copie de la liste ListeCoinTEMP pour éviterde modifier la liste originale
                    areaSol = sol.surface();
                    ListeSol.add(sol);

                    // création d'un nouveau Plafond
                    Plafond plafond = new Plafond(idPlafond, new ArrayList<>(ListeCoinTEMP), new ArrayList<>(ListeMurTEMP), idRevetement);
                    // copie de la liste ListeCoinTEMP pour éviter de modifier la liste originale
                    areaPlafond = plafond.surface();
                    ListePlafond.add(plafond);
                    
                    // effacement des éléments de la liste ListeCoinTEMP
                    ListeCoinTEMP.clear();
                    
                    Piece piece = new Piece(idPiece, new ArrayList<>(ListeMurTEMP),idSol, idPlafond); 
                    ListePiece.add(piece);
                    ListePieceTEMP.add(piece);
                    ListeMurTEMP.clear();
                    idMur++;
                    idSol++;
                    idPlafond++;
                    idPiece++;
                    r=true;
                    piecefinie = true;
                    break;
                    
                }
            }
            
            
            /*
            if (select != null) {
                // appliquer un style de sélection au point sélectionné
                select.setFill(Color.BLUE);
                select.setStroke(Color.RED);
                select.setStrokeWidth(2.0);
            }
            */
            if (idPiece > 1 && firstpoint==0) {
                if(!r){
                    Circle point = new Circle(x, y, 5, Color.BLACK); // Création du cercle représentant le point

                    drawingPane.getChildren().add(point); // Ajout du cercle au conteneur
                    Coin coin = new Coin(idCoin,x, y); // Création d'un objet Coin représentant le point
                    ListeCoin.add(coin);
                    ListeCoinTEMP.add(coin);
                }
            } 
            
            else {
                if(!r){
                    Circle point = new Circle(x, y, 5, Color.BLACK); // Création du cercle représentant le point

                    drawingPane.getChildren().add(point); // Ajout du cercle au conteneur
                    Coin coin = new Coin(idCoin,x, y); // Création d'un objet Coin représentant le point
                    ListeCoin.add(coin);
                    ListeCoinTEMP.add(coin);
                    
                    if (ListeCoin.size() > 1) {
                        Coin debut = ListeCoin.get(idCoin-2);
                        Coin fin = ListeCoin.get(idCoin-1);
                        Mur mur = new Mur(idMur, debut, fin, idRevetement);
                        ListeMur.add(mur);
                        ListeMurTEMP.add(mur);
                        Line ligne = new Line(debut.getX(), debut.getY(), fin.getX(), fin.getY());
                        ligne.setStrokeWidth(5); ligne.setStroke(Color.BLACK);
                        drawingPane.getChildren().add(ligne);
                        idMur++;
                        drawingPane.setDisable(true);
                    }
                }
            }
               
         
            
            
            if (!ListeMur.isEmpty()) {
                // S'il y a déjà un point précédent, on calcule la distance entre les deux points
                double distance = ListeMur.get(idMur-2).longueur();
                

                // Affichage de la distance
                Label distanceLabel = new Label("Distance: " + Math.ceil(distance/30*100)/100 + " mètres");
                distanceLabel.setStyle("-fx-text-fill : red;");
                // Affichage de la question
                Label heightLabel = new Label("Quelle est la hauteur du mur ?");
                Label porteLabel = new Label("Combien y a-t-il de portes dans le mur ?");
                Label fenetreLabel = new Label("Combien y a-t-il de fenetres dans le mur ?");

                
                
                infoContainer.getChildren().clear();
                infoContainer.getChildren().addAll(distanceLabel, heightLabel, heightInput, porteLabel, porteInput, fenetreLabel, fenetreInput);
            
                porteInput.setOnKeyPressed(event2 -> {
                    if (event2.getCode() == KeyCode.ENTER) {
                        String porteText = porteInput.getText();
                        nbrporte = Integer.parseInt(porteText);
                    }
                });
                
                fenetreInput.setOnKeyPressed(event3 -> {
                    if (event3.getCode() == KeyCode.ENTER) {
                        String fenetreText = fenetreInput.getText();
                        nbrfenetre = Integer.parseInt(fenetreText);
                        
                    }
                });
                
                heightInput.setOnKeyPressed(event1 -> {
                    if (event1.getCode() == KeyCode.ENTER) {
                        String heightText = heightInput.getText();
                        double height = 30*Double.parseDouble(heightText);
                        if (height>hmax){
                            hmax=height;
                        }
                        // Calcul de l'aire du mur
                        this.area = ListeMur.get(idMur-2).surface(height, nbrporte, nbrfenetre);
                        // Affichage de l'aire du mur
                        Label areaLabel = new Label("Aire du mur: " + Math.ceil(area/900*100)/100 + " mètres carrés");
                        areaLabel.setStyle("-fx-text-fill: green;");
                        if(piecefinie){
                        infoContainer.getChildren().clear();
                        infoContainer.getChildren().addAll(distanceLabel, areaLabel, MurRevetement, SolRevetement, PlafondRevetement);
                        } else {
                            infoContainer.getChildren().clear();
                            infoContainer.getChildren().addAll(distanceLabel, areaLabel, MurRevetement);
                        }
                    }
                });
                
                
                // Ajout des labels au conteneur
            }    
        idCoin++;  
        firstpoint++;  
            //previousPoint[0] = point; // Met à jour le point précédent
        });
        
        Button button = new Button("Terminer");
        button.setOnAction(event ->  { primaryStage.setScene(finScene());
            FileWriter fileWriter = null;
            BufferedWriter bufferedWriter = null;
            
            try {
                fileWriter = new FileWriter("DevisBatiment.txt");
                bufferedWriter = new BufferedWriter(fileWriter);

                // Ajoute le code pour traiter la liste des points ici
                for (Coin coin : ListeCoin) {
                    // Traiter chaque objet Coin de la liste
                    bufferedWriter.write(coin.toString());
                    bufferedWriter.newLine();
                }
                bufferedWriter.flush();
                
                
                for (Mur mur : ListeMur) {
                    // Traiter chaque objet Mur de la liste
                    bufferedWriter.write(mur.toString());
                    bufferedWriter.newLine();
                }
                bufferedWriter.flush();
                
                for (Sol sol : ListeSol) {
                    // Traiter chaque objet Sol de la liste
                    bufferedWriter.write(sol.toString());
                    bufferedWriter.newLine();
                }
                bufferedWriter.flush();
                
                for (Plafond plafond : ListePlafond) {
                    // Traiter chaque objet Plafond de la liste
                    bufferedWriter.write(plafond.toString());
                    bufferedWriter.newLine();
                }
                bufferedWriter.flush();
                
                for (Piece piece : ListePiece) {
                    // Traiter chaque objet Piece de la liste
                    bufferedWriter.write(piece.toString());
                    bufferedWriter.newLine();
                }
                bufferedWriter.flush();
                
                for (Appartement appart : ListeAppart) {
                    // Traiter chaque objet Appartement de la liste
                    bufferedWriter.write(appart.toString());
                    bufferedWriter.newLine();
                }
                bufferedWriter.flush();
                
                for (Niveau niveau : ListeNiveau) {
                    // Traiter chaque objet Coin de la liste
                    bufferedWriter.write(niveau.toString());
                    bufferedWriter.newLine();
                }
                bufferedWriter.flush();
                
                bufferedWriter.close();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        
        });
        
                /*      Future sauvegarde à l'arrache
        // Affichage des pièces sur le conteneur
        for (Piece piece : pieces) {
            for (Coin coin : piece.getCoins()) {
                Circle circle = new Circle(coin.getX(), coin.getY(), 5, Color.BLACK);
                pane.getChildren().add(circle);
            }
        }
        
        */

        HBox rootContainer = new HBox(10);
        rootContainer.getChildren().addAll(drawingPane, infoContainer);

        VBox Root1 = new VBox(10);
        Root1.setAlignment(Pos.CENTER_LEFT);
        Root1.getChildren().addAll(buttonPieceSuivante, buttonAppartSuivant, buttonNiveauSuivant);
        VBox Root2 = new VBox(10);
        Root2.setAlignment(Pos.CENTER);
        Root2.getChildren().addAll(button);
        VBox mainRoot = new VBox(rootContainer, Root1, Root2);
        thirdScene = new Scene(mainRoot, 850, 480);
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