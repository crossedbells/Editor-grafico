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
 * Painel responsavel pelo desenho de primitivos graficos.
 * Gerencia a interacao com o usuario atraves de eventos de mouse e renderiza
 * as formas geometricas na tela. Suporta funcoes de desfazer, refazer e
 * persistencia em formato JSON.
 * 
 * @author Amora Marinho Machado
 * @author Gabriel Azevedo Cruz
 * @author Gabriel Mechi Lima
 * @author Luiz Fernando de Marchi Andrade
 * @version 05/09/2025
 */
public class PainelDesenho extends JPanel implements MouseListener, MouseMotionListener {

    // --- Variáveis da UI e de Estado ---

    /** Label para exibir mensagens ao usuario */
    private JLabel msg;

    /** Tipo de primitivo atualmente selecionado */
    private TipoPrimitivo tipo = TipoPrimitivo.NENHUM;

    /** Indica se o viewport esta ativo */
    private boolean comViewport;

    /** Cor atual para desenho */
    private Color corAtual = Color.BLACK;

    /** Espessura atual do traço */
    private int espessura = 1;

    // --- Coordenadas temporárias para desenho ---

    /** Coordenada X do primeiro ponto */
    private int x1;

    /** Coordenada Y do primeiro ponto */
    private int y1;

    /** Coordenada X do segundo ponto */
    private int x2;

    /** Coordenada Y do segundo ponto */
    private int y2;

    /** Coordenada X do terceiro ponto */
    private int x3;

    /** Coordenada Y do terceiro ponto */
    private int y3;

    /** Contador de cliques do mouse */
    private int cliques = 0;

    // --- Estruturas de Dados para Formas e Histórico ---

    /** Lista de formas desenhadas na tela */
    private List<Object> formas = new ArrayList<>();

    /** Lista de formas desfeitas (para funcao refazer) */
    private List<Object> desfeitas = new ArrayList<>();

    /** Reta temporaria durante o desenho (feedback visual) */
    private RetaGr retaElastica = null;

    /** Triangulo temporario durante o desenho (feedback visual) */
    private TrianguloGraf trianguloElastico = null;

    /** Retangulo temporario durante o desenho (feedback visual) */
    private RetanguloGraf retanguloElastico = null;

    /** Circulo temporario durante o desenho (feedback visual) */
    private CirculoGr circuloElastico = null;

    /** Indica se esta desenhando um circulo */
    private boolean desenhandoCirculo = false;

    /** Indica se esta desenhando um triangulo */
    private boolean desenhandoTriangulo = false;

    /** Estado do desenho do triangulo: 0=aguardando p1, 1=aguardando p2, 2=aguardando p3 */
    private int estadoTriangulo = 0;

    /**
     * Construtor do painel de desenho.
     * 
     * @param msg label para exibir mensagens
     * @param tipo tipo inicial de primitivo
     * @param corAtual cor inicial de desenho
     * @param esp espessura inicial do traço
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
     * Define o tipo de primitivo a ser desenhado.
     * 
     * @param tipo tipo de primitivo
     */
    public void setTipo(TipoPrimitivo tipo) {
        this.tipo = tipo;
        this.cliques = 0;
    }

    /**
     * Retorna o tipo de primitivo atual.
     * 
     * @return tipo de primitivo
     */
    public TipoPrimitivo getTipo(){ return this.tipo; }

    /**
     * Define a espessura do traço.
     * 
     * @param esp espessura em pixels
     */
    public void setEsp(int esp){ this.espessura = esp; }

    /**
     * Retorna a espessura atual do traço.
     * 
     * @return espessura em pixels
     */
    public int getEsp(){ return this.espessura; }

    /**
     * Define a cor atual de desenho.
     * 
     * @param corAtual cor para desenho
     */
    public void setCorAtual(Color corAtual){ this.corAtual = corAtual; }

    /**
     * Retorna a cor atual de desenho.
     * 
     * @return cor atual
     */
    public Color getCorAtual(){ return this.corAtual; }

    /**
     * Define o label de mensagens.
     * 
     * @param msg label para exibir mensagens
     */
    public void setMsg(JLabel msg){ this.msg = msg; }

    /**
     * Retorna o label de mensagens.
     * 
     * @return label de mensagens
     */
    public JLabel getMsg(){ return this.msg; }

    /**
     * Verifica se o viewport esta ativo.
     * 
     * @return true se viewport esta ativo, false caso contrario
     */
    public boolean isComViewport() { return comViewport; }

    /**
     * Define se o viewport esta ativo.
     * 
     * @param comViewport true para ativar viewport, false para desativar
     */
    public void setComViewport(boolean comViewport) { this.comViewport = comViewport; }

    // --- Lógica de Desenho e Eventos de Mouse ---

