package telas;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CadastroAluno extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField campoNome;
    private JTextField campoIdade;
    private JTextField campoCpf;
    private JTextField campoTelefone;
    private JTextField campoEndereco;
    private JTextField campoMensalidade;
    private JTextField campoMultaporatraso;
    private JTextField campoPlano;
    private JList list1;
    private JButton limparButton;
    private JLabel tagNome;
    private JLabel tagIdade;
    private JLabel tagCpf;
    private JLabel tagTelefone;
    private JLabel tagEndereco;
    private JLabel tagMensalidade;
    private JLabel tagMultaporatraso;
    private JLabel tagPlano;

    public CadastroAluno() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salvarAluno();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void salvarAluno() {
        String nome = campoNome.getText();
        int idade = Integer.parseInt(campoIdade.getText());
        String cpf = campoCpf.getText();
        String telefone = campoTelefone.getText();
        String endereco = campoEndereco.getText();
        double mensalidade = Double.parseDouble(campoMensalidade.getText());
        double multaporatraso = Double.parseDouble(campoMultaporatraso.getText());
        String plano = campoPlano.getText();

        // Configuração da conexão com o banco de dados
        String localBD = "localhost";
        String url = "jdbc:mysql://" + localBD + ":3306/academia";
        String user = "root";
        String password = "usbw";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO aluno (nome, idade, cpf, telefone, endereco, mensalidade, multaporatraso, plano) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, nome);
            statement.setInt(2, idade);
            statement.setString(3, cpf);
            statement.setString(4, telefone);
            statement.setString(5, endereco);
            statement.setDouble(6, mensalidade);
            statement.setDouble(7, multaporatraso);
            statement.setString(8, plano);

            statement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!");
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar aluno: " + ex.getMessage());
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

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                CadastroAluno dialog = new CadastroAluno();
                dialog.pack();
                dialog.setVisible(true);
            }
        });
    }
}
