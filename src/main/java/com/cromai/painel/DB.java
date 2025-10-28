package com.cromai.painel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
    private static final String URL = "jdbc:sqlite:painel.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void init() {
        try (Connection conn = getConnection(); Statement st = conn.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS vendedor (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nome TEXT NOT NULL" +
                    ")");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS cliente (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nome TEXT NOT NULL" +
                    ")");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS meta (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "valor_alvo REAL NOT NULL, " +
                    "prazo TEXT NOT NULL" +
                    ")");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS venda (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "valor REAL NOT NULL, " +
                    "data TEXT NOT NULL, " +
                    "vendedor_id INTEGER NOT NULL, " +
                    "cliente_id INTEGER NOT NULL, " +
                    "FOREIGN KEY (vendedor_id) REFERENCES vendedor(id), " +
                    "FOREIGN KEY (cliente_id) REFERENCES cliente(id)" +
                    ")");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inicializar banco: " + e.getMessage(), e);
        }
    }
}
