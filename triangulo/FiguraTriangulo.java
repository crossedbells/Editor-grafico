package triangulo;

import java.awt.Color;
import java.awt.Graphics2D;
import ponto.Ponto;

/**
 * Implementação de métodos estáticos para desenhar triângulos
 * utilizando a API gráfica do Java ({@code Graphics2D}).
 *
 * <p>Esta classe serve como utilitário para criação de objetos
 * {@link TrianguloGr} e delegação da lógica de desenho.</p>
 *
 * <p>Exemplo de uso:</p>
 * <pre>
 *     Graphics2D g = (Graphics2D) painel.getGraphics();
 *     FiguraTriangulo.desenharTriangulo(g, 10, 20, 60, 80, 100, 30, Color.RED, 2);
 * </pre>
 *
 * Nesse exemplo, um triângulo vermelho será desenhado
 * conectando os pontos (10,20), (60,80) e (100,30).
 * 
 * @author Seu Nome
 * @version 1.0
 */
public class FiguraTriangulo {

    /**
     * Desenha um triângulo na área de desenho fornecida.
     * O triângulo é definido por três pontos {@code (x1,y1)}, {@code (x2,y2)} e {@code (x3,y3)}.
     *
     * @param g         o contexto gráfico {@code Graphics2D} onde o triângulo será desenhado
     * @param x1        coordenada X do primeiro ponto
     * @param y1        coordenada Y do primeiro ponto
     * @param x2        coordenada X do segundo ponto
     * @param y2        coordenada Y do segundo ponto
     * @param x3        coordenada X do terceiro ponto
     * @param y3        coordenada Y do terceiro ponto
     * @param cor       a cor da linha do triângulo
     * @param espessura a espessura da linha do triângulo
     */
    public static void desenharTriangulo(Graphics2D g, int x1, int y1, int x2, int y2, int x3, int y3, Color cor, int espessura) {
        // Cria os pontos que definem o triângulo
        Ponto p1 = new Ponto(x1, y1);
        Ponto p2 = new Ponto(x2, y2);
        Ponto p3 = new Ponto(x3, y3);

        // Instancia um triângulo gráfico
        TrianguloGr triangulo = new TrianguloGr(p1, p2, p3, cor, espessura);

        // Desenha o triângulo na área de desenho
        triangulo.desenharTriangulo(g);
    }
}
