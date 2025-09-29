package triangulo;

import ponto.Ponto;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um tri�ngulo matem�tico definido por tr�s v�rtices {@code p1}, {@code p2} e {@code p3}.
 * Permite manipula��o individual de cada v�rtice e convers�o para/da representa��o JSON.
 * 
 * <p>Um tri�ngulo pode ser criado a partir de:</p>
 * <ul>
 *   <li>tr�s objetos {@link Ponto}</li>
 *   <li>coordenadas inteiras</li>
 *   <li>coordenadas em ponto flutuante (double)</li>
 *   <li>c�pia de outro tri�ngulo existente</li>
 * </ul>
 *
 * @author Amora Marinho Machado
 * @author Gabriel Azevedo Cruz
 * @author Gabriel Mechi Lima
 * @author Luiz Fernando de Marchi Andrade
 * @version 05/09/2025
 */
public class Triangulo {

    /** Primeiro v�rtice do tri�ngulo */
    private Ponto p1;

    /** Segundo v�rtice do tri�ngulo */
    private Ponto p2;

    /** Terceiro v�rtice do tri�ngulo */
    private Ponto p3;

    /**
     * Constr�i um tri�ngulo a partir de tr�s pontos.
     * 
     * @param p1 primeiro v�rtice
     * @param p2 segundo v�rtice
     * @param p3 terceiro v�rtice
     */
    public Triangulo(Ponto p1, Ponto p2, Ponto p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    /**
     * Constr�i um tri�ngulo a partir de coordenadas inteiras.
     */
    public Triangulo(int x1, int y1, int x2, int y2, int x3, int y3) {
        this.p1 = new Ponto(x1, y1);
        this.p2 = new Ponto(x2, y2);
        this.p3 = new Ponto(x3, y3);
    }

    /**
     * Constr�i um tri�ngulo a partir de coordenadas em ponto flutuante (double).
     */
    public Triangulo(double x1, double y1, double x2, double y2, double x3, double y3) {
        this.p1 = new Ponto(x1, y1);
        this.p2 = new Ponto(x2, y2);
        this.p3 = new Ponto(x3, y3);
    }

    /**
     * Constr�i um tri�ngulo copiando outro tri�ngulo existente.
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

    /** Atualiza os v�rtices individualmente */
    public void atualizarP1(int x, int y) { this.p1.setX(x); this.p1.setY(y); }
    public void atualizarP2(int x, int y) { this.p2.setX(x); this.p2.setY(y); }
    public void atualizarP3(int x, int y) { this.p3.setX(x); this.p3.setY(y); }

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
