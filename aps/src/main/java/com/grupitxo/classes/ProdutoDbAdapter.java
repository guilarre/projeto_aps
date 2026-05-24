package com.grupitxo.classes;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProdutoDbAdapter {
    private Connection conn;

    public ProdutoDbAdapter(Connection conn) {
        this.conn = conn;
    }

    public boolean salvar(Produto p) {
        String sql = "INSERT INTO produtos (sku, nome_produto, descricao, valor, categoria, qtd_estoque) VALUES (?, ?, ?, ?, ?, ?);";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getSku());
            stmt.setString(2, p.getNomeProduto());
            stmt.setString(3, p.getDescricao());
            stmt.setDouble(4, p.getValor());
            stmt.setString(5, p.getCategoria().toString()); // ou .toString() dependendo do tipo na classe
            stmt.setInt(6, p.getQtdEstoque());
            
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getQtd() {
        String sql = "SELECT COUNT(*) FROM produtos;";
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
