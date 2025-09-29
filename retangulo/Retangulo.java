package retangulo;

import ponto.Ponto;

import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um retangulo definido por dois pontos opostos (p1 e p2).
 * A partir desses pontos, os outros dois cantos (p3 e p4) sao calculados
 * automaticamente, formando um retangulo alinhado aos eixos cartesianos.
 * 
 * <p>Exemplo de uso:</p>
 * <pre>
 *     Ponto p1 = new Ponto(0, 0);
 *     Ponto p2 = new Ponto(4, 3);
 *     Retangulo r = new Retangulo(p1, p2);
 * </pre>
 * 
 * Nesse exemplo, o retangulo e formado pelos pontos (0,0), (4,3), (0,3) e (4,0).
 * 
 * @author Amora Marinho Machado
 * @author Gabriel Azevedo Cruz
 * @author Gabriel Mechi Lima
 * @author Luiz Fernando de Marchi Andrade
 * @version 05/09/2025
 */
public class Retangulo {
    /** Um canto do retangulo */
    private Ponto p1;
    
    /** O canto oposto ao ponto p1 */
    private Ponto p2;
    
    /** O terceiro canto do retangulo, calculado automaticamente */
    private Ponto p3;
    
    /** O quarto canto do retangulo, calculado automaticamente */
    private Ponto p4;

    /**
     * Constroi um retangulo a partir de dois pontos opostos.
     * Os outros dois cantos sao calculados automaticamente.
     *
     * @param p1 um dos cantos do retangulo
     * @param p2 o canto oposto a p1
     */
    public Retangulo(Ponto p1, Ponto p2) {
        this.p1 = p1;
        this.p2 = p2;
        calcularP3P4();
    }

    /**
     * Calcula os pontos p3 e p4 com base em p1 e p2.
     * p3 tera a coordenada X de p1 e a coordenada Y de p2.
     * p4 tera a coordenada X de p2 e a coordenada Y de p1.
     */
    private void calcularP3P4() {
        this.p3 = new Ponto(p1.getX(), p2.getY());
        this.p4 = new Ponto(p2.getX(), p1.getY());
    }

    /**
     * Retorna o ponto p1.
     *
     * @return o ponto p1
     */
    public Ponto getP1() {
        return p1;
    }

    /**
     * Define o ponto p1 e recalcula os outros cantos do retangulo.
     *
     * @param p1 o novo ponto p1
     */
    public void setP1(Ponto p1) {
        this.p1 = p1;
        calcularP3P4();
    }

    /**
     * Retorna o ponto p2.
     *
     * @return o ponto p2
     */
    public Ponto getP2() {
        return p2;
    }

    /**
     * Define o ponto p2 e recalcula os outros cantos do retangulo.
     *
     * @param p2 o novo ponto p2
     */
    public void setP2(Ponto p2) {
        this.p2 = p2;
        calcularP3P4();
    }

    /**
     * Retorna o ponto p3, calculado automaticamente.
     *
     * @return o ponto p3
     */
    public Ponto getP3() {
        return p3;
    }

    /**
     * Retorna o ponto p4, calculado automaticamente.
     *
     * @return o ponto p4
     */
    public Ponto getP4() {
        return p4;
    }

    /**
     * Atualiza o ponto p2 (canto oposto) do retangulo e recalcula os outros cantos.
     * 
     * @param x nova coordenada x do ponto p2
     * @param y nova coordenada y do ponto p2
     */
    public void atualizarP2(int x, int y) {
        this.p2.setX(x);
        this.p2.setY(y);
        calcularP3P4();
    }

    /**
     * Converte o retangulo para formato JSON.
     * 
     * @return JSONObject representando o retangulo
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("p1", p1.toJson());
        json.put("p2", p2.toJson());
        return json;
    }

    /**
     * Cria um Retangulo a partir de um JSONObject.
     * 
     * @param json JSONObject contendo os dados do retangulo
     * @return novo objeto Retangulo
     */
    public static Retangulo fromJson(JSONObject json) {
        Ponto p1 = Ponto.fromJson(json.getJSONObject("p1"));
        Ponto p2 = Ponto.fromJson(json.getJSONObject("p2"));
        return new Retangulo(p1, p2);
    }

    /**
     * Cria uma lista de Retangulos a partir de um JSONArray.
     * 
     * @param jsonArray JSONArray contendo retangulos
     * @return lista de objetos Retangulo
     */
    public static List<Retangulo> fromJsonArray(JSONArray jsonArray) {
        List<Retangulo> retangulos = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            retangulos.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return retangulos;
    }
}