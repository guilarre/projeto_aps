package com.grupitxo.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.grupitxo.enums.Cargo;

// CRUD no BD
public class FuncionarioDbAdapter implements FuncionarioStorageStrategy {
    private Connection conn;

    public FuncionarioDbAdapter(Connection conn) {
        this.conn = conn;
    }

    // CREATE
    @Override
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
    
    // READ
    @Override
    public List<Funcionario> buscarTodos() {
        List<Funcionario> listaFuncionarios = new ArrayList<>();
        String sql = "SELECT * FROM funcionarios;";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // O while vai rodar enquanto tiverem linhas na tabela
            while (rs.next()) {
                int idDb = rs.getInt("id");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");
                String preferenciaComunicacao = rs.getString("preferencia_comunicacao");
                String endereco = rs.getString("endereco");
                String aniversario = rs.getString("aniversario");
                String genero = rs.getString("genero");
                Cargo cargo = Cargo.valueOf(rs.getString("cargo"));
                double salario = rs.getDouble("salario");

                Funcionario f = new Funcionario(
                    idDb, nome, cpf, telefone, email, preferenciaComunicacao, endereco, aniversario, genero, cargo, salario
                );
                
                listaFuncionarios.add(f); // Adiciona na lista temporária
            }
        } catch (SQLException e) {
            System.err.println("ERRO! Não foi possível buscar todos os funcionários: " + e.getMessage());
        }

        return listaFuncionarios; // Retorna a lista (pode estar vazia, mas não nula) 
    }
    
    @Override
    public Funcionario buscarPorId(int id) {
        String sql = "SELECT * FROM funcionarios WHERE id == ?;";
        Funcionario funcionarioEncontrado = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int idDb = rs.getInt("id");
                    String nome = rs.getString("nome");
                    String cpf = rs.getString("cpf");
                    String telefone = rs.getString("telefone");
                    String email = rs.getString("email");
                    String preferenciaComunicacao = rs.getString("preferencia_comunicacao");
                    String endereco = rs.getString("endereco");
                    String aniversario = rs.getString("aniversario");
                    String genero = rs.getString("genero");
                    // pra poder passar de TEXT (tipo do sql) pra o ENUM cargo (do java)
                    String cargoString = rs.getString("cargo");
                    Cargo cargo = Cargo.valueOf(cargoString);
                    double salario = rs.getDouble("salario");
        
                    funcionarioEncontrado = new Funcionario(
                        id, nome, cpf, telefone, email, preferenciaComunicacao, endereco, aniversario, genero, cargo, salario
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("ERRO! Não foi possível buscar funcionário por ID: " + e.getMessage());
            // e.printStackTrace();
        }
        return funcionarioEncontrado;
    }
    
    @Override
    public Funcionario buscarPorNome(String nomeBusca) {
        // usar LIKE em pesquisa de nomes, juntamente com as wildcards % ao redor do nome pesquisado
        String sql = "SELECT * FROM funcionarios WHERE nome LIKE ?;";
        Funcionario funcionarioEncontrado = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // inserção dos % ao redor do nome
            stmt.setString(1, "%" + nomeBusca + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int idDb = rs.getInt("id");
                    String nome = rs.getString("nome");
                    String cpf = rs.getString("cpf");
                    String telefone = rs.getString("telefone");
                    String email = rs.getString("email");
                    String preferenciaComunicacao = rs.getString("preferencia_comunicacao");
                    String endereco = rs.getString("endereco");
                    String aniversario = rs.getString("aniversario");
                    String genero = rs.getString("genero");
                    // pra poder passar de TEXT (tipo do sql) pra o ENUM cargo (do java)
                    String cargoString = rs.getString("cargo");
                    Cargo cargo = Cargo.valueOf(cargoString);
                    double salario = rs.getDouble("salario");
        
                    funcionarioEncontrado = new Funcionario(
                        idDb, nome, cpf, telefone, email, preferenciaComunicacao, endereco, aniversario, genero, cargo, salario
                    );
                }                
            }
        } catch (SQLException e) {
            System.err.println("ERRO! Não foi possível buscar funcionário por NOME: " + e.getMessage());
            // e.printStackTrace();
        }
        return funcionarioEncontrado;
    }

    // UPDATE
    @Override
    public Funcionario atualizar(Funcionario f) {
        String sql = "UPDATE funcionarios SET " +
        "nome = ?, " +
        "cpf = ?, " + 
        "telefone = ?, " + 
        "email = ?, " + 
        "preferencia_comunicacao = ?, " + 
        "endereco = ?, " + 
        "aniversario = ?, " + 
        "genero = ?, " + 
        "cargo = ?, " + 
        "salario = ?" +
        "WHERE id = ?;";
    
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getCpf());
            stmt.setString(3, f.getTelefone());
            stmt.setString(4, f.getEmail());
            stmt.setString(5, f.getPreferenciaComunicacao());
            stmt.setString(6, f.getEndereco());
            stmt.setString(7, f.getAniversario());
            stmt.setString(8, f.getGenero());
            stmt.setString(9, f.getCargo().toString()); 
            stmt.setDouble(10, f.getSalario());
            stmt.setInt(11, f.getIdFuncionario());

            // Executa o update no banco
            int linhasAfetadas = stmt.executeUpdate();
            
            // Se alterou alguma linha, significa que o ID existia e deu tudo certo
            if (linhasAfetadas > 0) {
                return f; 
            }
        } catch (SQLException e) {
            System.err.println("ERRO! Não foi possível atualizar o funcionário: " + e.getMessage());
        }
        // se funcionario n for encontrado ou houver algum outro erro
        return null;
    }

    // DELETE
    @Override
    public boolean remover(Funcionario f) {
        String sql = "DELETE FROM funcionarios WHERE id = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, f.getIdFuncionario());

            // Executa a exclusão
            int linhasAfetadas = stmt.executeUpdate();

            // Se linhasAfetadas > 0, significa que o registro existia e foi apagado
            if (linhasAfetadas > 0) {
                return true; // se apagou, true
            }
        } catch (SQLException e) {
            System.err.println("ERRO! Não foi possível remover o funcionário: " + e.getMessage());
        }
        
        // Retorna false se o funcionário não foi encontrado ou se deu algum erro no SQL
        return false;
    }

    // Auxiliares
    @Override
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
