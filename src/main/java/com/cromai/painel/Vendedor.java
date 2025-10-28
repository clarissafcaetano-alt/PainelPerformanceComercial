package com.cromai.painel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class Vendedor {
    private Integer id;
    private String nome;

    public Vendedor(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() { return id; }
    public String getNome() { return nome; }

    /** Registra uma venda associada ao vendedor. */
    public void registrarVenda(double valor, int clienteId) {
        String sql = "INSERT INTO venda(valor, data, vendedor_id, cliente_id) VALUES(?, ?, ?, ?)";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, valor);
            ps.setString(2, LocalDate.now().toString());
            ps.setInt(3, this.id);
            ps.setInt(4, clienteId);
            ps.executeUpdate();
            System.out.println("Venda registrada: R$ " + valor + " (Vendedor=" + nome + ", ClienteId=" + clienteId + ")");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao registrar venda: " + e.getMessage(), e);
        }
    }
}
