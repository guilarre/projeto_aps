package com.grupitxo.classes;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfig {
    // Caminho pra pasta do banco de dados
    private static final String URL = "jdbc:sqlite:banco/sistema.db";

    public static Connection getConnection() throws SQLException {
        File pastaBanco = new File("banco");
        if (!pastaBanco.exists()) {
            pastaBanco.mkdirs();  //cria pasta banco se n existir
        }
        
        // Conecta ao banco. Se o arquivo sistema.db não existir, ele é criado agora.
        return DriverManager.getConnection(URL);
    }

    public static void inicializarBanco() {
        String sqlFuncionario = "CREATE TABLE IF NOT EXISTS funcionario (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "nome TEXT NOT NULL, " +
                                "cpf TEXT NOT NULL, " +
                                "telefone TEXT NOT NULL, " +
                                "email TEXT NOT NULL, " +
                                "preferencia_comunicacao TEXT NOT NULL, " +
                                "endereco TEXT NOT NULL, " +
                                "aniversario TEXT NOT NULL, " +
                                "genero TEXT NOT NULL, " +
                                "cargo TEXT NOT NULL, " +
                                "salario REAL NOT NULL" +
                                ");";

        try (Connection conn = getConnection();
            Statement stmt = conn.createStatement()) {
                
            stmt.execute(sqlFuncionario);

            System.out.println("Banco de dados e tabelas inicializadas com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inicializar o banco de dadoos: " + e.getMessage());
        }
    }
}