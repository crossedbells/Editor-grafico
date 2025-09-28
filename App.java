import constantes.Constantes;

/**
 * Aplicacao para testar primitivos graficos.
 * 
 * @author Amora Marinho Machado
 * @author Gabriel Azevedo Cruz
 * @author Gabriel Mechi Lima
 * @author Luiz Fernando de Marchi Andrade
 * @version 05/09/2025
 */
public class App {
    /**
     * Programa principal. Ativa a UI da aplicacao, definindo a largura e altura da janela.
     * 
     * @param args vetor de strings de par�metros de entrada
     */
    public static void main(String args[]) {
        // Cria e define dimensao da janela (em pixels)
        new Gui(Constantes.LARGURA, Constantes.ALTURA); 
    }
}
