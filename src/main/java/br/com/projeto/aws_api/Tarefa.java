package br.com.projeto.aws_api;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // Avisa ao Spring que esta classe é uma tabela no banco de dados
public class Tarefa {

    @Id // Marca este campo como a Chave Primária (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O banco de dados vai gerar o ID automaticamente
    private Long id;

    private String descricao;
    private boolean concluido;

    // Getters e Setters (necessários para o Spring funcionar)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }
}