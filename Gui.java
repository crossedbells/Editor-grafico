import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import constantes.Constantes;
import constantes.TipoPrimitivo;

/**
 * Classe que cria a interface gráfica do usuário (GUI) para testar primitivas gráficas.
 * Esta classe configura e exibe a janela com todos os componentes, como botões, controles de espessura,
 * menus, entre outros. Também gerencia as interações do usuário com os botões e atalhos de teclado.
 * 
 * @author Amora Marinho Machado
 * @author Gabriel Azevedo Cruz
 * @author Gabriel Mechi Lima
 * @author Luiz Fernando de Marchi Andrade
 * @version 05/09/2025
 */
class Gui extends JFrame {

    // Tipo Atual de primitivo
    private TipoPrimitivo tipoAtual = TipoPrimitivo.NENHUM;

    // Cor atual
    private Color corAtual = Color.BLACK;

    // Espessura atual do primitivo
    private int espAtual = 1;

    // Componentes de GUI
    private JToolBar barraComandos = new JToolBar();

    // Botoes de formas
    private JButton jbPonto = new JButton("Ponto");
    private JButton jbRetaEq = new JButton("Reta");
    private JButton jbCirculoEq = new JButton("Circulo");
    private JButton jbRetangulo = new JButton("Retangulo");
    private JButton jbTriangulo = new JButton("Triangulo");

    // Botoes de controle
    private JButton jbDesfazer = new JButton("Desfazer");
    private JButton jbRefazer = new JButton("Refazer");
    private JButton jbLimpar = new JButton("Limpar");
    private JButton jbCor = new JButton("Cor");
    private JButton jbSair = new JButton("Sair");

    // Controle de espessura
    private JLabel jlEsp = new JLabel("   Espessura: " + String.format("%-5s", 1));
    private JSlider jsEsp = new JSlider(1, 50, 1);
    private JCheckBox jcbComViewp = new JCheckBox("Viewport");

    // Barra de menu
    private JMenuBar jmbBarra = new JMenuBar(); 
    private JMenu jmArquivo, jmEditar, jmAjuda;
    private JMenuItem jmSalvar, jmCarregar, jmSair, jmDesfazer, jmRefazer, jmSobre;

    // Para mensagens
    private JLabel msg = new JLabel("Msg: ");

    // Painel de desenho
    private PainelDesenho areaDesenho = new PainelDesenho(msg, tipoAtual, corAtual, 10);

