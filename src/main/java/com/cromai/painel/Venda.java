package com.cromai.painel;

import java.time.LocalDate;

public class Venda {
    private Integer id;
    private double valor;
    private LocalDate data;
    private Integer vendedorId;
    private Integer clienteId;

    public Venda(Integer id, double valor, LocalDate data, Integer vendedorId, Integer clienteId) {
        this.id = id;
        this.valor = valor;
        this.data = data;
        this.vendedorId = vendedorId;
        this.clienteId = clienteId;
    }

    public Integer getId() { return id; }
    public double getValor() { return valor; }
    public LocalDate getData() { return data; }
    public Integer getVendedorId() { return vendedorId; }
    public Integer getClienteId() { return clienteId; }
}
