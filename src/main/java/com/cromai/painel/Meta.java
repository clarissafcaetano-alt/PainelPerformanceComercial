package com.cromai.painel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Meta {
    private Integer id;
    private double valorAlvo;
    private String prazo; // ISO yyyy-MM (ex: 2025-10)

    public Meta(Integer id, double valorAlvo, String prazo) {
        this.id = id;
        this.valorAlvo = valorAlvo;
        this.prazo = prazo;
    }

    public Integer getId() { return id; }
    public double getValorAlvo() { return valorAlvo; }
    public String getPrazo() { return prazo; }

    /** Retorna o progresso (0..100) com base na soma das vendas. */
    public double verificarProgresso() {
        String sql = "SELECT COALESCE(SUM(valor),0) FROM venda";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            double soma = rs.next() ? rs.getDouble(1) : 0.0;
            double progresso = (soma / valorAlvo) * 100.0;
            System.out.printf("Meta %.2f / Prazo %s — Vendas acumuladas: R$ %.2f — Progresso: %.2f%%%n",
                    valorAlvo, prazo, soma, progresso);
            return progresso;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar progresso: " + e.getMessage(), e);
        }
    }
}
