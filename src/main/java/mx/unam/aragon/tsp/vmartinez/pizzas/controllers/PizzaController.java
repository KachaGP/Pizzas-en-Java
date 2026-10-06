package mx.unam.aragon.tsp.vmartinez.pizzas.controllers;

import mx.unam.aragon.tsp.vmartinez.pizzas.dtos.PizzaDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/v1/pizzas")
public class PizzaController {

    private final List<PizzaDto> pizzas = new ArrayList<>();
    private final AtomicInteger contadorId = new AtomicInteger(1);

    public PizzaController() {
        pizzas.add(new PizzaDto(contadorId.getAndIncrement(), "Pepperoni", "Salsa de tomate, queso y pepperoni", 189.0));
        pizzas.add(new PizzaDto(contadorId.getAndIncrement(), "Hawaiana", "Salsa de tomate, queso, jamón y piña", 179.0));
    }

    @GetMapping
    public ResponseEntity<List<PizzaDto>> obtenerTodas() {
        return ResponseEntity.ok(pizzas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PizzaDto> obtenerPorId(@PathVariable int id) {
        return pizzas.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PizzaDto> crear(@RequestBody PizzaDto nuevaPizza) {
        nuevaPizza.setId(contadorId.getAndIncrement());
        pizzas.add(nuevaPizza);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPizza);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PizzaDto> actualizar(@PathVariable int id, @RequestBody PizzaDto pizzaActualizada) {
        for (int i = 0; i < pizzas.size(); i++) {
            if (pizzas.get(i).getId() == id) {
                pizzaActualizada.setId(id);
                pizzas.set(i, pizzaActualizada);
                return ResponseEntity.ok(pizzaActualizada);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        boolean eliminada = pizzas.removeIf(p -> p.getId() == id);
        return eliminada ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}