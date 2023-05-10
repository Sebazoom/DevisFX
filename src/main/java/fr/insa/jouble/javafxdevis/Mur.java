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
    int idMur;
    Coin debut, fin;
    final double surfacePorte = 1.89;
    final double surfaceFenetre = 1.44;
    
    
    
    Mur(int id, Coin dc, Coin fc)
    {
        this.idMur=id;
        this.debut=dc;
        this.fin=fc;
    }
    
    void afficher()
    {System.out.println("==== Mur =====");
        this.debut.afficher();
        this.fin.afficher();    
    }
    
    double longueur()
    {
        return(Math.sqrt((this.fin.cx-this.debut.cx)*(this.fin.cx-this.debut.cx) + (this.fin.cy-this.debut.cy)*(this.fin.cy-this.debut.cy)));
    }
    
    double surface()
    {
        System.out.println("\nEntrez la Hauteur du mur (hauteur sous-plafond)");
        double hsp=Lire.d();
        System.out.println("\nEntrez le nombre de portes situees sur le mur");
        int nbportes = Lire.i();
        System.out.println("\nEntrez le nombre de fenetres situees sur le mur");
        int nbfenetres = Lire.i();
        return(this.longueur()*hsp-surfaceFenetre*nbfenetres-surfacePorte*nbportes);
    }
    
    @Override
    public String toString() {
        return "Mur{" + "idMur=" + idMur + ", debut=" + debut + ", fin=" + fin + '}';
    }
    
}
