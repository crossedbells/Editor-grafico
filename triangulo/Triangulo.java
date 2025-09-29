package triangulo;

import ponto.Ponto;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um triângulo matemático definido por três vértices {@code p1}, {@code p2} e {@code p3}.
 * Permite manipulação individual de cada vértice e conversão para/da representação JSON.
 * 
 * <p>Um triângulo pode ser criado a partir de:</p>
 * <ul>
 *   <li>três objetos {@link Ponto}</li>
 *   <li>coordenadas inteiras</li>
 *   <li>coordenadas em ponto flutuante (double)</li>
 *   <li>cópia de outro triângulo existente</li>
 * </ul>
 *
 * @author Amora Marinho Machado
 * @author Gabriel Azevedo Cruz
 * @author Gabriel Mechi Lima
 * @author Luiz Fernando de Marchi Andrade
 * @version 05/09/2025
 */
public class Triangulo {

    /** Primeiro vértice do triângulo */
    private Ponto p1;

    /** Segundo vértice do triângulo */
    private Ponto p2;

    /** Terceiro vértice do triângulo */
    private Ponto p3;

    /**
     * Constrói um triângulo a partir de três pontos.
     * 
     * @param p1 primeiro vértice
     * @param p2 segundo vértice
     * @param p3 terceiro vértice
     */
    public Triangulo(Ponto p1, Ponto p2, Ponto p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    /**
     * Constrói um triângulo a partir de coordenadas inteiras.
     */
    public Triangulo(int x1, int y1, int x2, int y2, int x3, int y3) {
        this.p1 = new Ponto(x1, y1);
        this.p2 = new Ponto(x2, y2);
        this.p3 = new Ponto(x3, y3);
    }

    /**
     * Constrói um triângulo a partir de coordenadas em ponto flutuante (double).
     */
    public Triangulo(double x1, double y1, double x2, double y2, double x3, double y3) {
        this.p1 = new Ponto(x1, y1);
        this.p2 = new Ponto(x2, y2);
        this.p3 = new Ponto(x3, y3);
    }

    /**
     * Constrói um triângulo copiando outro triângulo existente.
     */
    public Triangulo(Triangulo t) {
        this.p1 = t.getP1();
        this.p2 = t.getP2();
        this.p3 = t.getP3();
    }

    /** Getters e Setters */
    public Ponto getP1() { return p1; }
    public void setP1(Ponto p1) { this.p1 = p1; }
    public Ponto getP2() { return p2; }
    public void setP2(Ponto p2) { this.p2 = p2; }
    public Ponto getP3() { return p3; }
    public void setP3(Ponto p3) { this.p3 = p3; }

    /** Atualiza os vértices individualmente */
    public void atualizarP1(int x, int y) { this.p1.setX(x); this.p1.setY(y); }
    public void atualizarP2(int x, int y) { this.p2.setX(x); this.p2.setY(y); }
    public void atualizarP3(int x, int y) { this.p3.setX(x); this.p3.setY(y); }

    /**
     * Converte o triângulo para formato JSON
     * @return JSONObject representando o triângulo
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("p1", p1.toJson());
        json.put("p2", p2.toJson());
        json.put("p3", p3.toJson());
        return json;
    }

    /**
     * Cria um Triângulo a partir de um JSONObject
     * @param json JSONObject contendo os dados do triângulo
     * @return novo objeto Triangulo
     */
    public static Triangulo fromJson(JSONObject json) {
        Ponto p1 = Ponto.fromJson(json.getJSONObject("p1"));
        Ponto p2 = Ponto.fromJson(json.getJSONObject("p2"));
        Ponto p3 = Ponto.fromJson(json.getJSONObject("p3"));
        return new Triangulo(p1, p2, p3);
    }

    /**
     * Cria uma lista de Triângulos a partir de um JSONArray
     * @param jsonArray JSONArray contendo triângulos
     * @return lista de objetos Triangulo
     */
    public static List<Triangulo> fromJsonArray(JSONArray jsonArray) {
        List<Triangulo> triangulos = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            triangulos.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return triangulos;
    }
}
