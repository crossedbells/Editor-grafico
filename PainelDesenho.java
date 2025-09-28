import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

// Importações dos objetos gráficos
import circulo.CirculoGr;
import ponto.Ponto;
import ponto.PontoGr;
import reta.RetaGr;
import retangulo.RetanguloGraf;
import triangulo.TrianguloGraf;
import constantes.TipoPrimitivo;

/**
 * Implementa os eventos de mouse para desenhar primitivos, armazenar o histórico
 * de desenhos e permitir salvar/carregar.
 * * @author (Seu Nome, adaptado de Julio Arakaki)
 * @version (Data Atual)
 */
public class PainelDesenho extends JPanel implements MouseListener, MouseMotionListener {

    // --- Variáveis da UI e de Estado ---
    private JLabel msg;
    private TipoPrimitivo tipo = TipoPrimitivo.NENHUM;
    private boolean comViewport;
    private Color corAtual = Color.BLACK;
    private int espessura = 1;

    // --- Coordenadas temporárias para desenho ---
    private int x1, y1, x2, y2, x3, y3;
    private int cliques = 0; // Substitui a variável 'primeiraVez'

    // --- Estruturas de Dados para Formas e Histórico ---
    private List<Object> formas = new ArrayList<>();
    private List<Object> desfeitas = new ArrayList<>();

