package mx.unam.aragon.tsp.vmartinez.pizzas.controllers;

import mx.unam.aragon.tsp.vmartinez.pizzas.dtos.TamanoDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/v1/tamanos")
public class TamanoController {

    private final List<TamanoDto> tamanos = new ArrayList<>();
    private final AtomicInteger contadorId = new AtomicInteger(1);

    public TamanoController() {
        tamanos.add(new TamanoDto(contadorId.getAndIncrement(), "Individual", 4));
        tamanos.add(new TamanoDto(contadorId.getAndIncrement(), "Mediana", 8));
        tamanos.add(new TamanoDto(contadorId.getAndIncrement(), "Grande", 12));
    }

    @GetMapping
    public ResponseEntity<List<TamanoDto>> obtenerTodos() {
        return ResponseEntity.ok(tamanos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TamanoDto> obtenerPorId(@PathVariable int id) {
        return tamanos.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TamanoDto> crear(@RequestBody TamanoDto nuevoTamano) {
        nuevoTamano.setId(contadorId.getAndIncrement());
        tamanos.add(nuevoTamano);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTamano);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TamanoDto> actualizar(@PathVariable int id, @RequestBody TamanoDto tamanoActualizado) {
        for (int i = 0; i < tamanos.size(); i++) {
            if (tamanos.get(i).getId() == id) {
                tamanoActualizado.setId(id);
                tamanos.set(i, tamanoActualizado);
                return ResponseEntity.ok(tamanoActualizado);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        boolean eliminado = tamanos.removeIf(t -> t.getId() == id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}