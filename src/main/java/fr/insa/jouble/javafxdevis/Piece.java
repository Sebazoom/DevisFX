package fr.insa.jouble.javafxdevis;
import java.util.ArrayList;
/**
 *
 * @author sjoub
 */
public class Piece {
    int idPiece;
    ArrayList<Coin> listeCoin;
    ArrayList<Mur> listeMur;

    Piece(int id, ArrayList<Coin> listeCoin, ArrayList<Mur> listeMur) {
        this.idPiece = id;
        this.listeCoin = listeCoin;
        this.listeMur = listeMur;
    }
    
   
   
}
