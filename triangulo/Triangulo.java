package triangulo;

import ponto.Ponto;

import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um triangulo matematico definido por tres pontos
 * (vertices p1, p2 e p3).
 *
 * <p>Um triangulo pode ser criado a partir de:
 * <ul>
 *   <li>tres objetos {@link Ponto}</li>
 *   <li>coordenadas inteiras</li>
 *   <li>coordenadas em ponto flutuante (double)</li>
 *   <li>copia de outro triangulo existente</li>
 * </ul>
 *
 * <p>Exemplo de uso:</p>
 * <pre>
 *     Triangulo t1 = new Triangulo(new Ponto(0, 0), new Ponto(5, 0), new Ponto(3, 4));
 *     Triangulo t2 = new Triangulo(0, 0, 5, 0, 3, 4); // usando inteiros
 *     Triangulo t3 = new Triangulo(t1); // copia de outro triangulo
 * </pre>
 *
 * @author Amora Marinho Machado
 * @author Gabriel Azevedo Cruz
 * @author Gabriel Mechi Lima
 * @author Luiz Fernando de Marchi Andrade
 * @version 05/09/2025
 */
public class Triangulo {
    /** Primeiro vertice do triangulo */
    private Ponto p1;
    
    /** Segundo vertice do triangulo */
    private Ponto p2;
    
    /** Terceiro vertice do triangulo */
    private Ponto p3;

    /**
     * Constroi um triangulo a partir de tres pontos.
     *
     * @param p1 o primeiro vertice
     * @param p2 o segundo vertice
     * @param p3 o terceiro vertice
     */
    public Triangulo(Ponto p1, Ponto p2, Ponto p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    /**
     * Constroi um triangulo a partir de coordenadas inteiras.
     *
     * @param x1 coordenada X do primeiro vertice
     * @param y1 coordenada Y do primeiro vertice
     * @param x2 coordenada X do segundo vertice
     * @param y2 coordenada Y do segundo vertice
     * @param x3 coordenada X do terceiro vertice
     * @param y3 coordenada Y do terceiro vertice
     */
    public Triangulo(int x1, int y1, int x2, int y2, int x3, int y3) {
        this.p1 = new Ponto(x1, y1);
        this.p2 = new Ponto(x2, y2);
        this.p3 = new Ponto(x3, y3);
    }

    /**
     * Constroi um triangulo a partir de coordenadas em ponto flutuante.
     *
     * @param x1 coordenada X do primeiro vertice
     * @param y1 coordenada Y do primeiro vertice
     * @param x2 coordenada X do segundo vertice
     * @param y2 coordenada Y do segundo vertice
     * @param x3 coordenada X do terceiro vertice
     * @param y3 coordenada Y do terceiro vertice
     */
    public Triangulo(double x1, double y1, double x2, double y2, double x3, double y3) {
        this.p1 = new Ponto(x1, y1);
        this.p2 = new Ponto(x2, y2);
        this.p3 = new Ponto(x3, y3);
    }

    /**
     * Constroi um triangulo copiando outro triangulo existente.
     *
     * @param t triangulo a ser copiado
     */
    public Triangulo(Triangulo t) {
        this.p1 = t.getP1();
        this.p2 = t.getP2();
        this.p3 = t.getP3();
    }

    /**
     * Retorna o primeiro vertice do triangulo.
     *
     * @return o ponto p1
     */
    public Ponto getP1() {
        return p1;
    }

    /**
     * Define o primeiro vertice do triangulo.
     *
     * @param p1 o novo ponto p1
     */
    public void setP1(Ponto p1) {
        this.p1 = p1;
    }

    /**
     * Retorna o segundo vertice do triangulo.
     *
     * @return o ponto p2
     */
    public Ponto getP2() {
        return p2;
    }

    /**
     * Define o segundo vertice do triangulo.
     *
     * @param p2 o novo ponto p2
     */
    public void setP2(Ponto p2) {
        this.p2 = p2;
    }

    /**
     * Retorna o terceiro vertice do triangulo.
     *
     * @return o ponto p3
     */
    public Ponto getP3() {
        return p3;
    }

    /**
     * Define o terceiro vertice do triangulo.
     *
     * @param p3 o novo ponto p3
     */
    public void setP3(Ponto p3) {
        this.p3 = p3;
    }

    /**
     * Atualiza o primeiro vertice do triangulo.
     * 
     * @param x nova coordenada x do ponto p1
     * @param y nova coordenada y do ponto p1
     */
    public void atualizarP1(int x, int y) {
        this.p1.setX(x);
        this.p1.setY(y);
    }

    /**
     * Atualiza o segundo vertice do triangulo.
     * 
     * @param x nova coordenada x do ponto p2
     * @param y nova coordenada y do ponto p2
     */
    public void atualizarP2(int x, int y) {
        this.p2.setX(x);
        this.p2.setY(y);
    }

    /**
     * Atualiza o terceiro vertice do triangulo.
     * 
     * @param x nova coordenada x do ponto p3
     * @param y nova coordenada y do ponto p3
     */
    public void atualizarP3(int x, int y) {
        this.p3.setX(x);
        this.p3.setY(y);
    }

    /**
     * Converte o triangulo para formato JSON.
     * 
     * @return JSONObject representando o triangulo
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("p1", p1.toJson());
        json.put("p2", p2.toJson());
        json.put("p3", p3.toJson());
        return json;
    }

    /**
     * Cria um Triangulo a partir de um JSONObject.
     * 
     * @param json JSONObject contendo os dados do triangulo
     * @return novo objeto Triangulo
     */
    public static Triangulo fromJson(JSONObject json) {
        Ponto p1 = Ponto.fromJson(json.getJSONObject("p1"));
        Ponto p2 = Ponto.fromJson(json.getJSONObject("p2"));
        Ponto p3 = Ponto.fromJson(json.getJSONObject("p3"));
        return new Triangulo(p1, p2, p3);
    }

    /**
     * Cria uma lista de Triangulos a partir de um JSONArray.
     * 
     * @param jsonArray JSONArray contendo triangulos
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