package reta;
import constantes.Constantes;
import janela.Janela;
import java.awt.Color;
import java.awt.Graphics2D;
import ponto.Ponto;

/**
 * Desenha figuras com retas.
 * 
 * @author Julio Arakaki
 * @version (10/09/2021
 */
public class FiguraRetas {
    /**
     * @param g
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param nome
     * @param esp
     * @param cor
     */
    public static void desenharRetaLib(Graphics2D g, int x1, int y1, int x2, int y2, String nome, int esp, Color cor){
        RetaGr r = new RetaGr(x1, y1, x2, y2, cor, nome, esp);
        r.desenharRetaLib(g );
    }
    /**
     * @param g
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param nome
     * @param esp
     * @param cor
     */
    public static void desenharRetaEq(Graphics2D g, int x1, int y1, int x2, int y2, String nome, int esp, Color cor){
        RetaGr r = new RetaGr(x1, y1, x2, y2, cor, nome, esp);
        r.desenharRetaEq(g );
    }
    
    /**
     * @param g
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param nome
     * @param esp
     * @param cor
     */
    public static void desenharRetaMp(Graphics2D g, int x1, int y1, int x2, int y2, String nome, int esp, Color cor){
        RetaGr r = new RetaGr(x1, y1, x2, y2, cor, nome, esp);
        r.desenharRetaMp(g );
    }

    /**
     * @param g
     * @param window
     * @param viewport
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param nome
     * @param esp
     * @param cor
     */
    public static void desenharRetaLibViewp(Graphics2D g, Janela window, Janela viewport, int x1, int y1, int x2, int y2, String nome, int esp, Color cor){
        Ponto pAux1 = window.calcularNaViewPort(viewport, new Ponto(x1, y1));
        Ponto pAux2 = window.calcularNaViewPort(viewport, new Ponto(x2, y2));
        RetaGr r = new RetaGr((int)pAux1.getX(),(int)pAux1.getY(), (int)pAux2.getX(), (int)pAux2.getY(), Color.BLUE, nome, esp);
        r.desenharRetaLib(g );
    }
    
    /**
     * @param g
     * @param window
     * @param viewport
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param nome
     * @param esp
     * @param cor
     */
    public static void desenharRetaEqViewp(Graphics2D g, Janela window, Janela viewport, int x1, int y1, int x2, int y2, String nome, int esp, Color cor){
        Ponto pAux1 = window.calcularNaViewPort(viewport, new Ponto(x1, y1));
        Ponto pAux2 = window.calcularNaViewPort(viewport, new Ponto(x2, y2));
        RetaGr r = new RetaGr((int)pAux1.getX(),(int)pAux1.getY(), (int)pAux2.getX(), (int)pAux2.getY(), Color.BLUE, nome, esp);
        r.desenharRetaEq(g );
    }
    
    /**
     * @param g
     * @param window
     * @param viewport
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param nome
     * @param esp
     * @param cor
     */
    public static void desenharRetaMpViewp(Graphics2D g, Janela window, Janela viewport, int x1, int y1, int x2, int y2, String nome, int esp, Color cor){
        Ponto pAux1 = window.calcularNaViewPort(viewport, new Ponto(x1, y1));
        Ponto pAux2 = window.calcularNaViewPort(viewport, new Ponto(x2, y2));
        RetaGr r = new RetaGr((int)pAux1.getX(),(int)pAux1.getY(), (int)pAux2.getX(), (int)pAux2.getY(), Color.BLUE, nome, esp);
        r.desenharRetaMp(g );
    }
   
    /**
     * @param g
     * @param qtde
     * @param esp
     */
    public static void desenharRetas(Graphics2D g, int qtde, int esp){

        for(int i=0; i < qtde; i++) {
            int x1 = (int) (Math.random() * Constantes.XW_MAX+1);
            int y1 = (int) (Math.random() * Constantes.YW_MAX+1);
            int x2 = (int) (Math.random() * Constantes.XW_MAX+1);
            int y2 = (int) (Math.random() * Constantes.YW_MAX+1);

            // R, G e B aleatorio
            Color cor = new Color((int) (Math.random() * 256),  
                    (int) (Math.random() * 256),  
                    (int) (Math.random() * 256));
            RetaGr r = new RetaGr(x1, y1, x2, y2, cor, "", esp);
            r.desenharRetaMp(g);
        }
    }
    /**
     * Desenha uma reta elástica (drag and draw) usando um objeto RetaGr já existente.
     * @param g Graphics2D
     * @param reta RetaGr (com os pontos já atualizados)
     */
    public static void desenharRetaElastica(Graphics2D g, RetaGr reta) {
        if (reta != null) {
            reta.desenharRetaLib(g);
        }
    }
}
