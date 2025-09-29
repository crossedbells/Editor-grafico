import constantes.Constantes;

/**
 * Aplicação para testar primitivas gráficas.
 * Esta aplicação inicializa a interface gráfica do usuário (UI), definindo a largura e a altura da janela.
 * 
 * @author Amora Marinho Machado
 * @author Gabriel Azevedo Cruz
 * @author Gabriel Mechi Lima
 * @author Luiz Fernando de Marchi Andrade
 * @version 05/09/2025
 */
public class App {
    
    /**
     * Método principal da aplicação.
     * Este método é responsável por iniciar a interface gráfica (UI), configurando a largura e a altura
     * da janela conforme as constantes definidas.
     * 
     * @param args Vetor de strings que pode conter parâmetros de entrada para a aplicação.
     *             Não utilizado neste caso.
     */
    public static void main(String args[]) {
        // Cria a janela da interface gráfica e define suas dimensões (largura e altura)
        new Gui(Constantes.LARGURA, Constantes.ALTURA); 
    }
}
