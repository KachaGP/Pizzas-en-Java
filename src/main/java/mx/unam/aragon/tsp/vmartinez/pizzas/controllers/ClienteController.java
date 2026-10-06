package mx.unam.aragon.tsp.vmartinez.pizzas.controllers;

import mx.unam.aragon.tsp.vmartinez.pizzas.dtos.ClienteDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private final List<ClienteDto> clientes = new ArrayList<>();
    private final AtomicInteger contadorId = new AtomicInteger(1);

    public ClienteController() {
        clientes.add(new ClienteDto(
                contadorId.getAndIncrement(),
                "Juan Perez",
                "5512345678",
                "Av. Universidad 3000"
        ));

        clientes.add(new ClienteDto(
                contadorId.getAndIncrement(),
                "Maria Lopez",
                "5587654321",
                "Av. Insurgentes 120"
        ));
    }

    @GetMapping
    public ResponseEntity<List<ClienteDto>> obtenerTodos() {
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> obtenerPorId(@PathVariable int id) {
        return clientes.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClienteDto> crear(
            @RequestBody ClienteDto nuevoCliente) {

        nuevoCliente.setId(contadorId.getAndIncrement());

        clientes.add(nuevoCliente);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevoCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> actualizar(
            @PathVariable int id,
            @RequestBody ClienteDto clienteActualizado) {

        for (int i = 0; i < clientes.size(); i++) {

            if (clientes.get(i).getId() == id) {

                clienteActualizado.setId(id);

                clientes.set(i, clienteActualizado);

                return ResponseEntity.ok(clienteActualizado);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable int id) {

        boolean eliminado =
                clientes.removeIf(c -> c.getId() == id);

        return eliminado
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}