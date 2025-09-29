import circulo.CirculoGr;
import constantes.TipoPrimitivo;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import ponto.Ponto;
import ponto.PontoGr;
import reta.RetaGr;
import retangulo.RetanguloGraf;
import triangulo.TrianguloGraf;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Painel de desenho que permite criar primitivas geométricas como pontos, retas,
 * retângulos, triângulos e círculos. Suporta operações de desfazer/refazer,
 * desenho interativo com mouse e salvamento/carregamento de arquivos JSON.
 * 
 * @author Amora Marinho Machado
 * @author Gabriel Azevedo Cruz
 * @author Gabriel Mechi Lima
 * @author Luiz Fernando de Marchi Andrade
 * @version 05/09/2025
 */
public class PainelDesenho extends JPanel implements MouseListener, MouseMotionListener {

    // --- Variáveis da UI e de Estado ---
    private JLabel msg; // Label de mensagens do painel
    private TipoPrimitivo tipo = TipoPrimitivo.NENHUM; // Tipo de primitiva selecionada
    private boolean comViewport; // Define se o painel tem viewport
    private Color corAtual = Color.BLACK; // Cor atual para desenho
    private int espessura = 1; // Espessura do traço

    // --- Coordenadas temporárias para desenho ---
    private int x1, y1, x2, y2, x3, y3; // Coordenadas auxiliares
    private int cliques = 0; // Contador de cliques para desenho de múltiplos pontos

    // --- Estruturas de Dados para Formas e Histórico ---
    private List<Object> formas = new ArrayList<>(); // Lista de formas desenhadas
    private List<Object> desfeitas = new ArrayList<>(); // Lista de formas desfeitas

    // Reta elástica temporária
    private RetaGr retaElastica = null;
    private TrianguloGraf trianguloElastico = null;
    private RetanguloGraf retanguloElastico = null;
    private CirculoGr circuloElastico = null;
    private boolean desenhandoCirculo = false;
    private boolean desenhandoTriangulo = false;
    private int estadoTriangulo = 0; // Estado do desenho do triângulo: 0=p1, 1=p2, 2=p3

    /**
     * Construtor do painel de desenho.
     * 
     * @param msg Label para exibir mensagens.
     * @param tipo Tipo inicial de primitiva.
     * @param corAtual Cor inicial.
     * @param esp Espessura inicial do traço.
     */
    public PainelDesenho(JLabel msg, TipoPrimitivo tipo, Color corAtual, int esp) {
        setTipo(tipo);
        setMsg(msg);
        setCorAtual(corAtual);
        setEsp(esp);

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    // --- Getters e Setters ---

    /**
     * Define o tipo de primitiva a ser desenhada.
     * @param tipo Tipo de primitiva.
     */
    public void setTipo(TipoPrimitivo tipo) {
        this.tipo = tipo;
        this.cliques = 0;
    }

    public TipoPrimitivo getTipo(){ return this.tipo; }

    public void setEsp(int esp){ this.espessura = esp; }

    public int getEsp(){ return this.espessura; }

    public void setCorAtual(Color corAtual){ this.corAtual = corAtual; }

    public Color getCorAtual(){ return this.corAtual; }

    public void setMsg(JLabel msg){ this.msg = msg; }

    public JLabel getMsg(){ return this.msg; }

    public boolean isComViewport() { return comViewport; }

    public void setComViewport(boolean comViewport) { this.comViewport = comViewport; }

    // --- Eventos de Mouse para Desenho Interativo ---
    
    /**
     * Método chamado quando o mouse é pressionado.
     * Inicia o desenho da primitiva selecionada.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // Implementação conforme o tipo de primitiva
    }

    /**
     * Método chamado quando o mouse é arrastado.
     * Atualiza o desenho da primitiva elástica.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        // Implementação conforme o tipo de primitiva
    }

    /**
     * Método chamado quando o mouse é solto.
     * Finaliza o desenho da primitiva e adiciona à lista de formas.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // Implementação conforme o tipo de primitiva
    }

    /**
     * Desenha todas as primitivas do painel.
     * 
     * @param g Objeto Graphics2D para desenho.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        desenharPrimitivos(g2d);

        // Desenho de formas elásticas temporárias
    }

    /**
     * Percorre a lista de formas e desenha cada uma no painel.
     * 
     * @param g Objeto Graphics2D para desenho.
     */
    public void desenharPrimitivos(Graphics2D g) {
        for (Object forma : formas) {
            if (forma instanceof PontoGr) {
                ((PontoGr) forma).desenharPonto(g);
            } else if (forma instanceof RetaGr) {
                ((RetaGr) forma).desenharRetaLib(g);
            } else if (forma instanceof CirculoGr) {
                ((CirculoGr) forma).desenharCirculoLib(g);
            } else if (forma instanceof RetanguloGraf) {
                ((RetanguloGraf) forma).desenharRetangulo(g);
            } else if (forma instanceof TrianguloGraf) {
                ((TrianguloGraf) forma).desenharTriangulo(g);
            }
        }
    }

    // --- Métodos de Controle de Desenho ---

    /**
     * Limpa todas as primitivas do painel.
     */
    public void limparTela() {
        formas.clear();
        desfeitas.clear();
        x1 = y1 = x2 = y2 = x3 = y3 = 0;
        cliques = 0;
        repaint();
    }

    /**
     * Desfaz a última ação de desenho.
     */
    public void desfazer() {
        if (!formas.isEmpty()) {
            Object ultimaForma = formas.remove(formas.size() - 1);
            desfeitas.add(ultimaForma);
            repaint();
        }
    }

    /**
     * Refaz a última ação desfeita.
     */
    public void refazer() {
        if (!desfeitas.isEmpty()) {
            Object ultimaFormaDesfeita = desfeitas.remove(desfeitas.size() - 1);
            formas.add(ultimaFormaDesfeita);
            repaint();
        }
    }

    // --- Métodos de Salvar/Carregar JSON ---

    /**
     * Salva as primitivas do painel em um arquivo JSON.
     * 
     * @param arquivo Arquivo de destino.
     * @throws IOException Se ocorrer erro de escrita.
     */
    public void salvar(File arquivo) throws IOException {
        // Implementação de serialização JSON
    }

    /**
     * Carrega primitivas de um arquivo JSON para o painel.
     * 
     * @param arquivo Arquivo de origem.
     * @throws IOException Se ocorrer erro de leitura.
     */
    public void carregar(File arquivo) throws IOException {
        limparTela();
        // Implementação de desserialização JSON
        repaint();
    }

    // --- Métodos auxiliares para conversão de cores ---
    
    private JSONObject corToJson(Color cor) {
        JSONObject json = new JSONObject();
        json.put("r", cor.getRed());
        json.put("g", cor.getGreen());
        json.put("b", cor.getBlue());
        return json;
    }

    private Color jsonToCor(JSONObject json) {
        int r = json.getInt("r");
        int g = json.getInt("g");
        int b = json.getInt("b");
        return new Color(r, g, b);
    }

    // --- Listeners de Mouse não utilizados ---
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    /**
     * Atualiza a mensagem de coordenadas do mouse.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        this.msg.setText("(" + e.getX() + ", " + e.getY() + ") - " + getTipo());
    }
}
