package circulo;
import ponto.Ponto;

import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa circulo matematico.
 * 
 * @author Julio Arakaki
 * @version 24/08/2021
 */
public class Circulo {
    // centro do circulo
    private Ponto centro;
    private double raio; // raio do circulo

    /**
     * Contrutor da classe circulo
     * @param centro Ponto centro do circulo
     * @param raio double raio do circulo
     */
    public Circulo(Ponto centro, double raio) {
        setCentro(centro);
        setRaio(raio);
    }

    /**
     * Contrutor da classe circulo
     * @param x double coordenada x do centro do circulo
     * @param y double coordenada y do centro do circulo
     * @param raio double raio do circulo
     */
    public Circulo(double x, double y, double raio) {
        setCentro(new Ponto(x, y));
        setRaio(raio);
    }

    /**
     * Contrutor da classe circulo. Cria uma copia
     * @param c Circulo circulo a ser copiado
     */
    public Circulo(Circulo c) {
        setCentro(new Ponto(c.getCentro()));
        setRaio(c.getRaio());
    }

    /**
     * @return the centro
     */
    public Ponto getCentro() {
        return centro;
    }

    /**
     * @param centro the centro to set
     */
    public void setCentro(Ponto centro) {
        this.centro = centro;
    }

    /**
     * @return the raio
     */
    public double getRaio() {
        return raio;
    }

    /**
     * @param raio the raio to set
     */
    public void setRaio(double raio) {
        this.raio = raio;
    }    

    /**
     * Method toString
     *
     * @return The return value
     */
    public String toString(){
        String s = "Circulo: \n Centro: " + getCentro().toString() + " Raio: " + getRaio();
        return s;
    }

    /**
     * Atualiza o raio do cÃ­rculo com base em um ponto na borda.
     * @param x PosiÃ§Ã£o x do mouse (borda)
     * @param y PosiÃ§Ã£o y do mouse (borda)
     */
    public void atualizarRaio(int x, int y) {
        double dx = x - getCentro().getX();
        double dy = y - getCentro().getY();
        setRaio(Math.sqrt(dx*dx + dy*dy));
    }

    /**
     * Converte o círculo para formato JSON
     * @return JSONObject representando o círculo
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("centro", centro.toJson());
        json.put("raio", this.raio);
        return json;
    }

    /**
     * Cria um Círculo a partir de um JSONObject
     * @param json JSONObject contendo os dados do círculo
     * @return novo objeto Circulo
     */
    public static Circulo fromJson(JSONObject json) {
        Ponto centro = Ponto.fromJson(json.getJSONObject("centro"));
        double raio = json.getDouble("raio");
        return new Circulo(centro, raio);
    }

    /**
     * Cria uma lista de Círculos a partir de um JSONArray
     * @param jsonArray JSONArray contendo círculos
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
