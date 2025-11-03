package br.com.projeto.aws_api;

import org.springframework.data.jpa.repository.JpaRepository;

// Nós criamos uma interface, e o Spring vai criar uma classe que a implementa
// JpaRepository<TipoDaEntidade, TipoDoId>
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    // É só isso!
    // O Spring Data JPA vai ler o nome dos métodos e criar as queries automaticamente.
    // Por enquanto, não precisamos de nenhum método customizado.
    // Os métodos de CRUD básico (save, findAll, findById, deleteById) já estão inclusos.
}