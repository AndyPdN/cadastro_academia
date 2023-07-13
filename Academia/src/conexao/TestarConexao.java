package conexao;

import java.sql.Connection;
import java.sql.SQLException;

public class TestarConexao {

    public static void main(String[] args) throws SQLException {
        Connection connection = new ConnectionFactory().connection();
        System.out.println("Conexão realizada com sucesso");
        connection.close();
    }
}
