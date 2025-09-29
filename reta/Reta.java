package reta;
import ponto.Ponto;

import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

/**
 * Reta matematica definida por dois pontos.
 * Permite calcular a equacao da reta no formato y = mx + b.
 *
 * @author Amora Marinho Machado
 * @author Gabriel Azevedo Cruz
 * @author Gabriel Mechi Lima
 * @author Luiz Fernando de Marchi Andrade
 * @version 05/09/2025
 */
public class Reta {

    /** Primeiro ponto da reta */
    public Ponto p1;
    
    /** Segundo ponto da reta */
    public Ponto p2;

    /**
     * Constroi reta entre as coordenadas x1, y1 e x2, y2 (int).
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
     * Constroi reta entre as coordenadas x1, y1 e x2, y2 (double).
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
     * Construtor de reta entre P1 e P2.
     * 
     * @param p1 ponto p1 da reta
     * @param p2 ponto p2 da reta
     */
    public Reta(Ponto p1, Ponto p2) {
        setP1(p1);
        setP2(p2);
    }

    /**
     * Construtor de copia. Gera uma copia da reta.
     * 
     * @param r reta a ser copiada
     */
    public Reta (Reta r){
        setP1(r.getP1());
        setP2(r.getP2());
    }

    /**
     * Define o primeiro ponto da reta.
     * 
     * @param p ponto a ser definido
     */
    public void setP1(Ponto p){
        this.p1 = p;
    }

    /**
     * Define o segundo ponto da reta.
     * 
     * @param p ponto a ser definido
     */
    public void setP2(Ponto p){
        this.p2 = p;
    }

    /**
     * Retorna o primeiro ponto da reta.
     * 
     * @return ponto p1
     */
    public Ponto getP1(){
        return this.p1;
    }

    /**
     * Retorna o segundo ponto da reta.
     * 
     * @return ponto p2
     */
    public Ponto getP2(){
        return this.p2;
    }

    /**
     * Calcula o coeficiente angular (m) da equacao da reta: y = mx + b.
     * Formula: m = (y2-y1)/(x2-x1)
     * 
     * @return valor de m da equacao
     */
    public double calcularM(){
        double m = (getP2().getY() - getP1().getY())/(getP2().getX() - getP1().getX());
        return m;
    }

    /**
     * Calcula o coeficiente linear (b) da equacao da reta: y = mx + b.
     * Formula: b = y1 - mx1
     * 
     * @return valor de b da equacao
     */
    public double calcularB(){
        double b = getP1().getY() - calcularM()*getP1().getX();
        return b;
    }

    /**
     * Retorna representacao em String da reta.
     * Inclui os pontos e a equacao da reta.
     * 
     * @return string contendo informacoes da reta
     */
    public String toString(){
        String s = "P1: " + getP1().toString() + " P2: " + getP2().toString();
        s = s + "\nEq. da reta: y = " + calcularM() + "*x + " + calcularB();
        return s;
    }

    /**
     * Converte a reta para formato JSON.
     * 
     * @return JSONObject representando a reta
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("p1", p1.toJson());
        json.put("p2", p2.toJson());
        return json;
    }

    /**
     * Cria uma Reta a partir de um JSONObject.
     * 
     * @param json JSONObject contendo os dados da reta
     * @return novo objeto Reta
     */
    public static Reta fromJson(JSONObject json) {
        Ponto p1 = Ponto.fromJson(json.getJSONObject("p1"));
        Ponto p2 = Ponto.fromJson(json.getJSONObject("p2"));
        return new Reta(p1, p2);
    }

    /**
     * Cria uma lista de Retas a partir de um JSONArray.
     * 
     * @param jsonArray JSONArray contendo retas
     * @return lista de objetos Reta
     */
    public static List<Reta> fromJsonArray(JSONArray jsonArray) {
        List<Reta> retas = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            retas.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return retas;
    }
}