import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONException;

import java.awt.Color;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ponto.Ponto;
import ponto.PontoGr;
import reta.Reta;
import reta.RetaGr;
import circulo.Circulo;
import circulo.CirculoGr;
import retangulo.Retangulo;
import retangulo.RetanguloGraf;
import triangulo.Triangulo;
import triangulo.TrianguloGraf;

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
     * Salva uma lista mista de formas graficas em um arquivo JSON.
     * Este metodo e usado pelo PainelDesenho para salvar o estado da tela.
     * 
     * @param formas lista de objetos graficos (PontoGr, RetaGr, CirculoGr, etc.)
     * @param arquivo arquivo onde sera salvo o JSON
     * @throws IOException se houver erro ao escrever no arquivo
     */
    public static void salvarFormasGraficas(List<Object> formas, File arquivo) throws IOException {
        JSONArray arrayFormas = new JSONArray();

        for (Object forma : formas) {
            JSONObject jsonForma = new JSONObject();

            if (forma instanceof PontoGr) {
                PontoGr p = (PontoGr) forma;
                jsonForma.put("tipo", "PONTO");
                jsonForma.put("x", p.getX());
                jsonForma.put("y", p.getY());
                jsonForma.put("cor", corToJson(p.getCorPto()));
                jsonForma.put("espessura", p.getDiametro());

            } else if (forma instanceof RetaGr) {
                RetaGr r = (RetaGr) forma;
                jsonForma.put("tipo", "RETA");
                jsonForma.put("x1", r.p1.getX());
                jsonForma.put("y1", r.p1.getY());
                jsonForma.put("x2", r.p2.getX());
                jsonForma.put("y2", r.p2.getY());
                jsonForma.put("cor", corToJson(r.getCorReta()));
                jsonForma.put("espessura", r.getEspReta());

            } else if (forma instanceof RetanguloGraf) {
                RetanguloGraf r = (RetanguloGraf) forma;
                jsonForma.put("tipo", "RETANGULO");
                jsonForma.put("x1", r.getP1().getX());
                jsonForma.put("y1", r.getP1().getY());
                jsonForma.put("x2", r.getP2().getX());
                jsonForma.put("y2", r.getP2().getY());
                jsonForma.put("cor", corToJson(r.getCor()));
                jsonForma.put("espessura", r.getEspessura());

            } else if (forma instanceof CirculoGr) {
                CirculoGr c = (CirculoGr) forma;
                jsonForma.put("tipo", "CIRCULO");
                jsonForma.put("centroX", c.getCentro().getX());
                jsonForma.put("centroY", c.getCentro().getY());
                jsonForma.put("raio", c.getRaio());
                jsonForma.put("cor", corToJson(c.getCorCirculo()));
                jsonForma.put("espessura", c.getEspCirculo());

            } else if (forma instanceof TrianguloGraf) {
                TrianguloGraf t = (TrianguloGraf) forma;
                jsonForma.put("tipo", "TRIANGULO");
                jsonForma.put("x1", t.getP1().getX());
                jsonForma.put("y1", t.getP1().getY());
                jsonForma.put("x2", t.getP2().getX());
                jsonForma.put("y2", t.getP2().getY());
                jsonForma.put("x3", t.getP3().getX());
                jsonForma.put("y3", t.getP3().getY());
                jsonForma.put("cor", corToJson(t.getCor()));
                jsonForma.put("espessura", t.getEspessura());
            }

            if (jsonForma.length() > 0) {
                arrayFormas.put(jsonForma);
            }
        }

        JSONObject root = new JSONObject();
        root.put("figuras", arrayFormas);

        try (FileWriter file = new FileWriter(arquivo)) {
            file.write(root.toString(4));
        }
    }

    /**
     * Carrega formas graficas de um arquivo JSON.
     * Este metodo e usado pelo PainelDesenho para restaurar o estado da tela.
     * 
     * @param arquivo arquivo JSON a ser carregado
     * @return lista de objetos graficos carregados
     * @throws IOException se houver erro ao ler o arquivo
     */
    public static List<Object> carregarFormasGraficas(File arquivo) throws IOException {
        List<Object> formas = new ArrayList<>();

        try (FileReader reader = new FileReader(arquivo)) {
            JSONObject jsonObject = new JSONObject(new JSONTokener(reader));
            JSONArray arrayFormas = jsonObject.getJSONArray("figuras");

            for (int i = 0; i < arrayFormas.length(); i++) {
                JSONObject jsonForma = arrayFormas.getJSONObject(i);
                String tipo = jsonForma.getString("tipo");

                if (tipo.equals("PONTO")) {
                    double x = jsonForma.getDouble("x");
                    double y = jsonForma.getDouble("y");
                    Color cor = jsonToCor(jsonForma.getJSONObject("cor"));
                    int esp = jsonForma.getInt("espessura");
                    formas.add(new PontoGr((int)x, (int)y, cor, esp));

                } else if (tipo.equals("RETA")) {
                    double x1 = jsonForma.getDouble("x1");
                    double y1 = jsonForma.getDouble("y1");
                    double x2 = jsonForma.getDouble("x2");
                    double y2 = jsonForma.getDouble("y2");
                    Color cor = jsonToCor(jsonForma.getJSONObject("cor"));
                    int esp = jsonForma.getInt("espessura");
                    formas.add(new RetaGr((int)x1, (int)y1, (int)x2, (int)y2, cor, "", esp));

                } else if (tipo.equals("RETANGULO")) {
                    double x1 = jsonForma.getDouble("x1");
                    double y1 = jsonForma.getDouble("y1");
                    double x2 = jsonForma.getDouble("x2");
                    double y2 = jsonForma.getDouble("y2");
                    Color cor = jsonToCor(jsonForma.getJSONObject("cor"));
                    int esp = jsonForma.getInt("espessura");
                    formas.add(new RetanguloGraf(new Ponto(x1, y1), new Ponto(x2, y2), cor, esp));

                } else if (tipo.equals("CIRCULO")) {
                    double centroX = jsonForma.getDouble("centroX");
                    double centroY = jsonForma.getDouble("centroY");
                    double raio = jsonForma.getDouble("raio");
                    Color cor = jsonToCor(jsonForma.getJSONObject("cor"));
                    int esp = jsonForma.getInt("espessura");
                    formas.add(new CirculoGr((int)centroX, (int)centroY, (int)raio, cor, "", esp));

                } else if (tipo.equals("TRIANGULO")) {
                    double x1 = jsonForma.getDouble("x1");
                    double y1 = jsonForma.getDouble("y1");
                    double x2 = jsonForma.getDouble("x2");
                    double y2 = jsonForma.getDouble("y2");
                    double x3 = jsonForma.getDouble("x3");
                    double y3 = jsonForma.getDouble("y3");
                    Color cor = jsonToCor(jsonForma.getJSONObject("cor"));
                    int esp = jsonForma.getInt("espessura");
                    Ponto p1 = new Ponto(x1, y1);
                    Ponto p2 = new Ponto(x2, y2);
                    Ponto p3 = new Ponto(x3, y3);
                    formas.add(new TrianguloGraf(p1, p2, p3, cor, esp));
                }
            }
        }
        return formas;
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

    // --- Métodos auxiliares de conversão ---

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
     * Converte um objeto Color para formato JSON.
     * 
     * @param cor cor a ser convertida
     * @return objeto JSON contendo componentes RGB da cor
     */
    private static JSONObject corToJson(Color cor) {
        JSONObject json = new JSONObject();
        json.put("r", cor.getRed());
        json.put("g", cor.getGreen());
        json.put("b", cor.getBlue());
        return json;
    }

    /**
     * Converte um objeto JSON para Color.
     * 
     * @param json objeto JSON contendo componentes RGB
     * @return objeto Color correspondente
     */
    private static Color jsonToCor(JSONObject json) {
        int r = json.getInt("r");
        int g = json.getInt("g");
        int b = json.getInt("b");
        return new Color(r, g, b);
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