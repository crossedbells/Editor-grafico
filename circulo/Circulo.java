package circulo;
import ponto.Ponto;

/**
 * Implementa circulo matematico.
 * 
 * @author Julio Arakaki
 * @version 24/08/2021
 */
public class Circulo {
    // centro do circulo
    private Ponto centro;
    private double raio; // raio do circulo

    /**
     * Contrutor da classe circulo
     * @param centro Ponto centro do circulo
     * @param raio double raio do circulo
     */
    public Circulo(Ponto centro, double raio) {
        setCentro(centro);
        setRaio(raio);
    }

    /**
     * Contrutor da classe circulo
     * @param x double coordenada x do centro do circulo
     * @param y double coordenada y do centro do circulo
     * @param raio double raio do circulo
     */
    public Circulo(double x, double y, double raio) {
        setCentro(new Ponto(x, y));
        setRaio(raio);
    }

    /**
     * Contrutor da classe circulo. Cria uma copia
     * @param c Circulo circulo a ser copiado
     */
    public Circulo(Circulo c) {
        setCentro(new Ponto(c.getCentro()));
        setRaio(c.getRaio());
    }

    /**
     * @return the centro
     */
    public Ponto getCentro() {
        return centro;
    }

    /**
     * @param centro the centro to set
     */
    public void setCentro(Ponto centro) {
        this.centro = centro;
    }

    /**
     * @return the raio
     */
    public double getRaio() {
        return raio;
    }

    /**
     * @param raio the raio to set
     */
    public void setRaio(double raio) {
        this.raio = raio;
    }    

    /**
     * Method toString
     *
     * @return The return value
     */
    public String toString(){
        String s = "Circulo: \n Centro: " + getCentro().toString() + " Raio: " + getRaio();
        return s;
    }
}
