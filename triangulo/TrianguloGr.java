package triangulo;

import java.awt.Color;
import java.awt.Graphics2D;
import ponto.Ponto;
import reta.RetaGr;

/**
 * Representa um triângulo gráfico.
 * <p>
 * Esta classe herda de {@link Triangulo}, adicionando atributos gráficos como
 * cor e espessura da linha, além de um método para desenhar o triângulo em um
 * contexto gráfico {@link Graphics2D}.
 * </p>
 *
 * <p>O triângulo é desenhado utilizando três instâncias da classe {@link RetaGr},
 * que representam os lados do triângulo.</p>
 *
 * <p>Exemplo de uso:</p>
 * <pre>
 *     Ponto p1 = new Ponto(50, 50);
 *     Ponto p2 = new Ponto(100, 150);
 *     Ponto p3 = new Ponto(150, 50);
 *
 *     TrianguloGr t = new TrianguloGr(p1, p2, p3, Color.BLUE, 2);
 *     t.desenharTriangulo(g);
 * </pre>
 *
 * Nesse exemplo, um triângulo azul será desenhado com espessura de linha 2.
 * 
 * @author Seu Nome
 * @version 1.0
 */
public class TrianguloGr extends Triangulo {
    /** Cor do triângulo (padrão: preto). */
    private Color cor = Color.BLACK;

    /** Espessura da linha do triângulo (padrão: 1). */
    private int espessura = 1;

    /**
     * Constrói um triângulo gráfico a partir de três pontos
     * e define os atributos de cor e espessura.
     *
     * @param p1        o primeiro vértice do triângulo
     * @param p2        o segundo vértice do triângulo
     * @param p3        o terceiro vértice do triângulo
     * @param cor       a cor da linha do triângulo
     * @param espessura a espessura da linha do triângulo
     */
    public TrianguloGr(Ponto p1, Ponto p2, Ponto p3, Color cor, int espessura) {
        super(p1, p2, p3);
        this.cor = cor;
        this.espessura = espessura;
    }

    /**
     * Desenha o triângulo no contexto gráfico fornecido.
     * <p>
     * O desenho é realizado utilizando três objetos {@link RetaGr},
     * cada um representando um dos lados do triângulo.
     * </p>
     *
     * @param g o objeto {@code Graphics2D} usado para desenhar
     */
    public void desenharTriangulo(Graphics2D g) {
        Ponto p1 = getP1();
        Ponto p2 = getP2();
        Ponto p3 = getP3();

        // Criando as 3 retas gráficas com a cor e espessura deste triângulo
        RetaGr lado1 = new RetaGr((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY(), this.cor, "", this.espessura);
        RetaGr lado2 = new RetaGr((int) p2.getX(), (int) p2.getY(), (int) p3.getX(), (int) p3.getY(), this.cor, "", this.espessura);
        RetaGr lado3 = new RetaGr((int) p3.getX(), (int) p3.getY(), (int) p1.getX(), (int) p1.getY(), this.cor, "", this.espessura);

        // Desenhando cada lado do triângulo
        lado1.desenharRetaLib(g);
        lado2.desenharRetaLib(g);
        lado3.desenharRetaLib(g); 
    }

    /**
     * Atualiza o primeiro vértice do triângulo gráfico.
     */
    public void atualizarP1(int x, int y) {
        super.atualizarP1(x, y);
    }

    /**
     * Atualiza o segundo vértice do triângulo gráfico.
     */
    public void atualizarP2(int x, int y) {
        super.atualizarP2(x, y);
    }

    /**
     * Atualiza o terceiro vértice do triângulo gráfico.
     */
    public void atualizarP3(int x, int y) {
        super.atualizarP3(x, y);
    }
}
