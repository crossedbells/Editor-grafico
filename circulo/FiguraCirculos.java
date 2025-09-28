package circulo;
import java.awt.Color;
import java.awt.Graphics2D;

import constantes.Constantes;
import janela.Janela;
import ponto.Ponto;

/**
 * Desenhar figuras com circulo.
 * 
 * @author Julio Arakaki
 * @version 27/08/2021
 */
public class FiguraCirculos {
    
    /**
     * Desenha circulo utilizando funcao de biblioteca
     * 
     * @param g Biblioteca gráfica
     * @param xc coordenada x do centro
     * @param yc coordenada y do centro
     * @param raio raio do circulo
     * @param nome nome do circulo
     * @param esp espessura da borda
     * @param cor cor do circulo
     */
    public static void desenharCirculoLib(Graphics2D g, int xc, int yc, int raio, String nome, int esp, Color cor){
        CirculoGr c = new CirculoGr(xc, yc, raio, cor, nome, esp);
        c.desenharCirculoLib(g);
    }

    /**
     * Desenha circulo utilizando equacoes parametricas
     * 
     * @param g Biblioteca gráfica
     * @param xc coordenada x do centro
     * @param yc coordenada y do centro
     * @param raio raio do circulo
     * @param nome nome do circulo
     * @param esp espessura da borda
     * @param cor cor do circulo
    */
    public static void desenharCirculoEq(Graphics2D g, int xc, int yc, int raio, String nome, int esp, Color cor){
        CirculoGr c = new CirculoGr(xc, yc, raio, cor, nome, esp);
        c.desenharCirculoEq(g);
    }
    
    /**
     * Desenha circulo na window utilizando algoritmo midpoint
     * 
     * @param g Biblioteca gráfica
     * @param xc coordenada x do centro
     * @param yc coordenada y do centro
     * @param raio raio do circulo
     * @param nome nome do circulo
     * @param esp espessura da borda
     * @param cor cor do circulo
     */
    public static void desenharCirculoMp(Graphics2D g, int xc, int yc, int raio, String nome, int esp, Color cor){
        CirculoGr c = new CirculoGr(xc, yc, raio, cor, nome, esp);
        c.desenharCirculoMp(g);
    }
    
    /**
     * Desenha circulo na viewport utilizando a funcao de biblioteca
     * 
     * @param g Biblioteca gráfica
     * @param window dimensoes da window
     * @param viewport dimensoes da viewport
     * @param xc coordenada x do centro
     * @param yc coordenada y do centro
     * @param raio raio do circulo
     * @param nome nome do circulo
     * @param esp espessura da borda
     * @param cor cor do circulo
     */
    public static void desenharCirculoLibViewp(Graphics2D g, Janela window, Janela viewport, int xc, int yc, int raio, String nome, int esp, Color cor){
        Ponto pAux = window.calcularNaViewPort(viewport, new Ponto(xc, yc));
        int aux = window.calcularNaViewPort(viewport, raio);
        CirculoGr c = new CirculoGr((int)pAux.getX(), (int)pAux.getY(), aux,  Color.BLUE, nome, esp);
        c.desenharCirculoLib(g);
    }
    
    /**
     * Desenha circulo na viewport utilizando a equacao parametrica
     * 
     * @param g Biblioteca gráfica
     * @param window dimensoes da window
     * @param viewport dimensoes da viewport
     * @param xc coordenada x do centro
     * @param yc coordenada y do centro
     * @param raio raio do circulo
     * @param nome nome do circulo
     * @param esp espessura da borda
     * @param cor cor do circulo
     */
    public static void desenharCirculoEqViewp(Graphics2D g, Janela window, Janela viewport, int xc, int yc, int raio, String nome, int esp, Color cor){
        Ponto pAux = window.calcularNaViewPort(viewport, new Ponto(xc, yc));
        int aux = window.calcularNaViewPort(viewport, raio);
        CirculoGr c = new CirculoGr((int)pAux.getX(), (int)pAux.getY(), aux,  Color.BLUE, nome, esp);
        c.desenharCirculoEq(g);
    }

    /**
     * Desenha circulo com o algoritmo midpoint na viewport
     * 
     * @param g Biblioteca gráfica
     * @param window dimensoes da window
     * @param viewport dimensoes da viewport
     * @param xc coordenada x do centro
     * @param yc coordenada y do centro
     * @param raio raio do circulo
     * @param nome nome do circulo
     * @param esp espessura da borda
     * @param cor cor do circulo
     */
    public static void desenharCirculoMpViewp(Graphics2D g, Janela window, Janela viewport, int xc, int yc, int raio, String nome, int esp, Color cor){
        Ponto pAux = window.calcularNaViewPort(viewport, new Ponto(xc, yc));
        int aux = window.calcularNaViewPort(viewport, raio);
        CirculoGr c = new CirculoGr((int)pAux.getX(), (int)pAux.getY(), aux,  Color.BLUE, nome, esp);
        c.desenharCirculoMp(g);
    }
    
    /**
     * Desenha varios circulos
     * 
     * @param g Biblioteca gráfica
     * @param qtde quantidade de circulos
     * @param esp espessura da borda do circulo
     */
    public static void desenharCirculos(Graphics2D g, int qtde, int esp){

        for(int i=0; i < qtde; i++) {
            int xc = (int) (Math.random() * Constantes.XW_MAX+1);
            int yc = (int) (Math.random() * Constantes.YW_MAX+1);
            int raio = (int) (Math.random() * Constantes.XW_MAX+1);

            // R, G e B aleatorio
            Color cor = new Color((int) (Math.random() * 256),  
                    (int) (Math.random() * 256),  
                    (int) (Math.random() * 256));
            CirculoGr c = new CirculoGr(xc, yc, raio, cor, "", esp);
            c.desenharCirculoMp(g);
        }
    }
}
