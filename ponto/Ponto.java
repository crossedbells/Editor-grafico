package ponto;

import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

/**
 * Representacao de ponto matematico em um plano cartesiano.
 * Armazena coordenadas X e Y e fornece operacoes basicas como calculo de distancia.
 * 
 * @author Amora Marinho Machado
 * @author Gabriel Azevedo Cruz
 * @author Gabriel Mechi Lima
 * @author Luiz Fernando de Marchi Andrade
 * @version 05/09/2025
 */
public class Ponto {
    /** Coordenada X do ponto */
    private double x;
    
    /** Coordenada Y do ponto */
    private double y;
    
    /**
     * Construtor padrao. Cria um ponto na origem (0, 0).
     */
    public Ponto() {
        setX(0);
        setY(0);
    }

    /**
     * Construtor de copia. Cria um novo ponto com as mesmas coordenadas.
     * 
     * @param p ponto a ser copiado
     */
    public Ponto(Ponto p) {
        setX(p.getX());
        setY(p.getY());
    }

    /**
     * Construtor com coordenadas especificas.
     * 
     * @param x coordenada x do ponto
     * @param y coordenada y do ponto
     */
    public Ponto(double x, double y) {
        setX(x);
        setY(y);
    }

    /**
     * Retorna a coordenada X do ponto.
     * 
     * @return coordenada x
     */
    public double getX() {
        return x;
    }

    /**
     * Define a coordenada X do ponto.
     * 
     * @param x coordenada x a ser definida
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Retorna a coordenada Y do ponto.
     * 
     * @return coordenada y
     */
    public double getY() {
        return y;
    }

    /**
     * Define a coordenada Y do ponto.
     * 
     * @param y coordenada y a ser definida
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Calcula a distancia euclidiana entre este ponto e outro ponto.
     * Utiliza a formula: d = sqrt((x2-x1)^2 + (y2-y1)^2)
     * 
     * @param p ponto externo para calcular a distancia
     * @return distancia entre os pontos
     */
    public double calcularDistancia(Ponto p) {
        double d = Math.sqrt(Math.pow(p.getY()-getY(), 2) + Math.pow(p.getX()-getX(), 2));
        return(d);
    }

    /**
     * Retorna representacao em String do ponto.
     * 
     * @return string no formato "Ponto [x, y]"
     */
    @Override
    public String toString() {
        return "Ponto [" + getX() + ", " + getY() +  "]";
    }

    /**
     * Converte o ponto para formato JSON.
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
     * Cria um Ponto a partir de um JSONObject.
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
     * Cria uma lista de Pontos a partir de um JSONArray.
     * 
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