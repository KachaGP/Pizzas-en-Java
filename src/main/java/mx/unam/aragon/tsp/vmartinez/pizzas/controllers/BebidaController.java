package mx.unam.aragon.tsp.vmartinez.pizzas.controllers;

import mx.unam.aragon.tsp.vmartinez.pizzas.dtos.BebidaDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/v1/bebidas")
public class BebidaController {

    private final List<BebidaDto> bebidas = new ArrayList<>();
    private final AtomicInteger contadorId = new AtomicInteger(1);

    public BebidaController() {
        bebidas.add(new BebidaDto(contadorId.getAndIncrement(), "Refresco Cola", "600 ml", 35.0));
        bebidas.add(new BebidaDto(contadorId.getAndIncrement(), "Agua Natural", "1 L", 25.0));
    }

    @GetMapping
    public ResponseEntity<List<BebidaDto>> obtenerTodas() {
        return ResponseEntity.ok(bebidas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BebidaDto> obtenerPorId(@PathVariable int id) {
        return bebidas.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BebidaDto> crear(@RequestBody BebidaDto nuevaBebida) {
        nuevaBebida.setId(contadorId.getAndIncrement());
        bebidas.add(nuevaBebida);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaBebida);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BebidaDto> actualizar(@PathVariable int id, @RequestBody BebidaDto bebidaActualizada) {
        for (int i = 0; i < bebidas.size(); i++) {
            if (bebidas.get(i).getId() == id) {
                bebidaActualizada.setId(id);
                bebidas.set(i, bebidaActualizada);
                return ResponseEntity.ok(bebidaActualizada);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        boolean eliminada = bebidas.removeIf(b -> b.getId() == id);
        return eliminada ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}