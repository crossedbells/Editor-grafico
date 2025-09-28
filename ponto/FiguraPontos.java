package ponto;
import java.awt.Color;
import java.awt.Graphics2D;

import janela.Janela;

/**
 * Implementacao de metodos estaticos para desenhar ponto.
 * 
 * @author Julio Arakaki
 * 
 * @version 07/09/2021
 */

public class FiguraPontos {

    /**
     * Desenha ponto na window (area de desenho)
     * 
     * @param g Graphics biblioteca grafica do java
     * @param x int coordenada x do ponto
     * @param y int coordenada y do ponto
     * @param nome String nome do ponto
     * @param diametro int espessura do ponto (diametro)
     * @param cor Color cor do ponto
     */
    public static void desenharPontoWind(Graphics2D g, int x, int y, String nome, int diametro, Color cor){
        // Instancia um ponto grafico
        PontoGr p = new PontoGr(x, y, cor, nome, diametro);
        
        // Desenha o ponto na area de desenho
        p.desenharPonto(g);
    }
    
    /**
     * Desenha ponto numa viewport. Utiliza window e viwport recebidos como pametros.
     * 
     * @param g Graphics biblioteca grafica do java
     * @param window janela de desenho 
     * @param viewport janela de visualizacao
     * @param x int coordenada x do ponto
     * @param y int coordenada y do ponto
     * @param nome String nome do ponto
     * @param diametro int espessura do ponto (diametro)
     */
    public static void desenharPontoViewp(Graphics2D g, Janela window, Janela viewport, int x, int y, String nome, int diametro, Color cor){
        Ponto pAux = window.calcularNaViewPort(viewport, new Ponto(x, y));
        PontoGr p = new PontoGr((int)pAux.getX(), (int)pAux.getY(), Color.BLUE, nome, diametro);
        p.desenharPonto(g);
    }

    /**
     * Desenha varios pontos na janela de desenho
     * 
     * @param g biblioteca grafica do java
     * @param qtde quantidade de pontos a ser desenhado
     * @param larg largura do ponto
     */
    public static void desenharPontos(Graphics2D g, int qtde, int larg){

        for(int i=0; i < qtde; i++) {
            int x = (int) (Math.random() * 801);
            int y = (int) (Math.random() * 801);

            // R, G e B aleatorio
            Color cor = new Color((int) (Math.random() * 256),  
                    (int) (Math.random() * 256),  
                    (int) (Math.random() * 256));
            PontoGr p = new PontoGr(x, y, cor, larg);
            p.desenharPonto(g);
        }
    }
}