    /**
     * Tratador de evento de pressionar botao do mouse.
     * Inicia o processo de desenho de primitivos baseado no tipo selecionado.
     * 
     * @param e evento do mouse
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (tipo == TipoPrimitivo.PONTO) {
            formas.add(new PontoGr(e.getX(), e.getY(), getCorAtual(), getEsp()));
            desfeitas.clear();
        } else if (tipo == TipoPrimitivo.RETA_EQ ||
        tipo == TipoPrimitivo.RETA_MP ||
        tipo == TipoPrimitivo.RETA_LIB) {
            x1 = e.getX();
            y1 = e.getY();
            x2 = x1;
            y2 = y1;
            retaElastica = new RetaGr(x1, y1, x2, y2, corAtual, espessura);
        } else if (tipo == TipoPrimitivo.RETANGULO) {
            x1 = e.getX();
            y1 = e.getY();
            retanguloElastico = new RetanguloGraf(new Ponto(x1, y1), new Ponto(x1, y1), getCorAtual(), getEsp());
        } else if (tipo == TipoPrimitivo.CIRCULO_EQ ||
        tipo == TipoPrimitivo.CIRCULO_MP ||
        tipo == TipoPrimitivo.CIRCULO_LIB) {
            x1 = e.getX();
            y1 = e.getY();
            circuloElastico = new CirculoGr(x1, y1, 0, getCorAtual(), "", getEsp());
            desenhandoCirculo = true;
        } else if (tipo == TipoPrimitivo.TRIANGULO) {
            if (estadoTriangulo == 0) {
                x1 = e.getX();
                y1 = e.getY();
                trianguloElastico = new TrianguloGraf(
                    new Ponto(x1, y1),
                    new Ponto(x1, y1),
                    new Ponto(x1, y1),
                    getCorAtual(),
                    getEsp()
                );
                estadoTriangulo = 1;
            } else if (estadoTriangulo == 1) {
                x2 = e.getX();
                y2 = e.getY();
                trianguloElastico.atualizarP2(x2, y2);
                trianguloElastico.atualizarP3(x2, y2);
                estadoTriangulo = 2;
            } else if (estadoTriangulo == 2) {
                x3 = e.getX();
                y3 = e.getY();
                trianguloElastico.atualizarP3(x3, y3);
                formas.add(trianguloElastico);
                trianguloElastico = null;
                estadoTriangulo = 0;
                desfeitas.clear();
            }
        }
        repaint();
    }

    /**
     * Tratador de evento de arrastar o mouse.
     * Atualiza as formas elasticas durante o desenho para fornecer feedback visual.
     * 
     * @param e evento do mouse
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if ((tipo == TipoPrimitivo.RETA_EQ ||
            tipo == TipoPrimitivo.RETA_MP ||
            tipo == TipoPrimitivo.RETA_LIB) && retaElastica != null) {
            x2 = e.getX();
            y2 = e.getY();
            retaElastica.atualizarPontoFinal(x2, y2);
            repaint();
        } else if (tipo == TipoPrimitivo.TRIANGULO && trianguloElastico != null) {
            if (estadoTriangulo == 1) {
                trianguloElastico.atualizarP2(e.getX(), e.getY());
                trianguloElastico.atualizarP3(x1, y1);
            } else if (estadoTriangulo == 2) {
                trianguloElastico.atualizarP3(e.getX(), e.getY());
            }
            repaint();
        } else if (tipo == TipoPrimitivo.RETANGULO && retanguloElastico != null) {
            retanguloElastico.atualizarP2(e.getX(), e.getY());
            repaint();
        } else if ((tipo == TipoPrimitivo.CIRCULO_EQ ||
            tipo == TipoPrimitivo.CIRCULO_MP ||
            tipo == TipoPrimitivo.CIRCULO_LIB) && circuloElastico != null && desenhandoCirculo) {
            circuloElastico.atualizarRaio(e.getX(), e.getY());
            repaint();
        }
    }

    /**
     * Tratador de evento de soltar botao do mouse.
     * Finaliza o desenho da forma e adiciona a lista de formas.
     * 
     * @param e evento do mouse
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if ((tipo == TipoPrimitivo.RETA_EQ ||
            tipo == TipoPrimitivo.RETA_MP ||
            tipo == TipoPrimitivo.RETA_LIB) && retaElastica != null) {
            x2 = e.getX();
            y2 = e.getY();
            retaElastica.atualizarPontoFinal(x2, y2);
            formas.add(retaElastica);
            retaElastica = null;
            repaint();
        } else if (tipo == TipoPrimitivo.RETANGULO && retanguloElastico != null) {
            retanguloElastico.atualizarP2(e.getX(), e.getY());
            formas.add(retanguloElastico);
            retanguloElastico = null;
            desfeitas.clear();
            repaint();
        } else if (tipo == TipoPrimitivo.CIRCULO_EQ || tipo == TipoPrimitivo.CIRCULO_MP || tipo == TipoPrimitivo.CIRCULO_LIB) {
            if (desenhandoCirculo && circuloElastico != null) {
                x2 = e.getX();
                y2 = e.getY();
                circuloElastico.atualizarRaio(x2, y2);
                formas.add(circuloElastico);
                circuloElastico = null;
                desenhandoCirculo = false;
                desfeitas.clear();
                repaint();
            }
        }
    }

    /**
     * Renderiza o componente na tela.
     * Desenha todas as formas armazenadas e as formas elasticas temporarias.
     * 
     * @param g contexto grafico
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        desenharPrimitivos(g2d);

        if (retaElastica != null) {
            retaElastica.desenharRetaLib(g2d);
        }
        if (retanguloElastico != null) {
            retanguloElastico.desenharRetangulo(g2d);
        }
        if (trianguloElastico != null) {
            trianguloElastico.desenharTriangulo(g2d);
        }
        if (circuloElastico != null && desenhandoCirculo) {
            circuloElastico.desenharCirculoLib(g2d);
        }
    }

    /**
     * Desenha todos os primitivos armazenados na lista de formas.
     * 
     * @param g contexto grafico 2D
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

    // --- Métodos de Controle ---

    /**
     * Limpa toda a tela, removendo todas as formas desenhadas.
     * Reseta tambem o historico de undo/redo e coordenadas temporarias.
     */
    public void limparTela() {
        formas.clear();
        desfeitas.clear();
        x1 = y1 = x2 = y2 = x3 = y3 = 0;
        cliques = 0;
        repaint();
    }

