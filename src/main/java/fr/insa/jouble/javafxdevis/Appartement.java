/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.jouble.javafxdevis;

import java.util.ArrayList;

/**
 *
 * @author sjoub
 */
public class Appartement {
    int idAppartement;
    int idNiveauAppart;
    ArrayList<Piece> listePiece;

    public Appartement(int idAppartement, int idNiveauAppart, ArrayList<Piece> listePiece) {
        this.idAppartement = idAppartement;
        this.idNiveauAppart = idNiveauAppart;
        this.listePiece = listePiece;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Appartement;");
        sb.append(idAppartement);
        sb.append(";");
        sb.append(idNiveauAppart);
        sb.append(";");
        for(int i=0; i<=listePiece.size()-1; i++){
            sb.append(listePiece.get(i).getID());
            sb.append(";");
        }
        return sb.toString();
    }
    
    int getID() {
        return idAppartement;
    }
    
}
