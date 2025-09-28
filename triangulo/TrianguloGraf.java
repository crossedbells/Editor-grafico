package triangulo;
import java.awt.Color;
import java.awt.Graphics2D;
import ponto.Ponto;
import reta.RetaGr;

/**
 * Classe para representar um triângulo gráfico.
 * Herda de Triangulo e adiciona propriedades de desenho.
 * @author (Seu nome)
 * @version (Data)
 */
public class TrianguloGraf extends Triangulo {
    private Color cor = Color.BLACK;
    private int espessura = 1;

    /**
     * Construtor que recebe três pontos e atributos gráficos.
     */
    public TrianguloGraf(Ponto p1, Ponto p2, Ponto p3, Color cor, int espessura) {
        super(p1, p2, p3);
        this.cor = cor;
        this.espessura = espessura;
    }
    
        public Color getCor() {
        return this.cor;
    }
    
    public int getEspessura() {
        return this.espessura;
    }
    
    /**
     * Desenha o triângulo no contexto gráfico fornecido, utilizando 3 objetos RetaGr.
     * @param g Objeto Graphics2D para desenhar.
     */
    public void desenharTriangulo(Graphics2D g) {
        Ponto p1 = getP1();
        Ponto p2 = getP2();
        Ponto p3 = getP3();

        // Criando as 3 retas gráficas com a cor e espessura deste triângulo
        RetaGr lado1 = new RetaGr((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY(), this.cor, "", this.espessura);
        RetaGr lado2 = new RetaGr((int)p2.getX(), (int)p2.getY(), (int)p3.getX(), (int)p3.getY(), this.cor, "", this.espessura);
        RetaGr lado3 = new RetaGr((int)p3.getX(), (int)p3.getY(), (int)p1.getX(), (int)p1.getY(), this.cor, "", this.espessura);

        // Desenhando cada lado do triângulo
        lado1.desenharRetaLib(g);
        lado2.desenharRetaLib(g);
        lado3.desenharRetaLib(g);
    }
}