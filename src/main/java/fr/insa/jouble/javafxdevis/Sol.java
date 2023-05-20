/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.jouble.javafxdevis;

import java.util.ArrayList;
import javafx.scene.layout.Pane;

/**
 *
 * @author sjoub
 */
public class Sol {
    int idSol;
    double aire;
    ArrayList<Coin> listeCoin;
    ArrayList<Revetements> listeRevetements;
    private Pane drawingPane;
    
    Sol(int idSol, ArrayList<Coin> listeCoin, ArrayList<Revetements> listeRevetements) {
        this.idSol = idSol;
        this.listeCoin = listeCoin;
        this.listeRevetements = listeRevetements;
        double xhg=drawingPane.getWidth(), yhg=drawingPane.getHeight(), xhd=0, yhd=drawingPane.getWidth(), xbg=drawingPane.getWidth(), ybg=0, xbd=0, ybd=0;
        for (Coin test : listeCoin) {
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
        if (listeCoin.size()==3){
            Coin c1 = listeCoin.get(0);
            Coin c2 = listeCoin.get(1);
            Coin c3 = listeCoin.get(2);
            this.aire = AireTri(c1.getX(), c2.getX(), c3.getX(), c1.getY(), c2.getY(), c3.getY());
        }
        else {
            this.aire = AireRect(xhg,xhd,xbg,xbd,yhg,yhd,ybg,ybd);
        }
    }
    public static double AireRect(double x1, double x2, double x3, double x4, double y1, double y2, double y3, double y4){
        double largeur1 = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
        double largeur2 = Math.sqrt((x4-x3)*(x4-x3)+(y4-y3)*(y4-y3));
        double hauteur1 = Math.sqrt((x3-x1)*(x3-x1)+(y3-y1)*(y3-y1));
        double hauteur2 = Math.sqrt((x4-x2)*(x4-x2)+(y4-y2)*(y4-y2));
        return Math.abs(Math.max(largeur1, largeur2)*Math.max(hauteur1,hauteur2));
    }
    public static double AireTri(double x1, double x2, double x3, double y1, double y2, double y3) {
        double aire = 0.5 * (x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2));
        return Math.abs(aire);
    }
}