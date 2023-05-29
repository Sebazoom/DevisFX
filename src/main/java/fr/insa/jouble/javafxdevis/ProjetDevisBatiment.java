/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package fr.insa.jouble.javafxdevis;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.util.*;
/**
 *
 * @author sjoub
 */
public class ProjetDevisBatiment {
    
    // Obtenir le répertoire de travail actuel de l'application
    static String workingDir = System.getProperty("user.dir");
    // Définir le chemin d'accès relatif du répertoire de sauvegarde
    static String saveDirectory = workingDir + File.separator + "src\\main\\java\\fr\\insa\\jouble\\javafxdevis\\CatalogueRevetement.txt";
    private static Map<Integer, Double> revetementsCache = new HashMap<>(); // Déclaration d'un Map pour stocker les revêtements lus depuis le fichier

    
    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        
    }
   
   
    

    public static Double LectureRevetement(int recherche) {
        if (revetementsCache.containsKey(recherche)) {
            // Si le revêtement a déjà été lu, on le récupère depuis le cache
            return revetementsCache.get(recherche);
        }
        String fileName = saveDirectory;
        Double prixunitaire = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] morceaux = line.split(";");
                if (morceaux[0].equals(Integer.toString(recherche))) {
                    try {
                        prixunitaire = Double.parseDouble(morceaux[5]);
                        break;
                    } catch (NumberFormatException e) {
                        System.err.println("Erreur : le prix unitaire n'est pas un nombre valide pour le revêtement " + recherche);
                        prixunitaire = null;
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        revetementsCache.put(recherche, prixunitaire); // On ajoute le revêtement au cache
        return prixunitaire;
    }
    
     public static Double LectureRevetementSauvegarde(int recherche, String fileName) {        
        Double prixunitaire = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String ligne = bufferedReader.readLine();
                    while (ligne != null) {
                        String[] morceaux = ligne.split(";");
                        if(morceaux[0].equals(Integer.toString(recherche))) {
                            prixunitaire = Double.parseDouble(morceaux[5]);
                        }
                        ligne = bufferedReader.readLine();
                    }
        }
        catch(FileNotFoundException err){
            System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
        catch (IOException err){
            System.out.println(" Erreur :\n "+err);}             
            
        return prixunitaire;
    }
  
    
    
    public static int NbrMur(String FileName) {
        int nombre = 0;
        try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(FileName));
                    String ligne = bufferedReader.readLine();
                    while (ligne != null) {
                           String[] morceaux = ligne.split(";");
                           ligne = bufferedReader.readLine();
                            if (morceaux[0].equals("Mur")) {
                                nombre = nombre + 1;
                            }
                    }
            } 
                catch(FileNotFoundException err){
                    System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
                catch (IOException err){
                    System.out.println(" Erreur :\n "+err);}             
        return nombre;    
    } 
    
    public static int NbrSol(String FileName) {
        int nombre = 0;
        try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(FileName));
                    String ligne = bufferedReader.readLine();
                    while (ligne != null) {
                           String[] morceaux = ligne.split(";");
                           ligne = bufferedReader.readLine();
                            if (morceaux[0].equals("Sol")) {
                                nombre = nombre + 1;
                            }
                    }
            } 
                catch(FileNotFoundException err){
                    System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
                catch (IOException err){
                    System.out.println(" Erreur :\n "+err);}             
        return nombre;    
    }
    
    public static int NbrPlafond(String FileName) {
        int nombre = 0;
        try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(FileName));
                    String ligne = bufferedReader.readLine();
                    while (ligne != null) {
                           String[] morceaux = ligne.split(";");
                           ligne = bufferedReader.readLine();
                            if (morceaux[0].equals("Plafond")) {
                                nombre = nombre + 1;
                            }
                    }
            } 
                catch(FileNotFoundException err){
                    System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
                catch (IOException err){
                    System.out.println(" Erreur :\n "+err);}             
        return nombre;    
    }
    
    
    public static double Distance(Coin c1, Coin c2) {
        double area=Math.sqrt((c2.cx-c1.cx)*(c2.cx-c1.cx) + (c2.cy-c1.cy)*(c2.cy-c1.cy));
        return area;
    }
    
    

    public static ArrayList RevetementDispo(boolean mur, boolean sol, boolean plafond) { // m=1 si mur, s=1 si sol, p=1 si plafond
        ArrayList<String> listeMur = new ArrayList<>();
        ArrayList<String> listeSol = new ArrayList<>();
        ArrayList<String> listePlafond = new ArrayList<>();
        
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(saveDirectory));
            String ligne = bufferedReader.readLine();
            
            while (ligne != null) {
                String[] morceaux = ligne.split(";");
                if (morceaux[2].equals("1")) {
                    listeMur.add(ligne);
                }
                if (morceaux[3].equals("1")) {
                    listeSol.add(ligne);
                }
                if (morceaux[4].equals("1")) {
                    listePlafond.add(ligne);
                }
                ligne = bufferedReader.readLine();

            }

        } catch (FileNotFoundException err) {
            System.out.println("Erreur : le fichier n’existe pas!\n " + err);
        } catch (IOException err) {
            System.out.println("Erreur :\n " + err);
        }
        
        if (mur) {
            return listeMur;
        }
        if (sol) {
            return listeSol;
        }
        else {
            return listePlafond;
        }
    }
    
    
    
     
    public static Coin CreaCoin (int idCoin, String fileName){
        try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
                    String ligne = bufferedReader.readLine();

                    while (ligne != null) {

                           String[] morceaux = ligne.split(";");
                           


                            if ((morceaux[0].equals("Coin"))&&(morceaux[1].equals(Integer.toString(idCoin)))) {
                                Coin c=new Coin(idCoin,Double.parseDouble(morceaux[2]),Double.parseDouble(morceaux[3]));
                                return c;
                            }
                            ligne = bufferedReader.readLine();

                    }

            } 

                catch(FileNotFoundException err){
                    System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
                catch (IOException err){
                    System.out.println(" Erreur :\n "+err);} 
                Coin C = new Coin(0,0,0);
                System.out.println("Coin" + C.toString());
                return C;
    }

    public static double MontantMurs (int idMur, String FileName) {

        double montant;
        double hsp = 0, surface = 0;
        int idPiece = 0, idAppart = 0, idNiveau = 0, idRevetement = 1;

        // On trouve dans quelle piece est située le mur 
        try {
            BufferedReader ReaderPiece = new BufferedReader(new FileReader(FileName));
            String ligne = ReaderPiece.readLine();

            while(ligne != null) {
                String[] morceaux = ligne.split(";");

                boolean verif = false;
                for (int i=3; i <= morceaux.length-1; i++) {
                    if(morceaux[i].equals(Integer.toString(idMur))) {
                        verif = true;
                    }
                }

                if (morceaux[0].equals("Piece") && verif == true) {
                    idPiece = Integer.parseInt(morceaux[1]);
                }

                ligne = ReaderPiece.readLine();
            }
        } 
        catch(FileNotFoundException err){
        System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
        catch (IOException err){
        System.out.println(" Erreur :\n "+err);}


        // On trouve dans quel appartement est située la pièce
        try {
            BufferedReader ReaderPiece = new BufferedReader(new FileReader(FileName));
            String ligne = ReaderPiece.readLine();

            while(ligne != null) {
                String[] morceaux = ligne.split(";");

                boolean verif = false;
                for (int i=2; i <= morceaux.length-1; i++) {
                    if(morceaux[i].equals(Integer.toString(idPiece))) {
                        verif = true;
                    }
                }

                if (morceaux[0].equals("Appartement") && verif == true) {
                    idAppart = Integer.parseInt(morceaux[1]);
                }

                ligne = ReaderPiece.readLine();
            }
        } 
        catch(FileNotFoundException err){
        System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
        catch (IOException err){
        System.out.println(" Erreur :\n "+err);}


        // On trouve dans quel niveau est situé l'appartement
        try {
            BufferedReader ReaderPiece = new BufferedReader(new FileReader(FileName));
            String ligne = ReaderPiece.readLine();

            while(ligne != null) {
                String[] morceaux = ligne.split(";");

                boolean verif = false;
                for (int i=2; i <= morceaux.length-1; i++) {
                    if(morceaux[i].equals(Integer.toString(idAppart))) {
                        verif = true;
                    }
                }

                if (morceaux[0].equals("Niveau") && verif == true) {
                    idNiveau = Integer.parseInt(morceaux[1]);
                }

                ligne = ReaderPiece.readLine();
            }
        } 
        catch(FileNotFoundException err){
        System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
        catch (IOException err){
        System.out.println(" Erreur :\n "+err);}


        try {
            BufferedReader ReaderPiece = new BufferedReader(new FileReader(FileName));
            String ligne = ReaderPiece.readLine();

            while(ligne != null) {
                String[] morceaux = ligne.split(";");
                ligne = ReaderPiece.readLine();
                if (morceaux[0].equals("Niveau") && morceaux[1].equals(Integer.toString(idNiveau))) {
                    hsp = Double.parseDouble(morceaux[2]);
                    System.out.println("Hauteur " +hsp);
                }
            }
        } 
        catch(FileNotFoundException err){
        System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
        catch (IOException err){
        System.out.println(" Erreur :\n "+err);}



        try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(FileName));
                String ligne = bufferedReader.readLine();
                
                while (ligne != null) {
                    
                    String[] morceaux = ligne.split(";");
                    
                    if(morceaux[0].equals("Mur") && morceaux[1].equals(Integer.toString(idMur))) {
                        Coin debut = CreaCoin(Integer.parseInt(morceaux[2]), FileName);
                        Coin fin = CreaCoin(Integer.parseInt(morceaux[3]), FileName);
                        Mur mur = new Mur(idMur, debut, fin, Integer.parseInt(morceaux[3]), Integer.parseInt(morceaux[4]), Integer.parseInt(morceaux[5]));
                        System.out.println("longEuR : " +Distance(debut, fin));
                        idRevetement = Integer.parseInt(morceaux[5]);
                        surface = mur.surface(hsp);
                        System.out.println("Surface : " +surface);
                    }
                    ligne = bufferedReader.readLine();
                }
                
                montant = surface*LectureRevetementSauvegarde(idRevetement, FileName);
                System.out.println("Montant: " + montant);
                
                return montant;
        }

        catch(FileNotFoundException err){
        System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
        catch (IOException err){
        System.out.println(" Erreur :\n "+err);} 
        return 0;                    
    }


    public static double MontantSol (int idSol, String FileName) {
        double montant = 0;
        double surface = 0;
        int idRevetement = 1;
        ArrayList <Coin> listeCoin = new ArrayList<>();

        try {
            BufferedReader ReaderPiece = new BufferedReader(new FileReader(FileName));
            String ligne = ReaderPiece.readLine();

            while(ligne != null) {
                String[] morceaux = ligne.split(";");
                ligne = ReaderPiece.readLine();
                if (morceaux[0].equals("Sol") && morceaux[1].equals(idSol)) {
                    idRevetement = Integer.parseInt(morceaux[morceaux.length-1]);
                    for(int i=0; i<=morceaux.length-2; i++){
                        listeCoin.add(CreaCoin(i,FileName));
                    }
                    Sol sol = new Sol(idSol, listeCoin, idRevetement);
                    surface = sol.surface();
                }
            }
        } 
        catch(FileNotFoundException err){
        System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
        catch (IOException err){
        System.out.println(" Erreur :\n "+err);}

        montant = surface * LectureRevetement(idRevetement);

        return montant;
    }

    public static double MontantPlafond (int idPlafond, String FileName) {
        double montant = 0;
        double surface = 0;
        int idRevetement = 1;
        ArrayList <Coin> listeCoin = new ArrayList<>();

        try {
            BufferedReader ReaderPiece = new BufferedReader(new FileReader(FileName));
            String ligne = ReaderPiece.readLine();

            while(ligne != null) {
                String[] morceaux = ligne.split(";");
                ligne = ReaderPiece.readLine();
                if (morceaux[0].equals("Sol") && morceaux[1].equals(idPlafond)) {
                    idRevetement = Integer.parseInt(morceaux[morceaux.length-1]);
                    for(int i=0; i<=morceaux.length-2; i++){
                        listeCoin.add(CreaCoin(i,FileName));
                    }
                    Sol sol = new Sol(idPlafond, listeCoin, idRevetement);
                    surface = sol.surface();
                }
            }
        } 
        catch(FileNotFoundException err){
        System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
        catch (IOException err){
        System.out.println(" Erreur :\n "+err);}

        montant = surface * LectureRevetement(idRevetement);

        return montant;
    }
    
    public static int Fonctiontest(int id, String FileName) {
        double montant;
        double hsp = 0, surface = 0;

        int idPiece = 0;
        int idAppart = 0;
        int idNiveau = 0;
        try {
            BufferedReader ReaderPiece = new BufferedReader(new FileReader(FileName));
            String ligne = ReaderPiece.readLine();

            while(ligne != null) {
                String[] morceaux = ligne.split(";");

                boolean verif = false;
                for (int i=3; i <= morceaux.length-1; i++) {
                    if(morceaux[i].equals(id)) {
                        verif = true;
                    }
                }

                if (morceaux[0].equals("Piece") && verif == true) {
                    idPiece = Integer.parseInt(morceaux[1]);
                }

                ligne = ReaderPiece.readLine();
            }
        } 
        catch(FileNotFoundException err){
        System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
        catch (IOException err){
        System.out.println(" Erreur :\n "+err);}


        // On trouve dans quel appartement est située la pièce
        try {
            BufferedReader ReaderPiece = new BufferedReader(new FileReader(FileName));
            String ligne = ReaderPiece.readLine();

            while(ligne != null) {
                String[] morceaux = ligne.split(";");

                boolean verif = false;
                for (int i=2; i <= morceaux.length-1; i++) {
                    if(morceaux[i].equals(idPiece)) {
                        verif = true;
                    }
                }

                if (morceaux[0].equals("Appartement") && verif == true) {
                    idAppart = Integer.parseInt(morceaux[1]);
                }

                ligne = ReaderPiece.readLine();
            }
        } 
        catch(FileNotFoundException err){
        System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
        catch (IOException err){
        System.out.println(" Erreur :\n "+err);}


        // On trouve dans quel niveau est situé l'appartement
        try {
            BufferedReader ReaderPiece = new BufferedReader(new FileReader(FileName));
            String ligne = ReaderPiece.readLine();

            while(ligne != null) {
                String[] morceaux = ligne.split(";");

                boolean verif = false;
                for (int i=2; i <= morceaux.length-1; i++) {
                    if(morceaux[i].equals(idAppart)) {
                        verif = true;
                    }
                }

                if (morceaux[0].equals("Niveau") && verif == true) {
                    idNiveau = Integer.parseInt(morceaux[1]);
                }

                ligne = ReaderPiece.readLine();
            }
        } 
        catch(FileNotFoundException err){
        System.out.println( "Erreur :le fichier n’existe pas!\n "+err);}
        catch (IOException err){
        System.out.println(" Erreur :\n "+err);}
        return idNiveau;
    }
        
         
            /*try
                {
                BufferedWriter Devisbat=new BufferedWriter(new FileWriter("Devisbat.txt",false));
                 Devisbat.write("Coins :");
                 Devisbat.newLine();
                for (int t=0;t<A.size(); t++){
                      Devisbat.write(A.get(t).idCoin+";"+A.get(t).cx+";"+A.get(t).cy);
                      Devisbat.newLine();
                  }
                Devisbat.write("Murs :");
                 Devisbat.newLine();
                for (int t=0;t<listeMurtot.size(); t++){
                      Devisbat.write(listeMurtot.get(t).idMur+";"+listeMurtot.get(t).d.idCoin+";"+listeMurtot.get(t).f.idCoin+";"+listeMurtot.get(t).nbrfenetres+";"+listeMurtot.get(t).nbrportes);
                      Devisbat.newLine();
                  }
                Devisbat.write("Sols :");
                 Devisbat.newLine();
                int b=0;
                  for (int t=0;t<listesol.size(); t++){
                      
                      Devisbat.write(listesol.get(t).idSol+";"+listeMurtot.get(b).idMur+";"+listeMurtot.get(b+1).idMur+";"+listeMurtot.get(b+2).idMur+";"+listeMurtot.get(b+3).idMur);
                      Devisbat.newLine();
                      b=b+4;
                  }
                  Devisbat.write("Plafonds :");
                 Devisbat.newLine();
                  b=0;
                  for (int t=0;t<listepla.size(); t++){
                      Devisbat.write(listepla.get(t).idPlafond+";"+listeMurtot.get(b).idMur+";"+listeMurtot.get(b+1).idMur+";"+listeMurtot.get(b+2).idMur+";"+listeMurtot.get(b+3).idMur);
                      Devisbat.newLine();
                      b=b+4;
                  }
                  
                
                    Devisbat.close();
                }
                catch (IOException err)
                {System.out.println("Erreur :\n"+err);}*/

        
    
    
    
    


}
