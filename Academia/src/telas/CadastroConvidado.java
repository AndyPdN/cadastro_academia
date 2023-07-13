package telas;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CadastroConvidado extends JDialog {
    private JPanel contentPane;
    private JButton limpar;
    private JButton buttonCancel;
    private JTextField campoNome;
    private JTextField campoCpf;
    private JTextField campoValorPago;
    private JButton cadastrarButton;
    private JLabel tagNome;
    private JLabel tagCpf;
    private JLabel tagvalorpago;

    public CadastroConvidado() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(limpar);

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarConvidado();
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

    private void salvarConvidado() {
        String nome = campoNome.getText();
        String cpf = campoCpf.getText();
        String valorPago = campoValorPago.getText();

        // Configuração da conexão com o banco de dados
        String localBD = "localhost";
        String url = "jdbc:mysql://" + localBD + ":3306/academia";
        String user = "root";
        String password = "usbw";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO convidado (nome, cpf, valorPago) " +
                    "VALUES (?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, nome);
            statement.setString(2, cpf);
            statement.setString(3, valorPago);

            statement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Convidado cadastrado com sucesso!");
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar Convidado: " + ex.getMessage());
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
        campoCpf.setText("");
        campoValorPago.setText("");
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        CadastroConvidado dialog = new CadastroConvidado();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
