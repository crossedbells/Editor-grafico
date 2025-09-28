import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONException;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Utilitário para salvar e ler figuras em formato JSON.
 */
public class FiguraJsonUtil {

    // Salva as listas de figuras em um arquivo JSON
    public static void salvarFiguras(
            List<Ponto> pontos,
            List<Reta> retas,
            List<Circulo> circulos,
            List<Triangulo> triangulos,
            List<Retangulo> retangulos,
            String caminhoArquivo) throws IOException {

        JSONObject desenho = new JSONObject();
        desenho.put("ponto", pontosToJsonArray(pontos));
        desenho.put("reta", retasToJsonArray(retas));
        desenho.put("circulo", circulosToJsonArray(circulos));
        desenho.put("triangulo", triangulosToJsonArray(triangulos));
        desenho.put("retangulo", retangulosToJsonArray(retangulos));

        JSONObject root = new JSONObject();
        root.put("figura", desenho);

        try (FileWriter file = new FileWriter(caminhoArquivo)) {
            file.write(root.toString(4));
        }
    }

    // Lê as listas de figuras de um arquivo JSON
    public static FigurasContainer lerFiguras(String caminhoArquivo) throws IOException, JSONException {
        try (FileReader reader = new FileReader(caminhoArquivo)) {
            JSONObject jsonObject = new JSONObject(new JSONTokener(reader));
            JSONObject desenho = jsonObject.getJSONObject("figura");

            List<Ponto> pontos = Ponto.fromJsonArray(desenho.getJSONArray("ponto"));
            List<Reta> retas = Reta.fromJsonArray(desenho.getJSONArray("reta"));
            List<Circulo> circulos = Circulo.fromJsonArray(desenho.getJSONArray("circulo"));
            List<Triangulo> triangulos = Triangulo.fromJsonArray(desenho.getJSONArray("triangulo"));
            List<Retangulo> retangulos = Retangulo.fromJsonArray(desenho.getJSONArray("retangulo"));

            return new FigurasContainer(pontos, retas, circulos, triangulos, retangulos);
        }
    }

    // Métodos auxiliares para converter listas em JSONArray
    private static JSONArray pontosToJsonArray(List<Ponto> pontos) {
        JSONArray arr = new JSONArray();
        for (Ponto p : pontos) arr.put(p.toJson());
        return arr;
    }
    private static JSONArray retasToJsonArray(List<Reta> retas) {
        JSONArray arr = new JSONArray();
        for (Reta r : retas) arr.put(r.toJson());
        return arr;
    }
    private static JSONArray circulosToJsonArray(List<Circulo> circulos) {
        JSONArray arr = new JSONArray();
        for (Circulo c : circulos) arr.put(c.toJson());
        return arr;
    }
    private static JSONArray triangulosToJsonArray(List<Triangulo> triangulos) {
        JSONArray arr = new JSONArray();
        for (Triangulo t : triangulos) arr.put(t.toJson());
        return arr;
    }
    private static JSONArray retangulosToJsonArray(List<Retangulo> retangulos) {
        JSONArray arr = new JSONArray();
        for (Retangulo r : retangulos) arr.put(r.toJson());
        return arr;
    }

    // Classe container para retornar todas as listas lidas
    public static class FigurasContainer {
        public final List<Ponto> pontos;
        public final List<Reta> retas;
        public final List<Circulo> circulos;
        public final List<Triangulo> triangulos;
        public final List<Retangulo> retangulos;

        public FigurasContainer(List<Ponto> pontos, List<Reta> retas, List<Circulo> circulos, List<Triangulo> triangulos, List<Retangulo> retangulos) {
            this.pontos = pontos;
            this.retas = retas;
            this.circulos = circulos;
            this.triangulos = triangulos;
            this.retangulos = retangulos;
        }
    }
}