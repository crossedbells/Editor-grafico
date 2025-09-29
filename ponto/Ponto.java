package ponto;

import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

/**
 * Representacao de ponto matematico
 * 
 * @author julio
 *
 */
public class Ponto {
    private double x;
    private double y;
    /**
     * 
     */
    public Ponto() {
        setX(0);
        setY(0);
    }

    public Ponto(Ponto p) {
        setX(p.getX());
        setY(p.getY());
    }

    /**
     * @param x
     * @param y
     */
    public Ponto(double x, double y) {
        setX(x);
        setY(y);
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Calcula a distancia entre o ponto que vem como parametro
     * 
     * @param p ponto externo
     * 
     * @return d double valor da distancia
     * 
     */
    public double calcularDistancia(Ponto p) {

        double d = Math.sqrt(Math.pow(p.getY()-getY(), 2) + Math.pow(p.getX()-getX(), 2));

        return(d);

    }

    @Override
    public String toString() {
        return "Ponto [" + getX() + ", " + getY() +  "]";
    }

    /**
     * Converte o ponto para formato JSON
     * @return JSONObject representando o ponto
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("x", this.x);
        json.put("y", this.y);
        return json;
    }

    /**
     * Cria um Ponto a partir de um JSONObject
     * @param json JSONObject contendo os dados do ponto
     * @return novo objeto Ponto
     */
    public static Ponto fromJson(JSONObject json) {
        double x = json.getDouble("x");
        double y = json.getDouble("y");
        return new Ponto(x, y);
    }

    /**
     * Cria uma lista de Pontos a partir de um JSONArray
     * @param jsonArray JSONArray contendo pontos
     * @return lista de objetos Ponto
     */
    public static List<Ponto> fromJsonArray(JSONArray jsonArray) {
        List<Ponto> pontos = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            pontos.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return pontos;
    }

    

}
