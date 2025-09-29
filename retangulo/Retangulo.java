package retangulo;

import ponto.Ponto;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um retângulo definido por dois pontos opostos (p1 e p2).
 * A partir desses pontos, os outros dois cantos (p3 e p4) são calculados
 * automaticamente, formando um retângulo alinhado aos eixos cartesianos.
 * 
 * <p>Exemplo de uso:</p>
 * <pre>
 *     Ponto p1 = new Ponto(0, 0);
 *     Ponto p2 = new Ponto(4, 3);
 *     Retangulo r = new Retangulo(p1, p2);
 * </pre>
 *
 * Nesse exemplo, o retângulo é formado pelos pontos (0,0), (4,3), (0,3) e (4,0).
 * 
 * @author Amora Marinho Machado
 * @author Gabriel Azevedo Cruz
 * @author Gabriel Mechi Lima
 * @author Luiz Fernando de Marchi Andrade
 * @version 05/09/2025
 */
public class Retangulo {

    private Ponto p1; // Um canto do retângulo
    private Ponto p2; // Canto oposto a p1
    private Ponto p3; // Canto calculado
    private Ponto p4; // Canto calculado

    /**
     * Constrói um retângulo a partir de dois pontos opostos.
     *
     * @param p1 um dos cantos
     * @param p2 canto oposto a p1
     */
    public Retangulo(Ponto p1, Ponto p2) {
        this.p1 = p1;
        this.p2 = p2;
        calcularP3P4();
    }

    /** Calcula os pontos p3 e p4 com base em p1 e p2 */
    private void calcularP3P4() {
        this.p3 = new Ponto(p1.getX(), p2.getY());
        this.p4 = new Ponto(p2.getX(), p1.getY());
    }

    /** Getters e Setters */
    public Ponto getP1() { return p1; }
    public void setP1(Ponto p1) { this.p1 = p1; calcularP3P4(); }
    public Ponto getP2() { return p2; }
    public void setP2(Ponto p2) { this.p2 = p2; calcularP3P4(); }
    public Ponto getP3() { return p3; }
    public Ponto getP4() { return p4; }

    /** Atualiza p2 e recalcula os outros cantos */
    public void atualizarP2(int x, int y) {
        this.p2.setX(x);
        this.p2.setY(y);
        calcularP3P4();
    }

    /** Converte o retângulo para JSON */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("p1", p1.toJson());
        json.put("p2", p2.toJson());
        return json;
    }

    /** Cria um Retângulo a partir de JSON */
    public static Retangulo fromJson(JSONObject json) {
        Ponto p1 = Ponto.fromJson(json.getJSONObject("p1"));
        Ponto p2 = Ponto.fromJson(json.getJSONObject("p2"));
        return new Retangulo(p1, p2);
    }

    /** Cria uma lista de Retângulos a partir de um JSONArray */
    public static List<Retangulo> fromJsonArray(JSONArray jsonArray) {
        List<Retangulo> retangulos = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            retangulos.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return retangulos;
    }
}
