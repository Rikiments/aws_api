package br.com.projeto.aws_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController 
@RequestMapping("/api/tarefas") 
public class TarefaController {

    @Autowired
    private TarefaRepository repository;

    @PostMapping
    public Tarefa criarTarefa(@RequestBody Tarefa tarefa) {
        return repository.save(tarefa);
    }

    // --- R (Read - Todos) ---
    @GetMapping
    public List<Tarefa> listarTarefas() {
        return repository.findAll();
    }

    // --- R (Read - Por ID) ---
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarTarefaPorId(@PathVariable Long id) {
        Optional<Tarefa> tarefa = repository.findById(id);

        if (tarefa.isPresent()) {
            return ResponseEntity.ok(tarefa.get()); 
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // --- U (Update) ---
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaDetalhes) {
        Optional<Tarefa> tarefaOptional = repository.findById(id);

        if (tarefaOptional.isPresent()) {
            Tarefa tarefaExistente = tarefaOptional.get();
            tarefaExistente.setDescricao(tarefaDetalhes.getDescricao());
            tarefaExistente.setConcluido(tarefaDetalhes.isConcluido());
            
            Tarefa tarefaAtualizada = repository.save(tarefaExistente);
            return ResponseEntity.ok(tarefaAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // --- D (Delete) ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        Optional<Tarefa> tarefaOptional = repository.findById(id);

        if (tarefaOptional.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}