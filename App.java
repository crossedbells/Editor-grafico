import constantes.Constantes;

/**
 * Aplica��o para testar primitivas gr�ficas.
 * Esta aplica��o inicializa a interface gr�fica do usu�rio (UI), definindo a largura e a altura da janela.
 * 
 * @author Amora Marinho Machado
 * @author Gabriel Azevedo Cruz
 * @author Gabriel Mechi Lima
 * @author Luiz Fernando de Marchi Andrade
 * @version 05/09/2025
 */
public class App {
    
    /**
     * M�todo principal da aplica��o.
     * Este m�todo � respons�vel por iniciar a interface gr�fica (UI), configurando a largura e a altura
     * da janela conforme as constantes definidas.
     * 
     * @param args Vetor de strings que pode conter par�metros de entrada para a aplica��o.
     *             N�o utilizado neste caso.
     */
    public static void main(String args[]) {
        // Cria a janela da interface gr�fica e define suas dimens�es (largura e altura)
        new Gui(Constantes.LARGURA, Constantes.ALTURA); 
    }
}
