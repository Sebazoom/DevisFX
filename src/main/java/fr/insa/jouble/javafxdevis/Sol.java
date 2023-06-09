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
public class Sol {
    int idSol, idRevetement;
    ArrayList<Coin> listeCoin;
    // ArrayList<Revetements> listeRevetements;
    // private Pane drawingPane;
    
    Sol(int idSol, ArrayList<Coin> listeCoin, int idRevetement) {
        this.idSol = idSol;
        this.listeCoin = listeCoin;
        this.idRevetement = idRevetement;
        /*double xhg=drawingPane.getWidth(), yhg=drawingPane.getHeight(), xhd=0, yhd=drawingPane.getWidth(), xbg=drawingPane.getWidth(), ybg=0, xbd=0, ybd=0;
        for (Coin test : listeCoin) {
            if (test.getID()<2) {
                xhg=xhd=xbg=xbd=test.getX();
                yhg=yhd=ybg=ybd=test.getY();
            }
            else {
                if (test.getX()<= xhg && test.getY()<= yhg){
                    xhg=test.getX();
                    yhg=test.getY();
                }
                if (test.getX()>= xhd && test.getY()<= yhd){
                    xhd=test.getX();
                    yhd=test.getY();
                }
                if (test.getX()<= xbg && test.getY()>= ybg){
                    xbg=test.getX();
                    ybg=test.getY();
                }
                if (test.getX()>= xbd && test.getY()>= ybd){
                    xbd=test.getX();
                    ybd=test.getY();
                }
            }
        } */ 
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sol;");
        sb.append(idSol);
        sb.append(";");
        for(int i=0; i<=listeCoin.size()-1; i++){
            sb.append(listeCoin.get(i).getID());
            sb.append(";");
        }
        sb.append(idRevetement);
        //ArrayList<String> listeRevetement = ProjetDevisBatiment.RevetementDispo(mur, sol, plafond);
        
        

        
        
        return sb.toString();
    }

     
    
    double distancePointSegment(ArrayList<Coin> listeCoin) {
        double dx = listeCoin.get(2).getX() - listeCoin.get(1).getX();
        double dy = listeCoin.get(2).getY() - listeCoin.get(1).getY();
        double numerateur = Math.abs(dy * listeCoin.get(0).getX() - dx * listeCoin.get(0).getY() + listeCoin.get(2).getX() * listeCoin.get(1).getY() - listeCoin.get(2).getY() * listeCoin.get(1).getX());
        double denominateur = Math.sqrt(dx * dx + dy * dy);
        double distance = numerateur / denominateur;
        return distance;
    }
    
    double distance(Coin debut, Coin fin)
    {
        return(Math.sqrt((fin.cx-debut.cx)*(fin.cx-debut.cx) + (fin.cy-debut.cy)*(fin.cy-debut.cy)));
    }
    
    double surface(){
        double distanceMax = 0;
        double distanceDeuxiemeMax = 0;
       
        for (int i = 0; i < this.listeCoin.size() - 1; i++) {
            for (int j = i + 1; j < this.listeCoin.size(); j++) {
                double d = distance(this.listeCoin.get(i), this.listeCoin.get(j));
                if (d > distanceMax) {
                    distanceDeuxiemeMax = distanceMax;
                    distanceMax = d;
                } else if (d > distanceDeuxiemeMax) {
                    distanceDeuxiemeMax = d;
                }
            }
        }

        if (listeCoin.size() == 3) {
            double base, hauteur;
            base = distance(listeCoin.get(1),listeCoin.get(2));
            hauteur = distancePointSegment(listeCoin);
            return base * hauteur * 0.5;
        } else {
            return distanceMax*distanceMax; 
        }
    }
}
    
    
    

