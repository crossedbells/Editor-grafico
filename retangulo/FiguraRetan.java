package retangulo;

import java.awt.Color;
import java.awt.Graphics2D;
import ponto.Ponto;

/**
 * Implementação de métodos estáticos para desenhar retângulos
 * utilizando a API gráfica do Java ({@code Graphics2D}).
 *
 * <p>Esta classe serve como uma camada de utilidade, criando os objetos
 * necessários e delegando o desenho a {@link RetanguloGraf}.</p>
 * 
 * <p>Exemplo de uso:</p>
 * <pre>
 *     Graphics2D g = (Graphics2D) painel.getGraphics();
 *     FiguraRetan.desenharRetangulo(g, 10, 20, 100, 80, Color.BLUE, 2);
 * </pre>
 * 
 * Nesse exemplo, um retângulo azul será desenhado entre os pontos (10,20) e (100,80).
 * 
 * @author Seu Nome
 * @version 1.0
 */
public class FiguraRetan {

    /**
     * Desenha um retângulo na área de desenho fornecida ({@code Graphics2D}).
     * O retângulo é definido por dois pontos opostos ({@code (x1, y1)} e {@code (x2, y2)}).
     *
     * @param g         o contexto gráfico {@code Graphics2D} onde o retângulo será desenhado
     * @param x1        coordenada X do primeiro ponto
     * @param y1        coordenada Y do primeiro ponto
     * @param x2        coordenada X do segundo ponto (oposto ao primeiro)
     * @param y2        coordenada Y do segundo ponto (oposto ao primeiro)
     * @param cor       cor do retângulo
     * @param espessura espessura da linha do retângulo
     */
    public static void desenharRetangulo(Graphics2D g, int x1, int y1, int x2, int y2, Color cor, int espessura) {
        // Cria os pontos que definem o retângulo
        Ponto p1 = new Ponto(x1, y1);
        Ponto p2 = new Ponto(x2, y2);

        // Instancia um retângulo gráfico
        RetanguloGraf retangulo = new RetanguloGraf(p1, p2, cor, espessura);

        // Desenha o retângulo na área de desenho
        retangulo.desenharRetangulo(g);
    }
}