    /**
     * Construtor da interface gráfica.
     * Configura a janela principal, os componentes da barra de ferramentas e os eventos associados.
     *
     * @param larg Largura da janela.
     * @param alt Altura da janela.
     */
    public Gui(int larg, int alt) {
        // Definições da janela
        super("Testa Primitivos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(larg, alt);

        // Adiciona os componentes à barra de comandos
        barraComandos.add(jbPonto);
        barraComandos.add(jbRetaEq);
        barraComandos.add(jbCirculoEq);
        barraComandos.add(jbRetangulo);
        barraComandos.add(jbTriangulo);
        barraComandos.addSeparator();
        barraComandos.add(jbDesfazer);
        barraComandos.add(jbRefazer);
        barraComandos.add(jbLimpar);
        barraComandos.addSeparator();
        barraComandos.add(jbCor);
        barraComandos.add(jlEsp);
        barraComandos.add(jsEsp);
        barraComandos.add(jcbComViewp);
        barraComandos.addSeparator();
        barraComandos.add(jbSair);

        // Adiciona os componentes com os respectivos layouts
        add(barraComandos, BorderLayout.NORTH);
        areaDesenho.setEsp(espAtual);
        add(areaDesenho, BorderLayout.CENTER);
        msg.setForeground(Color.BLUE);
        add(msg, BorderLayout.SOUTH);

        // Insere o menu e os listeners
        insereMenu();
        adicionaListeners();
        adicionaAtalhosDeTeclado();

        setVisible(true);
    }

    /**
     * Adiciona os listeners de eventos para os botões da interface.
     * Cada botão realiza uma ação no painel de desenho.
     */
    private void adicionaListeners() {
        // --- Action Listeners para Formas ---
        jbPonto.addActionListener(e -> areaDesenho.setTipo(TipoPrimitivo.PONTO));
        jbRetaEq.addActionListener(e -> areaDesenho.setTipo(TipoPrimitivo.RETA_EQ));
        jbCirculoEq.addActionListener(e -> areaDesenho.setTipo(TipoPrimitivo.CIRCULO_EQ));
        jbRetangulo.addActionListener(e -> areaDesenho.setTipo(TipoPrimitivo.RETANGULO));
        jbTriangulo.addActionListener(e -> areaDesenho.setTipo(TipoPrimitivo.TRIANGULO));

        // --- Action Listeners para Controles ---
        jbDesfazer.addActionListener(e -> areaDesenho.desfazer());
        jbRefazer.addActionListener(e -> areaDesenho.refazer());

        jbLimpar.addActionListener(e -> {
            areaDesenho.limparTela();
            jsEsp.setValue(1);
            areaDesenho.setCorAtual(Color.BLACK);
            jbCor.setForeground(Color.BLACK);
            areaDesenho.setTipo(TipoPrimitivo.NENHUM);
        });

        jbCor.addActionListener(e -> {
            Color c = JColorChooser.showDialog(this, "Escolha uma cor", corAtual);
            if (c != null) {
                corAtual = c;
                areaDesenho.setCorAtual(corAtual);
                jbCor.setForeground(corAtual);
            }
        });

        jsEsp.addChangeListener(e -> {
            espAtual = jsEsp.getValue();
            jlEsp.setText("   Espessura: " + String.format("%-5s", espAtual));
            areaDesenho.setEsp(espAtual);
        });

        jcbComViewp.addActionListener(e -> {
            AbstractButton absB = (AbstractButton) e.getSource();
            areaDesenho.setComViewport(absB.getModel().isSelected());
        });

        jbSair.addActionListener(e -> System.exit(0));
    }

    /**
     * Adiciona atalhos de teclado para as ações de Desfazer e Refazer.
     */
    private void adicionaAtalhosDeTeclado() {
        // Atalho para Desfazer (Ctrl+Z)
        areaDesenho.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), "desfazerAcao");
        areaDesenho.getActionMap().put("desfazerAcao", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                areaDesenho.desfazer();
            }
        });

        // Atalho para Refazer (Ctrl+Y)
        areaDesenho.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK), "refazerAcao");
        areaDesenho.getActionMap().put("refazerAcao", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                areaDesenho.refazer();
            }
        });
    }

    /**
     * Insere os menus na barra de menus da interface.
     */
    private void insereMenu() {
        // --- Menu Arquivo ---
        jmArquivo = new JMenu("Arquivo");
        jmArquivo.setMnemonic(KeyEvent.VK_A);
        jmbBarra.add(jmArquivo);

        jmSalvar = new JMenuItem("Salvar...");
        jmSalvar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        jmSalvar.addActionListener(e -> salvarArquivo());
        jmArquivo.add(jmSalvar);

        jmCarregar = new JMenuItem("Carregar...");
        jmCarregar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        jmCarregar.addActionListener(e -> carregarArquivo());
        jmArquivo.add(jmCarregar);

        jmArquivo.addSeparator();
        jmSair = new JMenuItem("Sair");
        jmSair.addActionListener(e -> System.exit(0));
        jmArquivo.add(jmSair);

        // --- Menu Editar ---
        jmEditar = new JMenu("Editar");
        jmEditar.setMnemonic(KeyEvent.VK_E);
        jmbBarra.add(jmEditar);

        jmDesfazer = new JMenuItem("Desfazer");
        jmDesfazer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        jmDesfazer.addActionListener(e -> areaDesenho.desfazer());
        jmEditar.add(jmDesfazer);

        jmRefazer = new JMenuItem("Refazer");
        jmRefazer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        jmRefazer.addActionListener(e -> areaDesenho.refazer());
        jmEditar.add(jmRefazer);

        // --- Menu Ajuda ---
        jmAjuda = new JMenu("Ajuda");
        jmAjuda.setMnemonic(KeyEvent.VK_J);
        jmbBarra.add(jmAjuda);

        jmSobre = new JMenuItem("Sobre");
        jmSobre.addActionListener(e -> sobre());
        jmAjuda.add(jmSobre);

        setJMenuBar(jmbBarra);
    }

    /**
     * Salva o desenho em um arquivo.
     */
    private void salvarArquivo() {
        JFileChooser seletor = new JFileChooser();
        seletor.setDialogTitle("Salvar Desenho");
        if (seletor.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File arquivo = seletor.getSelectedFile();
            try {
                // Garante que o arquivo tenha extensão .json
                if (!arquivo.getName().toLowerCase().endsWith(".json")) {
                    arquivo = new File(arquivo.getParentFile(), arquivo.getName() + ".json");
                }
                areaDesenho.salvar(arquivo);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar o arquivo: " + ex.getMessage(), "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Carrega um desenho de um arquivo.
     */
    private void carregarArquivo() {
        JFileChooser seletor = new JFileChooser();
        seletor.setDialogTitle("Carregar Desenho");
        if (seletor.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File arquivo = seletor.getSelectedFile();
            try {
                areaDesenho.carregar(arquivo);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar o arquivo: " + ex.getMessage(), "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Arquivo em formato inválido ou corrompido.", "Erro de Leitura", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Exibe informações sobre o aplicativo na janela de diálogo.
     */
    private void sobre() {
        String texto = "Testador de Primitivos Gráficos\nVersao 2.0\n\n"
            + "(c) Copyright 2021-2025. Todos os direitos reservados.\n\n"
            + "Computação Gráfica e Processamento de Imagens\n"
            + "Grupo AGGL - PUCSP";
        JOptionPane.showMessageDialog(this, texto, "Sobre Testador de Primitivos Gráficos", JOptionPane.INFORMATION_MESSAGE);
    }
}
