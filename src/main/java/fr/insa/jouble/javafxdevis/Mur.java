/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.jouble.javafxdevis;

/**
 *
 * @author Amadou Coulibaly
 */
public class Mur {
    int idMur, idRevetement;
    Coin debut, fin;
    final double surfacePorte = 1.89*900;
    final double surfaceFenetre = 1.44*900;
    int nbfenetres;
     int nbportes;
    
    
    Mur(int id, Coin dc, Coin fc, int idRevetement)
    {
        this.idMur=id;
        this.debut=dc;
        this.fin=fc;
        this.idRevetement = idRevetement;
    }
    
    void afficher()
    {System.out.println("===== Mur =====");
        this.debut.afficher();
        this.fin.afficher();    
    }
    
    double longueur()
    {
        return(Math.sqrt((this.fin.cx-this.debut.cx)*(this.fin.cx-this.debut.cx) + (this.fin.cy-this.debut.cy)*(this.fin.cy-this.debut.cy)));
    }
    
    double surface(double hsp, int nbportes, int nbfenetres)
    {
        this.nbfenetres = nbfenetres;
        this.nbportes = nbportes;
        return(this.longueur()*hsp-surfaceFenetre*nbfenetres-surfacePorte*nbportes);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Mur;");
        sb.append(idMur);
        sb.append(";");
        sb.append(debut.getID());
        sb.append(";");
        sb.append(fin.getID());
        sb.append(";");
        sb.append(nbportes);
        sb.append(";");
        sb.append(nbfenetres);
        sb.append(";");
        sb.append(idRevetement);
        return sb.toString();
    }
    int getID() {
        return idMur;
    }
}
