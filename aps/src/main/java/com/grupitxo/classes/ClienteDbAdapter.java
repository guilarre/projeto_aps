package com.grupitxo.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// CRUD no BD para Cliente
public class ClienteDbAdapter implements ClienteStorageStrategy {
    private Connection conn;

    public ClienteDbAdapter(Connection conn) {
        this.conn = conn;
    }

    // CREATE
    @Override
    public boolean salvar(Cliente c) {
        String sql = "INSERT INTO clientes (nome, cpf, telefone, email, preferencia_comunicacao, endereco, aniversario, genero) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getCpf());
            stmt.setString(3, c.getTelefone());
            stmt.setString(4, c.getEmail());
            stmt.setString(5, c.getPreferenciaComunicacao());
            stmt.setString(6, c.getEndereco());
            stmt.setString(7, c.getAniversario());
            stmt.setString(8, c.getGenero());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ - buscar todos
    @Override
    public List<Cliente> buscarTodos() {
        List<Cliente> listaClientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes;";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                listaClientes.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("ERRO! Não foi possível buscar todos os clientes: " + e.getMessage());
        }

        return listaClientes;
    }

    // READ - buscar por ID
    @Override
    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id == ?;";
        Cliente clienteEncontrado = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    clienteEncontrado = mapearResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("ERRO! Não foi possível buscar cliente por ID: " + e.getMessage());
        }
        return clienteEncontrado;
    }

    // READ - buscar por nome (LIKE)
    @Override
    public Cliente buscarPorNome(String nomeBusca) {
        String sql = "SELECT * FROM clientes WHERE nome LIKE ?;";
        Cliente clienteEncontrado = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nomeBusca + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    clienteEncontrado = mapearResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("ERRO! Não foi possível buscar cliente por nome: " + e.getMessage());
        }
        return clienteEncontrado;
    }

    // UPDATE
    @Override
    public Cliente atualizar(Cliente c) {
        String sql = "UPDATE clientes SET " +
                     "nome = ?, " +
                     "cpf = ?, " +
                     "telefone = ?, " +
                     "email = ?, " +
                     "preferencia_comunicacao = ?, " +
                     "endereco = ?, " +
                     "aniversario = ?, " +
                     "genero = ? " +
                     "WHERE id = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getCpf());
            stmt.setString(3, c.getTelefone());
            stmt.setString(4, c.getEmail());
            stmt.setString(5, c.getPreferenciaComunicacao());
            stmt.setString(6, c.getEndereco());
            stmt.setString(7, c.getAniversario());
            stmt.setString(8, c.getGenero());
            stmt.setInt(9, c.getIdCliente());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                return c;
            }
        } catch (SQLException e) {
            System.err.println("ERRO! Não foi possível atualizar o cliente: " + e.getMessage());
        }
        return null;
    }

    // DELETE
    @Override
    public boolean remover(Cliente c) {
        String sql = "DELETE FROM clientes WHERE id = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, c.getIdCliente());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("ERRO! Não foi possível remover o cliente: " + e.getMessage());
        }
        return false;
    }

    // Auxiliares
    @Override
    public int getQtd() {
        String sql = "SELECT COUNT(*) FROM clientes;";
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

    private Cliente mapearResultSet(ResultSet rs) throws SQLException {
        int idDb        = rs.getInt("id");
        String nome     = rs.getString("nome");
        String cpf      = rs.getString("cpf");
        String telefone = rs.getString("telefone");
        String email    = rs.getString("email");
        String prefCom  = rs.getString("preferencia_comunicacao");
        String endereco = rs.getString("endereco");
        String aniv     = rs.getString("aniversario");
        String genero   = rs.getString("genero");

        return new Cliente(idDb, nome, cpf, telefone, email, prefCom, endereco, aniv, genero, false);
    }
}
