package com.cromai.painel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        DB.init();
        seedIfEmpty();
        System.out.println("=== Painel de Performance Comercial (console) ===");

        Vendedor vendedor = findVendedorByNome("Vendedor Exemplo");
        Cliente cliente = findClienteByNome("Cliente Exemplo");
        Meta meta = findMetaAtual();

        // Registrar uma venda de exemplo
        vendedor.registrarVenda(1500.00, cliente.getId());

        // Verificar progresso da meta
        meta.verificarProgresso();

        System.out.println("Execução concluída.");
    }

    private static void seedIfEmpty() {
        try (Connection conn = DB.getConnection()) {
            if (isTableEmpty(conn, "vendedor")) {
                try (PreparedStatement ps = conn.prepareStatement("INSERT INTO vendedor(nome) VALUES (?)")) {
                    ps.setString(1, "Vendedor Exemplo");
                    ps.executeUpdate();
                }
            }
            if (isTableEmpty(conn, "cliente")) {
                try (PreparedStatement ps = conn.prepareStatement("INSERT INTO cliente(nome) VALUES (?)")) {
                    ps.setString(1, "Cliente Exemplo");
                    ps.executeUpdate();
                }
            }
            if (isTableEmpty(conn, "meta")) {
                try (PreparedStatement ps = conn.prepareStatement("INSERT INTO meta(valor_alvo, prazo) VALUES (?, ?)")) {
                    ps.setDouble(1, 10000.00);
                    ps.setString(2, "2025-10");
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isTableEmpty(Connection conn, String table) throws SQLException {
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM " + table)) {
            return rs.next() && rs.getInt(1) == 0;
        }
    }

    private static Vendedor findVendedorByNome(String nome) {
        String sql = "SELECT id, nome FROM vendedor WHERE nome=? LIMIT 1";
        try (Connection conn = DB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nome);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Vendedor(rs.getInt("id"), rs.getString("nome"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Vendedor não encontrado: " + nome);
    }

    private static Cliente findClienteByNome(String nome) {
        String sql = "SELECT id, nome FROM cliente WHERE nome=? LIMIT 1";
        try (Connection conn = DB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nome);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Cliente(rs.getInt("id"), rs.getString("nome"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Cliente não encontrado: " + nome);
    }

    private static Meta findMetaAtual() {
        String sql = "SELECT id, valor_alvo, prazo FROM meta ORDER BY id DESC LIMIT 1";
        try (Connection conn = DB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Meta(rs.getInt("id"), rs.getDouble("valor_alvo"), rs.getString("prazo"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Nenhuma meta cadastrada.");
    }
}
