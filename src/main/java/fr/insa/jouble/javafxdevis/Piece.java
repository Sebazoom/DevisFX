package fr.insa.jouble.javafxdevis;
import java.util.ArrayList;
/**
 *
 * @author sjoub
 */
public class Piece {
    int idPiece, idSol, idPlafond;
    ArrayList<Mur> listesMur;

    Piece(int idPiece, int idSol, int idPlafond, ArrayList<Mur> listesMur) {
        this.idPiece = idPiece;
        this.idSol = idSol;
        this.idPlafond = idPlafond;
        this.listesMur = listesMur;
    }
    
   
   
}
