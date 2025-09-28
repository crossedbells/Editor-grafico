package retangulo;
import java.awt.Color;
import java.awt.Graphics2D;
import ponto.Ponto;
import reta.RetaGr;

/**
 * Representa um retângulo gráfico.
 * <p>
 * Esta classe herda de {@link Retangulo}, adicionando atributos gráficos como
 * cor e espessura da linha, além do método para desenhar o retângulo na tela.
 * </p>
 *
 * <p>O retângulo é desenhado utilizando quatro instâncias da classe {@link RetaGr},
 * que representam cada lado do retângulo.</p>
 *
 * <p>Exemplo de uso:</p>
 * <pre>
 *     Ponto p1 = new Ponto(10, 20);
 *     Ponto p2 = new Ponto(100, 80);
 *     RetanguloGraf ret = new RetanguloGraf(p1, p2, Color.RED, 3);
 *     ret.desenharRetangulo(g);
 * </pre>
 *
 * Nesse exemplo, um retângulo vermelho com espessura 3 será desenhado
 * no contexto gráfico {@code g}.
 *
 * @author Seu Nome
 * @version 1.0
 */
public class RetanguloGraf extends Retangulo {
    /** Cor do retângulo (padrão: preto). */
    private Color cor = Color.BLACK;

    /** Espessura da linha do retângulo (padrão: 1). */
    private int espessura = 1;

    /**
     * Constrói um retângulo gráfico a partir de dois pontos opostos,
     * definindo também a cor e a espessura da linha.
     *
     * @param p1        um dos cantos do retângulo
     * @param p2        o canto oposto ao {@code p1}
     * @param cor       a cor da linha do retângulo
     * @param espessura a espessura da linha do retângulo
     */
    public RetanguloGraf(Ponto p1, Ponto p2, Color cor, int espessura) {
        super(p1, p2);
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
     * Desenha o retângulo no contexto gráfico fornecido.
     * <p>
     * O desenho é feito utilizando quatro objetos {@link RetaGr},
     * cada um representando um dos lados do retângulo.
     * </p>
     *
     * @param g o objeto {@code Graphics2D} usado para desenhar
     */
    public void desenharRetangulo(Graphics2D g) {
        // Os 4 pontos do retângulo já são calculados na classe pai (Retangulo)
        Ponto p1 = getP1(); // Canto inicial (x1, y1)
        Ponto p2 = getP2(); // Canto oposto (x2, y2)
        Ponto p3 = getP3(); // Canto (x1, y2)
        Ponto p4 = getP4(); // Canto (x2, y1)

        // Criando as 4 retas gráficas com a cor e espessura deste retângulo
        RetaGr lado1 = new RetaGr((int)p1.getX(), (int)p1.getY(), (int)p3.getX(), (int)p3.getY(), this.cor, "", this.espessura);
        RetaGr lado2 = new RetaGr((int)p3.getX(), (int)p3.getY(), (int)p2.getX(), (int)p2.getY(), this.cor, "", this.espessura);
        RetaGr lado3 = new RetaGr((int)p2.getX(), (int)p2.getY(), (int)p4.getX(), (int)p4.getY(), this.cor, "", this.espessura);
        RetaGr lado4 = new RetaGr((int)p4.getX(), (int)p4.getY(), (int)p1.getX(), (int)p1.getY(), this.cor, "", this.espessura);

        // Desenhando cada lado do retângulo
        lado1.desenharRetaLib(g);
        lado2.desenharRetaLib(g);
        lado3.desenharRetaLib(g);
        lado4.desenharRetaLib(g);
    }
}
