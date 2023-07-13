package telas;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CadastroProfessor extends JDialog {
    private JPanel contentPane;
    private JButton buttonCadastrar;
    private JButton buttonSair;
    private JTextField campoNome;
    private JTextField campoHoras;
    private JTextField campoCpf;
    private JTextField campoTelefone;
    private JTextField campoEndereco;
    private JTextField campoSalario;
    private JLabel tagNome;
    private JLabel tagHoras;
    private JLabel tagCpf;
    private JLabel tagTelefone;
    private JLabel tagEndereco;
    private JLabel tagSalario;
    private JButton buttonLimpar;

    public CadastroProfessor() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonCadastrar);

        buttonCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salvarProfessor();
            }
        });

        buttonSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSair();
            }
        });

        buttonLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onLimpar();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onSair();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSair();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void salvarProfessor() {
        String nome = campoNome.getText();
        String horas = campoHoras.getText();
        String cpf = campoCpf.getText();
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
            String sql = "INSERT INTO professor (nome, horas, cpf, telefone, endereco, salario) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, nome);
            statement.setString(2, horas);
            statement.setString(3, cpf);
            statement.setString(4, telefone);
            statement.setString(5, endereco);
            statement.setString(6, salario);

            statement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Professor cadastrado com sucesso!");
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar Professor: " + ex.getMessage());
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
        campoHoras.setText("");
        campoCpf.setText("");
        campoTelefone.setText("");
        campoEndereco.setText("");
        campoSalario.setText("");
    }

    private void onSair() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        CadastroProfessor dialog = new CadastroProfessor();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
