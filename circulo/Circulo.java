package circulo;
import ponto.Ponto;

import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa circulo matematico.
 * Representa um circulo definido por um ponto central e um raio.
 * 
 * @author Amora Marinho Machado
 * @author Gabriel Azevedo Cruz
 * @author Gabriel Mechi Lima
 * @author Luiz Fernando de Marchi Andrade
 * @version 05/09/2025
 */
public class Circulo {
    /** Centro do circulo */
    private Ponto centro;
    
    /** Raio do circulo */
    private double raio;

    /**
     * Construtor da classe circulo.
     * 
     * @param centro Ponto centro do circulo
     * @param raio raio do circulo
     */
    public Circulo(Ponto centro, double raio) {
        setCentro(centro);
        setRaio(raio);
    }

    /**
     * Construtor da classe circulo com coordenadas.
     * 
     * @param x coordenada x do centro do circulo
     * @param y coordenada y do centro do circulo
     * @param raio raio do circulo
     */
    public Circulo(double x, double y, double raio) {
        setCentro(new Ponto(x, y));
        setRaio(raio);
    }

    /**
     * Construtor de copia da classe circulo.
     * 
     * @param c circulo a ser copiado
     */
    public Circulo(Circulo c) {
        setCentro(new Ponto(c.getCentro()));
        setRaio(c.getRaio());
    }

    /**
     * Retorna o centro do circulo.
     * 
     * @return ponto central do circulo
     */
    public Ponto getCentro() {
        return centro;
    }

    /**
     * Define o centro do circulo.
     * 
     * @param centro ponto central a ser definido
     */
    public void setCentro(Ponto centro) {
        this.centro = centro;
    }

    /**
     * Retorna o raio do circulo.
     * 
     * @return raio do circulo
     */
    public double getRaio() {
        return raio;
    }

    /**
     * Define o raio do circulo.
     * 
     * @param raio raio a ser definido
     */
    public void setRaio(double raio) {
        this.raio = raio;
    }    

    /**
     * Retorna representacao em String do circulo.
     * 
     * @return string contendo informacoes do centro e raio
     */
    public String toString(){
        String s = "Circulo: \n Centro: " + getCentro().toString() + " Raio: " + getRaio();
        return s;
    }

    /**
     * Atualiza o raio do circulo com base em um ponto na borda.
     * Calcula a distancia euclidiana entre o centro e o ponto fornecido.
     * 
     * @param x posicao x do mouse (borda)
     * @param y posicao y do mouse (borda)
     */
    public void atualizarRaio(int x, int y) {
        double dx = x - getCentro().getX();
        double dy = y - getCentro().getY();
        setRaio(Math.sqrt(dx*dx + dy*dy));
    }

    /**
     * Converte o circulo para formato JSON.
     * 
     * @return JSONObject representando o circulo
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("centro", centro.toJson());
        json.put("raio", this.raio);
        return json;
    }

    /**
     * Cria um Circulo a partir de um JSONObject.
     * 
     * @param json JSONObject contendo os dados do circulo
     * @return novo objeto Circulo
     */
    public static Circulo fromJson(JSONObject json) {
        Ponto centro = Ponto.fromJson(json.getJSONObject("centro"));
        double raio = json.getDouble("raio");
        return new Circulo(centro, raio);
    }

    /**
     * Cria uma lista de Circulos a partir de um JSONArray.
     * 
     * @param jsonArray JSONArray contendo circulos
     * @return lista de objetos Circulo
     */
    public static List<Circulo> fromJsonArray(JSONArray jsonArray) {
        List<Circulo> circulos = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            circulos.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return circulos;
    }
}