package reta;
import ponto.Ponto;
/**
 * Reta matematica.
 *
 * @author Julio
 * @version 12/08/2020
 */
public class Reta {

    /**
     * Atributos da reta
     */
    public Ponto p1, p2;

    /**
     * Constroi reta entre as coordenadas x1, y1 e x2, y2 (int)
     * 
     * @param x1 coordenada x de p1
     * @param y1 coordenada y de p1
     * @param x2 coordenada x de p2
     * @param y2 coordenada y de p2
     */
    public Reta(int x1, int y1, int x2, int y2) {
        setP1(new Ponto(x1, y1));
        setP2(new Ponto(x2, y2));
    }

    /**
     * Constroi reta entre as coordenadas x1, y1 e x2, y2 (double)
     * 
     * @param x1 coordenada x de p1
     * @param y1 coordenada y de p1
     * @param x2 coordenada x de p2
     * @param y2 coordenada y de p2
     */
    public Reta(double x1, double y1, double x2, double y2) {
        setP1(new Ponto(x1, y1));
        setP2(new Ponto(x2, y2));
    }

    /**
     * Construtor de reta entre P1 e P2
     * @param p1 ponto p1 da reta
     * @param p2 ponto p2 da reta
     */
    public Reta(Ponto p1, Ponto p2) {
        setP1(p1);
        setP2(p2);
    }

    /**
     * Gera uma copia da reta
     * @param r reata ser copiado
     */
    public Reta (Reta r){
        setP1(r.getP1());
        setP2(r.getP2());
    }

    /**
     * @param p ponto a ser copiado
     */
    public void setP1(Ponto p){
        this.p1 = p;
    }

    /**
     * @param p ponto a ser copiado
     */
    public void setP2(Ponto p){
        this.p2 = p;
    }

    /**
     * @return ponto p1
     */
    public Ponto getP1(){
        return this.p1;
    }

    /**
     * @return ponto p2
     */
    public Ponto getP2(){
        return this.p2;
    }
    /**
     * @return valor de m da equacao: y=mx+b
     */
    public double calcularM(){
        // m = (y2-y1)/(x2-x1)
        double m = (getP2().getY() - getP1().getY())/(getP2().getX() - getP1().getX());
        return m;
    }
    /**
     * @return valor de b da equacao: y=mx+b
     */
    public double calcularB(){
        //b = y1 - mx1
        double b = getP1().getY() - calcularM()*getP1().getX();
        return b;
    }


    /**
     * Method toString
     *
     * @return The return value
     */
    public String toString(){
        String s = "P1: " + getP1().toString() + " P2: " + getP2().toString();
        s = s + "\nEq. da reta: y = " + calcularM() + "*x + " + calcularB();
        return s;
    }

}