    /**
     * Desfaz a ultima forma desenhada.
     * Move a forma para a lista de desfeitas, permitindo refaze-la.
     */
    public void desfazer() {
        if (!formas.isEmpty()) {
            Object ultimaForma = formas.remove(formas.size() - 1);
            desfeitas.add(ultimaForma);
            repaint();
        }
    }

    /**
     * Refaz a ultima forma desfeita.
     * Move a forma da lista de desfeitas de volta para a lista de formas.
     */
    public void refazer() {
        if (!desfeitas.isEmpty()) {
            Object ultimaFormaDesfeita = desfeitas.remove(desfeitas.size() - 1);
            formas.add(ultimaFormaDesfeita);
            repaint();
        }
    }

    // --- Métodos de Salvar/Carregar em JSON ---

    /**
     * Salva todas as formas desenhadas em um arquivo JSON.
     * Delega a responsabilidade de serializacao para a classe jsonCoisas.
     * 
     * @param arquivo arquivo onde sera salvo o desenho
     * @throws IOException se houver erro ao escrever no arquivo
     */
    public void salvar(File arquivo) throws IOException {
        jsonCoisas.salvarFormasGraficas(formas, arquivo);
    }

    /**
     * Carrega formas de um arquivo JSON.
     * Delega a responsabilidade de desserializacao para a classe jsonCoisas.
     * 
     * @param arquivo arquivo JSON a ser carregado
     * @throws IOException se houver erro ao ler o arquivo
     */
    public void carregar(File arquivo) throws IOException {
        limparTela();
        formas = jsonCoisas.carregarFormasGraficas(arquivo);
        repaint();
    }

    /**
     * Converte um objeto Color para formato JSON.
     * 
     * @param cor cor a ser convertida
     * @return objeto JSON contendo componentes RGB da cor
     */
    private JSONObject corToJson(Color cor) {
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
    private Color jsonToCor(JSONObject json) {
        int r = json.getInt("r");
        int g = json.getInt("g");
        int b = json.getInt("b");
        return new Color(r, g, b);
    }

    // --- Listeners de Mouse não utilizados ---

    /**
     * Tratador de clique do mouse (nao utilizado).
     * 
     * @param e evento do mouse
     */
    @Override public void mouseClicked(MouseEvent e) {}

    /**
     * Tratador de entrada do mouse na area (nao utilizado).
     * 
     * @param e evento do mouse
     */
    @Override public void mouseEntered(MouseEvent e) {}

    /**
     * Tratador de saida do mouse da area (nao utilizado).
     * 
     * @param e evento do mouse
     */
    @Override public void mouseExited(MouseEvent e) {}

    /**
     * Tratador de movimento do mouse.
     * Atualiza a mensagem exibindo as coordenadas atuais e o tipo de primitivo.
     * 
     * @param e evento do mouse
     */
    @Override public void mouseMoved(MouseEvent e) {
        this.msg.setText("(" + e.getX() + ", " + e.getY() + ") - " + getTipo());
    }
}