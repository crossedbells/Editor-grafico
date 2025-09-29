package triangulo;

import ponto.Ponto;

import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um triângulo matemático definido por três pontos
 * (vértices {@code p1}, {@code p2} e {@code p3}).
 *
 * <p>Um triângulo pode ser criado a partir de:
 * <ul>
 *   <li>três objetos {@link Ponto}</li>
 *   <li>coordenadas inteiras</li>
 *   <li>coordenadas em ponto flutuante (double)</li>
 *   <li>cópia de outro triângulo existente</li>
 * </ul>
 *
 * <p>Exemplo de uso:</p>
 * <pre>
 *     Triangulo t1 = new Triangulo(new Ponto(0, 0), new Ponto(5, 0), new Ponto(3, 4));
 *     Triangulo t2 = new Triangulo(0, 0, 5, 0, 3, 4); // usando inteiros
 *     Triangulo t3 = new Triangulo(t1); // cópia de outro triângulo
 * </pre>
 *
 * @author Seu Nome
 * @version 1.0
 */
public class Triangulo {
    /** Primeiro vértice do triângulo. */
    private Ponto p1;
    /** Segundo vértice do triângulo. */
    private Ponto p2;
    /** Terceiro vértice do triângulo. */
    private Ponto p3;

    /**
     * Constrói um triângulo a partir de três pontos.
     *
     * @param p1 o primeiro vértice
     * @param p2 o segundo vértice
     * @param p3 o terceiro vértice
     */
    public Triangulo(Ponto p1, Ponto p2, Ponto p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    /**
     * Constrói um triângulo a partir de coordenadas inteiras.
     *
     * @param x1 coordenada X do primeiro vértice
     * @param y1 coordenada Y do primeiro vértice
     * @param x2 coordenada X do segundo vértice
     * @param y2 coordenada Y do segundo vértice
     * @param x3 coordenada X do terceiro vértice
     * @param y3 coordenada Y do terceiro vértice
     */
    public Triangulo(int x1, int y1, int x2, int y2, int x3, int y3) {
        this.p1 = new Ponto(x1, y1);
        this.p2 = new Ponto(x2, y2);
        this.p3 = new Ponto(x3, y3);
    }

    /**
     * Constrói um triângulo a partir de coordenadas em ponto flutuante.
     *
     * @param x1 coordenada X do primeiro vértice
     * @param y1 coordenada Y do primeiro vértice
     * @param x2 coordenada X do segundo vértice
     * @param y2 coordenada Y do segundo vértice
     * @param x3 coordenada X do terceiro vértice
     * @param y3 coordenada Y do terceiro vértice
     */
    public Triangulo(double x1, double y1, double x2, double y2, double x3, double y3) {
        this.p1 = new Ponto(x1, y1);
        this.p2 = new Ponto(x2, y2);
        this.p3 = new Ponto(x3, y3);
    }

    /**
     * Constrói um triângulo copiando outro triângulo existente.
     *
     * @param t triângulo a ser copiado
     */
    public Triangulo(Triangulo t) {
        this.p1 = t.getP1();
        this.p2 = t.getP2();
        this.p3 = t.getP3();
    }

    /**
     * Retorna o primeiro vértice do triângulo.
     *
     * @return o ponto {@code p1}
     */
    public Ponto getP1() {
        return p1;
    }

    /**
     * Define o primeiro vértice do triângulo.
     *
     * @param p1 o novo ponto {@code p1}
     */
    public void setP1(Ponto p1) {
        this.p1 = p1;
    }

    /**
     * Retorna o segundo vértice do triângulo.
     *
     * @return o ponto {@code p2}
     */
    public Ponto getP2() {
        return p2;
    }

    /**
     * Define o segundo vértice do triângulo.
     *
     * @param p2 o novo ponto {@code p2}
     */
    public void setP2(Ponto p2) {
        this.p2 = p2;
    }

    /**
     * Retorna o terceiro vértice do triângulo.
     *
     * @return o ponto {@code p3}
     */
    public Ponto getP3() {
        return p3;
    }

    /**
     * Define o terceiro vértice do triângulo.
     *
     * @param p3 o novo ponto {@code p3}
     */
    public void setP3(Ponto p3) {
        this.p3 = p3;
    }

    /**
     * Atualiza o primeiro vértice do triângulo.
     */
    public void atualizarP1(int x, int y) {
        this.p1.setX(x);
        this.p1.setY(y);
    }

    /**
     * Atualiza o segundo vértice do triângulo.
     */
    public void atualizarP2(int x, int y) {
        this.p2.setX(x);
        this.p2.setY(y);
    }

    /**
     * Atualiza o terceiro vértice do triângulo.
     */
    public void atualizarP3(int x, int y) {
        this.p3.setX(x);
        this.p3.setY(y);
    }

    /**
     * Converte o tri�ngulo para formato JSON
     * @return JSONObject representando o tri�ngulo
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("p1", p1.toJson());
        json.put("p2", p2.toJson());
        json.put("p3", p3.toJson());
        return json;
    }

    /**
     * Cria um Tri�ngulo a partir de um JSONObject
     * @param json JSONObject contendo os dados do tri�ngulo
     * @return novo objeto Triangulo
     */
    public static Triangulo fromJson(JSONObject json) {
        Ponto p1 = Ponto.fromJson(json.getJSONObject("p1"));
        Ponto p2 = Ponto.fromJson(json.getJSONObject("p2"));
        Ponto p3 = Ponto.fromJson(json.getJSONObject("p3"));
        return new Triangulo(p1, p2, p3);
    }

    /**
     * Cria uma lista de Tri�ngulos a partir de um JSONArray
     * @param jsonArray JSONArray contendo tri�ngulos
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
