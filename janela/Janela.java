
package janela;
import ponto.Ponto;

/**
 * Escreva a descrição da classe Janela aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class Janela {
    // variaveis de instância 
    private int xmin, ymin, xmax, ymax;

    /**
     * COnstrutor para objetos da classe Janela
     */
    public Janela() {
        setXmin(0);
        setYmin(0);
        setXmax(0);
        setYmax(0);
    }

    
    /**
     * @param xwMin
     * @param ymin
     * @param xmax
     * @param ymax
     */
    public Janela(int xwMin, int ymin, int xmax, int ymax) {        
        super();
        setXmin(xwMin);
        setYmin(ymin);
        setXmax(xmax);
        setYmax(ymax);
    }

    /**
     * @return the xmin
     */
    public int getXmin() {
        return xmin;
    }

    /**
     * @param xmin the xmin to set
     */
    public void setXmin(int xmin) {
        this.xmin = xmin;
    }

    /**
     * @return the ymin
     */
    public int getYmin() {
        return ymin;
    }

    /**
     * @param ymin the ymin to set
     */
    public void setYmin(int ymin) {
        this.ymin = ymin;
    }

    /**
     * @return the xmax
     */
    public int getXmax() {
        return xmax;
    }

    /**
     * @param xmax the xmax to set
     */
    public void setXmax(int xmax) {
        this.xmax = xmax;
    }

    /**
     * @return the ymax
     */
    public int getYmax() {
        return ymax;
    }

    /**
     * @param ymax the ymax to set
     */
    public void setYmax(int ymax) {
        this.ymax = ymax;
    }
    
    /**
     * Metodo para fazer omapeamento numa viewport
     * Calcula um ponto da window para a viewport
     * @param viewp Janela viewport onde o ponto sera calculado
     * @param pw Ponto ponto a ser calculado
     * 
     * @return Ponto ponto calculado
     */
    public Ponto calcularNaViewPort(Janela viewp, Ponto pw) {
        Ponto pViewp = new Ponto();
        
        pViewp.setX(viewp.getXmin() + ((pw.getX()-getXmin())/(getXmax()-getXmin()))*(viewp.getXmax() - viewp.getXmin()));
        pViewp.setY(viewp.getYmin() + ((pw.getY()-getYmin())/(getYmax()-getYmin()))*(viewp.getYmax() - viewp.getYmin()));
        
        return pViewp;
    }
    //
    // Especifico para calculo do raio...    
    // Neste caso, fica proporcional a borda horizontal da viewport
    //
    public int calcularNaViewPort(Janela viewp, int a){
        return (int)(((double)a/(double)(getXmax()-getXmin()))*((double)viewp.getXmax()-(double)viewp.getXmin()));
    }
}
