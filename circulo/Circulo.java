package circulo;

import ponto.Ponto;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um círculo matemático no plano 2D.
 * Permite manipulação do centro e raio, atualização do raio
 * a partir de um ponto na borda, e conversão para/da representação JSON.
 * 
 * @author Amora Marinho Machado
 * @author Gabriel Azevedo Cruz
 * @author Gabriel Mechi Lima
 * @author Luiz Fernando de Marchi Andrade
 * @version 05/09/2025
 */
public class Circulo {

    /**
     * Centro do círculo
     */
    private Ponto centro;

    /**
     * Raio do círculo
     */
    private double raio;

    /**
     * Construtor do círculo a partir de um centro e raio.
     * 
     * @param centro ponto central do círculo
     * @param raio raio do círculo
     */
    public Circulo(Ponto centro, double raio) {
        setCentro(centro);
        setRaio(raio);
    }

    /**
     * Construtor do círculo a partir de coordenadas do centro e raio.
     * 
     * @param x coordenada x do centro
     * @param y coordenada y do centro
     * @param raio raio do círculo
     */
    public Circulo(double x, double y, double raio) {
        setCentro(new Ponto(x, y));
        setRaio(raio);
    }

    /**
     * Construtor de cópia.
     * 
     * @param c círculo a ser copiado
     */
    public Circulo(Circulo c) {
        setCentro(new Ponto(c.getCentro()));
        setRaio(c.getRaio());
    }

    /**
     * Retorna o centro do círculo.
     * 
     * @return objeto Ponto representando o centro
     */
    public Ponto getCentro() {
        return centro;
    }

    /**
     * Define o centro do círculo.
     * 
     * @param centro ponto central
     */
    public void setCentro(Ponto centro) {
        this.centro = centro;
    }

    /**
     * Retorna o raio do círculo.
     * 
     * @return valor do raio
     */
    public double getRaio() {
        return raio;
    }

    /**
     * Define o raio do círculo.
     * 
     * @param raio valor do raio
     */
    public void setRaio(double raio) {
        this.raio = raio;
    }    

    /**
     * Retorna uma representação textual do círculo.
     * 
     * @return string representando o círculo
     */
    @Override
    public String toString(){
        return "Circulo: \n Centro: " + getCentro().toString() + " Raio: " + getRaio();
    }

    /**
     * Atualiza o raio do círculo com base em um ponto na borda.
     * 
     * @param x posição x do ponto na borda
     * @param y posição y do ponto na borda
     */
    public void atualizarRaio(int x, int y) {
        double dx = x - getCentro().getX();
        double dy = y - getCentro().getY();
        setRaio(Math.sqrt(dx*dx + dy*dy));
    }

    /**
     * Converte o círculo para um JSONObject.
     * 
     * @return JSONObject representando o círculo
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("centro", centro.toJson());
        json.put("raio", this.raio);
        return json;
    }

    /**
     * Cria um círculo a partir de um JSONObject.
     * 
     * @param json JSONObject contendo os dados do círculo
     * @return novo objeto Circulo
     */
    public static Circulo fromJson(JSONObject json) {
        Ponto centro = Ponto.fromJson(json.getJSONObject("centro"));
        double raio = json.getDouble("raio");
        return new Circulo(centro, raio);
    }

    /**
     * Cria uma lista de círculos a partir de um JSONArray.
     * 
     * @param jsonArray JSONArray contendo objetos círculo
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
