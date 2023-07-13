package telas;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CadastroFuncionario extends JDialog {
    private JPanel contentPane;
    private JButton limpar;
    private JButton buttonCancel;
    private JTextField campoNome;
    private JTextField campoFuncao;
    private JTextField campoTelefone;
    private JTextField campoEndereco;
    private JTextField campoSalario;
    private JButton cadastrarButton;
    private JLabel tagNome;
    private JLabel tagFuncao;
    private JLabel tagTelefone;
    private JLabel tagEndereco;
    private JLabel tagSalario;

    public CadastroFuncionario() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(limpar);

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarFuncionario();

            }
        });

        limpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onLimpar();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void salvarFuncionario() {
        String nome = campoNome.getText();
        String telefone = campoTelefone.getText();
        String endereco = campoEndereco.getText();
        String salario = campoSalario.getText();

        // Configuração da conexão com o banco de dados
        String localBD = "localhost";
        String url = "jdbc:mysql://" + localBD + ":3306/academia";
        String user = "root";
        String password = "usbw";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO funcionario (nome, telefone, endereco, salario) " +
                    "VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, nome);
            statement.setString(2, telefone);
            statement.setString(3, endereco);
            statement.setString(4, salario);

            statement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso!");
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar Funcionário: " + ex.getMessage());
        } finally {
            // Fechar statement e conexão
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    private void onLimpar() {
        campoNome.setText("");
        campoTelefone.setText("");
        campoEndereco.setText("");
        campoSalario.setText("");
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        CadastroFuncionario dialog = new CadastroFuncionario();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
