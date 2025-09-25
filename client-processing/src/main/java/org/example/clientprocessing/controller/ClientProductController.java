package org.example.clientprocessing.controller;

import lombok.RequiredArgsConstructor;
import org.example.clientprocessing.model.dto.*;
import org.example.clientprocessing.service.ClientProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client-products")
@RequiredArgsConstructor
public class ClientProductController {

    private final ClientProductService clientProductService;

    @PostMapping
    public ResponseEntity<ClientProductResponseDTO> addProduct(@RequestBody ClientProductRequestDTO dto) {
        return ResponseEntity.ok(clientProductService.addProductToClient(dto));
    }

    @GetMapping
    public ResponseEntity<List<ClientProductResponseDTO>> getAll() {
        return ResponseEntity.ok(clientProductService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientProductResponseDTO> get(@PathVariable Long clientId) {
        return ResponseEntity.ok(clientProductService.getById(clientId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientProductResponseDTO> update (@PathVariable Long id, @RequestBody ClientProductRequestDTO dto) {
        return ResponseEntity.ok(clientProductService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        clientProductService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<Void> closeProduct(@PathVariable Long id) {
        clientProductService.closeClientProduct(id);
        return ResponseEntity.noContent().build();
    }


}
