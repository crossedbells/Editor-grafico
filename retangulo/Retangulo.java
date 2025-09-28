package retangulo;

import ponto.Ponto;

/**
 * Representa um retângulo definido por dois pontos opostos (p1 e p2).
 * A partir desses pontos, os outros dois cantos (p3 e p4) são calculados
 * automaticamente, formando um retângulo alinhado aos eixos cartesianos.
 * 
 * <p>Exemplo de uso:</p>
 * <pre>
 *     Ponto p1 = new Ponto(0, 0);
 *     Ponto p2 = new Ponto(4, 3);
 *     Retangulo r = new Retangulo(p1, p2);
 * </pre>
 * 
 * Nesse exemplo, o retângulo é formado pelos pontos (0,0), (4,3), (0,3) e (4,0).
 * 
 * @author Seu Nome
 * @version 1.0
 */
public class Retangulo {
    /** Um canto do retângulo. */
    private Ponto p1;
    /** O canto oposto ao ponto {@code p1}. */
    private Ponto p2;
    /** O terceiro canto do retângulo, calculado automaticamente. */
    private Ponto p3;
    /** O quarto canto do retângulo, calculado automaticamente. */
    private Ponto p4;

    /**
     * Constrói um retângulo a partir de dois pontos opostos.
     * Os outros dois cantos são calculados automaticamente.
     *
     * @param p1 um dos cantos do retângulo
     * @param p2 o canto oposto a {@code p1}
     */
    public Retangulo(Ponto p1, Ponto p2) {
        this.p1 = p1;
        this.p2 = p2;
        calcularP3P4();
    }

    /**
     * Calcula os pontos {@code p3} e {@code p4} com base em {@code p1} e {@code p2}.
     * <ul>
     *   <li>{@code p3} terá a coordenada X de {@code p1} e a coordenada Y de {@code p2}.</li>
     *   <li>{@code p4} terá a coordenada X de {@code p2} e a coordenada Y de {@code p1}.</li>
     * </ul>
     */
    private void calcularP3P4() {
        this.p3 = new Ponto(p1.getX(), p2.getY());
        this.p4 = new Ponto(p2.getX(), p1.getY());
    }

    /**
     * Retorna o ponto {@code p1}.
     *
     * @return o ponto {@code p1}
     */
    public Ponto getP1() {
        return p1;
    }

    /**
     * Define o ponto {@code p1} e recalcula os outros cantos do retângulo.
     *
     * @param p1 o novo ponto {@code p1}
     */
    public void setP1(Ponto p1) {
        this.p1 = p1;
        calcularP3P4();
    }

    /**
     * Retorna o ponto {@code p2}.
     *
     * @return o ponto {@code p2}
     */
    public Ponto getP2() {
        return p2;
    }

    /**
     * Define o ponto {@code p2} e recalcula os outros cantos do retângulo.
     *
     * @param p2 o novo ponto {@code p2}
     */
    public void setP2(Ponto p2) {
        this.p2 = p2;
        calcularP3P4();
    }

    /**
     * Retorna o ponto {@code p3}, calculado automaticamente.
     *
     * @return o ponto {@code p3}
     */
    public Ponto getP3() {
        return p3;
    }

    /**
     * Retorna o ponto {@code p4}, calculado automaticamente.
     *
     * @return o ponto {@code p4}
     */
    public Ponto getP4() {
        return p4;
    }

    /**
     * Atualiza o ponto p2 (canto oposto) do retângulo e recalcula os outros cantos.
     */
    public void atualizarP2(int x, int y) {
        this.p2.setX(x);
        this.p2.setY(y);
        calcularP3P4();
    }
}
