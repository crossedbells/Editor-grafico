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
 * Utilitario para salvar e carregar figuras em formato JSON.
 * Fornece metodos para serializacao e desserializacao de primitivos graficos.
 * 
 * @author Amora Marinho Machado
 * @author Gabriel Azevedo Cruz
 * @author Gabriel Mechi Lima
 * @author Luiz Fernando de Marchi Andrade
 * @version 05/09/2025
 */
public class jsonCoisas {

    /**
     * Salva as listas de figuras em um arquivo JSON.
     * Cria um arquivo JSON formatado contendo todas as figuras desenhadas.
     * 
     * @param pontos lista de pontos a serem salvos
     * @param retas lista de retas a serem salvas
     * @param circulos lista de circulos a serem salvos
     * @param triangulos lista de triangulos a serem salvos
     * @param retangulos lista de retangulos a serem salvos
     * @param caminhoArquivo caminho do arquivo onde sera salvo o JSON
     * @throws IOException se houver erro ao escrever no arquivo
     */
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

    /**
     * Le as listas de figuras de um arquivo JSON.
     * Carrega e deserializa todas as figuras contidas no arquivo.
     * 
     * @param caminhoArquivo caminho do arquivo JSON a ser lido
     * @return objeto container com todas as listas de figuras lidas
     * @throws IOException se houver erro ao ler o arquivo
     * @throws JSONException se o formato JSON for invalido
     */
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

    /**
     * Converte lista de pontos em JSONArray.
     * 
     * @param pontos lista de pontos a converter
     * @return JSONArray contendo representacao JSON de cada ponto
     */
    private static JSONArray pontosToJsonArray(List<Ponto> pontos) {
        JSONArray arr = new JSONArray();
        for (Ponto p : pontos) arr.put(p.toJson());
        return arr;
    }

    /**
     * Converte lista de retas em JSONArray.
     * 
     * @param retas lista de retas a converter
     * @return JSONArray contendo representacao JSON de cada reta
     */
    private static JSONArray retasToJsonArray(List<Reta> retas) {
        JSONArray arr = new JSONArray();
        for (Reta r : retas) arr.put(r.toJson());
        return arr;
    }

    /**
     * Converte lista de circulos em JSONArray.
     * 
     * @param circulos lista de circulos a converter
     * @return JSONArray contendo representacao JSON de cada circulo
     */
    private static JSONArray circulosToJsonArray(List<Circulo> circulos) {
        JSONArray arr = new JSONArray();
        for (Circulo c : circulos) arr.put(c.toJson());
        return arr;
    }

    /**
     * Converte lista de triangulos em JSONArray.
     * 
     * @param triangulos lista de triangulos a converter
     * @return JSONArray contendo representacao JSON de cada triangulo
     */
    private static JSONArray triangulosToJsonArray(List<Triangulo> triangulos) {
        JSONArray arr = new JSONArray();
        for (Triangulo t : triangulos) arr.put(t.toJson());
        return arr;
    }

    /**
     * Converte lista de retangulos em JSONArray.
     * 
     * @param retangulos lista de retangulos a converter
     * @return JSONArray contendo representacao JSON de cada retangulo
     */
    private static JSONArray retangulosToJsonArray(List<Retangulo> retangulos) {
        JSONArray arr = new JSONArray();
        for (Retangulo r : retangulos) arr.put(r.toJson());
        return arr;
    }

    /**
     * Classe container para armazenar todas as listas de figuras lidas.
     * Utilizada para retornar multiplas listas de forma organizada.
     */
    public static class FigurasContainer {
        /** Lista de pontos carregados */
        public final List<Ponto> pontos;
        
        /** Lista de retas carregadas */
        public final List<Reta> retas;
        
        /** Lista de circulos carregados */
        public final List<Circulo> circulos;
        
        /** Lista de triangulos carregados */
        public final List<Triangulo> triangulos;
        
        /** Lista de retangulos carregados */
        public final List<Retangulo> retangulos;

        /**
         * Construtor do container de figuras.
         * 
         * @param pontos lista de pontos
         * @param retas lista de retas
         * @param circulos lista de circulos
         * @param triangulos lista de triangulos
         * @param retangulos lista de retangulos
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