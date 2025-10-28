# PainelPerformanceComercial — Java 17 + Maven + SQLite

Projeto de console para demonstrar POO com classes **Vendedor**, **Cliente**, **Meta** e **Venda**, 
usando **SQLite** como persistência.

## Como executar
1) Requisitos: **Java 17** e **Maven**.
2) No terminal, dentro da pasta do projeto:
```
mvn -q clean compile
mvn -q exec:java
```
O banco `painel.db` é criado automaticamente.

## Fluxo
- Seed automático (vendedor/cliente/meta) se as tabelas estiverem vazias.
- Registra uma venda de exemplo via `Vendedor.registrarVenda(...)`.
- `Meta.verificarProgresso()` calcula o progresso com base na soma das vendas.

Ajuste livremente os atributos e regras conforme sua disciplina.
