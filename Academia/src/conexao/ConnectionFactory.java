package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static Connection conexao_MYSQL = null;
    private static String localBD = "localhost";
    private static String LINK1 = "jdbc:mysql://"+localBD+":3306/academia";
    private static final String usuario = "root";
    private static final String senha ="usbw";

    public static Connection connection(){
        try {
            conexao_MYSQL = DriverManager.getConnection(LINK1, usuario, senha);

        } catch (SQLException e) {
            throw new IllegalStateException("Não foi possível conectar com o BD", e);
        }
        return conexao_MYSQL;
    }

    public static void closeConnection() {
        try {
            if (conexao_MYSQL != null) {
                conexao_MYSQL.close();
                System.out.println("Conexão com o BD finalizada com sucesso.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um problema ao encerrar a conexão com o Banco de dados");
        }
    }
}
