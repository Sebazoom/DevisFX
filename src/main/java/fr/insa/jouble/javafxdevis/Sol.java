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
    ArrayList<Coin> listeCoin;
    ArrayList<Revetements> listeRevetements;
    private Pane drawingPane;
    
    Sol(int idSol, ArrayList<Coin> listeCoin, ArrayList<Revetements> listeRevetements) {
        this.idSol = idSol;
        this.listeCoin = listeCoin;
        this.listeRevetements = listeRevetements;
        double xhg=drawingPane.getWidth(), yhg=drawingPane.getHeight(), xhd=0, yhd=drawingPane.getWidth(), xbg=drawingPane.getWidth(), ybg=0, xbd=0, ybd=0;
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
        }
        
    }
}
