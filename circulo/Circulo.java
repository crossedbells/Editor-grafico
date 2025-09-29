package circulo;

import ponto.Ponto;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um c�rculo matem�tico no plano 2D.
 * Permite manipula��o do centro e raio, atualiza��o do raio
 * a partir de um ponto na borda, e convers�o para/da representa��o JSON.
 * 
 * @author Amora Marinho Machado
 * @author Gabriel Azevedo Cruz
 * @author Gabriel Mechi Lima
 * @author Luiz Fernando de Marchi Andrade
 * @version 05/09/2025
 */
public class Circulo {

    /**
     * Centro do c�rculo
     */
    private Ponto centro;

    /**
     * Raio do c�rculo
     */
    private double raio;

    /**
     * Construtor do c�rculo a partir de um centro e raio.
     * 
     * @param centro ponto central do c�rculo
     * @param raio raio do c�rculo
     */
    public Circulo(Ponto centro, double raio) {
        setCentro(centro);
        setRaio(raio);
    }

    /**
     * Construtor do c�rculo a partir de coordenadas do centro e raio.
     * 
     * @param x coordenada x do centro
     * @param y coordenada y do centro
     * @param raio raio do c�rculo
     */
    public Circulo(double x, double y, double raio) {
        setCentro(new Ponto(x, y));
        setRaio(raio);
    }

    /**
     * Construtor de c�pia.
     * 
     * @param c c�rculo a ser copiado
     */
    public Circulo(Circulo c) {
        setCentro(new Ponto(c.getCentro()));
        setRaio(c.getRaio());
    }

    /**
     * Retorna o centro do c�rculo.
     * 
     * @return objeto Ponto representando o centro
     */
    public Ponto getCentro() {
        return centro;
    }

    /**
     * Define o centro do c�rculo.
     * 
     * @param centro ponto central
     */
    public void setCentro(Ponto centro) {
        this.centro = centro;
    }

    /**
     * Retorna o raio do c�rculo.
     * 
     * @return valor do raio
     */
    public double getRaio() {
        return raio;
    }

    /**
     * Define o raio do c�rculo.
     * 
     * @param raio valor do raio
     */
    public void setRaio(double raio) {
        this.raio = raio;
    }    

    /**
     * Retorna uma representa��o textual do c�rculo.
     * 
     * @return string representando o c�rculo
     */
    @Override
    public String toString(){
        return "Circulo: \n Centro: " + getCentro().toString() + " Raio: " + getRaio();
    }

    /**
     * Atualiza o raio do c�rculo com base em um ponto na borda.
     * 
     * @param x posi��o x do ponto na borda
     * @param y posi��o y do ponto na borda
     */
    public void atualizarRaio(int x, int y) {
        double dx = x - getCentro().getX();
        double dy = y - getCentro().getY();
        setRaio(Math.sqrt(dx*dx + dy*dy));
    }

    /**
     * Converte o c�rculo para um JSONObject.
     * 
     * @return JSONObject representando o c�rculo
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("centro", centro.toJson());
        json.put("raio", this.raio);
        return json;
    }

    /**
     * Cria um c�rculo a partir de um JSONObject.
     * 
     * @param json JSONObject contendo os dados do c�rculo
     * @return novo objeto Circulo
     */
    public static Circulo fromJson(JSONObject json) {
        Ponto centro = Ponto.fromJson(json.getJSONObject("centro"));
        double raio = json.getDouble("raio");
        return new Circulo(centro, raio);
    }

    /**
     * Cria uma lista de c�rculos a partir de um JSONArray.
     * 
     * @param jsonArray JSONArray contendo objetos c�rculo
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
