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

    // --- Vari�veis da UI e de Estado ---
    
    /** Label para exibir mensagens ao usuario */
    private JLabel msg;
    
    /** Tipo de primitivo atualmente selecionado */
    private TipoPrimitivo tipo = TipoPrimitivo.NENHUM;
    
    /** Indica se o viewport esta ativo */
    private boolean comViewport;
    
    /** Cor atual para desenho */
    private Color corAtual = Color.BLACK;
    
    /** Espessura atual do tra�o */
    private int espessura = 1;

    // --- Coordenadas tempor�rias para desenho ---
    
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

    // --- Estruturas de Dados para Formas e Hist�rico ---
    
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
     * @param esp espessura inicial do tra�o
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
     * Define a espessura do tra�o.
     * 
     * @param esp espessura em pixels
     */
    public void setEsp(int esp){ this.espessura = esp; }

    /**
     * Retorna a espessura atual do tra�o.
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

    // --- L�gica de Desenho e Eventos de Mouse ---

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

    // --- M�todos de Controle ---
    
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

    // --- M�todos de Salvar/Carregar em JSON ---
    
    /**
     * Salva todas as formas desenhadas em um arquivo JSON.
     * Serializa cada forma com suas propriedades (coordenadas, cor, espessura).
     * 
     * @param arquivo arquivo onde sera salvo o desenho
     * @throws IOException se houver erro ao escrever no arquivo
     */
    public void salvar(File arquivo) throws IOException {
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
            file.write(root.toString(4)); // Indenta��o de 4 espa�os
        }
    }

    /**
     * Carrega formas de um arquivo JSON.
     * Deserializa as formas e as adiciona ao painel de desenho.
     * 
     * @param arquivo arquivo JSON a ser carregado
     * @throws IOException se houver erro ao ler o arquivo
     */
    public void carregar(File arquivo) throws IOException {
        limparTela();

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

    // --- Listeners de Mouse n�o utilizados ---
    
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