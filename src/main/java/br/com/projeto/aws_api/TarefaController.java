package br.com.projeto.aws_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Marca esta classe como um Controller REST
@RequestMapping("/api/tarefas") // Todas as requisições que começarem com /api/tarefas virão para cá
public class TarefaController {

    // @Autowired faz a "Injeção de Dependência"
    // Basicamente, pede ao Spring: "Por favor, me dê uma instância de TarefaRepository"
    @Autowired
    private TarefaRepository repository;

    // --- C (Create) ---
    // Mapeia requisições HTTP POST para /api/tarefas
    @PostMapping
    public Tarefa criarTarefa(@RequestBody Tarefa tarefa) {
        // @RequestBody avisa ao Spring para pegar o JSON do corpo da requisição
        // e transformar em um objeto Tarefa
        return repository.save(tarefa);
    }

    // --- R (Read - Todos) ---
    // Mapeia requisições HTTP GET para /api/tarefas
    @GetMapping
    public List<Tarefa> listarTarefas() {
        return repository.findAll();
    }

    // --- R (Read - Por ID) ---
    // Mapeia requisições HTTP GET para /api/tarefas/1 (ou /2, /3, etc.)
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarTarefaPorId(@PathVariable Long id) {
        // @PathVariable pega o {id} da URL e passa como variável
        Optional<Tarefa> tarefa = repository.findById(id);

        if (tarefa.isPresent()) {
            return ResponseEntity.ok(tarefa.get()); // Retorna 200 OK com a tarefa
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found
        }
    }

    // --- U (Update) ---
    // Mapeia requisições HTTP PUT para /api/tarefas/1
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
    // Mapeia requisições HTTP DELETE para /api/tarefas/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        Optional<Tarefa> tarefaOptional = repository.findById(id);

        if (tarefaOptional.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content (sucesso, sem corpo)
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}