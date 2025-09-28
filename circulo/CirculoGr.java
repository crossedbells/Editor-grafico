package circulo;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import ponto.PontoGr;

/**
 * Implementa circulo grafico.
 * 
 * @author Julio Arakaki
 * @version 11/09/2021
 */

public class CirculoGr extends Circulo {
    // Atributos do circulo grafico
    Color corCirculo = Color.BLACK;   // cor da Circulo
    String nomeCirculo = ""; // nome da Circulo
    Color corNomeCirculo  = Color.BLACK;
    int espCirculo = 1; // espessura da Circulo

    /**
     * @param x coordenada x do centro
     * @param y coordenada y do centro
     * @param raio raio do circulo
     */
    public CirculoGr(double x, double y, double raio) {
        super(x, y, raio);
        // cor, nome e espessura sao defaults
    }

    /**
     * @param xc coordenada x do centro
     * @param yc coordenada y do centro
     * @param raio raio do circulo
     * @param cor Cor do circulo
     * @param nome Nome do circulo
     * @param esp espessura da borda do circulo
     */
    public CirculoGr(int xc, int yc, int raio, Color cor, String nome, int esp) {
        super((double) xc, (double) yc, (double) raio);
        setCorCirculo(cor);
        setNomeCirculo(nome);
        setEspCirculo(esp);
    }

    /**
     * @return the corCirculo
     */
    public Color getCorCirculo() {
        return corCirculo;
    }

    /**
     * @param corCirculo the corCirculo to set
     */
    public void setCorCirculo(Color corCirculo) {
        this.corCirculo = corCirculo;
    }

    /**
     * @return the nomeCirculo
     */
    public String getNomeCirculo() {
        return nomeCirculo;
    }

    /**
     * @param nomeCirculo the nomeCirculo to set
     */
    public void setNomeCirculo(String nomeCirculo) {
        this.nomeCirculo = nomeCirculo;
    }

    /**
     * @return the corNomeCirculo
     */
    public Color getCorNomeCirculo() {
        return corNomeCirculo;
    }

    /**
     * @param corNomeCirculo the corNomeCirculo to set
     */
    public void setCorNomeCirculo(Color corNomeCirculo) {
        this.corNomeCirculo = corNomeCirculo;
    }

    /**
     * @return the espCirculo
     */
    public int getEspCirculo() {
        return espCirculo;
    }

    /**
     * @param espCirculo the espCirculo to set
     */
    public void setEspCirculo(int espCirculo) {
        this.espCirculo = espCirculo;
    }

    /**
     * @param g Biblioteca grafica
     */
    public void desenharCirculoLib(Graphics2D g){
        // pega o centro do circulo
        int cx = (int)getCentro().getX();
        int cy = (int)getCentro().getY();
        int raio = (int)getRaio();
        // seta cor e espessura
        g.setColor(getCorCirculo());
        //ponto.setDiametro(getEspCirculo());
        g.setStroke(new BasicStroke((float)getEspCirculo()));
        
        // desenha ponto como um oval
        g.drawOval(cx - raio, cy - raio, 2*raio, 2*raio);
        
        // desenha nome do circulo
        g.setColor(getCorNomeCirculo());
        g.drawString(getNomeCirculo(), cx, cy);
        
    }
    /**
     * Desenha circulo grafico utilizando equacao parametrica (angulo de 0 a 360)
     *
     * @param g Graphics. Classe com os metodos graficos do Java
     */
    public void desenharCirculoEq(Graphics2D g){
        // Variaveis auxiliares
        PontoGr ponto = new PontoGr(); 
        double x, y;

        double angIni = 0;
        double angFim = 45;
        double incr = 0.1;
        double alfa = 0;

        // percorre de 0  ate 45. 
        // x e� calculado pela equacao: x = xc + R*seno(alfa)
        // y e� calculado pela equacao: y = yc + R*cos(alfa)
        for(alfa = angIni; alfa <= angFim; alfa = alfa + incr){ 
            // Calculo de x e y (por trigonometria)
            x = getRaio() * Math.sin((alfa*Math.PI)/180.);
            y = getRaio() * Math.cos((alfa*Math.PI)/180.);

            // desenhar 8 pontos (um em cada octante) por simetria
            desenharPontosSimetricos(g, (int)x, (int)y, ponto);
        }
    }
    /**
     * Desenha circulo utilizando algoritmo MidPoint (Bresenham)
     * @param g
     */
    void desenharCirculoMp(Graphics2D g) {

        if (getRaio() != 0) {
            // Variaveis auxiliares
            PontoGr ponto = new PontoGr(); 

            double x = 0;
            double y = getRaio();
            double d = 5 / 4 - getRaio();

            desenharPontosSimetricos (g, (int)x, (int)y, ponto);

            while (y > x) {
                if (d < 0) {
                    d = d + 2 * x + 3;
                    x++;
                }

                else {
                    d = d + 2 * (x - y) + 5;
                    x++;
                    y--;
                }
                desenharPontosSimetricos (g, (int)x, (int)y, ponto);
            }
        }       
    }

    /**
     * Desenha os pontos simetricos do circulo. Um em cada octante
     * @param g - componente para acessar modo gr�fico
     * @param x - coordenada x de um ponto do primeiro octante do circulo
     * @param y - coordenada y de um ponto do primeiro octante do circulo
     * @param ponto - objeto utilizado para "acender" (desenhar) um ponto
     */
    private void desenharPontosSimetricos(Graphics2D g, int x, int y, PontoGr ponto){
        // pega o centro do circulo
        int cx = (int)getCentro().getX();
        int cy = (int)getCentro().getY();

        // seta cor e espessura
        ponto.setCorPto(getCorCirculo());
        ponto.setDiametro(getEspCirculo());

        // desenha nome do circulo
        g.setColor(getCorNomeCirculo());
        g.drawString(getNomeCirculo(), cx, cy);

        // desenha os 8 pontos simetricos. Inclui o centro do circulo
        // (1) (cx+x, cy+y)
        desenharPontoSimetrico(g, cx + x, cy + y, ponto);
        // (2) (cx+y, cy+x)
        desenharPontoSimetrico(g, cx + y, cy + x, ponto);
        // (3) (cx-y, cy+x)
        desenharPontoSimetrico(g, cx - y, cy + x, ponto);
        // (4) (cx-x, cy+y)
        desenharPontoSimetrico(g, cx - x, cy + y, ponto);
        // (5) (cx-x, cy-y)
        desenharPontoSimetrico(g, cx - x, cy - y, ponto);
        // (6) (cx-y, cy-x)
        desenharPontoSimetrico(g, cx - y, cy - x, ponto);
        // (7) (cx+y, cy-x)
        desenharPontoSimetrico(g, cx + y, cy - x, ponto);
        // (8) (cx+x, cy-y)
        desenharPontoSimetrico(g, cx + x, cy - y, ponto);
    }


    /**
     * M�todo desenharPontoSimetrico
     *
     * @param x coordenada x
     * @param y coordenda y
     * @param ponto Ponto utilizado para desenhar o ponto
     * @param g Biblioteca grafica
     */
    private void desenharPontoSimetrico(Graphics2D g, int x, int y, PontoGr ponto){
        ponto.setX(x);
        ponto.setY(y);
        ponto.desenharPonto(g);
    }

    /**
     * Atualiza o raio do círculo gráfico com base em um ponto na borda.
     */
    public void atualizarRaio(int x, int y) {
        super.atualizarRaio(x, y);
    }
}
