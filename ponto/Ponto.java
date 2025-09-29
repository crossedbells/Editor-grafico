package ponto;

import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

/**
 * Representação de ponto matemático no plano 2D.
 * Permite manipulação das coordenadas x e y, cálculo de distância,
 * e conversão para/da representação JSON.
 * 
 * @author Amora Marinho Machado
 * @author Gabriel Azevedo Cruz
 * @author Gabriel Mechi Lima
 * @author Luiz Fernando de Marchi Andrade
 * @version 05/09/2025
 */
public class Ponto {
    private double x;
    private double y;

    /**
     * Construtor padrão, inicializa o ponto na origem (0,0).
     */
    public Ponto() {
        setX(0);
        setY(0);
    }

    /**
     * Construtor de cópia.
     * 
     * @param p Ponto a ser copiado
     */
    public Ponto(Ponto p) {
        setX(p.getX());
        setY(p.getY());
    }

    /**
     * Construtor com coordenadas específicas.
     * 
     * @param x coordenada x do ponto
     * @param y coordenada y do ponto
     */
    public Ponto(double x, double y) {
        setX(x);
        setY(y);
    }

    /**
     * Retorna a coordenada x do ponto.
     * 
     * @return coordenada x
     */
    public double getX() {
        return x;
    }

    /**
     * Define a coordenada x do ponto.
     * 
     * @param x coordenada x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Retorna a coordenada y do ponto.
     * 
     * @return coordenada y
     */
    public double getY() {
        return y;
    }

    /**
     * Define a coordenada y do ponto.
     * 
     * @param y coordenada y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Calcula a distância entre este ponto e outro ponto fornecido.
     * 
     * @param p ponto externo
     * @return valor da distância entre os dois pontos
     */
    public double calcularDistancia(Ponto p) {
        double d = Math.sqrt(Math.pow(p.getY() - getY(), 2) + Math.pow(p.getX() - getX(), 2));
        return d;
    }

    /**
     * Representação textual do ponto no formato: Ponto [x, y]
     * 
     * @return string representando o ponto
     */
    @Override
    public String toString() {
        return "Ponto [" + getX() + ", " + getY() +  "]";
    }

    /**
     * Converte o ponto para um objeto JSONObject.
     * 
     * @return JSONObject representando o ponto
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("x", this.x);
        json.put("y", this.y);
        return json;
    }

    /**
     * Cria um ponto a partir de um JSONObject.
     * 
     * @param json JSONObject contendo os dados do ponto
     * @return novo objeto Ponto
     */
    public static Ponto fromJson(JSONObject json) {
        double x = json.getDouble("x");
        double y = json.getDouble("y");
        return new Ponto(x, y);
    }

    /**
     * Cria uma lista de pontos a partir de um JSONArray.
     * 
     * @param jsonArray JSONArray contendo objetos ponto
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
