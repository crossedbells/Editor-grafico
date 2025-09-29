import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONException;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import ponto.Ponto;
import reta.Reta;
import circulo.Circulo;
import retangulo.Retangulo;
import triangulo.Triangulo;

/**
 * Utilitário para salvar e ler figuras em formato JSON. 
 * Esta classe fornece métodos para serializar e desserializar figuras geométricas,
 * como pontos, retas, círculos, triângulos e retângulos, em um arquivo JSON.
 */
public class jsonCoisas {

    /**
     * Salva as listas de figuras geométricas em um arquivo JSON.
     *
     * @param pontos Lista de objetos Ponto a ser salva no arquivo.
     * @param retas Lista de objetos Reta a ser salva no arquivo.
     * @param circulos Lista de objetos Circulo a ser salva no arquivo.
     * @param triangulos Lista de objetos Triangulo a ser salva no arquivo.
     * @param retangulos Lista de objetos Retangulo a ser salva no arquivo.
     * @param caminhoArquivo Caminho do arquivo onde os dados serão salvos.
     * @throws IOException Se ocorrer um erro ao escrever no arquivo.
     */
    public static void salvarFiguras(
        List<Ponto> pontos,
        List<Reta> retas,
        List<Circulo> circulos,
        List<Triangulo> triangulos,
        List<Retangulo> retangulos,
        String caminhoArquivo) throws IOException {

        // Criação do objeto JSON que irá conter os dados das figuras
        JSONObject desenho = new JSONObject();
        desenho.put("ponto", pontosToJsonArray(pontos));
        desenho.put("reta", retasToJsonArray(retas));
        desenho.put("circulo", circulosToJsonArray(circulos));
        desenho.put("triangulo", triangulosToJsonArray(triangulos));
        desenho.put("retangulo", retangulosToJsonArray(retangulos));

        JSONObject root = new JSONObject();
        root.put("figura", desenho);

        // Salva o conteúdo JSON no arquivo especificado
        try (FileWriter file = new FileWriter(caminhoArquivo)) {
            file.write(root.toString(4));  // Indenta o JSON com 4 espaços
        }
    }

    /**
     * Lê as listas de figuras geométricas de um arquivo JSON.
     *
     * @param caminhoArquivo Caminho do arquivo JSON a ser lido.
     * @return Um container com as listas de figuras lidas do arquivo.
     * @throws IOException Se ocorrer um erro ao ler o arquivo.
     * @throws JSONException Se o formato do JSON for inválido.
     */
    public static FigurasContainer lerFiguras(String caminhoArquivo) throws IOException, JSONException {
        try (FileReader reader = new FileReader(caminhoArquivo)) {
            // Lê o arquivo JSON
            JSONObject jsonObject = new JSONObject(new JSONTokener(reader));
            JSONObject desenho = jsonObject.getJSONObject("figura");

            // Converte as arrays JSON para listas de objetos Java
            List<Ponto> pontos = Ponto.fromJsonArray(desenho.getJSONArray("ponto"));
            List<Reta> retas = Reta.fromJsonArray(desenho.getJSONArray("reta"));
            List<Circulo> circulos = Circulo.fromJsonArray(desenho.getJSONArray("circulo"));
            List<Triangulo> triangulos = Triangulo.fromJsonArray(desenho.getJSONArray("triangulo"));
            List<Retangulo> retangulos = Retangulo.fromJsonArray(desenho.getJSONArray("retangulo"));

            // Retorna um container com todas as listas de figuras
            return new FigurasContainer(pontos, retas, circulos, triangulos, retangulos);
        }
    }

    // Métodos auxiliares para converter listas de figuras em JSONArray

    /**
     * Converte uma lista de objetos Ponto para um JSONArray.
     *
     * @param pontos Lista de objetos Ponto.
     * @return Um JSONArray contendo os pontos.
     */
    private static JSONArray pontosToJsonArray(List<Ponto> pontos) {
        JSONArray arr = new JSONArray();
        for (Ponto p : pontos) arr.put(p.toJson());
        return arr;
    }

    /**
     * Converte uma lista de objetos Reta para um JSONArray.
     *
     * @param retas Lista de objetos Reta.
     * @return Um JSONArray contendo as retas.
     */
    private static JSONArray retasToJsonArray(List<Reta> retas) {
        JSONArray arr = new JSONArray();
        for (Reta r : retas) arr.put(r.toJson());
        return arr;
    }

    /**
     * Converte uma lista de objetos Circulo para um JSONArray.
     *
     * @param circulos Lista de objetos Circulo.
     * @return Um JSONArray contendo os círculos.
     */
    private static JSONArray circulosToJsonArray(List<Circulo> circulos) {
        JSONArray arr = new JSONArray();
        for (Circulo c : circulos) arr.put(c.toJson());
        return arr;
    }

    /**
     * Converte uma lista de objetos Triangulo para um JSONArray.
     *
     * @param triangulos Lista de objetos Triangulo.
     * @return Um JSONArray contendo os triângulos.
     */
    private static JSONArray triangulosToJsonArray(List<Triangulo> triangulos) {
        JSONArray arr = new JSONArray();
        for (Triangulo t : triangulos) arr.put(t.toJson());
        return arr;
    }

    /**
     * Converte uma lista de objetos Retangulo para um JSONArray.
     *
     * @param retangulos Lista de objetos Retangulo.
     * @return Um JSONArray contendo os retângulos.
     */
    private static JSONArray retangulosToJsonArray(List<Retangulo> retangulos) {
        JSONArray arr = new JSONArray();
        for (Retangulo r : retangulos) arr.put(r.toJson());
        return arr;
    }

    /**
     * Classe auxiliar que serve como container para armazenar as listas de figuras geométricas.
     */
    public static class FigurasContainer {
        public final List<Ponto> pontos;
        public final List<Reta> retas;
        public final List<Circulo> circulos;
        public final List<Triangulo> triangulos;
        public final List<Retangulo> retangulos;

        /**
         * Constrói um container com as listas de figuras geométricas.
         *
         * @param pontos Lista de pontos.
         * @param retas Lista de retas.
         * @param circulos Lista de círculos.
         * @param triangulos Lista de triângulos.
         * @param retangulos Lista de retângulos.
         */
        public FigurasContainer(
            List<Ponto> pontos,
            List<Reta> retas,
            List<Circulo> circulos,
            List<Triangulo> triangulos,
            List<Retangulo> retangulos) {
            this.pontos = pontos;
            this.retas = retas;
            this.circulos = circulos;
            this.triangulos = triangulos;
            this.retangulos = retangulos;
        }
    }
}
