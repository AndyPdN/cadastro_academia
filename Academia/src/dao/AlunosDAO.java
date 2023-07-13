package dao;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

import conexao.ConnectionFactory;
import modelo.aluno;

public class AlunosDAO {
    private Connection connection;

    public void adiciona(aluno aluno) {
        connection = new ConnectionFactory().connection();
        String sql = "INSERT INTO aluno (nome, cpf, idade, mensalidade, multaporatraso, endereco, telefone, plano) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCpf());
            stmt.setInt(3, aluno.getIdade());
            stmt.setFloat(4, aluno.getMensalidade());
            stmt.setFloat(5, aluno.getMultaporatraso());
            stmt.setString(6, aluno.getEndereco());
            stmt.setString(7, aluno.getTelefone());
            stmt.setFloat(8, aluno.getPlano());

            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