    /**
     * Construtor para objetos da classe PainelDesenho
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
    public void setTipo(TipoPrimitivo tipo) {
        this.tipo = tipo;
        this.cliques = 0; // Zera o contador sempre que uma nova ferramenta é selecionada
    }
    
    // (O resto dos seus getters e setters continua aqui: getTipo, setEsp, getEsp, etc...)
    // Eles não precisam de alteração.
    public TipoPrimitivo getTipo(){ return this.tipo; }
    public void setEsp(int esp){ this.espessura = esp; }
    public int getEsp(){ return this.espessura; }
    public void setCorAtual(Color corAtual){ this.corAtual = corAtual; }
    public Color getCorAtual(){ return this.corAtual; }
    public void setMsg(JLabel msg){ this.msg = msg; }
    public JLabel getMsg(){ return this.msg; }
    public boolean isComViewport() { return comViewport; }
    public void setComViewport(boolean comViewport) { this.comViewport = comViewport; }

    // --- Lógica de Desenho e Eventos de Mouse ---

    @Override
    public void mousePressed(MouseEvent e) {
        if (tipo == TipoPrimitivo.PONTO) {
            formas.add(new PontoGr(e.getX(), e.getY(), getCorAtual(), getEsp()));
            desfeitas.clear();
        } else if (tipo == TipoPrimitivo.RETA_EQ || tipo == TipoPrimitivo.RETA_MP || tipo == TipoPrimitivo.RETA_LIB || tipo == TipoPrimitivo.RETANGULO) {
            if (cliques == 0) {
                x1 = e.getX();
                y1 = e.getY();
                cliques++;
            } else {
                x2 = e.getX();
                y2 = e.getY();
                if (tipo == TipoPrimitivo.RETANGULO) {
                    formas.add(new RetanguloGraf(new Ponto(x1, y1), new Ponto(x2, y2), getCorAtual(), getEsp()));
                } else {
                    formas.add(new RetaGr(x1, y1, x2, y2, getCorAtual(), "", getEsp()));
                }
                desfeitas.clear();
                cliques = 0;
            }
        } else if (tipo == TipoPrimitivo.CIRCULO_EQ || tipo == TipoPrimitivo.CIRCULO_MP || tipo == TipoPrimitivo.CIRCULO_LIB) {
            if (cliques == 0) {
                x1 = e.getX(); // centro x
                y1 = e.getY(); // centro y
                cliques++;
            } else {
                x2 = e.getX(); // ponto na borda
                y2 = e.getY(); // ponto na borda
                int raio = (int) Math.sqrt(Math.pow((y2 - y1), 2) + Math.pow((x2 - x1), 2));
                formas.add(new CirculoGr(x1, y1, raio, getCorAtual(), "", getEsp()));
                desfeitas.clear();
                cliques = 0;
            }
        } else if (tipo == TipoPrimitivo.TRIANGULO) {
            if (cliques == 0) {
                x1 = e.getX();
                y1 = e.getY();
                cliques++;
            } else if (cliques == 1) {
                x2 = e.getX();
                y2 = e.getY();
                cliques++;
            } else {
                x3 = e.getX();
                y3 = e.getY();
                formas.add(new TrianguloGraf(new Ponto(x1, y1), new Ponto(x2, y2), new Ponto(x3, y3), getCorAtual(), getEsp()));
                desfeitas.clear();
                cliques = 0;
            }
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        desenharPrimitivos((Graphics2D) g);
    }

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
    public void limparTela() {
        formas.clear();
        desfeitas.clear();
        x1 = y1 = x2 = y2 = x3 = y3 = 0;
        cliques = 0;
        repaint();
    }
    
    public void desfazer() {
        if (!formas.isEmpty()) {
            Object ultimaForma = formas.remove(formas.size() - 1);
            desfeitas.add(ultimaForma);
            repaint();
        }
    }

    public void refazer() {
        if (!desfeitas.isEmpty()) {
            Object ultimaFormaDesfeita = desfeitas.remove(desfeitas.size() - 1);
            formas.add(ultimaFormaDesfeita);
            repaint();
        }
    }
    
    // --- Métodos de Salvar/Carregar ---
        public void salvar(File arquivo) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(arquivo))) {
            for (Object forma : formas) {
                String linha = ""; // Corrigido de "inha" para "linha"
                
                if (forma instanceof PontoGr) {
                    PontoGr p = (PontoGr) forma;
                    Color cor = p.getCorPto(); 
                    int esp = p.getDiametro();
                    linha = String.format("PONTO,%d,%d,%d,%d,%d,%d", cor.getRed(), cor.getGreen(), cor.getBlue(), esp, (int) p.getX(), (int) p.getY());
                
                } else if (forma instanceof RetaGr) {
                    RetaGr r = (RetaGr) forma;
                    Color cor = r.getCorReta(); 
                    int esp = r.getEspReta();
                    linha = String.format("RETA,%d,%d,%d,%d,%d,%d,%d,%d", cor.getRed(), cor.getGreen(), cor.getBlue(), esp, (int) r.p1.getX(), (int) r.p1.getY(), (int) r.p2.getX(), (int) r.p2.getY());
                
                } else if (forma instanceof RetanguloGraf) {
                    RetanguloGraf r = (RetanguloGraf) forma;
                    Color cor = r.getCor(); 
                    int esp = r.getEspessura();
                    linha = String.format("RETANGULO,%d,%d,%d,%d,%d,%d,%d,%d", cor.getRed(), cor.getGreen(), cor.getBlue(), esp, (int) r.getP1().getX(), (int) r.getP1().getY(), (int) r.getP2().getX(), (int) r.getP2().getY());
                
                } else if (forma instanceof CirculoGr) {
                    CirculoGr c = (CirculoGr) forma;
                    Color cor = c.getCorCirculo(); 
                    int esp = c.getEspCirculo();
                    linha = String.format("CIRCULO,%d,%d,%d,%d,%d,%d,%d", cor.getRed(), cor.getGreen(), cor.getBlue(), esp, (int) c.getCentro().getX(), (int) c.getCentro().getY(), (int) c.getRaio());
                
                } else if (forma instanceof TrianguloGraf) {
                    TrianguloGraf t = (TrianguloGraf) forma;
                    Color cor = t.getCor(); 
                    int esp = t.getEspessura();
                    linha = String.format("TRIANGULO,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d", cor.getRed(), cor.getGreen(), cor.getBlue(), esp, (int) t.getP1().getX(), (int) t.getP1().getY(), (int) t.getP2().getX(), (int) t.getP2().getY(), (int) t.getP3().getX(), (int) t.getP3().getY());
                }
                
                if (!linha.isEmpty()) writer.println(linha);
            }
        }
    }

    public void carregar(File arquivo) throws IOException {
        limparTela();
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] parts = linha.split(",");
                if(parts.length < 5) continue; // Linha inválida

                String tipoForma = parts[0];
                Color cor = new Color(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
                int esp = Integer.parseInt(parts[4]);

                if (tipoForma.equals("PONTO") && parts.length >= 7) {
                    formas.add(new PontoGr(Integer.parseInt(parts[5]), Integer.parseInt(parts[6]), cor, esp));
                } else if (tipoForma.equals("RETA") && parts.length >= 9) {
                    formas.add(new RetaGr(Integer.parseInt(parts[5]), Integer.parseInt(parts[6]), Integer.parseInt(parts[7]), Integer.parseInt(parts[8]), cor, "", esp));
                } else if (tipoForma.equals("RETANGULO") && parts.length >= 9) {
                    formas.add(new RetanguloGraf(new Ponto(Integer.parseInt(parts[5]), Integer.parseInt(parts[6])), new Ponto(Integer.parseInt(parts[7]), Integer.parseInt(parts[8])), cor, esp));
                } else if (tipoForma.equals("CIRCULO") && parts.length >= 8) {
                    formas.add(new CirculoGr(Integer.parseInt(parts[5]), Integer.parseInt(parts[6]), Integer.parseInt(parts[7]), cor, "", esp));
                } else if (tipoForma.equals("TRIANGULO") && parts.length >= 11) {
                    Ponto p1 = new Ponto(Integer.parseInt(parts[5]), Integer.parseInt(parts[6]));
                    Ponto p2 = new Ponto(Integer.parseInt(parts[7]), Integer.parseInt(parts[8]));
                    Ponto p3 = new Ponto(Integer.parseInt(parts[9]), Integer.parseInt(parts[10]));
                    formas.add(new TrianguloGraf(p1, p2, p3, cor, esp));
                }
            }
        }
        repaint();
    }

    // --- Listeners de Mouse não utilizados ---
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseDragged(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {
        this.msg.setText("(" + e.getX() + ", " + e.getY() + ") - " + getTipo());
    }
}