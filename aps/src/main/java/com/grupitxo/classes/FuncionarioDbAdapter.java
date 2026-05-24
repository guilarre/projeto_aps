package com.grupitxo.classes;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FuncionarioDbAdapter {
    private Connection conn;

    public FuncionarioDbAdapter(Connection conn) {
        this.conn = conn;
    }

    public boolean salvar(Funcionario f) {
        String sql = "INSERT INTO funcionarios (nome, cpf, telefone, email, preferencia_comunicacao, endereco, aniversario, genero, cargo, salario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getCpf());
            stmt.setString(3, f.getTelefone());
            stmt.setString(4, f.getEmail());
            stmt.setString(5, f.getPreferenciaComunicacao()); // ou .toString() dependendo do tipo na classe
            stmt.setString(6, f.getEndereco());
            stmt.setString(7, f.getAniversario());
            stmt.setString(8, f.getGenero());
            stmt.setString(9, f.getCargo() != null ? f.getCargo().toString() : null); 
            stmt.setDouble(10, f.getSalario());
            
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getQtd() {
        String sql = "SELECT COUNT(*) FROM funcionarios;";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
