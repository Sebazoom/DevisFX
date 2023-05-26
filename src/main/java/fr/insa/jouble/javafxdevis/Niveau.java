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
public class Niveau {
    int idNiveauAppart;
    double hsp;
    ArrayList<Appartement> listeAppart;

    public Niveau(int idNiveauAppart, double hsp, ArrayList<Appartement> listeAppart) {
        this.idNiveauAppart = idNiveauAppart;
        this.hsp = hsp;
        this.listeAppart = listeAppart;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Niveau;");
        sb.append(idNiveauAppart);
        sb.append(";");
        sb.append(hsp);
        sb.append(";");
        for(int i=0; i<=listeAppart.size()-1; i++){
            sb.append(listeAppart.get(i).getID());
            sb.append(";");
        }
        return sb.toString();
    }
    
    int getID() {
        return idNiveauAppart;
    }
    
}
